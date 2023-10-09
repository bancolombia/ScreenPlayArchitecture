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
  - [Generate Critical Root](#generate-critical-root)
- [How can I help?](#how-can-i-help)
- [What's Next?](#whats-next)



# Plugin implementation 

To use the [plugin](#) you need Gradle version 8.6 or later, to start add the following section into your
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
The Scaffolding ScreenPlay Architecture plugin will allow you run 7 tasks:

## Generate Project

The **`screenPlayArchitecture | spa`** task will generate a ScreenPlay architecture structure in your project, this task
has 3 optional parameters; `projectName` , `groupId` and `principalPackage`.
If you run this task on an existing project it will override the `build.gradle` file.
  - **`projectName`** `= ProjectName`: This parameter is going to specify name of the project. `Default value = Screenplay_architecture`
  - **`groupId`** `= <your.company.domain>`: You can specify your domain, this parameter going to use for package structure. `Default value = co.com.bancolombia.certificacion`
  - **`principalPackage`** `= package container`: This parameter going to like package container and is a complement for groupId. `Default value = screen`
  - **`type`** `= choose between Rest or Ux Automation`: This parameter going to specify type automation will be create. `Default value = UX`


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
- **`name`** `= name_feature`: This parameter is going to specify the name of the feature class. `field is required`
- **`examples`** `= true`: This parameter is going to specify if Scenario Outline are needed. `field for default is false`
- **`nameSubFolder`** `= nameSubFolder`: This parameter is going to specify the name of the subfolder that will contain the feature files. `field is requered.`

```shell
  gradle generateFeature --name=feature_name --nameSubFolder=[nameSubFolder] --examples=[optionalBoolValue] 
  gradle gft --name=feature_name --nameSubFolder=[nameSubFolder] --examples=[optionalBoolValue] 
```
```bash
   📂test
   ┃   ┣ 📂java
   ┃   ┗ 📂resources          
   ┃     ┗ 📂features
   ┃       ┗ 📂[nameSubFolder]
   ┃         ┗ 📜[feature_name].feature
```

## Generate Runners
The **`generateRunner | grun`** task will generate runners classes inside runners package, this task has one required parameter `name`
- **`name`** `= NameRunner`: This parameter is going to specify the name of the runner class. `field is required`
- **`folderName`** `= package name`: This parameter is going to specify the name of the package that will contain the runners classes. `field is required`

```shell
  gradle generateRunner --name=runnerClassName --folderName=runnerspackage
  gradle grun --name=runnerClassName --folderName=runnerspackage
```
```bash

┃ ┗ 📂test
┃   ┣ 📂java
┃   ┃ ┗ 📂co
┃   ┃   ┗ 📂com
┃   ┃     ┗ 📂bancolombia
┃   ┃       ┗ 📂certificacion
┃   ┃         ┗ 📂[principalPackage]
┃   ┃           ┣ 📂runners
┃   ┃           ┃ ┗ 📂[runnerspackage]
┃   ┃           ┃   ┗ 📜[name].java

```


## Generate Rest Interaction
The **`generateRestInteraction | gri`** task will generate the rest interaction classes, this task has one required parameters `typeInteraction`
- **`typeInteraction`** `= interaction`: This parameter is going to specify the type of interaction to use. `field is required`
- **`nameInteraction`** `= Interaction`: This parameter is going to specify the name of interaction to use. `This field is required only when you choose` `GENERIC` `as type of interaction class`

```shell
  gradle generateRestInteraction --typeInteraction=[typeInteraction]
  gradle gri --typeInteraction=[typeInteraction] 
```

| Reference for **typeInteraction** | Name                |
|-----------------------------------|---------------------|
| Generic                           | Generic interaction |
| Post                              | Rest Post           |
| Get                               | Rest Get            |
| Options                           | Rest Options        |
| Patch                             | Rest Patch          |
| Put                               | Rest Put            |

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
 ┃ ┃             ┃ ┣ 📜[Post].java
 ┃ ┃             ┃ ┣ 📜[Get].java
 ┃ ┃             ┃ ┣ 📜[Put].java
 ┃ ┃             ┃ ┣ 📜[Options].java  
 ┃ ┃             ┃ ┣ 📜[Patch].java  
 ┃ ┃             ┃ ┣ 📜[nameInteraction].java
```

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
 ┃ ┃             ┃ ┣ 📜[Rest].java
 ┃ ┃             ┃ ┣ 📜[Ux].java
```

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

```bash
   ┣ 📜build.gradle
   ┣ 📜[name]_Build.gradle
   ┗ 📜settings.gradle
```
## Generate Critical Root
The **`generateCriticalRoot | gcr`** task will generate the Json file that containing the critical path feature,
this task has two required parameters `features` and `language`
- **`componentName`** `= component_name_test`: This parameter specify the project component name  `field is optional`
- **`features`** `= featureroot`: This parameter specify the feature root and name to extract the scenario separate for comma. `field is required`
- **`language`** `= languageFeature`: This parameter specify the language used in the Gherkin language into the feature file. `field is required`

```shell
  gradle generateCriticalRoot --componentName=component_test --features=root/feature_one,root/feature_two --language=EN 
  gradle gcr --componentName=component_test --features=root/feature_one,root/feature_two --language=EN
```

| Reference for **language** | Name    |
|----------------------------|---------|
| EN                         | English |
| ES                         | Spanish |


# How can I help?
Review the issues, we hear new ideas. Read more [Contributing](https://github.com/bancolombia/)

# Whats Next?
Read more  [About ScreenPlay Architecure]()