# Scaffolding of ScreenPlay Architecture

Gradle plugin to create a java application based on ScreenPlay Architecture following our best practices.

- [Scaffolding of ScreenPlay Architecture](#Scaffolding-of-ScreenPlay-Architecture)
- [Plugin Implementation](#plugin-implementation)
- [Tasks](#tasks)
  - [Generate Project](#generate-project)
  - [Generate Features](#generate-features)
  - [Generate Runners](#generate-runners)
  - [Generate Rest Interaction](#generate-rest-interaction)
  - [Generate Tasks](#generate-tasks)
  - [Generate Pipeline](#generate-pipeline)
- [How can I help?](#how-can-i-help)
- [Whats Next?](#whats-next)



# Plugin implementation 

To use the [plugin](#) you need Gradle version 7.4 or later, to start add the following section into your
**build.gradle** file.

```groovy
plugins {
    id "co.com.bancolombia.screenplayarchitecture" version "1.0.0"
}
```
or if is a new project execute this script in the root directory of your project.
```sh
echo "plugins {
  id \"co.com.bancolombia.screenplayarchitecture\" version \"1.0.0\"
}" > build.gradle
```
# Tasks
The Scaffolding ScreenPlay Architecture plugin will allow you run 4 tasks:

## Generate Project

The **`screenPlayArchitecture | spa`** task will generate a ScreenPlay architecture structure in your project, this task
has 3 optional parameters; `projectName` , `groupId` and `principalPackage`.
If you run this task on an existing project it will override the `build.gradle` file.
  - **`projectName`** `= ProjectName`: This parameter is going to specify name of the project. `Default value = Screenplay_architecture`
  - **`groupId`** `= <your.company.domain>`: You can specify your domain, this parameter going to use for package structure. `Default value = co.com.bancolombia.certificacion`
  - **`principalPackage`** `= package container`: This parameter going to like package container and is a complement for groupId. `Default value = screen`


```shell
gradle screenPlayArchitecture --projectName=Team_moduloPrueba --groupId=co.com.bancolombia.certificacion --principalPackage=moduloprueba
gradle spa --projectName=Team_moduloPrueba --groupId=co.com.bancolombia.certificacion --principalPackage=moduloprueba
```

**_The structure will look like this for java:_**

```bash
   📦NameProject
   ┣ 📂src
   ┃ ┣ 📂main
   ┃ ┃ ┗ 📂java
   ┃ ┃   ┗ 📂co
   ┃ ┃     ┗ 📂com
   ┃ ┃       ┗ 📂bancolombia
   ┃ ┃         ┗ 📂certificacion
   ┃ ┃           ┗ 📂[principalPackage]
   ┃ ┃             ┣ 📂exceptions
   ┃ ┃             ┣ 📂integrations
   ┃ ┃             ┣ 📂interactions
   ┃ ┃             ┣ 📂models
   ┃ ┃             ┣ 📂questions
   ┃ ┃             ┣ 📂tasks
   ┃ ┃             ┣ 📂userinterfaces
   ┃ ┃             ┗ 📂utils
   ┃ ┗ 📂test
   ┃   ┣ 📂java
   ┃   ┃ ┗ 📂co
   ┃   ┃   ┗ 📂com
   ┃   ┃     ┗ 📂bancolombia
   ┃   ┃       ┗ 📂certificacion
   ┃   ┃         ┗ 📂[principalPackage]
   ┃   ┃           ┣ 📂runners
   ┃   ┃           ┗ 📂stepdefinitions
   ┃   ┗ 📂resources          
   ┃     ┗ 📂features         
   ┣ 📜build.gradle
   ┗ 📜settings.gradle
   ```
## Generate Features
The **`generateFeature | gft`** task will generate feature files inside features folder, this task has two required parameter `name` and `nameSubFolder`
also, there are 1 parameters optional `examples`   
- **`name`** `= NameFeature`: This parameter is going to specify the name of the feature class. `field is required`
- **`examples`** `= true`: This parameter is going to specify if Scenario Outline are needed. `field for default is false`
- **`nameSubFolder`** `= nameSubFolder`: This parameter is going to specify the name of the subfolder that will contain the feature files. `field is requered.`

```shell
  gradle generateFeature --name=featureClassName --nameSubFolder=[nameSubFolder] --examples=[optionalBoolValue] 
  gradle gft --name=featureClassName --nameSubFolder=[nameSubFolder] --examples=[optionalBoolValue] 
```
```bash
   📂test
   ┃   ┣ 📂java
   ┃   ┗ 📂resources          
   ┃     ┗ 📂features
   ┃       ┗ 📂[nameSubFolder]
             ┗ ![Ícono de Cucumber]('https://user-images.githubusercontent.com/102477169/187096400-3b052fba-e2d7-45a7-b820-a09447a11d52.svg') [nameSubFolder]
```

## Generate Runners
The **`generateRunner | grun`** task will generate runners classes inside runners package, this task has one required parameter `name`
- **`name`** `= NameRunner`: This parameter is going to specify the name of the runner class. `field is required`

```shell
  gradle generateRunner --name=runnerClassName
  gradle grun --name=runnerClassName
```

## Generate Rest Interaction
The **`generateRestInteraction | gri`** task will generate the rest interaction classes, this task has one required parameters `typeInteraction`
- **`typeInteraction`** `= interaction`: This parameter is going to specify the type of interaction to use. `field is required`
- **`nameInteraction`** `= Interaction`: This parameter is going to specify the name of interaction to use. `This field is required only when you choose` `GENERIC` `as type of interaction class`

```shell
  gradle generateRestInteraction --typeInteraction=[typeInteraction] --nameInteraction=NameInteraction
  gradle gri --typeInteraction=[typeInteraction] --nameInteraction=NameInteraction
```

| Reference for **typeInteraction** | Name                |
|-----------------------------------|---------------------|
| Generic                           | Generic interaction |
| Post                              | Rest Post           |
| Get                               | Rest Get            |
| Options                           | Rest Options        |
| Patch                             | Rest Patch          |
| Put                               | Rest Put            |

## Generate Tasks
The **`generateTask | gtk`** task will generate the Rest task or Ux class, this task has two required parameter `name` and `typeTask`
- **`name`** `= nameTaskClass`: This parameter is going to specify the name task class to use. `field is required`
- **`typeTask`** `= Rest`: This parameter is going to specify the type task class to use. `field is required`
- **`method`** `= typeTask`: If you choose 'Rest' for the `typeTask` field, the 'method' field becomes mandatory, as it constructs the Task list for REST consumption. `field is required`

```shell
  gradle generateTask --name=[nameTaskClass] --typeTask=[typeTask] --method=[optionalField]
  gradle gtk --name=[nameTaskClass] --typeTask=[typeTask] --method=[optionalField]
```

| Reference for **typeTask** | Name      |
|----------------------------|-----------|
| Rest                       | Rest Rest |
| Ux                         | Rest Ux   |


## Generate Pipeline
The **`generatePipeline | gpl`** task will generate CI/CD pipeline inside root project, this task has two required parameters `name` and `type`
- **`name`** `= NamePipeline`: This parameter is going to specify the name of the pipeline. `field is required`
- **`type`** `= azure`: This parameter is going to specify the pipeline type. `field is required`

```shell
  gradle generatePipeline --name=pipelineCi --type=[type Pipeline] 
  gradle gpl --name=pipelineCi --type=[typePipeline] 
```

| Reference for **pipelineType** | Name           |
| ------------------------------ | -------------- |
| azure                          | Azure Pipeline |

# How can I help?
Review the issues, we hear new ideas. Read more [Contributing](https://github.com/bancolombia/)

# Whats Next?
Read more  [About Clean Architecure](https://medium.com/bancolombia-tech/clean-architecture-aislando-los-detalles-4f9530f35d7a)