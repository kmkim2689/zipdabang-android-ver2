package com.zipdabang.zipdabang_android.module.login.data

data class AuthDto(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: AuthTokenDto
)