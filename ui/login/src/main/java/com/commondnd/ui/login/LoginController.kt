package com.commondnd.ui.login

import android.net.Uri
import android.os.OperationCanceledException
import com.commondnd.data.user.UserRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.concurrent.TimeoutException
import javax.inject.Inject

interface LoginController {

    val currentState: Flow<LoginState>

    fun startAuth(codeVerifier: String, redirectUri: Uri)
    fun finishAuth(code: String?, authResult: AuthResult)
}

internal class LoginControllerImpl @Inject constructor(
    private val userRepository: UserRepository,
    private val coroutineScope: CoroutineScope
) : LoginController {

    private val _currentState: MutableStateFlow<LoginState> = MutableStateFlow(LoginState.Uninitialized)
    override val currentState: Flow<LoginState> = _currentState

    override fun startAuth(codeVerifier: String, redirectUri: Uri) {
        _currentState.update {
            require(it !is LoginState.AuthorizationRequesting && it !is LoginState.LoginStarted)
            LoginState.AuthorizationRequesting(codeVerifier = codeVerifier, redirectUri = redirectUri)
        }
    }

    override fun finishAuth(code: String?, authResult: AuthResult) {
        require(_currentState.value is LoginState.AuthorizationRequesting)
        when (authResult) {
            AuthResult.Cancelled -> _currentState.update {
                require(it is LoginState.AuthorizationRequesting)
                LoginState.AuthorizationCanceled
            }
            AuthResult.VerificationFailed -> _currentState.update {
                require(it is LoginState.AuthorizationRequesting)
                LoginState.AuthorizationError(OperationCanceledException())
            }
            AuthResult.Timeout -> _currentState.update {
                require(it is LoginState.AuthorizationRequesting)
                LoginState.AuthorizationError(TimeoutException())
            }

            AuthResult.Success -> {
                _currentState.update {
                    require(it is LoginState.AuthorizationRequesting)
                    if (code == null) {
                        LoginState.AuthorizationCanceled
                    } else {
                        LoginState.LoginStarted(requireNotNull(code), it.codeVerifier, it.redirectUri)
                    }
                }
                if (code != null) {
                    coroutineScope.launch {
                        try {
                            val codeVerifier = (_currentState.value as LoginState.LoginStarted).codeVerifier
                            userRepository.login(requireNotNull(code), codeVerifier)
                            _currentState.update {
                                require(it is LoginState.LoginStarted)
                                LoginState.LoginSuccess
                            }
                        } catch (cancellation: CancellationException) {
                            throw cancellation
                        } catch (expected: Exception) {
                            _currentState.update {
                                require(_currentState.value is LoginState.AuthorizationRequesting)
                                LoginState.LoginError(error = expected)
                            }
                        }
                    }
                }

            }
            AuthResult.Unknown -> _currentState.update {
                require(it is LoginState.AuthorizationRequesting)
                LoginState.AuthorizationError(IllegalStateException())
            }
        }
    }
}

enum class AuthResult {

    Cancelled, VerificationFailed, Timeout, Success, Unknown
}
