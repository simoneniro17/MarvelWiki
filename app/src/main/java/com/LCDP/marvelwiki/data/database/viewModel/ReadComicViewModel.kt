package com.LCDP.marvelwiki.data.database.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.LCDP.marvelwiki.data.database.appDatabase
import com.LCDP.marvelwiki.data.database.dao.ReadComicDAO
import com.LCDP.marvelwiki.data.database.model.ReadComic
import com.LCDP.marvelwiki.data.database.repository.ReadComicRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ReadComicViewModel(application: Application): AndroidViewModel(application) {

    // Oggetto 'LiveData' contenente la lista dei fumetti letti
    val readAllData: LiveData<List<ReadComic>>

    // Istanza del ReadComicRepository
    private val repository: ReadComicRepository

    // Viene inizializzato il ReadComicRepository utilizzando il DAO e viene impostato l'oggetto 'readAllData'
    init {
        val readComicDAO = appDatabase.getDatabase(application).readComicDAO()
        repository = ReadComicRepository(readComicDAO)
        readAllData = repository.readAllData
    }

    fun addReadComic(readComic: ReadComic){
        // L'operazione di inserimento è svolta in un contesto di coroutine in modo asincrono
        viewModelScope.launch(Dispatchers.IO){
            repository.addReadComic(readComic)
        }
    }
}