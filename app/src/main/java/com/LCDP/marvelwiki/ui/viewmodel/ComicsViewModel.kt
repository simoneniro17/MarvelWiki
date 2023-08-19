package com.LCDP.marvelwiki.ui.viewmodel

import android.annotation.SuppressLint
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.LCDP.marvelwiki.data.API.ComicsAPICall.ComicInstance
import com.LCDP.marvelwiki.data.model.Character
import com.LCDP.marvelwiki.data.model.CharacterResponse
import com.LCDP.marvelwiki.data.model.Comic
import com.LCDP.marvelwiki.data.model.ComicResponse
import com.LCDP.marvelwiki.data.repository.ComicsRepository
import com.LCDP.marvelwiki.usefulStuff.Constant
import com.LCDP.marvelwiki.usefulStuff.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class ComicsViewModel(private val comicsRepository: ComicsRepository) : ViewModel() {
    // Questa classe estente la classe viewModel

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
                    comicsRepository.getComicsByCharId_api(offset)                   //viene fatta una chiamata alla api specificando l'offset
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

    fun getComicsByIsbn(isbn: String) {
        _comicList.clear()
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
}


