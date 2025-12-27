package com.commondnd.ui.characters

import com.commondnd.ui.navigation.NavGraphRegistry

object CharactersScreen {

    const val Characters = "Characters"
    const val Details = "Details"
}

fun NavGraphRegistry.registerCharactersScreens() {
    register(
        key = CharactersScreen.Characters,
        content = { key, navController ->
            CharactersScreen { navController.push(CharactersScreen.Details) }
        }
    )
    register(
        key = CharactersScreen.Details,
        content = { key, navController ->
            DetailsScreen()
        }
    )
}
