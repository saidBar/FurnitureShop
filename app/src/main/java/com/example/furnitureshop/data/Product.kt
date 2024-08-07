package com.example.furnitureshop.com.example.furnitureshop.data

import android.health.connect.datatypes.units.Percentage

data class Product(
    val id: String,
    val name: String,
    val category: String,
    val price: Float,
    val offerPercentage: Float? = null,
    val description: String? = null,
    val colors: List<Int>? = null,
    val sizes: List<String>? = null,
    val images: List<String>
){
    constructor(): this("0", "","", 0f, images = emptyList())
}
