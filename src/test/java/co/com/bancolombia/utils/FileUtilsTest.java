package co.com.bancolombia.utils;

import org.apache.commons.io.file.SimplePathVisitor;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

public class FileUtilsTest {

    public static void deleteStructure(Path sourcePath) {
        try {
            Files.walkFileTree(
                    sourcePath,
                    new SimplePathVisitor() {
                        @Override
                        public FileVisitResult postVisitDirectory(Path dir, IOException e) throws IOException {
                            Files.delete(dir);
                            return FileVisitResult.CONTINUE;
                        }

                        @Override
                        public FileVisitResult visitFile(final Path file, final BasicFileAttributes attrs)
                                throws IOException {
                            Files.delete(file);
                            return FileVisitResult.CONTINUE;
                        }
                    });
        } catch (IOException e) {
            System.out.println("error delete Structure " + e.getMessage());
        }
    }
}
