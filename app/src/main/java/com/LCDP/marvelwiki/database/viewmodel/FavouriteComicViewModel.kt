package com.LCDP.marvelwiki.database.viewmodel

import androidx.lifecycle.ViewModel
import com.LCDP.marvelwiki.database.DatabaseAccess
import com.LCDP.marvelwiki.database.model.FavouriteComic
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class FavouriteComicViewModel(private val databaseAccess: DatabaseAccess) : ViewModel() {

    //  Lista degli ID dei fumetti preferiti (inizialmente vuota)
    private var favouriteComicList = emptyList<String>()

    //  Per ottenere gli ID dei fumetti preferiti
    @OptIn(DelicateCoroutinesApi::class)
    fun fetchData(): List<String> {
        GlobalScope.launch {
            favouriteComicList = databaseAccess.getAllFavouriteComics()
        }
        return favouriteComicList
    }

    //  Per inserire l'ID di un fumetto nei preferiti
    @OptIn(DelicateCoroutinesApi::class)
    fun insertData(favouriteComic: FavouriteComic) {
        GlobalScope.launch {
            databaseAccess.insertFavouriteComic(favouriteComic)
        }
    }

    //  Per rimuovere l'ID di un fumetto dai preferiti
    @OptIn(DelicateCoroutinesApi::class)
    fun deleteData(favouriteComic: FavouriteComic) {
        GlobalScope.launch {
            databaseAccess.deleteFavouriteComic(favouriteComic)
        }
    }

    //  Per verificare se l'ID di un fumetto è tra i preferiti
    suspend fun isComicFavourite(selectedId: String): Boolean {
        return databaseAccess.isComicFavourite(selectedId)
    }
}