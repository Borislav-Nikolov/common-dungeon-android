package com.commondnd.ui.login

import android.net.Uri

sealed interface LoginState {

    data object Uninitialized : LoginState
    data class AuthorizationRequesting(val codeVerifier: String, val redirectUri: Uri) : LoginState
    data object AuthorizationCanceled : LoginState
    data class AuthorizationError(val error: Throwable) : LoginState
    data class LoginStarted(val code: String, val codeVerifier: String, val redirectUri: Uri) : LoginState
    data class LoginError(val error: Throwable) : LoginState
    data object LoginSuccess : LoginState
}
