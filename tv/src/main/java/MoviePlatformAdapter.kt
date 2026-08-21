package com.example.pinehoteltv

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class MoviePlatformAdapter(
    private val context: Context,
    private val platforms: List<MoviePlatform>
) : BaseAdapter() {

    override fun getCount(): Int {
        return platforms.size
    }

    override fun getItem(position: Int): MoviePlatform {
        return platforms[position]
    }

    override fun getItemId(position: Int): Long {
        return platforms[position].id
    }

    override fun getView(
        position: Int,
        convertView: View?,
        parent: ViewGroup
    ): View {

        val view = convertView ?: LayoutInflater.from(context)
            .inflate(
                R.layout.item_movie_platform,
                parent,
                false
            )

        val platform = getItem(position)

        val icon = view.findViewById<TextView>(
            R.id.platform_icon
        )

        val name = view.findViewById<TextView>(
            R.id.platform_name
        )

        val subtitle = view.findViewById<TextView>(
            R.id.platform_subtitle
        )

        icon.text = platform.icon
        name.text = platform.name
        subtitle.text = platform.subtitle

        // Important for Android TV
        view.isFocusable = true
        view.isFocusableInTouchMode = true
        view.isClickable = true

        // Child TextViews must NOT take focus
        icon.isFocusable = false
        icon.isClickable = false

        name.isFocusable = false
        name.isClickable = false

        subtitle.isFocusable = false
        subtitle.isClickable = false

        view.setOnFocusChangeListener { focusedView, hasFocus ->

            val background = GradientDrawable()

            background.cornerRadius = 20f

            if (hasFocus) {

                background.setColor(
                    Color.parseColor("#351827")
                )

                background.setStroke(
                    3,
                    Color.parseColor("#D4AF37")
                )

                focusedView.scaleX = 1.04f
                focusedView.scaleY = 1.04f

            } else {

                background.setColor(
                    Color.parseColor("#2A1620")
                )

                background.setStroke(
                    1,
                    Color.parseColor("#4A2A38")
                )

                focusedView.scaleX = 1f
                focusedView.scaleY = 1f
            }

            focusedView.background = background
        }

        return view
    }
}