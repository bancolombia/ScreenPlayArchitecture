package co.com.bancolombia.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@NoArgsConstructor
@Getter
@Setter
public class TemplateDefinition {

    private Map<String, String> files;
    private Map<String, String> java;
    private List<String> folders;
}
