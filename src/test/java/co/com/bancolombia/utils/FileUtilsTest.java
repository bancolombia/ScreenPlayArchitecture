package co.com.bancolombia.utils;

import com.github.mustachejava.resolver.DefaultResolver;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.file.SimplePathVisitor;
import org.gradle.api.Project;
import org.gradle.testfixtures.ProjectBuilder;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FileUtilsTest {


    @Test
    public void readPropertiesExist() throws IOException {
        String property = "package";
        assertEquals("co.com.bancolombia",
                FileUtil.readProperties(".", Constants.GRADLE_PROPERTIES, property));
    }

    @Test
    public void returnRelativePath(){
        String path = "src/main/java/co/com/bancolombia/certificacion/moduloprueba/questions/Question.java";
        assertEquals("./src/main/java/co/com/bancolombia/certificacion/moduloprueba/questions/Question.java",
                FileUtil.toRelative(path));
    }

    @Test
    public void fileExist(){
        assertTrue(FileUtil.exists("src/test/resources", "temp.txt"));
    }

    @Test
    public void readFileFromResources() throws IOException {
        DefaultResolver defaultResolver = new DefaultResolver();
        String response = FileUtil.getResourceAsString(defaultResolver, "temp.txt");
        assertEquals("hello", response);
    }
    @Test
    public void writeString() throws IOException {
        Project project = ProjectBuilder.builder().withProjectDir(new File("build/tmp")).build();
        FileUtil.writeString(project, "temp.txt", "hello");
        String response = FileUtil.readFile(project, "temp.txt");

        assertEquals("hello", response);
    }

    @Test
    public void readFile() throws IOException {
        Project project =
                ProjectBuilder.builder().withProjectDir(new File("src/test/resources")).build();
        String response = FileUtil.readFile(project, "temp.txt");

        assertEquals("hello", response);
    }
    
    @Test
    public void findMatches() throws IOException {
        Project project =
                ProjectBuilder.builder().withProjectDir(new File("src/test/resources")).build();
        List<List<Integer>> response = FileUtil.findMatches(
                (FileUtil.readFile(project,"temp.txt")), "hello");
        assertEquals(1, response.get(0).size());
    }

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
