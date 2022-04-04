package com.example.simplemvi

interface LoginRepository {
    suspend fun login(email: String, password: String): Boolean
}