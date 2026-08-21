package com.example.pinehoteltv

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.media3.common.MediaItem
import androidx.media3.common.MimeTypes
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView

class PlaybackVideoFragment : Fragment() {

    private var player: ExoPlayer? = null
    private var playerView: PlayerView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = PlayerView(requireContext())

        playerView = view

        return view
    }

    override fun onStart() {
        super.onStart()

        initializePlayer()
    }

    private fun initializePlayer() {

        val intent = requireActivity().intent

        // ==============================
        // GET CHANNEL
        // ==============================

        val channel: Channel? =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {

                intent.getSerializableExtra(
                    "CHANNEL",
                    Channel::class.java
                )

            } else {

                @Suppress("DEPRECATION")
                intent.getSerializableExtra(
                    "CHANNEL"
                ) as? Channel
            }

        // ==============================
        // GET MOVIE
        // ==============================

        val movie: Movie? =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {

                intent.getSerializableExtra(
                    DetailsActivity.MOVIE,
                    Movie::class.java
                )

            } else {

                @Suppress("DEPRECATION")
                intent.getSerializableExtra(
                    DetailsActivity.MOVIE
                ) as? Movie
            }

        // ==============================
        // GET PLAYBACK INFORMATION
        // ==============================

        var title = ""
        var description = ""
        var videoUrl = ""

        if (channel != null) {

            title = channel.name
            description = channel.description
            videoUrl = channel.streamUrl

        } else if (movie != null) {

            val (
                _,
                movieTitle,
                movieDescription,
                _,
                _,
                movieVideoUrl
            ) = movie

            title = movieTitle ?: ""
            description = movieDescription ?: ""
            videoUrl = movieVideoUrl ?: ""
        }

        // ==============================
        // CHECK URL
        // ==============================

        if (videoUrl.isBlank()) {

            android.util.Log.e(
                "PINE_TV_PLAYER",
                "No video URL supplied"
            )

            return
        }

        android.util.Log.d(
            "PINE_TV_PLAYER",
            "Playing: $title"
        )

        android.util.Log.d(
            "PINE_TV_PLAYER",
            "URL: $videoUrl"
        )

        // ==============================
        // CREATE EXOPLAYER
        // ==============================

        val exoPlayer =
            ExoPlayer.Builder(requireContext())
                .build()

        player = exoPlayer

        playerView?.player = exoPlayer

        // ==============================
        // PLAYER LISTENER
        // ==============================

        exoPlayer.addListener(
            object : Player.Listener {

                override fun onPlaybackStateChanged(
                    playbackState: Int
                ) {

                    when (playbackState) {

                        Player.STATE_IDLE -> {

                            android.util.Log.d(
                                "PINE_TV_PLAYER",
                                "Player idle"
                            )
                        }

                        Player.STATE_BUFFERING -> {

                            android.util.Log.d(
                                "PINE_TV_PLAYER",
                                "Buffering..."
                            )
                        }

                        Player.STATE_READY -> {

                            android.util.Log.d(
                                "PINE_TV_PLAYER",
                                "Playback READY"
                            )
                        }

                        Player.STATE_ENDED -> {

                            android.util.Log.d(
                                "PINE_TV_PLAYER",
                                "Playback ended"
                            )
                        }
                    }
                }

                override fun onPlayerError(
                    error: androidx.media3.common.PlaybackException
                ) {

                    android.util.Log.e(
                        "PINE_TV_PLAYER",
                        "PLAYBACK ERROR"
                    )

                    android.util.Log.e(
                        "PINE_TV_PLAYER",
                        "Error code: ${error.errorCodeName}"
                    )

                    android.util.Log.e(
                        "PINE_TV_PLAYER",
                        "Message: ${error.message}",
                        error
                    )
                }
            }
        )

        // ==============================
        // CREATE HLS MEDIA ITEM
        // ==============================

        val mediaItem =
            MediaItem.Builder()
                .setUri(videoUrl)
                .setMimeType(
                    MimeTypes.APPLICATION_M3U8
                )
                .build()

        // ==============================
        // START PLAYBACK
        // ==============================

        exoPlayer.setMediaItem(mediaItem)

        exoPlayer.prepare()

        exoPlayer.playWhenReady = true
    }

    override fun onStop() {

        super.onStop()

        playerView?.player = null

        player?.release()

        player = null
    }

    override fun onDestroyView() {

        playerView = null

        super.onDestroyView()
    }
}