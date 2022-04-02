package com.example.simplemvi.ui.login

import com.example.simplemvi.mvi.Action

/**
 * These are all of the possible actions that can be triggered from the login screen.
 */
sealed class LoginAction:Action{
    data class EmailChanged(val newEmail: String) : LoginAction()
    data class PasswordChanged(val newPassword: String) : LoginAction()
    object SignInButtonClicked : LoginAction()
    object LoginStarted : LoginAction()
    object LoginCompleted : LoginAction()
    data class LoginFailed(val error: Throwable?) : LoginAction()
}