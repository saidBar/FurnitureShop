package com.example.furnitureshop.com.example.furnitureshop.util

sealed class RegisterValidation() {
    object Success : RegisterValidation()
    data class Failed(val message: String) : RegisterValidation()

}

data class RegisterFieldsState(
    val email: RegisterValidation,
    val password: RegisterValidation
)
