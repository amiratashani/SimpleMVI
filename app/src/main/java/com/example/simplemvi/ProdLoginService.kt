package com.example.simplemvi

import kotlinx.coroutines.delay


class ProdLoginService : LoginRepository {
    override suspend fun login(email: String, password: String): Boolean {
        delay(2500)
        return true
    }
}