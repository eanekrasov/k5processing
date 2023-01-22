package build.setup

import org.gradle.api.Project
import org.gradle.kotlin.dsl.the
import org.gradle.accessors.dm.LibrariesForLibs

internal val Project.libs get() = the<LibrariesForLibs>()
