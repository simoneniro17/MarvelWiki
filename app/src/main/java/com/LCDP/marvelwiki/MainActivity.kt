package com.LCDP.marvelwiki

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.LCDP.marvelwiki.data.repository.CharactersRepository
import com.LCDP.marvelwiki.data.repository.ComicsRepository
import com.LCDP.marvelwiki.printer.RetrieveAllChar
import com.LCDP.marvelwiki.printer.RetrieveComicByIsbn

class MainActivity : ComponentActivity() {

    //private lateinit var favouriteCharacterViewModel: FavouriteCharacterViewModel
    //private lateinit var favouriteComicViewModel: FavouriteComicViewModel
    // private lateinit var readComicViewModel: ReadComicViewModel

    //APP LAUNCH
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            //favouriteCharacterViewModel = ViewModelProvider(this).get(FavouriteCharacterViewModel::class.java)
            //favouriteComicViewModel = ViewModelProvider(this).get(FavouriteComicViewModel::class.java)
            //readComicViewModel = ViewModelProvider(this).get(ReadComicViewModel::class.java)
            //insertDataToDatabase()
            //readAllData()
            //Navigation()
            val charactersRepository = CharactersRepository()
            RetrieveAllChar(charactersRepository = charactersRepository)
        }
    }
}

    /*
    private fun insertDataToDatabase(){
        val favouriteCharacter = FavouriteCharacter(10001,"massimo regoli","subame la radio","frase tipica: è un bagno de sangue")
        favouriteCharacterViewModel.addFavouriteCharacter(favouriteCharacter)

        val favouriteComic = FavouriteComic(10," regoli","subame la radio","", "")
        favouriteComicViewModel.addFavouriteComic(favouriteComic)

        //val readComic = ReadComic(10," regoliiii","subame la radio","")
        //readComicViewModel.addReadComic(readComic)
    }

    private fun readAllData(){

    }
}
     */





