package co.com.bancolombia.utils;

import co.com.bancolombia.factory.ModuleFactory;
import co.com.bancolombia.tasks.annotations.CATask;
import lombok.experimental.UtilityClass;
import org.gradle.api.Task;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ScanResult;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.lang.annotation.Annotation;
import java.util.Set;

@UtilityClass
public class ReflectionUtils {

  /*public static Stream<Class<? extends ModuleFactory>> getModuleFactories(String packageName) {
    return new Reflections(packageName, Scanners.SubTypes)
        .getSubTypesOf(ModuleFactory.class).stream();
  }

  public static Stream<Class<? extends Task>> getTasks() {
    return new Reflections("co.com.bancolombia.tasks", Scanners.TypesAnnotated)
        .getTypesAnnotatedWith(CATask.class).stream()
            .filter(Task.class::isAssignableFrom)
            .map(c -> c.asSubclass(Task.class));
  }*/


  public static <T extends ModuleFactory> Stream<Class<? extends T>> getModuleFactories(String packageName) {
    try (ScanResult scanResult = new ClassGraph().enableClassInfo().enableAnnotationInfo().whitelistPackages(packageName).scan()) {
      List<Class<? extends T>> classes = scanResult
              .getClassesImplementing(ModuleFactory.class.getName())
              .stream()
              .map(classInfo -> (Class<? extends T>) classInfo.loadClass().asSubclass(ModuleFactory.class))
              .collect(Collectors.toList());

      // Puedes realizar más operaciones con la lista de clases aquí, si es necesario

      return classes.stream();
    }
  }

  public static <T extends Task> Stream<Class<? extends T>> getTasks() {
    try (ScanResult scanResult = new ClassGraph().enableClassInfo().enableAnnotationInfo().whitelistPackages("co.com.bancolombia.tasks").scan()) {
      List<Class<? extends T>> classes = scanResult
              .getClassesWithAnnotation(CATask.class.getName())
              .stream()
              .map(classInfo -> (Class<? extends T>) classInfo.loadClass().asSubclass(Task.class))
              .collect(Collectors.toList());

      // Puedes realizar más operaciones con la lista de clases aquí, si es necesario

      return classes.stream();
    }
  }
}
