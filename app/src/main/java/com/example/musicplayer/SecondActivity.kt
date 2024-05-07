package com.example.musicplayer

import android.content.Context
import android.content.SharedPreferences
import android.content.res.AssetFileDescriptor
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.concurrent.TimeUnit

class SecondActivity : AppCompatActivity() {

    private lateinit var progressBar: ProgressBar
    private lateinit var buttonMusic: Button
    private lateinit var handler: Handler
    private val UPDATE_PROGRESS_INTERVAL: Long = 1000
    lateinit var music: AssetFileDescriptor



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_second)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        music = assets.openFd("music.mp3")
        val mediaPlayer = MusicPlayerManager.getMediaPlayer(music)

        buttonMusic = findViewById(R.id.button)

        if(mediaPlayer.isPlaying){
            buttonMusic.text = "Pause"
        } else{
            buttonMusic.text = "Play"
        }


        progressBar = findViewById(R.id.progressBar2)
        progressBar.max = mediaPlayer.duration
        progressBar.progress = mediaPlayer.currentPosition

        createTime(findViewById<TextView>(R.id.maxTime), mediaPlayer.duration)
        createTime(findViewById<TextView>(R.id.nowTime), mediaPlayer.currentPosition)


        handler = Handler()
        handler.postDelayed(updateProgressTask, UPDATE_PROGRESS_INTERVAL)

        buttonMusic.setOnClickListener {

            if(mediaPlayer.isPlaying){
                mediaPlayer.pause()
                buttonMusic.text = "Play"
            } else{
                mediaPlayer.start()
                buttonMusic.text = "Pause"
            }
        }

    }

    private val updateProgressTask = object : Runnable {
        override fun run() {
            val currentPosition = MusicPlayerManager.getCurrentPosition()

            progressBar.progress = currentPosition

            createTime(findViewById<TextView>(R.id.nowTime), currentPosition)

            handler.postDelayed(this, UPDATE_PROGRESS_INTERVAL)
        }
    }

    private fun createTime(textView: TextView, time:Int){
        textView.text = String.format(
            "%02d:%02d",
            TimeUnit.MILLISECONDS.toMinutes(time.toLong()),
            TimeUnit.MILLISECONDS.toSeconds(time.toLong()) - TimeUnit.MINUTES.toSeconds(
                TimeUnit.MILLISECONDS.toMinutes(time.toLong())
            )
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(updateProgressTask)
    }

}