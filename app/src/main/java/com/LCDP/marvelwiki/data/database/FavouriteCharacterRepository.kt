package com.LCDP.marvelwiki.data.database

import androidx.lifecycle.LiveData

// Classe che fa da intermediario tra il ViewModel e il DAO
class FavouriteCharacterRepository(private val favouriteCharacterDAO: FavouriteCharacterDAO) {

    // Oggetto LiveData che contiene una lista di tutti i personaggi preferiti, ottenuta tramite il DAO
    val readAllData: LiveData<List<FavouriteCharacter>> = favouriteCharacterDAO.readAllData()

    // Funzione che permette di aggiungere un personaggio ai preferiti
    suspend fun addFavouriteCharacter(favouriteCharacter: FavouriteCharacter) {
        favouriteCharacterDAO.addFavouriteCharacter(favouriteCharacter)
    }
}