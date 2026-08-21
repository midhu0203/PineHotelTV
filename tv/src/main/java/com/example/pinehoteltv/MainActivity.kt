package com.example.pinehoteltv

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        initializeHome()
        setupTiles()
        setupFooter()

        // Give the first tile focus when the TV screen opens
        findViewById<View>(R.id.tile_live_tv).post {
            findViewById<View>(R.id.tile_live_tv).requestFocus()
        }
    }

    // ---------------------------------------------------------
    // HOME HEADER
    // ---------------------------------------------------------

    private fun initializeHome() {

        findViewById<TextView>(R.id.room_subtitle).text =
            getString(R.string.room_placeholder)

        findViewById<TextView>(R.id.header_wifi_pill).text =
            "Wi-Fi  ${WifiInfoActivity.WIFI_SSID}"

        updateDateTime()
    }

    private fun updateDateTime() {

        val formatter = SimpleDateFormat(
            "HH:mm  |  EEEE, dd MMM",
            Locale.getDefault()
        )

        findViewById<TextView>(R.id.header_datetime).text =
            formatter.format(Date())
    }

    // ---------------------------------------------------------
    // FOOTER
    // ---------------------------------------------------------

    private fun setupFooter() {

        findViewById<TextView>(R.id.footer_guest_label).text =
            getString(
                R.string.guest_label_format,
                "Guest",
                "205"
            )

        findViewById<TextView>(R.id.footer_hint_text).text =
            getString(R.string.footer_hint)

        findViewById<TextView>(R.id.btn_live_tv).setOnClickListener {
            openLiveTV()
        }

        findViewById<TextView>(R.id.btn_my_bookings).setOnClickListener {
            toastNotImplemented("My Bookings")
        }
    }

    // ---------------------------------------------------------
    // HOME TILES
    // ---------------------------------------------------------

    private fun setupTiles() {

        // LIVE TV
        bindTile(
            R.id.tile_live_tv,
            "▣",
            R.string.tile_live_tv,
            R.string.tile_live_tv_sub
        ) {
            openLiveTV()
        }

        // MOVIES
        bindTile(
            R.id.tile_movies,
            "▶",
            R.string.tile_movies,
            R.string.tile_movies_sub
        ) {
            openMovies()
        }

        // ROOM SERVICE
        bindTile(
            R.id.tile_room_service,
            "♨",
            R.string.tile_room_service,
            R.string.tile_room_service_sub
        ) {
            toastNotImplemented("Room Service")
        }

        // HOUSEKEEPING
        bindTile(
            R.id.tile_housekeeping,
            "✦",
            R.string.tile_housekeeping,
            R.string.tile_housekeeping_sub
        ) {
            toastNotImplemented("Housekeeping")
        }

        // HOTEL INFORMATION
        bindTile(
            R.id.tile_hotel_info,
            "i",
            R.string.tile_hotel_info,
            R.string.tile_hotel_info_sub
        ) {
            toastNotImplemented("Hotel Information")
        }

        // WI-FI
        bindTile(
            R.id.tile_wifi,
            "⌁",
            R.string.tile_wifi,
            R.string.tile_wifi_sub
        ) {
            openWifi()
        }

        // GALLERY
        bindTile(
            R.id.tile_gallery,
            "▧",
            R.string.tile_gallery,
            R.string.tile_gallery_sub
        ) {
            toastNotImplemented("Gallery")
        }

        // MESSAGES
        bindTile(
            R.id.tile_messages,
            "•••",
            R.string.tile_messages,
            R.string.tile_messages_sub
        ) {
            toastNotImplemented("Messages")
        }

        // SETTINGS
        bindTile(
            R.id.tile_settings,
            "⚙",
            R.string.tile_settings,
            R.string.tile_settings_sub
        ) {
            toastNotImplemented("Settings")
        }
    }

    // ---------------------------------------------------------
    // TILE BINDING
    // ---------------------------------------------------------

    private fun bindTile(
        containerId: Int,
        icon: String,
        titleRes: Int,
        subtitleRes: Int,
        action: () -> Unit
    ) {

        val tile = findViewById<View>(containerId)

        val iconView =
            tile.findViewById<TextView>(R.id.tile_icon)

        val titleView =
            tile.findViewById<TextView>(R.id.tile_title)

        val subtitleView =
            tile.findViewById<TextView>(R.id.tile_subtitle)

        iconView.text = icon

        titleView.setText(titleRes)

        subtitleView.setText(subtitleRes)

        tile.setOnClickListener {
            action()
        }

        // Make sure only the complete tile receives TV focus
        tile.isFocusable = true
        tile.isFocusableInTouchMode = true
        tile.isClickable = true

        iconView.isFocusable = false
        titleView.isFocusable = false
        subtitleView.isFocusable = false
    }

    // ---------------------------------------------------------
    // OPEN MODULES
    // ---------------------------------------------------------

    private fun openLiveTV() {

        val intent = Intent(
            this,
            LiveTVActivity::class.java
        )

        startActivity(intent)
    }

    private fun openMovies() {

        val intent = Intent(
            this,
            MovieListActivity::class.java
        )

        startActivity(intent)
    }

    private fun openWifi() {

        val intent = Intent(
            this,
            WifiInfoActivity::class.java
        )

        startActivity(intent)
    }

    // ---------------------------------------------------------
    // TEMPORARY PLACEHOLDER
    // ---------------------------------------------------------

    private fun toastNotImplemented(feature: String) {

        android.widget.Toast.makeText(
            this,
            "$feature — coming soon",
            android.widget.Toast.LENGTH_SHORT
        ).show()
    }
}