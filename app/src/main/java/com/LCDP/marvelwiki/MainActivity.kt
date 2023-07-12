package com.LCDP.marvelwiki

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import com.LCDP.marvelwiki.data.database.model.FavouriteCharacter
import com.LCDP.marvelwiki.data.database.model.FavouriteComic
import com.LCDP.marvelwiki.data.database.viewModel.FavouriteCharacterViewModel
import com.LCDP.marvelwiki.data.database.viewModel.FavouriteComicViewModel
import com.LCDP.marvelwiki.ui.screen.Navigation

class MainActivity : ComponentActivity() {

    private lateinit var favouriteCharacterViewModel: FavouriteCharacterViewModel
    private lateinit var favouriteComicViewModel: FavouriteComicViewModel

    //APP LAUNCH
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            favouriteCharacterViewModel = ViewModelProvider(this).get(FavouriteCharacterViewModel::class.java)
            favouriteComicViewModel = ViewModelProvider(this).get(FavouriteComicViewModel::class.java)
            insertDataToDatabase()
            readAllData()
            //Navigation()
        }
    }

    private fun insertDataToDatabase(){
        val favouriteCharacter = FavouriteCharacter(10001,"massimo regoli","subame la radio","frase tipica: è un bagno de sangue")
        favouriteCharacterViewModel.addFavouriteCharacter(favouriteCharacter)

        val favouriteComic = FavouriteComic(10," regoli","subame la radio","", "")
        favouriteComicViewModel.addFavouriteComic(favouriteComic)
    }

    private fun readAllData(){

    }
}





