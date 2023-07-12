package com.LCDP.marvelwiki.data.database.repository

import androidx.lifecycle.LiveData
import com.LCDP.marvelwiki.data.database.dao.FavouriteComicDAO
import com.LCDP.marvelwiki.data.database.model.FavouriteComic

//Classe che fa da intermediario tra il ViewModel e il DAO
class FavouriteComicRepository(private val favouriteComicDAO: FavouriteComicDAO){

    //Oggetto Live Data contiene una lista di tutti i personaggi preferiti, ottenuta tramite il DAO
    val readAllData: LiveData<List<FavouriteComic>> = favouriteComicDAO.readAllData()

    //Funzione che permette di aggiungere un personaggi ai preferiti
    suspend fun addFavouriteComic(favouriteComic: FavouriteComic){
        favouriteComicDAO.addFavouriteComic(favouriteComic)
    }
}