package com.example.test_store.Register

data class RegisterModel(
        val nick: String,
        val email: String,
        val phone: String,
        val description: String = "",
        val likes: Int = 0,
        val posts: Int = 0,
        val followers: Int = 0
        ) {
}