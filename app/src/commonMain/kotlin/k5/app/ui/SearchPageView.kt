package k5.app.ui

//import androidx.compose.foundation.layout.Arrangement.spacedBy
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.padding
//import androidx.compose.material.Surface
//import androidx.compose.material.icons.Icons.Default
//import androidx.compose.material.icons.filled.Code
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.ui.Alignment.Companion.Center
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.unit.dp
//import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
//import k5.app.ui.AppTheme.colors
//import k5.app.ui.ResponseState.Empty
//import k5.app.ui.ResponseState.Error
//import k5.app.ui.ResponseState.Processing
//import k5.app.ui.ResponseState.Success
//
//@Composable
//fun SearchPageView(component: SearchPage, modifier: Modifier = Modifier) {
//    Surface(modifier, color = colors.backgroundDark) {
//        Column {
//            val model by component.models.subscribeAsState()
//            SearchField(model.query, component::onQueryChanged, Modifier.fillMaxWidth().padding(16.dp), component::onSearchRequested)
//            when (val s = model.state) {
//                is Processing -> Box(Modifier.weight(1f).fillMaxSize(), Center) { Loading() }
//                is Success -> Matches(s.matches, Modifier.weight(1f).fillMaxSize()) { component.downloadJar(it) }
//                is Error -> TextView(s.message, Modifier.weight(1f))
//                is Empty -> TextView("Hit enter to perform search...", Modifier.weight(1f))
//            }
//        }
//    }
//}
//
//@Composable
//fun SearchPageView(state: SearchPageState, modifier: Modifier = Modifier) {
//    Surface(modifier, color = colors.backgroundDark) {
//        Column {
//            SearchField(state.query, state.onQueryChanged, Modifier.fillMaxWidth().padding(16.dp), state.onSearchRequested)
//            when (val s = state.state) {
//                is Processing -> Box(Modifier.weight(1f).fillMaxSize(), Center) { Loading() }
//                is Success -> Matches(s.matches, Modifier.weight(1f).fillMaxSize()) { state.downloadJar(it) }
//                is Error -> TextView(s.message, Modifier.weight(1f))
//                is Empty -> TextView("Hit enter to perform search...", Modifier.weight(1f))
//            }
//        }
//    }
//}
//
//@Composable
//private fun TextView(text: String, modifier: Modifier = Modifier) {
//    Box(modifier.fillMaxSize(), Center) {
//        Row(Modifier.height(1.6f), spacedBy(8.dp)) {
//            Icon(Default.Code, null)
//            ScaledText(text)
//        }
//    }
//}
