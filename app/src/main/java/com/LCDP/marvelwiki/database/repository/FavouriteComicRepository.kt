package com.LCDP.marvelwiki.database.repository

import com.LCDP.marvelwiki.database.dao.FavouriteComicDAO
import com.LCDP.marvelwiki.database.model.FavouriteComic

// Classe che fa da intermediario tra il rispettivo ViewModel e il DAO
class FavouriteComicRepository(private val favouriteComicDAO: FavouriteComicDAO){

    // Oggetto che contiene una lista degli ID di tutti i fumetti preferiti, ottenuta tramite il DAO
    val allFavouriteComicId: List<String> = favouriteComicDAO.getAllFavoriteComicId()

    // Funzione che permette di aggiungere (l'ID di) un fumetto ai preferiti
    suspend fun addFavouriteComic(favouriteComic: FavouriteComic){
        favouriteComicDAO.insertFavoriteComic(favouriteComic)
    }

    // Funzione che permette di rimuovere (l'ID di) un fumetto dall'elenco dei fumetti preferiti
    suspend fun deleteFavouriteComic(favouriteComic: FavouriteComic){
        favouriteComicDAO.deleteFavouriteComic(favouriteComic)
    }
}