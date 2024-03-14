package sahak.sahakyan.musicappui

import android.icu.text.CaseMap.Title
import androidx.annotation.DrawableRes

sealed class Screen(
    val title: String,
    val route: String,
) {

    sealed class BottomScreen(
        val bTitle: String,
        val bRoute: String,
        @DrawableRes val icon: Int
    ) : Screen(bTitle,bRoute){
        data object Home: BottomScreen(
            "Home","home",R.drawable.baseline_home_24
        )
        data object Browse: BottomScreen(
            "Browse","browse",R.drawable.browse
        )
        data object Library: BottomScreen(
            "Library","library",R.drawable.library
        )
    }

    sealed class DrawerScreen(
        val dTitle: String,
        val dRoute: String,
        @DrawableRes val icon: Int
    ): Screen(dTitle,dRoute) {
        data object Account: DrawerScreen(
            "Account", "account", R.drawable.account_circle
        )
        data object Subscription: DrawerScreen(
            "Subscription", "subscribe", R.drawable.subscribe
        )
        data object AddAccount: DrawerScreen(
            "Add Account", "add_account", R.drawable.baseline_person_add_alt_1_24
        )
    }
}

val screenInDrawer = listOf(
    Screen.DrawerScreen.Account,
    Screen.DrawerScreen.Subscription,
    Screen.DrawerScreen.AddAccount
)

val screenInBottom = listOf(
    Screen.BottomScreen.Home,
    Screen.BottomScreen.Browse,
    Screen.BottomScreen.Library
)