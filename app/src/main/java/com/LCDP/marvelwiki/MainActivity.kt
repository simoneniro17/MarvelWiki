package com.LCDP.marvelwiki

import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

import com.LCDP.marvelwiki.ui.screen.Navigation

class MainActivity : ComponentActivity() {

    private var mediaPlayer : MediaPlayer?= null

    //APP LAUNCH
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val context = this
            Navigation(context)
        }
    }
}





