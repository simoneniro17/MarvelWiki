package com.LCDP.marvelwiki

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.LCDP.marvelwiki.data.database.model.FavouriteCharacter
import com.LCDP.marvelwiki.data.database.viewModel.FavouriteCharacterViewModel
import com.LCDP.marvelwiki.ui.screen.Navigation

class MainActivity : ComponentActivity() {

    private lateinit var favouriteCharacterViewModel: FavouriteCharacterViewModel

    //APP LAUNCH
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            //favouriteCharacterViewModel = ViewModelProvider(this).get(FavouriteCharacterViewModel::class.java)
            //insertDataToDatabase()
            //readAllData()
            Navigation()
        }
    }

    private fun insertDataToDatabase(){
        val favouriteCharacter = FavouriteCharacter(10001,"massimo regoli","subame la radio","frase tipica: è un bagno de sangue")
        favouriteCharacterViewModel.addFavouriteCharacter(favouriteCharacter)
    }

    private fun readAllData(){

    }
}





