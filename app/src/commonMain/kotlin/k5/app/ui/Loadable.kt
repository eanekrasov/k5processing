package k5.app.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope

private val loadingKey = Any()

@Composable
fun <T : Any> loadable(load: CoroutineScope.() -> T): State<T?> = remember { mutableStateOf<T?>(null) }.apply {
    LaunchedEffect(loadingKey) {
        try {
            value = load()
        } catch (e: CancellationException) {
            // ignore
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
