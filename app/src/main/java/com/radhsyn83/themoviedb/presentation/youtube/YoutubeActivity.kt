package com.radhsyn83.themoviedb.presentation.youtube


import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView
import com.radhsyn83.themoviedb.BuildConfig
import com.radhsyn83.themoviedb.R

class YoutubeActivity : YouTubeBaseActivity() {

    private lateinit var mOnInitializedListener: YouTubePlayer.OnInitializedListener
    private lateinit var idYoutube: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_youtube)

        idYoutube = intent?.extras?.getString("youtubeId", "").toString()

        findViewById<ImageView>(R.id.iv_close).setOnClickListener {
            finish()
        }

        mOnInitializedListener = object : YouTubePlayer.OnInitializedListener {
            override fun onInitializationSuccess(
                provider: YouTubePlayer.Provider,
                youTubePlayer: YouTubePlayer,
                b: Boolean
            ) {
                if (idYoutube.isNotEmpty())
                    youTubePlayer.loadVideo(idYoutube)
            }

            override fun onInitializationFailure(
                provider: YouTubePlayer.Provider,
                youTubeInitializationResult: YouTubeInitializationResult
            ) {
                Log.e("YoutubeError", "Error ${youTubeInitializationResult.name}")
            }
        }

        findViewById<YouTubePlayerView>(R.id.youtbePlay).initialize(
            BuildConfig.YOUTUBE_API_KEY,
            mOnInitializedListener
        )
    }
}
