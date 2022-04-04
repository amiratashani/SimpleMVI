package com.example.simplemvi

import android.util.Log
import com.example.simplemvi.udf.Action
import com.example.simplemvi.udf.Middleware
import com.example.simplemvi.udf.State
import com.example.simplemvi.udf.Store

/**
 * This [Middleware] is responsible for logging every [Action] that is processed to the Logcat, so
 * that we can use this for debugging.
 */
class LoggingMiddleware<S : State, A : Action> : Middleware<S, A> {
    override suspend fun process(action: A, currentState: S, store: Store<S, A>) {
        Log.v(
            "LoggingMiddleware",
            "Processing action: $action; Current state: $currentState"
        )
    }
}