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

public class GenerateFeatureTaskTest {

    GenerateFeatureTask task;

    static Project project =
            ProjectBuilder.builder().withProjectDir(new File("build/unitTest")).build();

    @Before
    public void init() throws IOException, ScreenPlayException {
        deleteStructure(Path.of("build/unitTest"));
        setup();
    }

    @AfterClass
    public static void clean() {
        deleteStructure(project.getProjectDir().toPath());
    }

    public void setup() throws IOException, ScreenPlayException {
        deleteStructure(project.getProjectDir().toPath());
        project.getTasks().create("spa", GenerateArchitectureDefaultTask.class);
        GenerateArchitectureDefaultTask architectureDefaultTask = (GenerateArchitectureDefaultTask)
                project.getTasks().getByName("spa");
        architectureDefaultTask.execute();

        project.getTasks().create("test", GenerateFeatureTask.class);
        task = (GenerateFeatureTask) project.getTasks().getByName("test");
    }

    @Test
    public void generateFeature() throws ScreenPlayException, IOException {
        task.setNameSubFolder("pruebaunit");
        task.setName("listar_unit");
        task.execute();
        assertTrue(new File("build/unitTest/src/test/resources/features/pruebaunit/listar_unit.feature").exists());
    }
}
