package com.example.musicplayer

import android.content.res.AssetFileDescriptor
import android.media.MediaPlayer


object MusicPlayerManager{
    private var mediaPlayer: MediaPlayer? = null

    fun getMediaPlayer(music: AssetFileDescriptor): MediaPlayer {
        if (mediaPlayer == null) {

            mediaPlayer = MediaPlayer()
            mediaPlayer?.setDataSource(
                music.fileDescriptor,
                music.startOffset,
                music.length
            )
            music.close()
            mediaPlayer?.prepare()
        }
        return mediaPlayer!!
    }

    fun getCurrentPosition(): Int {
        return mediaPlayer?.currentPosition ?: 0
    }


    fun releaseMediaPlayer() {
        mediaPlayer?.release()
        mediaPlayer = null
    }
}