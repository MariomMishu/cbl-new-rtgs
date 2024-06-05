package com.cbl.cityrtgs.filereadwrite.write;

import com.cbl.cityrtgs.models.dto.message.MessageDefinitionIdentifier;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileWriteService {
    public String fileWriteForOutWard(String data, String fileName) {
        Path folder1 = Path.of("E:\\rtgs\\input\\");
        // Specify the folder and file name
        // Create the file object
        File file = null;
        file = new File(folder1 + "/" + fileName + ".xml");
        try {
            // Create the directories if they don't exist
            Path parentDirectory = file.toPath().getParent();
            if (!Files.exists(parentDirectory)) {
                Files.createDirectories(parentDirectory);
            }

            // Write the content to the file
            FileWriter writer = new FileWriter(file);
            writer.write(data);
            writer.close();

            System.out.println("File written successfully.");
        } catch (IOException e) {
            System.out.println("An error occurred while writing the file.");
            e.printStackTrace();
            throw new RuntimeException("An error occurred while writing the file.");
        }
        return "OK";
    }

    public String fileWriteTest(String data, String fileName) {
        Path folder1 = Path.of("E:\\rtgs\\output\\txn\\");
        Path folder2 = Path.of("E:\\rtgs\\output\\additional\\");
        Path folder3 = Path.of("E:\\rtgs\\output\\statement\\");
        // Specify the folder and file name
        // Create the file object
        File file;
        if (data.contains(MessageDefinitionIdentifier.PACS008.value()) || data.contains(MessageDefinitionIdentifier.PACS009.value()) || data.contains(MessageDefinitionIdentifier.PACS004.value())) {
            file = new File(folder1 + "/" + fileName + ".xml");
        } else if (data.contains(MessageDefinitionIdentifier.PACS002.value()) || data.contains(MessageDefinitionIdentifier.CAMT054.value()) || data.contains(MessageDefinitionIdentifier.CAMT025.value()) || data.contains(MessageDefinitionIdentifier.CAMT019.value())) {
            file = new File(folder2 + "/" + fileName + ".xml");
        } else if (data.contains(MessageDefinitionIdentifier.CAMT052.value()) || data.contains(MessageDefinitionIdentifier.CAMT053.value())) {
            file = new File(folder3 + "/" + fileName + ".xml");
        } else {
            throw new RuntimeException("Invalid File or Data.");
        }

        try {
            // Create the directories if they don't exist
            Path parentDirectory = file.toPath().getParent();
            if (!Files.exists(parentDirectory)) {
                Files.createDirectories(parentDirectory);
            }

            // Write the content to the file
            FileWriter writer = new FileWriter(file);
            writer.write(data);
            writer.close();
            System.out.println("File written successfully.");
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("An error occurred while writing the file.");
        }
        return "OK";
    }

}
