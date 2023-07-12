package com.LCDP.marvelwiki.data.database.repository

import androidx.lifecycle.LiveData
import com.LCDP.marvelwiki.data.database.dao.FavouriteCharacterDAO
import com.LCDP.marvelwiki.data.database.model.FavouriteCharacter

// Classe che fa da intermediario tra il ViewModel e il DAO
class FavouriteCharacterRepository(private val favouriteCharacterDAO: FavouriteCharacterDAO) {

    // Oggetto LiveData che contiene una lista di tutti i personaggi preferiti, ottenuta tramite il DAO
    val readAllData: LiveData<List<FavouriteCharacter>> = favouriteCharacterDAO.readAllData()

    // Funzione che permette di aggiungere un personaggio ai preferiti
    suspend fun addFavouriteCharacter(favouriteCharacter: FavouriteCharacter) {
        favouriteCharacterDAO.addFavouriteCharacter(favouriteCharacter)
    }
}