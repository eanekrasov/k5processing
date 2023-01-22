package k5.examples

import androidx.compose.foundation.background
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.loadImageBitmap
import androidx.compose.ui.res.useResource
import k5.K5
import k5.math.map
import k5.ui.onPointerMove

fun K5.showSkyline() {
    /* @pjs preload="cockpit.png,cityscape1.jpg"; */

    /**
    INSTRUCTIONS <br>
    Click on the sketch to make it active. <br>
    Horizontal position of mouse controls view.<br><br>
    KEYBOARD LEGEND <br>
    C Hide / show cockpit <br>
    S Hide / show skyline <br>
     */

    // size(512, 320)
    val w = 512f
    val sky = useResource("cityscape1.jpg", ::loadImageBitmap)
    val cockpit = useResource("cockpit.png", ::loadImageBitmap)
    var vx = (sky.width - w) / 2f
    val showSky = true
    val showCockpit = true
    // keyReleased: if (key == 's') showSky = !showSky, if (key == 'c') showCockpit = !showCockpit;
    show(modifier = Modifier.background(Color(0xFFEEEEEE)).onPointerMove { x, _ ->
        vx = map(x, 0f, w, 0f, sky.width - w)
    }) {
        // background(240);
        if (showSky) drawImage(sky, Offset(-vx, 0f))
        if (showCockpit) drawImage(cockpit)
    }
}
