package com.example.pinehoteltv

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.GridView
import android.widget.Toast
import androidx.fragment.app.FragmentActivity

class MovieListActivity : FragmentActivity() {

    private lateinit var platformGrid: GridView

    private val platforms = listOf(

        MoviePlatform(
            1,
            "JioHotstar",
            "Movies & Shows",
            "▶"
        ),

        MoviePlatform(
            2,
            "Netflix",
            "Movies & Series",
            "N"
        ),

        MoviePlatform(
            3,
            "Prime Video",
            "Movies & Shows",
            "▶"
        ),

        MoviePlatform(
            4,
            "Sony LIV",
            "Movies & Entertainment",
            "S"
        ),

        MoviePlatform(
            5,
            "ZEE5",
            "Movies & Shows",
            "Z"
        ),

        MoviePlatform(
            6,
            "YouTube",
            "Videos & Entertainment",
            "▶"
        ),

        MoviePlatform(
            7,
            "Sun NXT",
            "Tamil Movies & Shows",
            "S"
        ),

        MoviePlatform(
            8,
            "Aha",
            "Tamil & Telugu",
            "A"
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_movie_list)

        platformGrid = findViewById(R.id.movie_grid_view)

        val adapter = MoviePlatformAdapter(
            this,
            platforms
        )

        platformGrid.adapter = adapter

        platformGrid.setOnItemClickListener { _, _, position, _ ->

            val selectedPlatform = platforms[position]

            when (selectedPlatform.name) {

                "Prime Video" -> {
                    openPlatform(
                        "Prime Video",
                        "https://www.primevideo.com/"
                    )
                }

                "Netflix" -> {
                    openPlatform(
                        "Netflix",
                        "https://www.netflix.com/"
                    )
                }

                "JioHotstar" -> {
                    openPlatform(
                        "JioHotstar",
                        "https://www.hotstar.com/"
                    )
                }

                "Sony LIV" -> {
                    openPlatform(
                        "Sony LIV",
                        "https://www.sonyliv.com/"
                    )
                }

                "ZEE5" -> {
                    openPlatform(
                        "ZEE5",
                        "https://www.zee5.com/"
                    )
                }

                "YouTube" -> {
                    openPlatform(
                        "YouTube",
                        "https://www.youtube.com/"
                    )
                }

                "Sun NXT" -> {
                    openPlatform(
                        "Sun NXT",
                        "https://www.sunnxt.com/"
                    )
                }

                "Aha" -> {
                    openPlatform(
                        "Aha",
                        "https://www.aha.video/"
                    )
                }
            }
        }

        // Focus first card when screen opens
        platformGrid.post {

            if (platformGrid.childCount > 0) {
                platformGrid.getChildAt(0).requestFocus()
            }
        }
    }

    private fun openPlatform(
        platformName: String,
        websiteUrl: String
    ) {

        try {

            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse(websiteUrl)
            )

            startActivity(intent)

        } catch (e: ActivityNotFoundException) {

            Toast.makeText(
                this,
                "$platformName cannot be opened on this TV",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}