package co.com.bancolombia.tasks;

import co.com.bancolombia.exceptions.ScreenPlayException;
import org.gradle.api.Project;
import org.gradle.testfixtures.ProjectBuilder;
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

public class GenerateTaskTest {
    GenerateTasksTask task;

    @Before
    public void init() throws IOException, ScreenPlayException {
        deleteStructure(Path.of("build/unitTest"));
        File projectDir = new File("build/unitTest");
        Files.createDirectories(projectDir.toPath());
        writeString(
                new File(projectDir, "build.gradle"),
                "plugins {" + "  co.com.bancolombia.screenPlayArchitecture')" + "}");

        Project project =
                ProjectBuilder.builder()
                        .withName("cleanArchitecture")
                        .withProjectDir(new File("build/unitTest"))
                        .build();

        project.getTasks().create("testStructure", GenerateArchitectureDefaultTask.class);
        GenerateArchitectureDefaultTask taskStructure =
                (GenerateArchitectureDefaultTask) project.getTasks().getByName("testStructure");
        taskStructure.execute();

        project.getTasks().create("test", GenerateTasksTask.class);
        task = (GenerateTasksTask) project.getTasks().getByName("test");
    }

    @Test
    public void generatePostTask() throws ScreenPlayException, IOException {
        task.setName("ConsumerUnitTest");
        task.setTypeTask("rest");
        task.setmethod("post");
        task.execute();
        assertTrue(new File("build/unitTest/src/main/java/co/com/bancolombia/certificacion" +
                "/screen/tasks/ConsumerUnitTest.java").exists());
    }
    @Test
    public void generateGetTask() throws ScreenPlayException, IOException {
        task.setName("ConsumerUnitGet");
        task.setTypeTask("rest");
        task.setmethod("Get");
        task.execute();
        assertTrue(new File("build/unitTest/src/main/java/co/com/bancolombia/certificacion" +
                "/screen/tasks/ConsumerUnitGet.java").exists());
    }
    @Test
    public void generateOptionsTask() throws ScreenPlayException, IOException {
        task.setName("ConsumerUnitOption");
        task.setTypeTask("rest");
        task.setmethod("Options");
        task.execute();
        assertTrue(new File("build/unitTest/src/main/java/co/com/bancolombia/certificacion" +
                "/screen/tasks/ConsumerUnitOption.java").exists());
    }
    @Test
    public void generatePutTask() throws ScreenPlayException, IOException {
        task.setName("ConsumerUnitPut");
        task.setTypeTask("rest");
        task.setmethod("Put");
        task.execute();
        assertTrue(new File("build/unitTest/src/main/java/co/com/bancolombia/certificacion" +
                "/screen/tasks/ConsumerUnitPut.java").exists());
    }
    @Test
    public void generatePatchTask() throws ScreenPlayException, IOException {
        task.setName("ConsumerUnitPatch");
        task.setTypeTask("rest");
        task.setmethod("Patch");
        task.execute();
        assertTrue(new File("build/unitTest/src/main/java/co/com/bancolombia/certificacion" +
                "/screen/tasks/ConsumerUnitPatch.java").exists());
    }
    @Test
    public void generateUxTask() throws ScreenPlayException, IOException {
        task.setName("FrontUnitTest");
        task.setTypeTask("Ux");
        task.execute();
        assertTrue(new File("build/unitTest/src/main/java/co/com/bancolombia/certificacion" +
                "/screen/tasks/FrontUnitTest.java").exists());
    }
    private void writeString(File file, String string) throws IOException {
        try (Writer writer = new FileWriter(file)) {
            writer.write(string);
        }
    }
}
