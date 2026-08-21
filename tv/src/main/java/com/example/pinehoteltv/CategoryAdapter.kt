package com.example.pinehoteltv

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class CategoryAdapter(
    private val context: Context,
    private val categories: List<TVCategory>,
    private val onCategoryClick: (TVCategory) -> Unit
) : BaseAdapter() {

    override fun getCount(): Int {
        return categories.size
    }

    override fun getItem(position: Int): TVCategory {
        return categories[position]
    }

    override fun getItemId(position: Int): Long {
        return categories[position].id
    }

    override fun getView(
        position: Int,
        convertView: View?,
        parent: ViewGroup
    ): View {

        val view = convertView ?: LayoutInflater.from(context)
            .inflate(
                R.layout.item_tv_category,
                parent,
                false
            )

        val category = getItem(position)

        val name =
            view.findViewById<TextView>(R.id.category_name)

        val subtitle =
            view.findViewById<TextView>(R.id.category_subtitle)

        name.text = category.name
        subtitle.text = category.subtitle

        view.isFocusable = true
        view.isFocusableInTouchMode = true
        view.isClickable = true

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

                focusedView.scaleX = 1f
                focusedView.scaleY = 1f
            }

            focusedView.background = background
        }

        /*
         * IMPORTANT:
         * Handle Android TV OK directly on the
         * focused category card.
         */
        view.setOnClickListener {
            onCategoryClick(category)
        }

        return view
    }
}