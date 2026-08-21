package com.example.pinehoteltv

import java.io.Serializable

data class MoviePlatform(
    val id: Long,
    val name: String,
    val subtitle: String,
    val icon: String
) : Serializable