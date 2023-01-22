package k5.app.ui

//import androidx.compose.foundation.text.selection.SelectionContainer
//import androidx.compose.material.Surface
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.ui.Modifier
//import k5.app.ui.AppTheme.colors
//
//@Composable
//fun FilePageView(
//    component: FilePage,
//    modifier: Modifier = Modifier
//) {
//    SelectionContainer(modifier) {
//        Surface(color = colors.backgroundDark) {
//            when (val lines = loadable(component.lines).value) {
//                null -> Loading()
//                else -> Lines(lines)
//            }
//        }
//    }
//}
//
//@Composable
//fun FilePageView(
//    state: FilePageState,
//    modifier: Modifier = Modifier
//) {
//    SelectionContainer(modifier) {
//        val lines by loadable(state.lines)
//        when (lines) {
//            null -> Loading()
//            else -> Lines(lines!!)
//        }
//    }
//}
