package com.example.simplemvi


class ProdLoginService : LoginRepository {
    override fun login(email: String, password: String): Boolean {
        return true
    }
}