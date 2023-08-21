package com.LCDP.marvelwiki.database.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.LCDP.marvelwiki.database.appDatabase
import com.LCDP.marvelwiki.database.model.ReadComic
import com.LCDP.marvelwiki.database.repository.ReadComicRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ReadComicViewModel(application: Application): AndroidViewModel(application) {

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
}