package sahak.sahakyan.musicappui.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import sahak.sahakyan.musicappui.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainView(

) {

    /*
    *   And why do we need something called a scaffold state,
    *   which takes care of the state of the scaffold entirely(ամբողջությամբ).
    *   Basically the scaffold is the parent of UI element or the view for the entire page,
    *   which contains the top bar, which contains the content, which contains the bottom bar and etc.
    *
    * */
    val scaffoldState: ScaffoldState = rememberScaffoldState()
    /*
    * As I said, opening and closing the drawer is a coroutine method (suspend function).
    * So that's why we need to create a CoroutineScope, which will allow us to launch suspend function (opening drawer)
    * */
    val scope: CoroutineScope = rememberCoroutineScope()

    Scaffold(
        modifier = Modifier,
        topBar = {
             TopAppBar(
                 title = { Text("Home") },
                 navigationIcon = {
                     IconButton(onClick = {
                        /* TODO OPEN THE DRAWER*/
                         /* Opening a drawer is synchronize method(գործողություն), it's a suspend function that happens. ---(Look at 27 line)---
                         * */
                         /*
                         *  So this one will be our open drawer launch where I'm just going to use the scope to launch the following code.
                         *  I'm going to use the scaffold state and then access the drawer state and say open.
                         * */
                         scope.launch {
                             // So instead of scaffold state, there is this property called draw state, which you can use in order to open the draw.
                             scaffoldState.drawerState.open()
                         }
                     }) {
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = null
                        )
                    }
                 }
             )
        },
        bottomBar = {},
        snackbarHost = {},
        floatingActionButton = {},

    ) {
        Text(text = "Text", modifier = Modifier.padding(it))
    }
}

@Composable
fun DrawerItem(
    selected: Boolean,
    item: Screen.DrawerScreen,
    onDrawerItemClicked: () -> Unit,
) {

    val background = if (selected) Color.DarkGray else Color.White

    Row(
        Modifier.fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 16.dp).background(background)
            .clickable {
                onDrawerItemClicked()
            }
    ) {
        androidx.compose.material.Icon(
            painter = painterResource(id = item.icon),
            contentDescription = item.dTitle,
            Modifier.padding(end = 8.dp, top = 4.dp)
        )
        Text(
            text = item.dTitle,
            style = MaterialTheme.typography.h5,
        )
    }
}