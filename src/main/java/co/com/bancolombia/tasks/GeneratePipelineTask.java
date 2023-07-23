package co.com.bancolombia.tasks;

import co.com.bancolombia.tasks.annotations.CATask;

@CATask(
        name = "generatePipeline",
        shortCut = "gpl",
        description = "Generate CI/CD pipeline as a code in deployment layer"
)
public class GeneratePipelineTask extends AbstractResolvableTypeTask{
    @Override
    protected void prepareParams() {
        // No additional params required
    }
    @Override
    protected String resolvePrefix() {
        return "Pipeline";
    }

    @Override
    protected String resolvePackage() {
        return "co.com.bancolombia.factory.pipelines";
    }
}
