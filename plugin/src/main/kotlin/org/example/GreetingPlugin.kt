package org.example

import org.gradle.api.Project
import org.gradle.api.Plugin

open class GreetingExtension {
    lateinit var message: String
}

class GreetingPlugin: Plugin<Project> {
    override fun apply(project: Project) {
        val extension = project.extensions.create("greeting", GreetingExtension::class.java)
        project.tasks.register("sayGreeting") { task ->
            task.doLast {
                println(extension.message)
            }
        }

        project.tasks.register("hiGreeting") { task ->
            task.doLast {
                println("Hello from plugin 'org.example.greeting'")
            }
        }
    }
}
