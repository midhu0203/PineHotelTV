package com.example.pinehoteltv

import java.io.Serializable

data class TVCategory(
    val id: Long,
    val name: String,
    val subtitle: String,
    val icon: String
) : Serializable