package com.releaseshub.gradle.plugin.artifacts

class Artifact {

    var name: String? = null
    var groupId: String? = null
    var artifactId: String? = null
    var fromVersion: String? = null
    var toVersion: String? = null
    var sourceCodeUrl: String? = null
    var releaseNotesUrl: String? = null
    var documentationLinks: List<String>? = null

    constructor() { }

    constructor(groupId: String, artifactId: String, fromVersion: String) {
        this.groupId = groupId
        this.artifactId = artifactId
        this.fromVersion = fromVersion
    }

    fun match(includes : List<String>, excludes : List<String>) : Boolean {
        val includeMatches = includes.isEmpty() || includes.find { match(it) } != null
        return if (includeMatches) {
            excludes.find { match(it) } == null
        } else {
            false
        }
    }

    private fun match(expression: String) : Boolean {
        val split = expression.split(":")
        val groupIdToMatch = split[0]
        val artifactIdToMatch = if (split.size > 1) split[1] else null
        return groupIdToMatch == groupId && (artifactIdToMatch == null || artifactIdToMatch == artifactId)
    }

    override fun toString(): String {
        return "$groupId:$artifactId:$fromVersion"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Artifact

        if (groupId != other.groupId) return false
        if (artifactId != other.artifactId) return false
        if (fromVersion != other.fromVersion) return false
        if (toVersion != other.toVersion) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + (groupId?.hashCode() ?: 0)
        result = 31 * result + (artifactId?.hashCode() ?: 0)
        result = 31 * result + (fromVersion?.hashCode() ?: 0)
        result = 31 * result + (toVersion?.hashCode() ?: 0)
        return result
    }
}