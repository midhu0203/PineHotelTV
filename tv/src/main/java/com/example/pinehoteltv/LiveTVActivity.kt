package com.example.pinehoteltv

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.GridView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity

class LiveTVActivity : FragmentActivity() {

    private lateinit var categoryGrid: GridView
    private lateinit var channelGrid: GridView

    private lateinit var screenTitle: TextView
    private lateinit var screenHint: TextView

    private var showingChannels = false

    private val categories = listOf(

        TVCategory(
            1,
            "NEWS",
            "News Channels",
            "NEWS"
        ),

        TVCategory(
            2,
            "MOVIES",
            "Movie Channels",
            "▶"
        ),

        TVCategory(
            3,
            "SPORTS",
            "Sports Channels",
            "SPORTS"
        ),

        TVCategory(
            4,
            "KIDS",
            "Kids Channels",
            "KIDS"
        ),

        TVCategory(
            5,
            "MUSIC",
            "Music Channels",
            "♫"
        ),

        TVCategory(
            6,
            "ENTERTAINMENT",
            "Entertainment Channels",
            "★"
        ),

        TVCategory(
            7,
            "LIFESTYLE",
            "Lifestyle Channels",
            "LIFE"
        ),

        TVCategory(
            8,
            "DOCUMENTARY",
            "Documentary Channels",
            "DOC"
        )
    )

    /*
     * TEST CHANNELS
     *
     * These are only for testing the navigation
     * and playback pipeline.
     */
    private val channels = listOf(

        Channel(
            1L,
            1,
            "Test Channel 1",
            "NEWS",
            "HLS Playback Test",
            "https://stream.mux.com/OfjbQ3esQifgboENTs4oDXslCP5sSnst.m3u8"
        ),

        Channel(
            2L,
            2,
            "Test Channel 2",
            "NEWS",
            "HLS Playback Test",
            "https://stream.mux.com/OfjbQ3esQifgboENTs4oDXslCP5sSnst.m3u8"
        ),

        Channel(
            3L,
            3,
            "Test Sports",
            "SPORTS",
            "HLS Playback Test",
            "https://stream.mux.com/OfjbQ3esQifgboENTs4oDXslCP5sSnst.m3u8"
        ),

        Channel(
            4L,
            4,
            "Test Movies",
            "MOVIES",
            "HLS Playback Test",
            "https://stream.mux.com/OfjbQ3esQifgboENTs4oDXslCP5sSnst.m3u8"
        ),

        Channel(
            5L,
            5,
            "Test Kids",
            "KIDS",
            "HLS Playback Test",
            "https://stream.mux.com/OfjbQ3esQifgboENTs4oDXslCP5sSnst.m3u8"
        ),

        Channel(
            6L,
            6,
            "Test Music",
            "MUSIC",
            "HLS Playback Test",
            "https://stream.mux.com/OfjbQ3esQifgboENTs4oDXslCP5sSnst.m3u8"
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_live_tv)

        categoryGrid =
            findViewById(R.id.category_grid_view)

        channelGrid =
            findViewById(R.id.channel_grid_view)

        screenTitle =
            findViewById(R.id.live_tv_screen_title)

        screenHint =
            findViewById(R.id.live_tv_screen_hint)

        setupCategories()

        showCategories()
    }

    private fun setupCategories() {

        /*
         * Category adapter now handles the OK button
         * directly on each category card.
         */
        val adapter = CategoryAdapter(
            this,
            categories
        ) { selectedCategory ->

            android.util.Log.d(
                "PINE_TV",
                "Category selected: ${selectedCategory.name}"
            )

            showChannels(
                selectedCategory.name
            )
        }

        categoryGrid.adapter = adapter
    }

    private fun showCategories() {

        showingChannels = false

        categoryGrid.visibility =
            View.VISIBLE

        channelGrid.visibility =
            View.GONE

        screenTitle.text =
            "LIVE TV"

        screenHint.text =
            "SELECT A CATEGORY"

        categoryGrid.post {

            if (categoryGrid.childCount > 0) {

                categoryGrid
                    .getChildAt(0)
                    .requestFocus()
            }
        }
    }

    private fun showChannels(
        categoryName: String
    ) {

        android.util.Log.d(
            "PINE_TV",
            "Showing channels for: $categoryName"
        )

        val filteredChannels =
            channels.filter { channel ->

                channel.category.equals(
                    categoryName,
                    ignoreCase = true
                )
            }

        android.util.Log.d(
            "PINE_TV",
            "Found channels: ${filteredChannels.size}"
        )

        /*
         * Don't switch screen if there are no
         * channels in this category.
         */
        if (filteredChannels.isEmpty()) {
            return
        }

        showingChannels = true

        val adapter = ChannelAdapter(
            this,
            filteredChannels
        ) { selectedChannel ->

            openChannel(
                selectedChannel
            )
        }

        channelGrid.adapter = adapter

        categoryGrid.visibility =
            View.GONE

        channelGrid.visibility =
            View.VISIBLE

        screenTitle.text =
            categoryName

        screenHint.text =
            "SELECT A CHANNEL"

        channelGrid.post {

            if (channelGrid.childCount > 0) {

                channelGrid
                    .getChildAt(0)
                    .requestFocus()
            }
        }
    }

    private fun openChannel(
        channel: Channel
    ) {

        android.util.Log.d(
            "PINE_TV",
            "Opening channel: ${channel.name}"
        )

        val intent = Intent(
            this,
            PlaybackActivity::class.java
        )

        intent.putExtra(
            "CHANNEL",
            channel
        )

        startActivity(intent)
    }

    override fun onBackPressed() {

        if (showingChannels) {

            showCategories()

        } else {

            super.onBackPressed()
        }
    }
}