package com.github.blaxk3.compressor.api.zstd.dictionary.trainer;

import com.github.blaxk3.compressor.ui.windows.optionpane.AlertMessage;
import com.github.luben.zstd.ZstdDictTrainer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class DictTrainer {

    public DictTrainer(File path) {
        try {
            List<Path> sampleFiles = new ArrayList<>();

            if (path.isDirectory()) {
                File[] files = path.listFiles(File::isFile);
                assert files != null;
                for (File file : files) {
                    sampleFiles.add(file.toPath());
                }
            }
            else {
                sampleFiles.add(path.toPath());
            }

            int dictSize = 10 * 1024;
            int sampleSize = dictSize * 100;
            int level = 5;

            ZstdDictTrainer trainer = new ZstdDictTrainer(sampleSize, dictSize, level);

            for (Path file : sampleFiles) {
                byte[] data = Files.readAllBytes(file);
                trainer.addSample(data);
            }

            byte[] dict = trainer.trainSamples();

            Path outputDir = path.isDirectory() ? path.toPath() : path.getParentFile().toPath();
            Path dictOutput = outputDir.resolve("trained_dictionary.dict");

            Files.write(dictOutput, dict);
        } catch (IOException e) {
            AlertMessage.failedTraining();
        }
    }
}

