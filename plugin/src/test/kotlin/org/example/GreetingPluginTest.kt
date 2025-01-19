package org.example

import org.gradle.testkit.runner.GradleRunner
import org.gradle.testkit.runner.TaskOutcome.FAILED
import org.gradle.testkit.runner.TaskOutcome.SUCCESS
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.io.TempDir
import java.io.File

class GreetingPluginTest {
    @TempDir
    lateinit var testProjectDir: File
    private lateinit var settingsFile: File
    private lateinit var buildFile: File

    @BeforeEach
    fun setup() {
        settingsFile = File(testProjectDir, "settings.gradle.kts")
        buildFile = File(testProjectDir, "build.gradle.kts")
    }

    @org.junit.jupiter.api.Test
    fun `prints the value defined in the extension to the cmdline`() {
        val buildFileContent = """
         plugins {
            id("org.example.greeting")
         }
         configure<org.example.GreetingExtension>{
            message = "Hello World!"
         }
      """.trimIndent()

        buildFile.writeText(buildFileContent)

        val result = GradleRunner.create()
            .withProjectDir(testProjectDir)
            .withArguments("sayGreeting")
            .withPluginClasspath()
            .forwardOutput()
            .build()

        assertTrue(result.output.contains("Hello World!"))
        assertEquals(SUCCESS, result.task(":sayGreeting")?.outcome ?: FAILED)
    }
}
