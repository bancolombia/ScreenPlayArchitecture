package co.com.bancolombia;

import org.gradle.api.Action;
import org.gradle.api.tasks.Nested;

public interface ScreenPluginExtension {

    @Nested
    ModelProps getModelsProps();

    default void modelsProps(Action<? super ModelProps> action) { action.execute(getModelsProps());}
}
