package co.com.bancolombia.utils;

import co.com.bancolombia.exceptions.ParamNotFoundException;

import java.util.Map;

public class Util {

    private static final String SEPARATOR = "/";
    private static final String PARAM_START = "{{";
    private static final String PARAM_END = "}}";

    private static final int PARAM_LENGTH = 2;


    public static String getVersionPlugin() {
        return Constants.PLUGIN_VERSION;
    }

    public static String joinPath(String... args) {
        return String.join(SEPARATOR, args);
    }

    public static String extractDir(String path) {
        int index = path.lastIndexOf(SEPARATOR);
        if (index != -1) {
            return path.substring(0, index);
        } else {
            return null;
        }
    }

    public static String fillPath(String path, Map<String, Object> params) throws ParamNotFoundException{
        while (path.contains(PARAM_START)) {
            String key = path.substring(path.indexOf(PARAM_START) + PARAM_LENGTH, path.indexOf(PARAM_END));
            if (params.containsKey(key)) {
                path = path.replace(PARAM_END + key + PARAM_END, params.get(key).toString());
            }else {
                throw new ParamNotFoundException(key);
            }
        }
        return path;
    }
}
