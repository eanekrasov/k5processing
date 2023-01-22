package k5.app

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.arkivanov.decompose.defaultComponentContext
import com.sample.viewer.platform.asFile
import com.sample.viewer.ui.AppTheme
import com.sample.viewer.ui.CodeViewerComponent
import com.sample.viewer.ui.CodeViewerState
import com.sample.viewer.ui.CodeViewerView
import com.sample.viewer.ui.PagesState
import com.sample.viewer.ui.asTreeItem

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component()
    }

    private fun state() {
        val pages = PagesState()
        val state = CodeViewerState(pages, filesDir.asFile().asTreeItem { pages.viewer(it.file) })
        setContent { AppTheme { CodeViewerView(state) } }
    }

    private fun component() {
        val component = CodeViewerComponent(defaultComponentContext(), filesDir.asFile())
        setContent { AppTheme { CodeViewerView(component) } }
    }
}
