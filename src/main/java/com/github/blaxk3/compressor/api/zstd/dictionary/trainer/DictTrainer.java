package com.github.blaxk3.compressor.api.zstd.dictionary.trainer;

import com.github.blaxk3.compressor.ui.windows.optionpane.AlertMessage;
import com.github.luben.zstd.ZstdDictTrainer;
import com.github.luben.zstd.ZstdException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class DictTrainer {

    public DictTrainer(File path, int dictSize, int level, File output) {
        try {
            List<Path> sampleFiles = new ArrayList<>();

            File[] files = path.listFiles(File::isFile);
            assert files != null;
            for (File file : files) {
                sampleFiles.add(file.toPath());
            }

            ZstdDictTrainer trainer = new ZstdDictTrainer(dictSize * 100, dictSize * 1024, level);

            for (Path file : sampleFiles) {
                byte[] data = Files.readAllBytes(file);
                trainer.addSample(data);
            }

            byte[] dict = trainer.trainSamples();

            if (output == null) {
                Files.write(path.toPath().resolve("trained_dictionary.dict"), dict);
                return;
            }

            Files.write(output.toPath().resolve("trained_dictionary.dict"), dict);

        } catch (ZstdException | IOException e) {
            AlertMessage.failedTraining();
        }
    }
}
