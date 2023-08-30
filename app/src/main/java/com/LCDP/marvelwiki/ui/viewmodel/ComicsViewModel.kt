package com.LCDP.marvelwiki.ui.viewmodel

import android.app.Application
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.LCDP.marvelwiki.data.model.Comic
import com.LCDP.marvelwiki.data.repository.ComicsRepository
import com.LCDP.marvelwiki.database.DatabaseAccess
import com.LCDP.marvelwiki.database.AppDatabase
import kotlinx.coroutines.launch

class ComicsViewModel(private val comicsRepository: ComicsRepository, application: Application) : ViewModel() {
    // Questa classe estente la classe viewModel

    private val context: Context = application.applicationContext

    //La variabile comics è di tipo MutableLiveData, che è una classe fornita da Android Jetpack che può essere osservata per il cambiamento dei dati
    //Resource rappresenta lo stato dei dati (caricamento, successo, errore) e CharacterResponse è il tipo dei dati dei comics
    private val _searchQuery = mutableStateOf("")

    private val _comicList =
        mutableStateListOf<Comic>()                                            //è una mutable list, quando la lista viene modificata, Compose rileva i cambiamenti e aggiorna automaticamente l'interfaccia utente.
    val comicList: List<Comic> get() = _comicList

    private var offset = 0
    //Questo metodo serve per ottenere i dati dei comics.
    //viewModelScope.launch esegue il blocco in modo asincrono. Tramite postValue, si specifca che i dati sono in stato di loading
    //Il metodo getComic_api viene utilizzato per ottenere i dati dei comics

    fun getLatestComicsByCharId(charId: String) {
        _comicList.clear()
        viewModelScope.launch {
            try {
                val comicResponse =
                    comicsRepository.getComicsByCharId_api(charId)                   //viene fatta una chiamata alla api specificando l'offset
                val comics =
                    comicResponse.body()?.comicData?.results                  //prendo il corpo della  risposta che contiene i dati

                if (comics != null) {
                    _comicList.addAll(comics.toMutableList())                               //aggiungo alla mutable list gli altri personaggi appena caricati. È importante notare il cast della lista immutabile appena presa
                    // ad una mutabile per potergli inserire i dati
                    offset += comics.size                                                       //aggiorno l'offset
                }
            } catch (e: Exception) {
                // Gestisco eventuali errori durante il caricamento
                println("Errore durante il caricamento dei personaggi: ${e.message}")
            }
        }
    }

    fun getComicsByIsbn(input: String) {
        _comicList.clear()
        val isbn = input.substring(0,3) + "-" + input.substring(3,4) + "-" + input.substring(4,7) + "-" +input.substring(7,12) + "-" + input.substring(12)
        viewModelScope.launch {
            try {
                val comicResponse =
                    comicsRepository.getComicsByIsbn_api(isbn)                   //viene fatta una chiamata alla api specificando l'offset
                val comics =
                    comicResponse.body()?.comicData?.results                  //prendo il corpo della  risposta che contiene i dati

                if (comics != null) {
                    _comicList.addAll(comics.toMutableList())                               //aggiungo alla mutable list gli altri personaggi appena caricati. È importante notare il cast della lista immutabile appena presa
                    // ad una mutabile per potergli inserire i dati
                }
            } catch (e: Exception) {
                // Gestisco eventuali errori durante il caricamento
                println("Errore durante il caricamento dei personaggi: ${e.message}")
            }
        }
    }


    fun getLatestComic() {
        _comicList.clear()
        viewModelScope.launch {
            try {
                val comicResponse =
                    comicsRepository.getLatestComic_api()                   //viene fatta una chiamata alla api specificando l'offset
                val comics =
                    comicResponse.body()?.comicData?.results                  //prendo il corpo della  risposta che contiene i dati

                if (comics != null) {
                    _comicList.addAll(comics.toMutableList())

                    //aggiungo alla mutable list gli altri personaggi appena caricati. È importante notare il cast della lista immutabile appena presa
                    // ad una mutabile per potergli inserire i dati
                }
            } catch (e: Exception) {
                // Gestisco eventuali errori durante il caricamento
                println("Errore durante il caricamento dei personaggi: ${e.message}")
            }
        }
    }


    fun getComicByName(newQuery: String) {
        _comicList.clear()
        if (newQuery.isEmpty()) {
            _comicList.clear()
        } else {
        viewModelScope.launch {
            try {
                _searchQuery.value = newQuery

                val comicsResponse = comicsRepository.getComicsByName_api(
                    offset = 0,
                    name = newQuery
                )               //viene fatta una chiamata alla api specificando l'offset
                val comics =
                    comicsResponse.body()?.comicData?.results                  //prendo il corpo della  risposta che contiene i dati

                if (comics != null) {
                    _comicList.addAll(comics.toMutableList())                               //aggiungo alla mutable list gli altri personaggi appena caricati. È importante notare il cast della lista immutabile appena presa
                    // ad una mutabile per potergli inserire i dati
                    //offset += comics.size                                                       //aggiorno l'offset
                }
            } catch (e: Exception) {
                // Gestisco eventuali errori durante il caricamento
                println("Errore durante il caricamento dei personaggi: ${e.message}")
            }
        }
        }
    }

    fun unloadFavouriteComics(){
        offset = 0
        _comicList.clear()
    }

    fun loadFavouriteComics() {
        _comicList.clear()
        viewModelScope.launch {
            try {
                val appDatabase = AppDatabase.getDatabase(context)
                val databaseAccess = DatabaseAccess(appDatabase)
                val idList = databaseAccess.getAllFavouriteComics()

                idList.forEach { id ->
                    val comicResponse = comicsRepository.getComicsById_api(id = id)
                    val comics = comicResponse.body()?.comicData?.results
                    if (comics != null) {
                        _comicList.addAll(comics.toMutableList())
                    }
                }
            } catch (e: Exception) {
                // Gestisco eventuali errori durante il caricamento
                println("Errore durante il caricamento dei fumetti preferiti: ${e.message}")
            }
        }

    }

    fun unloadReadComics(){
        offset = 0
        _comicList.clear()
        //loadCharacterList()
    }
    fun loadReadComics() {
        _comicList.clear()
        viewModelScope.launch {
            try {
                val appDatabase = AppDatabase.getDatabase(context)
                val databaseAccess = DatabaseAccess(appDatabase)
                val idList = databaseAccess.getAllReadComics()

                idList.forEach { id ->
                    val comicResponse = comicsRepository.getComicsById_api(id = id)
                    val comics = comicResponse.body()?.comicData?.results
                    if (comics != null) {
                        _comicList.addAll(comics.toMutableList())
                    }
                }
            } catch (e: Exception) {
                // Gestisco eventuali errori durante il caricamento
                println("Errore durante il caricamento dei fumetti letti: ${e.message}")
            }
        }

    }
}


