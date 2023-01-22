package k5.app.ui

//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.setValue
//
//class TreeItem(val file: k5.app.platform.File, val onClick: (TreeItem) -> Unit, val level: Int = 0) {
//    var children by mutableStateOf(listOf<TreeItem>())
//    fun toggle() {
//        children = if (children.isEmpty()) file.children.map { TreeItem(it, onClick, level + 1) }
//            .sortedWith(compareBy({ it.file.isDirectory }, { it.file.name })).sortedBy { !it.file.isDirectory } else emptyList()
//    }
//
//    fun toItems(): List<TreeItem> {
//        fun TreeItem.addTo(list: MutableList<TreeItem>) {
//            list.add(this)
//            for (child in children) child.addTo(list)
//        }
//        return mutableListOf<TreeItem>().also(::addTo)
//    }
//    val name get() = file.name
//    fun open() = when {
//        file.isDirectory -> toggle()
//        else -> onClick(this)
//    }
//}
//
//fun k5.app.platform.File.asTreeItem(onClick: (TreeItem) -> Unit) = TreeItem(this, onClick).apply { toggle() }
