package com.LCDP.marvelwiki

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import com.LCDP.marvelwiki.data.database.model.FavouriteCharacter
import com.LCDP.marvelwiki.data.database.model.FavouriteComic
import com.LCDP.marvelwiki.data.database.model.ReadComic
import com.LCDP.marvelwiki.data.database.viewModel.FavouriteCharacterViewModel
import com.LCDP.marvelwiki.data.database.viewModel.FavouriteComicViewModel
import com.LCDP.marvelwiki.data.database.viewModel.ReadComicViewModel
import com.LCDP.marvelwiki.ui.screen.Navigation

class MainActivity : ComponentActivity() {

    private lateinit var favouriteCharacterViewModel: FavouriteCharacterViewModel
    private lateinit var favouriteComicViewModel: FavouriteComicViewModel
    private lateinit var readComicViewModel: ReadComicViewModel

    //APP LAUNCH
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            favouriteCharacterViewModel = ViewModelProvider(this).get(FavouriteCharacterViewModel::class.java)
            favouriteComicViewModel = ViewModelProvider(this).get(FavouriteComicViewModel::class.java)
            readComicViewModel = ViewModelProvider(this).get(ReadComicViewModel::class.java)
            insertDataToDatabase()
            readAllData()
            deleteDataFromDatabase()
            //Navigation()
        }
    }

    private fun insertDataToDatabase(){
        val favouriteCharacter = FavouriteCharacter(10001,"massimo regoli","subame la radio","frase tipica: è un bagno de sangue")
        favouriteCharacterViewModel.addFavouriteCharacter(favouriteCharacter)

        val favouriteComic = FavouriteComic(10," regoli","subame la radio","", "")
        favouriteComicViewModel.addFavouriteComic(favouriteComic)

        val favouriteComic2 = FavouriteComic(102030," regoli","subame la radio","", "")
        favouriteComicViewModel.addFavouriteComic(favouriteComic2)

        val readComic = ReadComic(10," regoliiii","subame la radio","")
        readComicViewModel.addReadComic(readComic)
    }

    private fun deleteDataFromDatabase(){
        val favouriteComic = FavouriteComic(10," regoli","subame la radio","", "")
        favouriteComicViewModel.deleteFavouriteComic(favouriteComic)
    }

    private fun readAllData(){

    }
}





