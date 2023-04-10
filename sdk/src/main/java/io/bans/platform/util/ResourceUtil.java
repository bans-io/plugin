package io.bans.platform.util;

import io.bans.platform.Platform;
import io.bans.platform.PlatformLogLevel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

/**
 * Utility class for extracting resources from the jar file.
 */
public final class ResourceUtil {
    private ResourceUtil() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    /**
     * Get a file from the resources folder
     * @param platform The platform
     * @param directory The directory
     * @param fileName The file name
     */
    public static File getBundledFile(Platform platform, File directory, String fileName) {
        File file = new File(directory, fileName);

        if (!file.exists()) {
            if (!directory.exists()) {
                if (!directory.mkdirs()) {
                    throw new RuntimeException("Failed to create plugin folder");
                }
            }

            try {
                // Copies the file from the resources' folder to the plugin folder
                Files.copy(getFile(fileName).toPath(), file.toPath());
            } catch (IOException e) {
                platform.log(PlatformLogLevel.ERROR, String.format("Failed to copy %s to plugin folder", fileName));
            }
        }

        return file;
    }

    /**
     * Extracts a resource from the jar file to the specified location.
     * @param fileName The name of the file to extract.
     * @return The file that was extracted.
     */
    private static InputStream getFileFromResourceAsStream(String fileName) {

        // The class loader that loaded the class
        ClassLoader classLoader = ResourceUtil.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);

        // the stream holding the file content
        if (inputStream == null) {
            throw new RuntimeException(String.format("File %s not found", fileName));
        } else {
            return inputStream;
        }
    }

    /**
     * Extracts a resource from the jar file to the specified location.
     * @param fileName The name of the file to extract.
     * @return The file that was extracted.
     * @throws IOException If the file could not be extracted.
     */
    private static File getFile(String fileName) throws IOException {
        InputStream inputStream = getFileFromResourceAsStream(String.format("%s", fileName));

        // Retrieve file name and extension
        String[] split = fileName.split("\\.");
        String name = split[0];
        String extension = split[1];

        // Convert the input stream to a File
        File tempFile = File.createTempFile(name, extension);

        try (FileOutputStream out = new FileOutputStream(tempFile)) {
            // Read the input stream and write it to the output stream
            int read;

            byte[] buffer = new byte[8192];
            // Read the input stream using the buffer
            while ((read = inputStream.read(buffer)) != -1) {
                // Write the buffer to the output stream
                out.write(buffer, 0, read);
            }
        }

        return tempFile;
    }
}