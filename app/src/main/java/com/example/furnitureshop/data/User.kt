package com.example.furnitureshop.data

data class User(
    val FirstName: String,
    val LastName: String,
    val Email: String,
    val imagePath: String = ""
) {
    constructor() : this("", "", "", "")
}
