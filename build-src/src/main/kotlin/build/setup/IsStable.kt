package build.setup

//https://github.com/ben-manes/gradle-versions-plugin#rejectversionsif-and-componentselection

val stableKeywords = listOf("RELEASE", "FINAL", "GA")
val stableRegex = "^[0-9,.v-]+(-r)?$".toRegex()

fun String.isStable() = stableKeywords.any { contains(it, true) } || stableRegex.matches(this)
