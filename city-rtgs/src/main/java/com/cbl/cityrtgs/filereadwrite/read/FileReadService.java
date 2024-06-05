package com.cbl.cityrtgs.filereadwrite.read;

import com.cbl.cityrtgs.common.enums.FileReadWriteEnum;
import com.cbl.cityrtgs.common.utility.DateUtility;
import com.cbl.cityrtgs.mapper.message.InwardTransactionHandlerService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileReadService {
    private final InwardTransactionHandlerService inwardHandle;


    public String fileReadStatement(String readType) {
        try { // Define the folders to watch
            Path folder1 = Path.of("E:\\rtgs\\output\\txn\\");
            Path folder2 = Path.of("E:\\rtgs\\output\\additional\\");
            Path folder3 = Path.of("E:\\rtgs\\output\\statement\\");
            // Create a WatchService
            WatchService watchService = FileSystems.getDefault().newWatchService();

            // Register the folders with the WatchService for creation events
            folder1.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);
            folder2.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);
            folder3.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);

            // Start an infinite loop to watch for events
            while (true) {
                if (readType.equals(FileReadWriteEnum.MANUAL.name())) {
                    // Check for new files even if watcher is temporarily disabled
                    List<Path> newFiles = checkForNewFiles(folder1, folder2, folder3);
                    if (!newFiles.isEmpty()) {
                        // Handle new files
                        //   System.out.println("New files detected:");
                        for (Path file : newFiles) {
                            System.out.println(file);
                            // Read The File
                            readAndPrintFileContents(file.toString());
                        }
                    } else {
                        throw new RuntimeException("No File Found.");
                    }
                    break;
                }

                // Wait for key to be signaled
                WatchKey key = watchService.take();

                // Process events
                for (WatchEvent<?> event : key.pollEvents()) {
                    // Check if the event is a new file creation event
                    if (event.kind() == StandardWatchEventKinds.ENTRY_CREATE) {
                        // Get the file name from the event context
                        Path createdFile = (Path) event.context();
                        System.out.println("New file created: " + createdFile);
                        // Get the directory path being watched
                        Path watchedDir = (Path) key.watchable();
                        // Construct the full path to the new file
                        Path filePath = watchedDir.resolve(createdFile);

                        // Check which folder the file belongs to
                        if (filePath.startsWith(folder1)) {
                            System.out.println("File created in folder 1: " + filePath);
                            readAndPrintFileContents(filePath.toString());
                        } else if (filePath.startsWith(folder2)) {
                            System.out.println("File created in folder 2: " + filePath);
                            readAndPrintFileContents(filePath.toString());
                        } else if (filePath.startsWith(folder3)) {
                            System.out.println("File created in folder 3: " + filePath);
                            readAndPrintFileContents(filePath.toString());
                        } else {
                            System.out.println("File created in unknown folder: " + filePath);
                        }
                    }
                }

                // Reset the key
                boolean valid = key.reset();
                if (!valid) {
                    // If the key is no longer valid, exit the loop
                    break;
                }
            }


        } catch (Exception ex) {

        }
        return "OK";
    }

    private void readAndPrintFileContents(String filePath) {
        // Open the file
        try {
            // Create a DocumentBuilder
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            // Parse the XSD or XML file
            Document doc = builder.parse(new File(filePath));

            String data = toString(doc);
            System.out.println("data = " + data);

            // Extract the file name from the file path
            Path sourcePath = Paths.get(filePath);
            String fileName = sourcePath.getFileName().toString();

            // Destination directories
            Path destinationDirectory = Paths.get("E:\\rtgs\\output\\" + DateUtility.folderCreateDate() + "\\");
            Path dbSuccess = Paths.get("E:\\rtgs\\output\\" + DateUtility.folderCreateDate() + "_dbsuccess\\");
            Path dbFail = Paths.get("E:\\rtgs\\output\\" + DateUtility.folderCreateDate() + "_dbfail\\");

            // Resolve the destination file paths
            Path destinationFile = destinationDirectory.resolve(fileName);
            Path dbSuccessFile = dbSuccess.resolve(fileName);
            Path dbFailFile = dbFail.resolve(fileName);

            // Check if the directory exists
            if (!Files.exists(destinationDirectory)) {
                Files.createDirectories(destinationDirectory);
            }
            if (!Files.exists(dbSuccess)) {
                Files.createDirectories(dbSuccess);
            }
            if (!Files.exists(dbFail)) {
                Files.createDirectories(dbFail);
            }


            // Move the file
            Files.move(sourcePath, destinationFile);
            if (!data.isEmpty()) {
                System.out.println("data = " + data);
                // DB Insert Process Call
                inwardHandle.handleInwardBlock4Message(data);
                Files.copy(destinationFile, dbSuccessFile);

            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public String toString(Document doc) {
        try {
            // Use StringWriter to capture the output
            StringWriter sw = new StringWriter();

            // Create a TransformerFactory instance
            TransformerFactory tf = TransformerFactory.newInstance();

            // Protect against XXE attacks
            tf.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
            tf.setAttribute(XMLConstants.ACCESS_EXTERNAL_STYLESHEET, "");

            // Create a Transformer instance
            Transformer transformer = tf.newTransformer();

            // Set output properties for the transformer
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

            // Properly set the indent amount (2 spaces in this case)
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "5");

            // Transform the document to a string
            transformer.transform(new DOMSource(doc), new StreamResult(sw));

            return sw.toString();

        } catch (Exception ex) {
            throw new RuntimeException("Error converting to String", ex);
        }
    }


    private static List<Path> checkForNewFiles(Path... folders) throws IOException {
        List<Path> newFiles = new ArrayList<>();
        Instant now = Instant.now();
        for (Path folder : folders) {
            try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(folder)) {
                // Filter files that are newly created
                for (Path path : directoryStream) {
                    BasicFileAttributes attr = Files.readAttributes(path, BasicFileAttributes.class);
                    Instant creationTime = attr.creationTime().toInstant();
//                    if (creationTime.isAfter(now.minusSeconds(60))) { // Adjust the time frame as needed
//                        newFiles.add(path);
//                    }

                    newFiles.add(path);
                }
            }
        }
        return newFiles;
    }


}
