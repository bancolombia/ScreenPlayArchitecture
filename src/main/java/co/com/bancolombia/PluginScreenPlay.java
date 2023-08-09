package co.com.bancolombia;

import co.com.bancolombia.models.TaskModel;
import co.com.bancolombia.tasks.annotations.CATask;
import co.com.bancolombia.utils.Constants;
import co.com.bancolombia.utils.ReflectionUtils;
import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.tasks.TaskContainer;
import org.gradle.api.tasks.testing.Test;
import org.jetbrains.annotations.NotNull;

import java.util.stream.Stream;

public class PluginScreenPlay implements Plugin<Project> {
    private PluginScreenPlayExtension pluginScreenPlayExtension;

    @Override
    public void apply(Project project) {
        project.getPluginManager().apply("java");
        pluginScreenPlayExtension = project.getExtensions().create("PluginScreenPlay", PluginScreenPlayExtension.class);

        TaskContainer taskContainer = project.getTasks();
        initTask().forEach(task -> this.appendTask(taskContainer, task));
        project.getSubprojects().forEach(this::listenTest);

    }

    private Stream<TaskModel> initTask() {
        return ReflectionUtils.getTasks()
                .map(clazz -> {
                    TaskModel.TaskModelBuilder builder =
                            TaskModel.builder()
                                    .name(clazz.getAnnotation(CATask.class).name())
                                    .shortcut(clazz.getAnnotation(CATask.class).shortCut())
                                    .description(clazz.getAnnotation(CATask.class).description())
                                    .group(Constants.PLUGIN_TASK_GROUP)
                                    .taskAction(clazz);
                    return builder.build();
                });
    }

    private void listenTest(Project project) {
        project.getLogger().info("Injecting test logger");
        project
                .getTasks()
                .withType(Test.class)
                .configureEach(
                        test -> test.addTestOutputListener(
                                (testDescriptor, testOutputEvent) -> {
                                    if (!testOutputEvent.getMessage().contains("DEBUG")) {
                                        test.getLogger().lifecycle(testOutputEvent.getMessage()
                                                .replace('\n', ' '));
                                    }}));
    }

    @SuppressWarnings("unchecked")
    private void appendTask(TaskContainer taskContainer, TaskModel t) {
        if (t.getAction() == null) {
            taskContainer.create(
                    t.getName(),
                    t.getTaskAction(),
                    task -> {
                        task.setGroup(t.getGroup());
                        task.setDescription(t.getDescription());
                    });
            taskContainer.create(
                    t.getShortcut(),
                    t.getTaskAction(),
                    task -> {
                        task.setGroup(t.getGroup());
                        task.setDescription(t.getDescription());
                    });
        } else {
            Task temp = taskContainer.create(t.getName(), t.getTaskAction(), t.getAction());
            temp.setGroup(t.getGroup());
            temp.setDescription(t.getDescription());
            Task temp2 = taskContainer.create(t.getShortcut(), t.getTaskAction(), t.getAction());
            temp2.setGroup(t.getGroup());
            temp2.setDescription(t.getDescription());
        }
    }
}