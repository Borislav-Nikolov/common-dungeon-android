package com.commondnd.ui.characters

import com.commondnd.ui.navigation.CommonNavigationGroup
import com.commondnd.ui.navigation.NavGraphRegistry

object CharactersScreen {

    const val Characters = "Characters"
    const val Details = "Details"
}

fun NavGraphRegistry.registerCharactersScreens() {
    register(
        group = CommonNavigationGroup.UserScoped.Characters,
        key = CharactersScreen.Characters,
        content = { key, navController ->
            CharactersScreen { navController.navigate(CharactersScreen.Characters) }
        }
    )
    register(
        group = CommonNavigationGroup.UserScoped.Characters,
        key = CharactersScreen.Details,
        content = { key, navController ->
            DetailsScreen()
        }
    )
}
