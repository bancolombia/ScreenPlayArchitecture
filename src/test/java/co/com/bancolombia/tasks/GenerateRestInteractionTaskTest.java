package co.com.bancolombia.tasks;

import co.com.bancolombia.exceptions.ScreenPlayException;
import org.gradle.api.Project;
import org.gradle.testfixtures.ProjectBuilder;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;

import static co.com.bancolombia.utils.FileUtilsTest.deleteStructure;
import static org.junit.Assert.assertTrue;

public class GenerateRestInteractionTaskTest {

    GenerateRestInteractionTask task;

    @Before
    public void init() throws IOException, ScreenPlayException {
        deleteStructure(Path.of("build/unitTest"));
        File projectDir = new File("build/unitTest");
        Files.createDirectories(projectDir.toPath());
        writeString(
                new File(projectDir, "build.gradle"),
                "plugins {" + "  co.com.bancolombia.screenplayarchitecture')" + "}");

        Project project =
                ProjectBuilder.builder()
                        .withName("screenArchitecture")
                        .withProjectDir(new File("build/unitTest"))
                        .build();

        project.getTasks().create("testStructure", GenerateArchitectureDefaultTask.class);
        GenerateArchitectureDefaultTask taskStructure =
                (GenerateArchitectureDefaultTask) project.getTasks().getByName("testStructure");
        taskStructure.execute();

        project.getTasks().create("test", GenerateRestInteractionTask.class);
        task = (GenerateRestInteractionTask) project.getTasks().getByName("test");
    }

    @Test
    public void GenerateRestInteractionGeneric() throws ScreenPlayException, IOException {
        task.setTypeInteraction("Generic");
        task.setNameInteraction("prueba");
        task.execute();
        assertTrue(new File("build/unitTest/src/main/java/co/com/bancolombia/certificacion/screen/interactions/prueba.java").exists());
    }
    @Test
    public void GenerateRestInteractionPost() throws ScreenPlayException, IOException {
        task.setTypeInteraction("Post");
        task.setNameInteraction("prueba");
        task.execute();
        assertTrue(new File("build/unitTest/src/main/java/co/com/bancolombia/certificacion/screen/interactions/Post.java").exists());
    }

    @Test
    public void GenerateRestInteractionGet() throws ScreenPlayException, IOException {
        task.setTypeInteraction("Get");
        task.setNameInteraction("prueba");
        task.execute();
        assertTrue(new File("build/unitTest/src/main/java/co/com/bancolombia/certificacion/screen/interactions/Get.java").exists());
    }

    @Test
    public void GenerateRestInteractionOptions() throws ScreenPlayException, IOException {
        task.setTypeInteraction("Options");
        task.setNameInteraction("prueba");
        task.execute();
        assertTrue(new File("build/unitTest/src/main/java/co/com/bancolombia/certificacion/screen/interactions/Options.java").exists());
    }

    @Test
    public void GenerateRestInteractionPut() throws ScreenPlayException, IOException {
        task.setTypeInteraction("Put");
        task.setNameInteraction("prueba");
        task.execute();
        assertTrue(new File("build/unitTest/src/main/java/co/com/bancolombia/certificacion/screen/interactions/Put.java").exists());
    }

    @Test
    public void GenerateRestInteractionPatch() throws ScreenPlayException, IOException {
        task.setTypeInteraction("Patch");
        task.setNameInteraction("prueba");
        task.execute();
        assertTrue(new File("build/unitTest/src/main/java/co/com/bancolombia/certificacion/screen/interactions/Patch.java").exists());
    }

    private void writeString(File file, String string) throws IOException {
        try (Writer writer = new FileWriter(file)) {
            writer.write(string);
        }
    }
}
