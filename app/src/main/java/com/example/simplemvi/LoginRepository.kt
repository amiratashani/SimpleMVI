package com.example.simplemvi

interface LoginRepository {
    fun login(email: String, password: String): Boolean
}