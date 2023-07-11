package com.LCDP.marvelwiki

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModelProvider
import com.LCDP.marvelwiki.data.model.CharacterResponse
import com.LCDP.marvelwiki.data.model.ComicResponse
import com.LCDP.marvelwiki.data.repository.CharactersRepository
import com.LCDP.marvelwiki.data.repository.ComicsRepository
import com.LCDP.marvelwiki.ui.screen.Navigation
import com.LCDP.marvelwiki.ui.viewmodel.CharactersViewModel
import com.LCDP.marvelwiki.ui.viewmodel.CharactersViewModelFactory
import com.LCDP.marvelwiki.ui.viewmodel.ComicsViewModel
import com.LCDP.marvelwiki.ui.viewmodel.ComicsViewModelFactory
import com.LCDP.marvelwiki.usefulStuff.Resource

class MainActivity : ComponentActivity() {

    //APP LAUNCH
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Navigation()
        }
    }
}





