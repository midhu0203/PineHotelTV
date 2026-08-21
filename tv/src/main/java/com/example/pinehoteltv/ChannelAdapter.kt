package com.example.pinehoteltv

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class ChannelAdapter(
    private val context: Context,
    private val channels: List<Channel>,
    private val onChannelClick: (Channel) -> Unit
) : BaseAdapter() {

    override fun getCount(): Int {
        return channels.size
    }

    override fun getItem(position: Int): Channel {
        return channels[position]
    }

    override fun getItemId(position: Int): Long {
        return channels[position].id
    }

    override fun getView(
        position: Int,
        convertView: View?,
        parent: ViewGroup
    ): View {

        val view = convertView ?: LayoutInflater.from(context)
            .inflate(
                R.layout.item_channel_card,
                parent,
                false
            )

        val channel = getItem(position)

        val number =
            view.findViewById<TextView>(R.id.channel_number)

        val name =
            view.findViewById<TextView>(R.id.channel_name)

        val category =
            view.findViewById<TextView>(R.id.channel_category)

        number.text = String.format(
            "%02d",
            channel.number
        )

        name.text = channel.name
        category.text = channel.category

        view.isFocusable = true
        view.isFocusableInTouchMode = true
        view.isClickable = true

        view.setOnFocusChangeListener { focusedView, hasFocus ->

            val background = GradientDrawable()

            background.cornerRadius = 12f

            if (hasFocus) {

                background.setColor(
                    Color.parseColor("#351827")
                )

                background.setStroke(
                    2,
                    Color.parseColor("#D6B06A")
                )

                focusedView.scaleX = 1.03f
                focusedView.scaleY = 1.03f

            } else {

                background.setColor(
                    Color.parseColor("#24121D")
                )

                background.setStroke(
                    1,
                    Color.parseColor("#4A2637")
                )

                focusedView.scaleX = 1f
                focusedView.scaleY = 1f
            }

            focusedView.background = background
        }

        /*
         * IMPORTANT:
         * Handle the Android TV OK/click directly
         * on the channel card.
         */
        view.setOnClickListener {
            onChannelClick(channel)
        }

        return view
    }
}