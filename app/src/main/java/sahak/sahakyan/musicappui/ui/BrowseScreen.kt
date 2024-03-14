package sahak.sahakyan.musicappui.ui

import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.lazy.grid.GridCells
import sahak.sahakyan.musicappui.R

@Composable
fun BrowseScreen() {
    val categories = listOf<String>("Hits", "Happy", "Workout", "Running", "TGIF", "Yoga")

    LazyVerticalGrid(columns = GridCells.Fixed(2)) {
        items(categories) {
            cat ->
            BrowseItem(cat = cat, drawable = R.drawable.browse)
        }
    }
}