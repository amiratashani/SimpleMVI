package com.example.simplemvi

import com.example.simplemvi.udf.Middleware
import com.example.simplemvi.udf.Store
import com.example.simplemvi.ui.login.LoginAction
import com.example.simplemvi.ui.login.LoginViewState

class LoginNetworkingMiddleware(private val loginRepository: LoginRepository) :
    Middleware<LoginViewState, LoginAction> {
    override suspend fun process(
        action: LoginAction,
        currentState: LoginViewState,
        store: Store<LoginViewState, LoginAction>
    ) {
        when (action) {
            is LoginAction.SignInButtonClicked -> {
                store.dispatch(LoginAction.LoginStarted)
                val isSuccessful = loginRepository.login(
                    email = currentState.email,
                    password = currentState.password
                )

                if (isSuccessful) {
                    store.dispatch(LoginAction.LoginCompleted)
                } else {
                    store.dispatch(LoginAction.LoginFailed(null))
                }
            }
        }
    }
}