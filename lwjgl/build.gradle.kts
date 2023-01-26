plugins {
    id("build.jvm")
}

application {
    mainClass.set("k5.lwjgl.MainKt")
}

dependencies {
    implementation(libs.skiko.macos.x64)
    implementation(libs.joml)
    implementation(platform(libs.lwjgl.bom))
    listOf(
        "lwjgl-cuda", "lwjgl-egl", "lwjgl-jawt", "lwjgl-odbc", "lwjgl-opencl",
        "lwjgl", "lwjgl-assimp", "lwjgl-bgfx", "lwjgl-glfw", "lwjgl-nanovg", "lwjgl-nuklear", "lwjgl-openal", "lwjgl-opengl", "lwjgl-par", "lwjgl-stb", "lwjgl-vulkan",
        "lwjgl-jemalloc", "lwjgl-libdivide", "lwjgl-llvm", "lwjgl-lmdb", "lwjgl-lz4", "lwjgl-meow", "lwjgl-meshoptimizer", "lwjgl-nfd",
        "lwjgl-opengles", "lwjgl-openvr", "lwjgl-opus", "lwjgl-remotery", "lwjgl-rpmalloc", "lwjgl-shaderc", "lwjgl-spvc",
        "lwjgl-sse", "lwjgl-tinyexr", "lwjgl-tinyfd", "lwjgl-tootle", "lwjgl-vma", "lwjgl-xxhash", "lwjgl-yoga", "lwjgl-zstd",
    ).forEach { implementation("org.lwjgl", it) }
    listOf(
        "lwjgl", "lwjgl-assimp", "lwjgl-bgfx", "lwjgl-glfw", "lwjgl-nanovg", "lwjgl-nuklear", "lwjgl-openal", "lwjgl-opengl", "lwjgl-par", "lwjgl-stb", "lwjgl-vulkan",
        "lwjgl-jemalloc", "lwjgl-libdivide", "lwjgl-llvm", "lwjgl-lmdb", "lwjgl-lz4", "lwjgl-meow", "lwjgl-meshoptimizer", "lwjgl-nfd",
        "lwjgl-opengles", "lwjgl-openvr", "lwjgl-opus", "lwjgl-remotery", "lwjgl-rpmalloc", "lwjgl-shaderc", "lwjgl-spvc",
        "lwjgl-sse", "lwjgl-tinyexr", "lwjgl-tinyfd", "lwjgl-tootle", "lwjgl-vma", "lwjgl-xxhash", "lwjgl-yoga", "lwjgl-zstd",
    ).forEach { runtimeOnly("org.lwjgl", it, classifier = "natives-macos") }

}
