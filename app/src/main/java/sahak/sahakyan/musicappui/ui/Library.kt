package sahak.sahakyan.musicappui.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import sahak.sahakyan.musicappui.Lib
import sahak.sahakyan.musicappui.R
import sahak.sahakyan.musicappui.libraries

@Composable
fun Library() {
    LazyColumn(
        modifier = Modifier
         .fillMaxSize()
    ) {
        items(libraries) {
            lib->
            LibItem(lib)

        }
    }
}

@Composable
fun LibItem(lib:Lib) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8 .dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            ) {
            Row(modifier = Modifier.padding(top = 16.dp)) {
                Icon(
                    painter = painterResource (id = lib.icon),
                    contentDescription = null,
                    modifier = Modifier.padding(end = 8.dp)
                )
                Text(text = lib.name)
            }
            IconButton(onClick = {

            }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = null
                )
            }
        }
        Divider()
    }
}