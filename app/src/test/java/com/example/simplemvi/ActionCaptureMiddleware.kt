package com.example.simplemvi

import com.example.simplemvi.udf.Action
import com.example.simplemvi.udf.Middleware
import com.example.simplemvi.udf.State
import com.example.simplemvi.udf.Store
import com.google.common.truth.Truth.assertThat

/**
 * This is a implementation of [Middleware] that will log every action sent to it.
 *
 * In a unit test, we can apply this middleware to a Store, and then use this middleware for asserting
 * that an action was sent to that store.
 */
class ActionCaptureMiddleware<S: State, A: Action> : Middleware<S, A> {
    private val actionHistory: MutableList<Action> = mutableListOf()

    override suspend fun process(action: A, currentState: S, store: Store<S, A>) {
        actionHistory.add(action)
    }

    fun assertActionProcessed(expectedAction: Action) {
        assertThat(actionHistory).contains(expectedAction)
    }
}