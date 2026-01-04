package com.commondnd

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DirectionsCar
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material.icons.rounded.PeopleOutline
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource

@Composable
internal fun iconMap(group: CommonNavigationGroup.UserScoped): Painter =
    when(group) {
        CommonNavigationGroup.UserScoped.Home -> rememberVectorPainter(Icons.Rounded.Home)
        CommonNavigationGroup.UserScoped.Characters -> rememberVectorPainter(Icons.Rounded.PeopleOutline)
        CommonNavigationGroup.UserScoped.Inventory -> painterResource(R.drawable.ic_inventory)
        CommonNavigationGroup.UserScoped.More -> rememberVectorPainter(Icons.Rounded.MoreVert)
    }

@Composable
internal fun labelMap(group: CommonNavigationGroup.UserScoped): String =
    when(group) {
        CommonNavigationGroup.UserScoped.Home -> stringResource(com.commondnd.ui.home.R.string.label_home)
        CommonNavigationGroup.UserScoped.Characters -> stringResource(com.commondnd.ui.characters.R.string.label_characters)
        CommonNavigationGroup.UserScoped.Inventory -> stringResource(com.commondnd.ui.inventory.R.string.label_inventory)
        CommonNavigationGroup.UserScoped.More -> stringResource(com.commondnd.ui.more.R.string.label_more)
    }
