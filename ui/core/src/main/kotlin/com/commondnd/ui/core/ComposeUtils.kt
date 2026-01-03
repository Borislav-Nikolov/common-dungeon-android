package com.commondnd.ui.core

import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import kotlinx.serialization.json.Json

inline fun <reified T : Any> jsonSaver(): Saver<T, String> = Saver(
    save = { Json.encodeToString(it) },
    restore = { Json.decodeFromString(it) }
)

@Composable
inline fun <reified T : Any> rememberSerializable(
    vararg inputs: Any?,noinline init: () -> T
) = rememberSaveable(
    inputs = inputs,
    saver = jsonSaver<T>(),
    init = init
)
