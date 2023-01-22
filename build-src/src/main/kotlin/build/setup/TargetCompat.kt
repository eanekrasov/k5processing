package build.setup

import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension as KMP

fun KMP.iosCompat(
    x64: String? = DEFAULT_TARGET_NAME,
    arm64: String? = DEFAULT_TARGET_NAME,
    simulatorArm64: String? = DEFAULT_TARGET_NAME,
) {
    enableTarget(x64, { iosX64() }, { iosX64(it) })
    enableTarget(arm64, { iosArm64() }, { iosArm64(it) })
    enableTarget(simulatorArm64, { iosSimulatorArm64() }, { iosSimulatorArm64(it) })
}

fun KMP.watchosCompat(
    x64: String? = DEFAULT_TARGET_NAME,
    arm32: String? = DEFAULT_TARGET_NAME,
    arm64: String? = DEFAULT_TARGET_NAME,
    simulatorArm64: String? = DEFAULT_TARGET_NAME,
) {
    enableTarget(x64, { watchosX64() }, { watchosX64(it) })
    enableTarget(arm32, { watchosArm32() }, { watchosArm32(it) })
    enableTarget(arm64, { watchosArm64() }, { watchosArm64(it) })
    enableTarget(simulatorArm64, { watchosSimulatorArm64() }, { watchosSimulatorArm64(it) })
}

fun KMP.tvosCompat(
    x64: String? = DEFAULT_TARGET_NAME,
    arm64: String? = DEFAULT_TARGET_NAME,
    simulatorArm64: String? = DEFAULT_TARGET_NAME,
) {
    enableTarget(x64, { tvosX64() }, { tvosX64(it) })
    enableTarget(arm64, { tvosArm64() }, { tvosArm64(it) })
    enableTarget(simulatorArm64, { tvosSimulatorArm64() }, { tvosSimulatorArm64(it) })
}

fun KMP.macosCompat(
    x64: String? = DEFAULT_TARGET_NAME,
    arm64: String? = DEFAULT_TARGET_NAME,
) {
    enableTarget(x64, { macosX64() }, { macosX64(it) })
    enableTarget(arm64, { macosArm64() }, { macosArm64(it) })
}

private fun KMP.enableTarget(
    name: String?,
    enableDefault: KMP.() -> Unit,
    enableNamed: KMP.(String) -> Unit,
) = when (name) {
    null -> Unit
    DEFAULT_TARGET_NAME -> enableDefault()
    else -> enableNamed(name)
}

private const val DEFAULT_TARGET_NAME = "DEFAULT_TARGET_NAME"
