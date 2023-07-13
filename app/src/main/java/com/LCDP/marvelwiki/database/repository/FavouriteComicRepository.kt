package com.LCDP.marvelwiki.database.repository

import androidx.lifecycle.LiveData
import com.LCDP.marvelwiki.database.dao.FavouriteComicDAO
import com.LCDP.marvelwiki.database.model.FavouriteCharacter
import com.LCDP.marvelwiki.database.model.FavouriteComic

//Classe che fa da intermediario tra il ViewModel e il DAO
class FavouriteComicRepository(private val favouriteComicDAO: FavouriteComicDAO){

    //Oggetto Live Data contiene una lista di tutti i personaggi preferiti, ottenuta tramite il DAO
    val readAllData: LiveData<List<FavouriteComic>> = favouriteComicDAO.readAllData()

    //Funzione che permette di aggiungere un personaggi ai preferiti
    suspend fun addFavouriteComic(favouriteComic: FavouriteComic){
        favouriteComicDAO.addFavouriteComic(favouriteComic)
    }

    // Funzione che permette di rimuovere un fumetto dall'elenco dei fumetti preferiti
    suspend fun deleteFavouriteComic(favouriteComic: FavouriteComic){
        favouriteComicDAO.deleteFavouriteComic(favouriteComic)
    }
}