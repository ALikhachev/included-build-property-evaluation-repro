group = "org.example"
version = "1.0-SNAPSHOT"

abstract class MyConsumingTask : DefaultTask() {
    @get:InputFiles
    abstract val someInput: ConfigurableFileCollection

    @TaskAction
    fun doSomething() {
        someInput.forEach {
            println(it.readText())
        }
    }
}

val myAttribute = Attribute.of("my-attribute", String::class.java)

val consumingConfiguration by configurations.creating {
    isCanBeResolved = true
    isCanBeConsumed = false
    attributes {
        attribute(myAttribute, "myValue")
    }
}

dependencies {
    consumingConfiguration("included-build:app")
}

val myConsumingTask by tasks.registering(MyConsumingTask::class) {
    someInput.from(consumingConfiguration)
}