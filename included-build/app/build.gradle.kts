val myAttribute = Attribute.of("my-attribute", String::class.java)

val producingConfiguration by configurations.registering {
    isCanBeResolved = false
    isCanBeConsumed = true
    attributes {
        attribute(myAttribute, "myValue")
    }
}

abstract class MyProducingTask : DefaultTask() {
    @get:Input
    @get:Optional
    abstract val myProp: Property<String>

    @get:OutputFile
    abstract val someOutput: RegularFileProperty

    @TaskAction
    fun doSomething() {
        someOutput.get().asFile.writeText("myProp value is ${myProp.orNull}")
    }
}

val myProducingTask by tasks.registering(MyProducingTask::class) {
    myProp.set(project.providers.gradleProperty("myProp"))
    someOutput.set(project.layout.buildDirectory.file("output.txt"))
}

artifacts {
    add(producingConfiguration.name, myProducingTask)
}