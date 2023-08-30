package com.LCDP.marvelwiki.database.viewmodel

import androidx.lifecycle.ViewModel
import com.LCDP.marvelwiki.database.DatabaseAccess
import com.LCDP.marvelwiki.database.model.ReadComic
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ReadComicViewModel(private val databaseAccess: DatabaseAccess) : ViewModel() {

    //  Lista degli ID dei fumetti letti (inizialmente vuota)
    private var readComicList = emptyList<String>()

    //  Per ottenere gli ID dei fumetti preferiti
    @OptIn(DelicateCoroutinesApi::class)
    fun fetchData(): List<String> {
        GlobalScope.launch {
            readComicList = databaseAccess.getAllReadComics()
        }
        return readComicList
    }

    //  Per inserire l'ID di un fumetto tra quelli letti
    @OptIn(DelicateCoroutinesApi::class)
    fun insertData(readComic: ReadComic) {
        GlobalScope.launch {
            databaseAccess.insertReadComic(readComic)
        }
    }

    //  Per rimuovere l'ID di un fumetto da quelli letti
    @OptIn(DelicateCoroutinesApi::class)
    fun deleteData(readComic: ReadComic) {
        GlobalScope.launch {
            databaseAccess.deleteReadComic(readComic)
        }
    }

    //  Per verificare se l'ID di un fumetto è tra quelli letti
    suspend fun isComicRead(selectedId: String): Boolean {
        return databaseAccess.isComicRead(selectedId)
    }
}