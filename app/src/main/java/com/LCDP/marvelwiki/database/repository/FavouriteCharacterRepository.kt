package com.LCDP.marvelwiki.database.repository

import com.LCDP.marvelwiki.database.dao.FavouriteCharacterDAO
import com.LCDP.marvelwiki.database.model.FavouriteCharacter


// Classe che fa da intermediario tra il rispettivo ViewModel e il DAO
class FavouriteCharacterRepository(private val favouriteCharacterDAO: FavouriteCharacterDAO) {

 /*   // Oggetto che contiene una lista degli ID di tutti i personaggi preferiti, ottenuta tramite il DAO
    val allFavouriteCharacterId: List<String> = favouriteCharacterDAO.getAllFavoriteCharacterId()

    // Funzione che permette di aggiungere (l'ID di) un personaggio ai preferiti
    suspend fun addFavouriteCharacter(favouriteCharacter: FavouriteCharacter) {
        favouriteCharacterDAO.insertFavoriteCharacter(favouriteCharacter)
    }

    // Funzione che permette di rimuovere (l'ID di) un personaggio dall'elenco dei personaggi preferiti
    suspend fun deleteFavouriteCharacter(favouriteCharacter: FavouriteCharacter){
        favouriteCharacterDAO.deleteFavouriteCharacter(favouriteCharacter)
    }

  */
}