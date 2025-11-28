package com.commondnd.ui.inventory

import com.commondnd.ui.navigation.CommonNavigationGroup
import com.commondnd.ui.navigation.NavGraphRegistry

object InventoryScreen {

    const val Inventory = "Inventory"
}

fun NavGraphRegistry.registerInventoryScreens() {
    register(
        group = CommonNavigationGroup.UserScoped.Inventory,
        key = InventoryScreen.Inventory,
        content = { key, navController ->
            InventoryScreen()
        }
    )
}
