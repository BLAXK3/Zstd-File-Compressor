package com.github.blaxk3.compressor.api.zstd.mode.compress;

import com.github.blaxk3.compressor.api.zstd.dictionary.trainer.DictTrainer;
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
        if (path.isDirectory()) {
            File[] files = path.listFiles(File::isFile);
            if (files != null) {
                for (File file : files) {
                    switch (mode) {
                        case "Compress" -> {
                            if (dict.exists()) {
                                dictCompress(file, dict, level);
                                return;
                            }
                            compress(file, level);
                        }
                        case "Decompress" -> {
                            if (dict.exists()) {
                                dictDecompress(file);
                                return;
                            }
                            decompress(file);
                        }
                        default -> {
                            new DictTrainer(path);
                        }
                    }
                }
            }
        } else {
            switch (mode) {
                case "Compress" -> {
                    if (dict.exists()) {
                        dictCompress(path, dict, level);
                        return;
                    }
                    compress(path, level);
                }
                case "Decompress" -> {
                    if (dict.exists()) {
                        dictDecompress(path);
                        return;
                    }
                    decompress(path);
                }
            }

        }
        switch (mode) {
            case "Compress" -> AlertMessage.complete("compressed");
            case "Decompress" -> AlertMessage.complete("decompressed");
        }
    }

    public void compress(File path, int level) {
        try (FileInputStream fis = new FileInputStream(path)) {
            FileChannel channel = fis.getChannel();
            ByteBuffer buffer = ByteBuffer.allocateDirect((int) channel.size());
            channel.read(buffer);
            buffer.flip();
            fis.close();
            ByteBuffer compressedBuffer = Zstd.compress(buffer, level);
            FileOutputStream fos = new FileOutputStream(path);
            FileChannel output = fos.getChannel();
            output.write(compressedBuffer);
        }
        catch (ZstdException | IOException f) {
            AlertMessage.failed("compress", path.getName());
        }
    }

    public void decompress(File path) {
        if (checkMagicNumber(path)) {
            try (FileInputStream fis = new FileInputStream(path)) {
                FileChannel channel = fis.getChannel();
                ByteBuffer buffer = ByteBuffer.allocateDirect((int) channel.size());
                channel.read(buffer);
                buffer.flip();
                fis.close();
                long decompressedSize = Zstd.getFrameContentSize(buffer);
//                if (decompressedSize <= 0 || decompressedSize > Integer.MAX_VALUE) {
//                    System.out.println("hello");
//                    AlertMessage.failed("decompress", path.getName());
//                    return;
//                }
                ByteBuffer decompressedBuffer = Zstd.decompress(buffer, (int) decompressedSize);
                FileOutputStream fos = new FileOutputStream(path);
                fos.getChannel().write(decompressedBuffer);
            }

            catch (ZstdException | IOException f) {
                System.out.println("test");
                AlertMessage.failed("decompress", path.getName());
            }
//            return;
        }
//        AlertMessage.failed("decompress", path.getName());
    }

    public void dictCompress(File path, File dict, int level) {
        try {
            byte[] dictBytes = Files.readAllBytes(dict.toPath());
            ByteBuffer dictBuffer = ByteBuffer.allocateDirect(dictBytes.length);
            dictBuffer.put(dictBytes);
            dictBuffer.flip();

            ZstdDictCompress dictCompress = new ZstdDictCompress(dictBuffer, level);

            FileInputStream fis = new FileInputStream(path);
            FileChannel inputChannel = fis.getChannel();
            ByteBuffer inputBuffer = ByteBuffer.allocateDirect((int) inputChannel.size());
            inputChannel.read(inputBuffer);
            inputBuffer.flip();
            fis.close();

            int maxCompressedSize = (int) Zstd.compressBound((int) inputChannel.size());
            ByteBuffer compressedBuffer = ByteBuffer.allocateDirect(maxCompressedSize);

            long compressedSize = Zstd.compress(compressedBuffer, inputBuffer, dictCompress);

            FileOutputStream fos = new FileOutputStream(path);
            FileChannel outputChannel = fos.getChannel();
            compressedBuffer.limit((int) compressedSize);
            outputChannel.write(compressedBuffer);
            fos.close();

            dictCompress.close();
        }
        catch (IOException f) {
            AlertMessage.failed("compress", path.getName());
        }
    }

    public void dictDecompress(File path) {

    }

    public boolean checkMagicNumber(File file) {
        byte[] magic = {
                (byte) 0x28,
                (byte) 0xB5,
                (byte) 0x2F,
                (byte) 0xFD
        };
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