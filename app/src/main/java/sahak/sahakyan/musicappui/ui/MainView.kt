package sahak.sahakyan.musicappui.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.ScaffoldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.primarySurface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import sahak.sahakyan.musicappui.MainViewModel
import sahak.sahakyan.musicappui.R
import sahak.sahakyan.musicappui.Screen
import sahak.sahakyan.musicappui.screenInBottom
import sahak.sahakyan.musicappui.screenInDrawer

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun MainView(

) {

    /*
    *   We need something called a scaffold state,
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

    val viewModel: MainViewModel = viewModel()

    // Allow us to find out on which "View" we are currently
    val controller: NavController = rememberNavController()
    val navBackStackEntry by controller.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route


    val currentScreen = remember {
        viewModel.currentScreen.value
    }

    val dialogOpen = remember {
        mutableStateOf(false)
    }

    val title = remember {
        mutableStateOf(currentScreen.title)
    }

    val bottomBar: @Composable () -> Unit = {
        if(currentScreen is Screen.DrawerScreen || currentScreen == Screen.BottomScreen.Home) {
            BottomNavigation(Modifier.wrapContentSize()) {
                screenInBottom.forEach{
                    item->
                    val isSelected = currentRoute == item.bRoute
                    val tint = if (isSelected) Color.White else Color.Black
                    BottomNavigationItem(
                        selected = currentRoute == item.bRoute,
                        onClick = {
                            controller.navigate(item.bRoute)
                            title.value = item.bTitle
                        },
                        icon = {
                            Icon(
                                tint = tint,
                                painter = painterResource(id = item.icon),
                                contentDescription = null
                            )
                        },
                        label = {
                            Text(text = item.bTitle, color = tint)
                        },
                        selectedContentColor = Color.White,
                        unselectedContentColor = Color.Black
                    )
                }
            }
        }
    }

    val isSheetFullScreen by remember {
        mutableStateOf(false)
    }
    val modifier = if (isSheetFullScreen) Modifier.fillMaxSize() else Modifier.fillMaxWidth()

    val modalSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmValueChange = { it != ModalBottomSheetValue.HalfExpanded }
    )

    val roundedCornerRadius = if (isSheetFullScreen) 0.dp else 12.dp

    ModalBottomSheetLayout(
        sheetState = modalSheetState,
        sheetShape = RoundedCornerShape(topStart = roundedCornerRadius, topEnd = roundedCornerRadius),
        sheetContent = {
            MoreButtonSheet(modifier = modifier)
        },
    ) {
        Scaffold(
            modifier = Modifier,
            topBar = {
                TopAppBar(
                    title = { Text(title.value) },
                    navigationIcon = {
                        IconButton(onClick = {
                            /* TODO OPEN THE DRAWER*/
                            /* Opening a drawer is synchronize method(գործողություն), it's a suspend function that happens. ---(Look at  *We need something called a scaffold state,...)---
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
                    },
                    actions = {
                        IconButton(onClick = {
                            scope.launch {
                                if(modalSheetState.isVisible)
                                    modalSheetState.hide()
                                else
                                    modalSheetState.show()
                            }
                        }) {
                            Icon(
                                imageVector = Icons.Default.MoreVert,
                                contentDescription = null
                            )
                        }
                    }
                )
            },
            scaffoldState = scaffoldState,
            drawerContent = {
                LazyColumn(
                    modifier = Modifier.padding(16.dp)
                ) {
                    items(screenInDrawer) { item ->
                        DrawerItem(selected = currentRoute == item.dRoute, item = item) {
                            scope.launch {
                                scaffoldState.drawerState.close()
                            }
                            if(item.dRoute == "add_account") {
                                dialogOpen.value = true
                            } else {
                                controller.navigate(item.dRoute)
                                title.value = item.dTitle
                            }
                        }
                    }
                }
            },
            bottomBar = bottomBar
        ) {
            Navigation(navController = controller, viewModel = viewModel, pd = it)
            AccountDialog(dialogOpen = dialogOpen)
        }
    }
}

@Composable
fun MoreButtonSheet(modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .background(MaterialTheme.colors.primarySurface)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
            ) {
                Icon(painter = painterResource(id = R.drawable.baseline_settings_24), contentDescription = null)
                Text(text = "Settings", fontSize = 20.sp, color = Color.White)
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
            ) {
                Icon(painter = painterResource(id = R.drawable.ic_baseline_share_24), contentDescription = null)
                Text(text = "Share", fontSize = 20.sp, color = Color.White)
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
            ) {
                Icon(painter = painterResource(id = R.drawable.ic_help_green), contentDescription = null)
                Text(text = "Help", fontSize = 20.sp, color = Color.White)
            }
        }
    }
    TODO("Not yet implemented")
}

@Composable
fun DrawerItem(
    selected: Boolean,
    item: Screen.DrawerScreen,
    onDrawerItemClicked: () -> Unit,
) {

    val background = if (selected) Color.DarkGray else Color.White

    Row(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 16.dp)
            .background(background)
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

@Composable
fun Navigation(
    navController: NavController,
    viewModel: MainViewModel,
    pd: PaddingValues
) {
    NavHost(
        navController = navController as NavHostController,
        startDestination = Screen.DrawerScreen.Account.route,
        modifier = Modifier.padding(pd),
    ) {
        composable(Screen.DrawerScreen.Account.route) {
            AccountView()
        }
        composable(Screen.DrawerScreen.Subscription.route) {
            Subscription()
        }

        composable(Screen.BottomScreen.Home.bRoute) {
            Home()
        }

        composable(Screen.BottomScreen.Browse.bRoute) {
            BrowseScreen()
        }

        composable(Screen.BottomScreen.Library.bRoute) {
            Library()
        }
    }
}