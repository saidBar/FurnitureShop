package com.example.furnitureshop.com.example.furnitureshop.util

import android.util.Patterns

fun validateEmail(email: String): RegisterValidation {
    if (email.isEmpty())
        return RegisterValidation.Failed("Email cannot be empty")
    if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        return RegisterValidation.Failed("Invalid email")

    return RegisterValidation.Success
}

fun validatePassword(password: String): RegisterValidation {
    if (password.isEmpty())
        return RegisterValidation.Failed("Password cannot be empty")
    if (password.length < 6)
        return RegisterValidation.Failed("Password must be at least 6 characters long")

    return RegisterValidation.Success
}
