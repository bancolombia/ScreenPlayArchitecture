package co.com.bancolombia.utils;

public class Constants {
    public enum BooleanOption {
        TRUE,
        FALSE
    }

    public enum Language {
        JAVA,
        KOTLIN
    }

    public enum JavaVersion {
        @Deprecated
        VERSION_1_8,
        VERSION_11,
        VERSION_17
    }

    public enum ProjectType {
        REST,
        UX
    }

    public static final String PLUGIN_VERSION = "1.0.0";
    public static final String BUILD_GRADLE = "./build.gradle";


}
