package com.example.pinehoteltv

import java.io.Serializable

data class Channel(
    val id: Long, val number: Int,
    val name: String, val category: String,
    val description: String, val streamUrl: String,
    val logoResId: Int? = null
) : Serializable