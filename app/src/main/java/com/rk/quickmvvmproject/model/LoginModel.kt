package com.rk.quickmvvmproject.model

data class LoginModel(
    val code: String,
    val `data`: LoginModelData,
    val message: String
)

data class LoginModelData(
    val token: String,
    val user: User
)

data class User(
    val blacklistStatus: Int,
    val channelId: Int,
    val hitsCount: Int,
    val lastLoginTime: String,
    val registerIp: String,
    val registerTime: String,
    val userId: Int,
    val userPhone: String
)