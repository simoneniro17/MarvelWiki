package com.lcdp.marvelwiki.database.viewmodel

import androidx.lifecycle.ViewModel
import com.lcdp.marvelwiki.database.DatabaseAccess
import com.lcdp.marvelwiki.database.model.FavouriteCharacter
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class FavouriteCharacterViewModel(private val databaseAccess: DatabaseAccess) : ViewModel() {

    //  Lista degli ID dei personaggi preferiti (inizialmente vuota)
    var favouriteCharacterList = emptyList<String>()

    //  Per ottenere gli ID dei personaggi preferiti
    @OptIn(DelicateCoroutinesApi::class)
    fun fetchData(): List<String> {
        GlobalScope.launch {
            favouriteCharacterList = databaseAccess.getAllFavouriteCharacters()
        }
        return favouriteCharacterList
    }

    //  Per inserire l'ID di un personaggio nei preferiti
    @OptIn(DelicateCoroutinesApi::class)
    fun insertData(favouriteCharacter: FavouriteCharacter) {
        GlobalScope.launch {
            databaseAccess.insertFavouriteCharacter(favouriteCharacter)
        }
    }

    //  Per rimuovere l'ID di un personaggio dai preferiti
    @OptIn(DelicateCoroutinesApi::class)
    fun deleteData(favouriteCharacter: FavouriteCharacter) {
        GlobalScope.launch {
            databaseAccess.deleteFavouriteCharacter(favouriteCharacter)
        }
    }

    //  Per verificare se l'ID di un personaggio è tra i preferiti
    suspend fun isCharacterFavourite(selectedId: String): Boolean {
        return databaseAccess.isCharacterFavourite(selectedId)
    }
}