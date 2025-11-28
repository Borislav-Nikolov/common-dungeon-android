package com.commondnd

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DirectionsCar
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material.icons.rounded.PeopleOutline
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import com.commondnd.ui.navigation.CommonNavigationGroup

internal fun iconMap(group: CommonNavigationGroup.UserScoped): ImageVector =
    when(group) {
        CommonNavigationGroup.UserScoped.Home -> Icons.Rounded.Home
        CommonNavigationGroup.UserScoped.Characters -> Icons.Rounded.PeopleOutline
        CommonNavigationGroup.UserScoped.Inventory -> Icons.Rounded.DirectionsCar
        CommonNavigationGroup.UserScoped.More -> Icons.Rounded.MoreVert
    }

@Composable
internal fun labelMap(group: CommonNavigationGroup.UserScoped): String =
    when(group) {
        CommonNavigationGroup.UserScoped.Home -> "stringResource(R.string.label_home)"
        CommonNavigationGroup.UserScoped.Characters -> "Characters"
        CommonNavigationGroup.UserScoped.Inventory -> "Inventory"
        CommonNavigationGroup.UserScoped.More -> "More"
    }
