package com.commondnd.ui.core

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import kotlinx.serialization.json.Json

inline fun <reified T : Any> jsonSaver(): Saver<T, String> = Saver(
    save = { Json.encodeToString(it) },
    restore = { Json.decodeFromString(it) }
)

inline fun <reified T : Any> mutableStateJsonSaver(): Saver<MutableState<T?>, String> = Saver(
    save = { state -> state.value?.let { Json.encodeToString(it) } },
    restore = { json -> mutableStateOf(Json.decodeFromString<T>(json)) }
)

@Composable
inline fun <reified T : Any> rememberSerializable(
    vararg inputs: Any?, noinline init: () -> T
) = rememberSaveable(
    inputs = inputs,
    saver = jsonSaver<T>(),
    init = init
)

@Composable
inline fun <reified T : Any> rememberSerializable(
    vararg inputs: Any?,
    noinline init: () -> MutableState<T?>
): MutableState<T?> = rememberSaveable(
    inputs = inputs,
    saver = mutableStateJsonSaver<T>(),
    init = init
)
