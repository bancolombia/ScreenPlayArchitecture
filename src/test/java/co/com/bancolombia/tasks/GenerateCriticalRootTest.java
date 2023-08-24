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

public class GenerateCriticalRootTest {
    GenerateCriticalRoot task;
    GenerateFeatureTask featureTask;

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

        project.getTasks().create("test", GenerateCriticalRoot.class);
        task = (GenerateCriticalRoot) project.getTasks().getByName("test");

        project.getTasks().create("testFeature", GenerateFeatureTask.class);
        featureTask = (GenerateFeatureTask) project.getTasks().getByName("testFeature");
    }

    public void createFeatures(String feature, String subFolder, String examples) throws ScreenPlayException, IOException {
        featureTask.setName(feature);
        featureTask.setNameSubFolder(subFolder);
        featureTask.setExamples(examples);
        featureTask.execute();
    }

    @Test
    public void generateRootWithOutExamplesEn() throws ScreenPlayException, IOException {
        createFeatures("feature_sin_example", "sinexample", "false");
        task.setFeatures("sinexample/feature_sin_example");
        task.setLanguage("EN");
        task.execute();
        assertTrue(new File("build/unitTest/rutacritica.json").exists());
    }

    @Test
    public void generateRootWithExamplesEn() throws ScreenPlayException, IOException {
        createFeatures("feature_example", "examples", "true");
        task.setComponentName("prueba_test");
        task.setFeatures("examples/feature_example");
        task.setLanguage("EN");
        task.execute();
        assertTrue(new File("build/unitTest/rutacritica.json").exists());
    }

    @Test
    public void generateRootSomeFeEn() throws ScreenPlayException, IOException {
        createFeatures("multiple", "examples", "true");
        createFeatures("test_multiple", "examplesmultimples", "true");
        task.setComponentName("modulos_test");
        task.setFeatures("examples/multiple,examplesmultimples/test_multiple");
        task.setLanguage("EN");
        task.execute();
        assertTrue(new File("build/unitTest/rutacritica.json").exists());
    }

    private void writeString(File file, String string) throws IOException {
        try (Writer writer = new FileWriter(file)) {
            writer.write(string);
        }
    }
}
