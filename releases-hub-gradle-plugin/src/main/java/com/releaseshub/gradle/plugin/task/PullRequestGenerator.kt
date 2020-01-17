package com.releaseshub.gradle.plugin.task

import com.releaseshub.gradle.plugin.core.FileSizeFormatter

object PullRequestGenerator {

    private const val PULL_REQUEST_FOOTER = "This pull request was automatically generated by **[Releases Hub Gradle Plugin](https://github.com/releaseshub/releases-hub-gradle-plugin)**"

    fun createBody(upgradeResults: List<UpgradeResult>): String {
        val builder = StringBuilder()
        builder.appendln("## Dependencies upgrades")
        addCommonText(builder, upgradeResults)
        builder.appendln()
        builder.append("---")
        builder.appendln()
        builder.append(PULL_REQUEST_FOOTER)
        return builder.toString()
    }

    private fun addCommonText(builder: StringBuilder, upgradeResults: List<UpgradeResult>) {
        upgradeResults.forEach {
            builder.appendln("#### ${it.artifactUpgrade} `${it.artifactUpgrade?.fromVersion}` -> `${it.artifactUpgrade?.toVersion}`")
            var atLeastOneItem = false

            if (it.artifactUpgrade?.toSize != null) {
                builder.append("* Size: ${FileSizeFormatter.format(it.artifactUpgrade.toSize!!)}")
            }
            if (!it.artifactUpgrade?.toAndroidPermissions.isNullOrEmpty()) {
                builder.append("* Android permissions: ${it.artifactUpgrade?.toAndroidPermissions}")
            }
            if (it.artifactUpgrade?.releaseNotesUrl != null) {
                builder.append("* [Releases notes](${it.artifactUpgrade.releaseNotesUrl})")
                atLeastOneItem = true
            }
            if (it.artifactUpgrade?.sourceCodeUrl != null) {
                builder.append(if (atLeastOneItem) " | " else "* ")
                builder.append("[Source code](${it.artifactUpgrade.sourceCodeUrl})")
                atLeastOneItem = true
            }
            if (it.artifactUpgrade?.documentationUrl != null) {
                builder.append(if (atLeastOneItem) " | " else "* ")
                builder.append("[Documentation](${it.artifactUpgrade.documentationUrl})")
                atLeastOneItem = true
            }
            if (it.artifactUpgrade?.issueTrackerUrl != null) {
                builder.append(if (atLeastOneItem) " | " else "* ")
                builder.append("[Issue tracker](${it.artifactUpgrade.issueTrackerUrl})")
                atLeastOneItem = true
            }
            if (atLeastOneItem) {
                builder.appendln()
            }
        }
    }

    fun createComment(upgradeResults: List<UpgradeResult>): String {
        val builder = StringBuilder()
        addCommonText(builder, upgradeResults)
        return builder.toString()
    }
}
