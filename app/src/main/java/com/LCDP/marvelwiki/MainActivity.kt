package com.LCDP.marvelwiki

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.LCDP.marvelwiki.data.repository.ComicsRepository
import com.LCDP.marvelwiki.printer.RetrieveLatestComic
import com.LCDP.marvelwiki.ui.screen.Navigation

class MainActivity : ComponentActivity() {
    //APP LAUNCH
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Navigation()
        }
    }
}





