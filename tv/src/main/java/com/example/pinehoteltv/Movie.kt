package com.example.pinehoteltv

import java.io.Serializable

data class Movie(
    var id: Long = 0L,
    var title: String? = null,
    var description: String? = null,
    var backgroundImageUrl: String? = null,
    var cardImageUrl: String? = null,
    var videoUrl: String? = null,
    var studio: String? = null
) : Serializable {

    override fun toString(): String {
        return "Movie{" +
                "id=$id, " +
                "title='$title', " +
                "videoUrl='$videoUrl', " +
                "backgroundImageUrl='$backgroundImageUrl', " +
                "cardImageUrl='$cardImageUrl', " +
                "studio='$studio'" +
                '}'
    }

    companion object {
        private const val serialVersionUID = 727566175075960653L
    }
}