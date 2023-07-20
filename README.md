# Scaffolding of ScreenPlay Architecture

Gradle plugin to create a java and kotlin application based on ScreenPlay Architecture following our best practices.

- [Scaffolding of ScreenPlay Architecture](#Scaffolding of ScreenPlay Architecture)
- [Plugin Implementation](#plugin-implementation)
- [Tasks](#tasks)
  - [Generate Project](#generate-project)



# Plugin implementation 

To use the [plugin](#) you need Gradle version 7.4 or later, to start add the following section into your
**build.gradle** file.

```groovy
plugins {
    id "co.com.bancolombia.screenPlayArchitecture" version "1.0.0"
}
```
or if is a new project execute this script in the root directory of your project.
```sh
echo "plugins {
  id \"co.com.bancolombia.screenPlayArchitecture\" version \"1.0.0\"
}" > build.gradle
```
# Tasks
The Scaffolding ScreenPlay Architecture plugin will allow you run ? tasks:

## Generate Project

The **`screenPlayArchitecture | spa`** task will generate a ScreenPlay architecture structure in your project, this task
has ? optional parameters; `package` , `type` and `name`.
If you run this task on an existing project it will override the `build.gradle` file.


```shell
    gradle screenPlayArchitecture
    gradle spa 
```