package co.com.bancolombia;

import co.com.bancolombia.exceptions.ScreenPlayException;
import co.com.bancolombia.tasks.GenerateArchitectureDefaultTask;
import co.com.bancolombia.utils.Constants;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.testfixtures.ProjectBuilder;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import static co.com.bancolombia.utils.FileUtilsTest.deleteStructure;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class PluginScreenPlayTest {

    @Test
    public void pluginRegistersATask() {
        String taskGroup = "ScreenPlay Architecture";
        String descriptionTask1 = "Scaffolding ScreenPlay architecture project";
        String descriptionTask2 = "generate feature file";
        String descriptionTask3 = "Generate CI/CD pipeline as a code in deployment layer";
        String descriptionTask4 = "Generate rest Interaction classes";
        String descriptionTask5 = "Generate runner class";
        String descriptionTask6 = "Generate Serenity task";
        Project project = ProjectBuilder.builder().build();
        project.getPluginManager().apply("co.com.bancolombia.screenPlayArchitecture");

        Task  task = project.getTasks().findByName("screenPlayArchitecture");
        Task  task2 = project.getTasks().findByName("generateFeature");
        Task  task3 = project.getTasks().findByName("generatePipeline");
        Task  task4 = project.getTasks().findByName("generateRestInteraction");
        Task  task5 = project.getTasks().findByName("generateRunner");
        Task  task6 = project.getTasks().findByName("generateTask");

        assertEquals(taskGroup, task.getGroup());
        assertEquals(descriptionTask1, task.getDescription());

        assertEquals(taskGroup, task2.getGroup());
        assertEquals(descriptionTask2, task2.getDescription());

        assertEquals(taskGroup, task3.getGroup());
        assertEquals(descriptionTask3, task3.getDescription());

        assertEquals(taskGroup, task4.getGroup());
        assertEquals(descriptionTask4, task4.getDescription());

        assertEquals(taskGroup, task5.getGroup());
        assertEquals(descriptionTask5, task5.getDescription());

        assertEquals(taskGroup, task6.getGroup());
        assertEquals(descriptionTask6, task6.getDescription());
    }

    @Test
    public void shouldApply() throws ScreenPlayException, IOException{
        deleteStructure(Path.of("build/unitTest"));
        Project project =
                ProjectBuilder.builder()
                        .withName("screenArchitecture")
                        .withProjectDir(new File("build/unitTest"))
                        .build();
        project.getTasks().create("other", GenerateArchitectureDefaultTask.class);
        GenerateArchitectureDefaultTask generateArchitectureDefaultTask =
                (GenerateArchitectureDefaultTask) project.getTasks().getByName("other");
        generateArchitectureDefaultTask.execute();


        Plugin<?> applied = project.getPlugins().apply("co.com.bancolombia.screenPlayArchitecture");
        assertNotNull(applied);
    }
}
