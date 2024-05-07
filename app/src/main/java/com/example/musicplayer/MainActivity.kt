package com.example.musicplayer

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var buttonMusic: Button
    private lateinit var buttonNextActivity: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        buttonMusic = findViewById(R.id.buttonMusic)

        buttonMusic.setOnClickListener {
            val music = assets.openFd("music.mp3")

            val mediaPlayer = MusicPlayerManager.getMediaPlayer(music)

            if(mediaPlayer.isPlaying){
                mediaPlayer.pause()
                buttonMusic.text = "Play"
            } else{
                mediaPlayer.start()
                buttonMusic.text = "Pause"
            }
        }

        buttonNextActivity = findViewById(R.id.button2)
        buttonNextActivity.setOnClickListener{
            val intent = Intent(this, SecondActivity::class.java)
            startActivity(intent)
        }

    }

}