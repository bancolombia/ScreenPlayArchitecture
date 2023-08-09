package co.com.bancolombia.tasks;

import co.com.bancolombia.exceptions.ScreenPlayException;
import org.gradle.api.Project;
import org.gradle.testfixtures.ProjectBuilder;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import static co.com.bancolombia.utils.FileUtilsTest.deleteStructure;
import static org.junit.Assert.assertTrue;

public class GenerateArchitectureDefaultTaskTest {
    public static final String DIR = "build/unitTest/";
    private GenerateArchitectureDefaultTask task;

    @Before
    public void  setup(){
        deleteStructure(Path.of(DIR));
        Project project =
                ProjectBuilder.builder()
                        .withProjectDir(new File(DIR))
                        .withGradleUserHomeDir(new File(DIR))
                        .build();
        project.getTasks().create("test", GenerateArchitectureDefaultTask.class);
        task = (GenerateArchitectureDefaultTask) project.getTasks().getByName("test");
    }

    @Test
    public void generateStructure() throws ScreenPlayException, IOException {
        task.execute();
        assertTrue(new File("build/unitTest/settings.gradle").exists());
        assertTrue(new File("build/unitTest/serenity.properties").exists());
        assertTrue(new File("build/unitTest/gradle.properties").exists());
        assertTrue(new File("build/unitTest/build.gradle").exists());
        assertTrue(new File("build/unitTest/src/main/java/co/com/bancolombia/certificacion" +
                "/screen/exceptions/Exception.java").exists());
        assertTrue(new File("build/unitTest/src/main/java/co/com/bancolombia/certificacion" +
                "/screen/integrations/Integration.java").exists());
        assertTrue(new File("build/unitTest/src/main/java/co/com/bancolombia/certificacion" +
                "/screen/interactions/Interaction.java").exists());
        assertTrue(new File("build/unitTest/src/main/java/co/com/bancolombia/certificacion" +
                "/screen/models/Model.java").exists());
        assertTrue(new File("build/unitTest/src/main/java/co/com/bancolombia/certificacion" +
                "/screen/questions/question.java").exists());
        assertTrue(new File("build/unitTest/src/main/java/co/com/bancolombia/certificacion" +
                "/screen/tasks/Task.java").exists());
        assertTrue(new File("build/unitTest/src/main/java/co/com/bancolombia/certificacion" +
                "/screen/userinterfaces/UserInterface.java").exists());
        assertTrue(new File("build/unitTest/src/main/java/co/com/bancolombia/certificacion" +
                "/screen/utils/Util.java").exists());
        assertTrue(new File("build/unitTest/src/test/java/co/com/bancolombia/certificacion" +
                "/screen/runners/Runner.java").exists());
        assertTrue(new File("build/unitTest/src/test/java/co/com/bancolombia/certificacion" +
                "/screen/stepdefinitions/StepDefinition.java").exists());
        assertTrue(new File("build/unitTest/src/test/resources/features").exists());

    }

    @Test
    public void generateStructureOnExistingProject() throws ScreenPlayException, IOException {
        task.execute();
        task.execute();
        assertTrue(new File("build/unitTest/settings.gradle").exists());
        assertTrue(new File("build/unitTest/serenity.properties").exists());
        assertTrue(new File("build/unitTest/gradle.properties").exists());
        assertTrue(new File("build/unitTest/build.gradle").exists());
    }

    @AfterClass
    public static void clean() {
        deleteStructure(Path.of(DIR));
    }
}
