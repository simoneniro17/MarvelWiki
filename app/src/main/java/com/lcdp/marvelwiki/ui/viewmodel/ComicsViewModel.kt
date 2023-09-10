package com.lcdp.marvelwiki.ui.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lcdp.marvelwiki.data.model.Comic
import com.lcdp.marvelwiki.data.repository.ComicsRepository
import com.lcdp.marvelwiki.database.DatabaseAccess
import com.lcdp.marvelwiki.database.AppDatabase
import kotlinx.coroutines.launch

//  Classe in cui vengono chiamati tutti i metodi relativi alle API dei Comic
class ComicsViewModel(
    private val comicsRepository: ComicsRepository,
    application: Application
) : ViewModel() {

    @SuppressLint("StaticFieldLeak")
    private val context: Context = application.applicationContext

    //  Query di ricerca per nome
    private val _searchQuery = mutableStateOf("")

    //  Quando Compose rileva i cambiamenti della mutableList, aggiorna automaticamente la UI
    private val _comicList = mutableStateListOf<Comic>()

    //  Prendiamo i dati dalla lista come lista immutabile
    val comicList: List<Comic> get() = _comicList

    //  Offset per comunicare alla API da quale punto prendere i fumetti
    private var offset = 0

    fun getLatestComic() {

        //  Prima di caricare nuovi dati, vengono rimossi quelli presenti nella lista corrente
        _comicList.clear()

        viewModelScope.launch {
            try {

                //  Chiamata all'API per ottenere gli ultimi fumetti
                val comicResponse =
                    comicsRepository.getLatestComicAPI()

                //  Viene preso il corpo della risposta che contiene i dati
                val comics =
                    comicResponse.body()?.comicData?.results

                //  La risposta contiene dei dati che vengono aggiunti alla lista
                if (comics != null) {
                    _comicList.addAll(comics.toMutableList())
                }

            } catch (e: Exception) {
                // Gestisco eventuali errori durante il caricamento
                println("Errore durante il caricamento degli ultimi fumetti: ${e.message}")
            }
        }
    }

    //  Per ottenere i fumetti in base ad una ricerca per nome
    fun getComicByName(newQuery: String) {

        //  Prima di caricare nuovi dati, vengono rimossi quelli presenti nella lista corrente
        _comicList.clear()

        //  Se la query è vuota, non devono essere caricati fumetti
        if (newQuery.isEmpty()) {
            _comicList.clear()
        } else {
            viewModelScope.launch {
                try {

                    //  Viene impostata la query di ricerca in base al parametro passato
                    _searchQuery.value = newQuery

                    //  Chiamata all'API per ottenere i fumetti corrispondenti
                    val comicsResponse =
                        comicsRepository.getComicsByNameAPI(offset = 0, name = newQuery)

                    //  Viene preso il corpo della risposta che contiene i dati
                    val comics =
                        comicsResponse.body()?.comicData?.results

                    //  La risposta contiene dei dati che vengono aggiunti alla lista
                    if (comics != null) {
                        _comicList.addAll(comics.toMutableList())
                    }

                } catch (e: Exception) {
                    // Gestione eventuali errori durante il caricamento
                    println("Errore durante il caricamento dei fumetti: ${e.message}")
                }
            }
        }
    }

    fun getComicsByIsbn(input: String) {

        //  Prima di caricare nuovi dati, vengono rimossi quelli presenti nella lista corrente
        _comicList.clear()

        //  Formattazione input nel formato ISBN
        val isbn = input.substring(0, 3) + "-" + input.substring(3, 4) + "-" +
                input.substring(4, 7) + "-" + input.substring(7, 12) + "-" + input.substring(12)

        viewModelScope.launch {
            try {

                //  Chiamata all'API per ottenere il fumetto con ISBN inserito
                val comicResponse =
                    comicsRepository.getComicsByIsbnAPI(isbn)

                //  Viene preso il corpo della risposta che contiene i dati
                val comics =
                    comicResponse.body()?.comicData?.results

                //  La risposta contiene dei dati che vengono aggiunti alla lista
                if (comics != null) {
                    _comicList.addAll(comics.toMutableList())
                }

            } catch (e: Exception) {
                // Gestisco eventuali errori durante il caricamento
                println("Errore durante il caricamento dei personaggi: ${e.message}")
            }
        }
    }

    fun loadFavouriteComics() {

        //  Prima di caricare nuovi dati, vengono rimossi quelli presenti nella lista corrente
        _comicList.clear()

        viewModelScope.launch {
            try {

                //  Istanza del database dall'AppDatabase
                val appDatabase = AppDatabase.getDatabase(context)

                //  Istanza per accedere ai dati del database
                val databaseAccess = DatabaseAccess(appDatabase)

                //  Elenco degli ID dei fumetti preferiti dal database locale
                val idList = databaseAccess.getAllFavouriteComics()

                //  Per ciascun fumetto, chiamate alle API per ottenerne i dettagli
                idList.forEach { id ->
                    val comicResponse = comicsRepository.getComicsByIdAPI(id = id)
                    val comics = comicResponse.body()?.comicData?.results

                    //  Aggiunta dei fumetti ottenuti alla lista visualizzata
                    if (comics != null) {
                        _comicList.addAll(comics.toMutableList())
                    }
                }

            } catch (e: Exception) {
                // Gestione eventuali errori durante il caricamento
                println("Errore durante il caricamento dei fumetti preferiti: ${e.message}")
            }
        }
    }

    fun loadReadComics() {

        //  Prima di caricare nuovi dati, vengono rimossi quelli presenti nella lista corrente
        _comicList.clear()

        viewModelScope.launch {
            try {

                //  Istanza del database dall'AppDatabase
                val appDatabase = AppDatabase.getDatabase(context)

                //  Istanza per accedere ai dati del database
                val databaseAccess = DatabaseAccess(appDatabase)

                //  Elenco degli ID dei fumetti letti dal database locale
                val idList = databaseAccess.getAllReadComics()

                //  Per ciascun fumetto, chiamate alle API per ottenerne i dettagli
                idList.forEach { id ->
                    val comicResponse = comicsRepository.getComicsByIdAPI(id = id)
                    val comics = comicResponse.body()?.comicData?.results

                    //  Aggiunta dei fumetti ottenuti alla lista visualizzata
                    if (comics != null) {
                        _comicList.addAll(comics.toMutableList())
                    }
                }

            } catch (e: Exception) {
                // Gestione eventuali errori durante il caricamento
                println("Errore durante il caricamento dei fumetti letti: ${e.message}")
            }
        }
    }
}