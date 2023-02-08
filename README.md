# Reproducer for a Gradle bug in evaluating Gradle property providers in included builds with configuration cache

Execute `./gradlew myConsumingTask`

It works fine

<hr/>

Execute `./gradlew myConsumingTask --configuration-cache` twice

It works fine on the first run, but fails on the second with
```
Execution failed for task ':included-build:app:myProducingTask'.
> Error while evaluating property 'myProp' of task ':included-build:app:myProducingTask'
   > GradleProperties has not been loaded yet.
```