package com.example.simplemvi


import com.example.simplemvi.udf.Store
import com.example.simplemvi.ui.login.LoginAction
import com.example.simplemvi.ui.login.LoginReducer
import com.example.simplemvi.ui.login.LoginViewState
import kotlinx.coroutines.runBlocking
import org.junit.Test

class LoginNetworkingMiddlewareTest{
    /**
     * When running this test, we want to ensure that after having [LoginNetworkingMiddleware] process
     * the [LoginAction.SignInButtonClicked] action when the email is invalid, it should then
     * have the store process the [LoginAction.InvalidEmailSubmitted] action.
     *
     * we mock view state and store for test [LoginNetworkingMiddleware] whether capture action or not
     */
    @Test
    fun submitInvalidEmail() {
        // Givens
        val fakeLoginRepository = FakeLoginRepository()
        fakeLoginRepository.shouldMockSuccess = true

        val inputState = LoginViewState(email = "")
        val inputAction = LoginAction.SignInButtonClicked

        val middlewareUnderTest = LoginNetworkingMiddleware(fakeLoginRepository)
        val actionCaptureMiddleware = ActionCaptureMiddleware<LoginViewState, LoginAction>()

        val loginStore = Store(
            inputState,
            LoginReducer(),
            listOf(actionCaptureMiddleware),
        )

        // When
        runBlocking {
            middlewareUnderTest.process(inputAction, inputState, loginStore)
        }

        // Then
        actionCaptureMiddleware.assertActionProcessed(LoginAction.InvalidEmailSubmitted)
    }

    /**
     * When the [LoginNetworkingMiddleware] processes the [LoginAction.SignInButtonClicked] action
     * and there are valid inputs, we want to ensure that the corresponding actions are sent back
     * into the store.
     */
    @Test
    fun validLogin() {
        // Givens
        val fakeLoginRepository = FakeLoginRepository()
        fakeLoginRepository.shouldMockSuccess = true

        val inputState = LoginViewState(
            email = "test@gmail.com",
            password = "1234",
        )
        val inputAction = LoginAction.SignInButtonClicked

        val middlewareUnderTest = LoginNetworkingMiddleware(fakeLoginRepository)
        val actionCaptureMiddleware = ActionCaptureMiddleware<LoginViewState, LoginAction>()

        val loginStore = Store(
            inputState,
            LoginReducer(),
            listOf(actionCaptureMiddleware),
        )

        // When
        runBlocking {
            middlewareUnderTest.process(inputAction, inputState, loginStore)
        }

        // Then
        actionCaptureMiddleware.assertActionProcessed(LoginAction.LoginStarted)
        actionCaptureMiddleware.assertActionProcessed(LoginAction.LoginCompleted)
    }

    @Test
    fun loginFailure() {
        // Givens
        val fakeLoginRepository = FakeLoginRepository()
        fakeLoginRepository.shouldMockSuccess = false

        val inputState = LoginViewState(
            email = "test@gmail.com",
            password = "4321",
        )
        val inputAction = LoginAction.SignInButtonClicked

        val middlewareUnderTest = LoginNetworkingMiddleware(fakeLoginRepository)
        val actionCaptureMiddleware = ActionCaptureMiddleware<LoginViewState, LoginAction>()

        val loginStore = Store(
            inputState,
            LoginReducer(),
            listOf(actionCaptureMiddleware),
        )

        // When
        runBlocking {
            middlewareUnderTest.process(inputAction, inputState, loginStore)
        }

        // Then
        actionCaptureMiddleware.assertActionProcessed(LoginAction.LoginStarted)
        actionCaptureMiddleware.assertActionProcessed(LoginAction.LoginFailed(null))
    }
}