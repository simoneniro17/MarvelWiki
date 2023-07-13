package com.LCDP.marvelwiki.database.repository

import androidx.lifecycle.LiveData
import com.LCDP.marvelwiki.database.dao.FavouriteCharacterDAO
import com.LCDP.marvelwiki.database.model.FavouriteCharacter
import com.LCDP.marvelwiki.database.model.ReadComic

// Classe che fa da intermediario tra il ViewModel e il DAO
class FavouriteCharacterRepository(private val favouriteCharacterDAO: FavouriteCharacterDAO) {

    // Oggetto LiveData che contiene una lista di tutti i personaggi preferiti, ottenuta tramite il DAO
    val readAllData: LiveData<List<FavouriteCharacter>> = favouriteCharacterDAO.readAllData()

    // Funzione che permette di aggiungere un personaggio ai preferiti
    suspend fun addFavouriteCharacter(favouriteCharacter: FavouriteCharacter) {
        favouriteCharacterDAO.addFavouriteCharacter(favouriteCharacter)
    }

    // Funzione che permette di rimuovere un personaggio dall'elenco dei personaggi preferiti
    suspend fun deleteFavouriteCharacter(favouriteCharacter: FavouriteCharacter){
        favouriteCharacterDAO.deleteFavouriteCharacter(favouriteCharacter)
    }
}