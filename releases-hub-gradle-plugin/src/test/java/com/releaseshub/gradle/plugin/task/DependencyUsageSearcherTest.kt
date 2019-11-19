package com.releaseshub.gradle.plugin.task

import com.releaseshub.gradle.plugin.artifacts.ArtifactUpgrade
import com.releaseshub.gradle.plugin.artifacts.Packaging
import com.releaseshub.gradle.plugin.common.ResourceUtils
import org.junit.Assert
import org.junit.Test
import java.io.File

class DependencyUsageSearcherTest {

    @Test
    fun noPackages() {
        val artifactUpgrade = createArtifactUpgrade()
        Assert.assertTrue(createDependencyUsageSearcher().isAnyPackageUsed(artifactUpgrade))
    }

    @Test
    fun usedOnKotlin() {
        val artifactUpgrade = createArtifactUpgrade("a.b.c", "j.k.l")
        Assert.assertTrue(createDependencyUsageSearcher().isAnyPackageUsed(artifactUpgrade))
    }

    @Test
    fun usedOnKotlin2() {
        val artifactUpgrade = createArtifactUpgrade("m.n.o")
        Assert.assertTrue(createDependencyUsageSearcher().isAnyPackageUsed(artifactUpgrade))
    }

    @Test
    fun usedOnJava() {
        val artifactUpgrade = createArtifactUpgrade("d.e.f", "j.k.l")
        Assert.assertTrue(createDependencyUsageSearcher().isAnyPackageUsed(artifactUpgrade))
    }

    @Test
    fun usedOnXml() {
        val artifactUpgrade = createArtifactUpgrade("g.h.i", "j.k.l")
        Assert.assertTrue(createDependencyUsageSearcher().isAnyPackageUsed(artifactUpgrade))
    }

    @Test
    fun notUsed() {
        val artifactUpgrade = createArtifactUpgrade("j.k.l")
        Assert.assertFalse(createDependencyUsageSearcher().isAnyPackageUsed(artifactUpgrade))
    }

    private fun createArtifactUpgrade(vararg packages: String): ArtifactUpgrade {
        val artifactUpgrade = ArtifactUpgrade()
        artifactUpgrade.packaging = Packaging.JAR
        artifactUpgrade.packages = packages.asList()
        return artifactUpgrade
    }

    private fun createDependencyUsageSearcher(): DependencyUsageSearcher {
        return DependencyUsageSearcher(listOf(
            File(ResourceUtils.getRequiredResourcePath("dependency_usage_1")),
            File(ResourceUtils.getRequiredResourcePath("dependency_usage_2"))
        ))
    }
}