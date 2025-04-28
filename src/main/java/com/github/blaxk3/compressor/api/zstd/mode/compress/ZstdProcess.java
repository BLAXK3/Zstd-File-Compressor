package com.github.blaxk3.compressor.api.zstd.mode.compress;

import com.github.blaxk3.compressor.api.zstd.dictionary.trainer.DictTrainer;
import com.github.blaxk3.compressor.ui.windows.dialog.SettingDialog;
import com.github.blaxk3.compressor.ui.windows.optionpane.AlertMessage;
import com.github.luben.zstd.Zstd;
import com.github.luben.zstd.ZstdDictCompress;
import com.github.luben.zstd.ZstdDictDecompress;
import com.github.luben.zstd.ZstdException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;

public class ZstdProcess {

    public ZstdProcess(File path, int level, File dict, String mode) {
        boolean success = false;
        try {
            if (path.isDirectory()) {
                File[] files = path.listFiles(File::isFile);
                if (files != null) {
                    for (File file : files) {
                        success |= processFile(file, level, dict, mode);
                    }
                }
            } else {
                success = processFile(path, level, dict, mode);
            }
        } finally {
            if (success) {
                switch (mode) {
                    case "Compress" -> AlertMessage.complete("compressed");
                    case "Decompress" -> AlertMessage.complete("decompressed");
                }
            }
        }
    }

    private boolean processFile(File file, int level, File dict, String mode) {
        switch (mode) {
            case "Compress" -> {
                if (dict != null && dict.exists()) {
                    dictCompress(file, dict, level);
                } else {
                    compress(file, level);
                }
                return true;
            }
            case "Decompress" -> {
                if (dict != null && dict.exists()) {
                    dictDecompress(file, dict);
                } else {
                    decompress(file);
                }
                return true;
            }
            default -> {
                new DictTrainer(file, SettingDialog.getTextFieldDictSize(), level, dict);
                return false;
            }
        }
    }

    public void compress(File path, int level) {
        try (FileInputStream fis = new FileInputStream(path);
             FileChannel inputChannel = fis.getChannel()) {

            ByteBuffer inputBuffer = ByteBuffer.allocateDirect((int) inputChannel.size());
            while (inputBuffer.hasRemaining()) {
                inputChannel.read(inputBuffer);
            }
            inputBuffer.flip();

            ByteBuffer compressedBuffer = Zstd.compress(inputBuffer, level);

            try (FileOutputStream fos = new FileOutputStream(path);
                 FileChannel outputChannel = fos.getChannel()) {

                compressedBuffer.position(0);
                while (compressedBuffer.hasRemaining()) {
                    outputChannel.write(compressedBuffer);
                }
            }

        } catch (ZstdException | IOException e) {
            AlertMessage.failed("compress", path.getName());
        }
    }


    public void decompress(File path) {
        if (!checkMagicNumber(path)) {
            AlertMessage.failed("decompress", path.getName());
            return;
        }

        try (FileInputStream fis = new FileInputStream(path);
             FileChannel inputChannel = fis.getChannel()) {

            ByteBuffer inputBuffer = ByteBuffer.allocateDirect((int) inputChannel.size());
            while (inputBuffer.hasRemaining()) {
                inputChannel.read(inputBuffer);
            }
            inputBuffer.flip();

            long decompressedSize = Zstd.getFrameContentSize(inputBuffer);
            if (decompressedSize <= 0 || decompressedSize > Integer.MAX_VALUE) {
                AlertMessage.failed("decompress", path.getName());
                return;
            }

            ByteBuffer decompressedBuffer = Zstd.decompress(inputBuffer, (int) decompressedSize);

            try (FileOutputStream fos = new FileOutputStream(path);
                 FileChannel outputChannel = fos.getChannel()) {
                outputChannel.write(decompressedBuffer);
            }

        } catch (ZstdException | IOException e) {
            AlertMessage.failed("decompress", path.getName());
        }
    }

    public void dictCompress(File path, File dict, int level) {
        try {
            byte[] dictBytes = Files.readAllBytes(dict.toPath());
            ByteBuffer dictBuffer = ByteBuffer.allocateDirect(dictBytes.length);
            dictBuffer.put(dictBytes);
            dictBuffer.flip();

            try (ZstdDictCompress dictCompress = new ZstdDictCompress(dictBuffer, level);
                 FileInputStream fis = new FileInputStream(path);
                 FileChannel inputChannel = fis.getChannel()) {

                ByteBuffer inputBuffer = ByteBuffer.allocateDirect((int) inputChannel.size());
                while (inputBuffer.hasRemaining()) {
                    inputChannel.read(inputBuffer);
                }
                inputBuffer.flip();

                int maxCompressedSize = (int) Zstd.compressBound(inputBuffer.remaining());
                ByteBuffer compressedBuffer = ByteBuffer.allocateDirect(maxCompressedSize);

                long compressedSize = Zstd.compress(compressedBuffer, inputBuffer, dictCompress);

                compressedBuffer.limit((int) compressedSize);
                compressedBuffer.position(0);

                try (FileOutputStream fos = new FileOutputStream(path);
                     FileChannel outputChannel = fos.getChannel()) {
                    while (compressedBuffer.hasRemaining()) {
                        outputChannel.write(compressedBuffer);
                    }
                }
            }
        } catch (IOException e) {
            AlertMessage.failed("compress", path.getName());
        }
    }

    public void dictDecompress(File path, File dict) {
        if (!checkMagicNumber(path)) {
            AlertMessage.failed("decompress", path.getName());
            return;
        }

        try {
            byte[] dictBytes = Files.readAllBytes(dict.toPath());
            ByteBuffer dictBuffer = ByteBuffer.allocateDirect(dictBytes.length);
            dictBuffer.put(dictBytes);
            dictBuffer.flip();

            try (ZstdDictDecompress dictDecompress = new ZstdDictDecompress(dictBuffer);
                 FileInputStream fis = new FileInputStream(path);
                 FileChannel inputChannel = fis.getChannel()) {

                ByteBuffer inputBuffer = ByteBuffer.allocateDirect((int) inputChannel.size());
                while (inputBuffer.hasRemaining()) {
                    inputChannel.read(inputBuffer);
                }
                inputBuffer.flip();

                long decompressedSize = Zstd.getFrameContentSize(inputBuffer);
                if (decompressedSize <= 0 || decompressedSize > Integer.MAX_VALUE) {
                    AlertMessage.failed("decompress", path.getName());
                    return;
                }

                ByteBuffer decompressedBuffer = Zstd.decompress(inputBuffer, dictDecompress, (int) decompressedSize);

                try (FileOutputStream fos = new FileOutputStream(path);
                     FileChannel outputChannel = fos.getChannel()) {
                    outputChannel.write(decompressedBuffer);
                }
            }

        } catch (IOException | ZstdException e) {
            AlertMessage.failed("decompress", path.getName());
        }
    }

    public boolean checkMagicNumber(File file) {
        byte[] magic = {(byte) 0x28, (byte) 0xB5, (byte) 0x2F, (byte) 0xFD};
        try (FileInputStream fis = new FileInputStream(file)) {
            byte[] header = new byte[4];
            if (fis.read(header) != 4) {
                return false;
            }
            for (int i = 0; i < 4; i++) {
                if (header[i] != magic[i]) {
                    return false;
                }
            }
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
