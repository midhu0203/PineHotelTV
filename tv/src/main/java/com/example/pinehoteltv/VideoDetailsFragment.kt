package com.example.pinehoteltv

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.leanback.app.DetailsSupportFragment
import androidx.leanback.widget.Action
import androidx.leanback.widget.ArrayObjectAdapter
import androidx.leanback.widget.ClassPresenterSelector
import androidx.leanback.widget.DetailsOverviewRow
import androidx.leanback.widget.FullWidthDetailsOverviewRowPresenter
import androidx.leanback.widget.OnActionClickedListener
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition

class VideoDetailsFragment : DetailsSupportFragment() {

    private lateinit var mSelectedMovie: Movie
    private lateinit var mPresenterSelector: ClassPresenterSelector
    private lateinit var mAdapter: ArrayObjectAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mSelectedMovie = activity?.intent?.getSerializableExtra(DetailsActivity.MOVIE, Movie::class.java)
            ?: Movie()

        mPresenterSelector = ClassPresenterSelector()
        mAdapter = ArrayObjectAdapter(mPresenterSelector)

        setupDetailsOverviewRow()
        setupDetailsOverviewRowPresenter()

        adapter = mAdapter
    }

    private fun setupDetailsOverviewRow() {
        val row = DetailsOverviewRow(mSelectedMovie)
        row.imageDrawable = ContextCompat.getDrawable(requireContext(), R.drawable.default_background)

        val width = 304
        val height = 172
        Glide.with(this)
            .asBitmap()
            .load(mSelectedMovie.cardImageUrl)
            .centerCrop()
            .into(object : CustomTarget<Bitmap>(width, height) {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    row.setImageBitmap(requireContext(), resource)
                    mAdapter.notifyArrayItemRangeChanged(0, mAdapter.size())
                }

                override fun onLoadCleared(placeholder: Drawable?) {}
            })

        val actionAdapter = ArrayObjectAdapter()
        actionAdapter.add(
            Action(ACTION_WATCH_TRAILER, resources.getString(R.string.watch_trailer_1), resources.getString(R.string.watch_trailer_2))
        )
        actionAdapter.add(
            Action(ACTION_RENT, resources.getString(R.string.rent_1), resources.getString(R.string.rent_2))
        )
        actionAdapter.add(
            Action(ACTION_BUY, resources.getString(R.string.buy_1), resources.getString(R.string.buy_2))
        )
        row.actionsAdapter = actionAdapter

        mAdapter.add(row)
    }

    private fun setupDetailsOverviewRowPresenter() {
        val detailsPresenter = FullWidthDetailsOverviewRowPresenter(DetailsDescriptionPresenter())
        detailsPresenter.backgroundColor = ContextCompat.getColor(requireContext(), R.color.tile_bg)

        detailsPresenter.onActionClickedListener = OnActionClickedListener { action ->
            when (action.id) {
                ACTION_WATCH_TRAILER -> { /* TODO: play trailer */ }
                ACTION_RENT -> { /* TODO: rent flow */ }
                ACTION_BUY -> { /* TODO: buy flow */ }
            }
        }

        mPresenterSelector.addClassPresenter(DetailsOverviewRow::class.java, detailsPresenter)
    }

    companion object {
        private const val ACTION_WATCH_TRAILER = 1L
        private const val ACTION_RENT = 2L
        private const val ACTION_BUY = 3L
    }
}