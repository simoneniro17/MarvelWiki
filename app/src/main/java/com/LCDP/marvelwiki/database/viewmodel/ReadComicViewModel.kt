package com.LCDP.marvelwiki.database.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.LCDP.marvelwiki.database.DatabaseAccess
import com.LCDP.marvelwiki.database.appDatabase
import com.LCDP.marvelwiki.database.model.FavouriteComic
import com.LCDP.marvelwiki.database.model.ReadComic
import com.LCDP.marvelwiki.database.repository.ReadComicRepository
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/* class ReadComicViewModel(application: Application): AndroidViewModel(application) {

    // Oggetto contenente la lista (degli ID) dei fumetti letti
    val allReadComicId: List<String>

    // Istanza del ReadComicRepository
    private val repository: ReadComicRepository

    // Viene inizializzato il ReadComicRepository utilizzando il DAO e viene impostato l'oggetto 'allReadComicId'
    init {
        val readComicDAO = appDatabase.getDatabase(application).readComicDAO()
        repository = ReadComicRepository(readComicDAO)
        allReadComicId = repository.allReadComicId
    }

    fun addReadComic(readComic: ReadComic){
        // L'operazione di inserimento è svolta in un contesto di coroutine in modo asincrono
        viewModelScope.launch(Dispatchers.IO){
            repository.addReadComic(readComic)
        }
    }

    fun deleteReadComic(readComic: ReadComic){
        // L'operazione di cancellazione è svolta in un contesto di coroutine in modo asincrono
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteReadComic(readComic)
        }
    }
} */

class ReadComicViewModel( private val databaseAccess: DatabaseAccess) : ViewModel() {

    private var readComicList = emptyList<String>()
    private var isFavourite : Boolean = false

    @OptIn(DelicateCoroutinesApi::class)
    fun fetchData():List<String>{
        GlobalScope.launch {
            readComicList = databaseAccess.getAllReadComics()
        }
        return readComicList
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun insertData(readComic: ReadComic) {
        GlobalScope.launch {
            databaseAccess.insertReadComic(readComic)
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun deleteData(readComic: ReadComic) {
        GlobalScope.launch {
            databaseAccess.deleteReadComic(readComic)
        }
    }

    /*
    @OptIn(DelicateCoroutinesApi::class)
    fun isComicRead(readComic: ReadComic) : Boolean {
        GlobalScope.launch {
            isFavourite = databaseAccess.isComicRead(readComic)
        }
        return isFavourite
    }

     */
}