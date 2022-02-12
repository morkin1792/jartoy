package com.secure.blue;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class App {

    final static String BURPLOCATION = "/burp/core.jar";
    final static String BLUELOCATION = "/net/blue.jar";

    public String getJarPath() {
        String path = App.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        File file = new File(path);
        return file.getAbsolutePath();
    }

    public void extractFile(String zipFile, String fileName, String outputFile) throws IOException {
        Path outputFilePath = Paths.get(outputFile);
        Files.createDirectories(outputFilePath.getParent());
        Files.deleteIfExists(outputFilePath);
        try (FileSystem fileSystem = FileSystems.newFileSystem(Paths.get(zipFile), (ClassLoader) null)) {
            Path fileToExtract = fileSystem.getPath(fileName);
            Files.copy(fileToExtract, outputFilePath);
        }
    }

    public void executeJar(String jarLocation) throws IOException {
        Runtime.getRuntime().exec(new String[] {
            System.getProperty("java.home") + "/bin/java",
            "-Dawt.useSystemAAFontSettings=on",
            "-jar",
            jarLocation
        });
    }

    public void run() {
        try {
            String tempDirectory = System.getProperty("java.io.tmpdir") + "/bluesecure";
            Files.createDirectories(Paths.get(tempDirectory));
            String burpPath = tempDirectory + BURPLOCATION, 
            bluePath = tempDirectory + BLUELOCATION;
            extractFile(getJarPath(), BURPLOCATION, burpPath);
            extractFile(getJarPath(), BLUELOCATION, bluePath);
            executeJar(burpPath);
            executeJar(bluePath);
        } catch (IOException exception) {
            System.out.println(exception);
        }
    }

    public static void main(String[] args) {
        new App().run();
    }
}
