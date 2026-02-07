package com.commondnd.common

import kotlinx.coroutines.CompletionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DisposableHandle
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

operator fun CoroutineScope.plusAssign(disposable: DisposableHandle) {
    this.invokeOnCompletion { disposable.dispose() }
}

fun CoroutineScope.invokeOnCompletion(handler: CompletionHandler) =
    coroutineContext.invokeOnCompletion(handler)

fun CoroutineContext.invokeOnCompletion(handler: CompletionHandler): DisposableHandle {
    val job = this[Job]!!
    return job.invokeOnCompletion(handler)
}
