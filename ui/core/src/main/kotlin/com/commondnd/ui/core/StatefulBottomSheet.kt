package com.commondnd.ui.core

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue


interface BottomSheetState {

    val show: Boolean
}

open class ComposeBottomSheetState : BottomSheetState {

    var _show: Boolean by mutableStateOf(false)
    val show: Boolean
}
