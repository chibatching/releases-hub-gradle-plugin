package com.releaseshub.gradle.plugin.task

import com.releaseshub.gradle.plugin.common.AbstractTask

open class ListDependenciesTask : AbstractTask() {

    companion object {
        const val TASK_NAME = "listDependencies"
    }

    init {
        description = "List all dependencies"
    }

    override fun onExecute() {

        getExtension().validateDependenciesClassNames()

        val dependenciesParserResult = DependenciesExtractor.extractArtifacts(project.rootProject.projectDir, dependenciesBasePath!!, dependenciesClassNames!!, includes, excludes)
        dependenciesParserResult.artifactsMap.forEach { (file, artifacts) ->
            if (artifacts.isNotEmpty()) {
                log(file)
                artifacts.forEach { artifact ->
                    log(" - $artifact:${artifact.fromVersion}")
                }
                log("")
            }
        }
    }
}
