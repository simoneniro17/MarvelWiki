package com.LCDP.marvelwiki

import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.LCDP.marvelwiki.ui.screen.Navigation

class MainActivity : ComponentActivity() {

    //Inizializzazione del media player per riprodurre musica
    private var mediaPlayer : MediaPlayer?= null

    //APP LAUNCH
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Setup del media player
        mediaPlayer = MediaPlayer.create(this, R.raw.superhero_theme)
        mediaPlayer?.isLooping = true
        mediaPlayer?.start()

        setContent {
            val context = this
            Navigation(context)
        }
    }

    //Gestione del media player nel caso in cui l' applicazione venga mandata in background
    override fun onPause() {
        super.onPause()
        mediaPlayer?.pause()
    }

    //Gestione del media player nel caso in cui l' applicazione venga ripresa dal background
    override fun onResume() {
        super.onResume()
        mediaPlayer?.start()
    }

    //Gestione del media player nel caso in cui l' applicazione venga chiusa
    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
    }
}





