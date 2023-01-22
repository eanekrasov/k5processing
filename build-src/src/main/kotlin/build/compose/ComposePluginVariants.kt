package build.compose

import org.jetbrains.compose.ComposeCompilerKotlinSupportPlugin
import org.jetbrains.kotlin.gradle.plugin.KotlinCompilation
import org.jetbrains.kotlin.gradle.plugin.KotlinCompilerPluginSupportPlugin
import org.jetbrains.kotlin.gradle.plugin.KotlinPlatformType
import org.jetbrains.kotlin.gradle.plugin.KotlinPlatformType.native

class ComposeUikitOnlyPlugin : KotlinCompilerPluginSupportPlugin by ComposeCompilerKotlinSupportPlugin() {
    private fun KotlinCompilation<*>.isNonKitNative() = target.platformType == native && !target.name.startsWith("uikit")
    override fun isApplicable(kotlinCompilation: KotlinCompilation<*>) = when {
        kotlinCompilation.isNonKitNative() -> false
        else -> ComposeCompilerKotlinSupportPlugin().isApplicable(kotlinCompilation)
    }
}

class ComposAndroidOnlyPlugin : KotlinCompilerPluginSupportPlugin by ComposeCompilerKotlinSupportPlugin() {
    override fun isApplicable(kotlinCompilation: KotlinCompilation<*>) = when {
        kotlinCompilation.target.platformType != KotlinPlatformType.androidJvm -> false
        else -> ComposeCompilerKotlinSupportPlugin().isApplicable(kotlinCompilation)
    }
}

class ComposeNoNativePlugin : KotlinCompilerPluginSupportPlugin by ComposeCompilerKotlinSupportPlugin() {
    override fun isApplicable(kotlinCompilation: KotlinCompilation<*>) = when (kotlinCompilation.target.platformType) {
        native -> false
        else -> ComposeCompilerKotlinSupportPlugin().isApplicable(kotlinCompilation)
    }
}
