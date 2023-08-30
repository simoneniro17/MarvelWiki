package com.LCDP.marvelwiki.ui.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.LCDP.marvelwiki.data.repository.CharactersRepository
import kotlinx.coroutines.launch
import com.LCDP.marvelwiki.data.model.Character
import com.LCDP.marvelwiki.database.DatabaseAccess
import com.LCDP.marvelwiki.database.AppDatabase

//  Classe in cui vengono chiamati tutti i metodi relativi alle API dei Character
class CharactersViewModel(
    private val charactersRepository: CharactersRepository,
    application: Application
) : ViewModel() {

    @SuppressLint("StaticFieldLeak")
    private val context: Context = application.applicationContext

    //  Query di ricerca per nome
    private val _searchQuery = mutableStateOf("")

    //  Quando Compose rileva i cambiamenti della mutableList, aggiorna automaticamente la UI
    private val _characterList = mutableStateListOf<Character>()

    //  Prendiamo i dati dalla lista come lista immutabile
    val characterList: List<Character> get() = _characterList

    //  Offset per comunicare alla API da quale punto prendere i personaggi
    private var offset = 0

    //  Per caricare 100 personaggi alla volta
    fun loadCharacterList() {
        viewModelScope.launch {
            try {

                //  Chiamata all'API specificando l'offset
                val charactersResponse = charactersRepository.getChar_api(offset)

                //  Viene preso il corpo della risposta che contiene i dati
                val characters = charactersResponse.body()?.characterData?.results

                //  Il corpo della risposta contiene effettivamente dei dati
                if (characters != null) {

                    /*  Aggiunta alla mutableList degli altri personaggi appena caricati.
                        Cast della lista immutabile ad una mutabile per potergli inserire i dati    */
                    _characterList.addAll(characters.toMutableList())

                    //  Incremento dell'offset
                    offset += characters.size
                }

            } catch (e: Exception) {
                //  Gestione eventuali errori durante il caricamento
                println("Errore durante il caricamento dei personaggi: ${e.message}")

                /*  Toast con il messaggio di errore
                val errorMessage = "Errore durante il caricamento dei personaggi: ${e.message}"
                Toast.makeText(application, errorMessage, Toast.LENGTH_SHORT).show()
                 */
            }
        }
    }

    //  Per caricare 100 personaggi alla volta in base ad una ricerca per nome
    fun loadCharacterByNameList(newQuery: String) {

        //  Prima di caricare nuovi dati, vengono rimossi quelli presenti nella lista corrente
        _characterList.clear()

        //  Se la query è vuota, riportiamo l'offset a 0 e ricarichiamo la lista di tutti i personaggi
        if (newQuery.isEmpty()) {
            offset = 0
            loadCharacterList()
        } else {
            viewModelScope.launch {
                try {

                    //  Viene impostata la query di ricerca in base al parametro passato
                    _searchQuery.value = newQuery

                    //  Chiamata all'API per ottenere i personaggi corrispondenti
                    val charactersResponse =
                        charactersRepository.getChar_api(offset = 0, name = newQuery)

                    //  Viene preso il corpo della risposta che contiene i dati
                    val characters = charactersResponse.body()?.characterData?.results

                    //  La risposta contiene dei dati che vengono aggiunti alla lista
                    if (characters != null) {
                        _characterList.addAll(characters.toMutableList())
                        offset += characters.size
                    }

                } catch (e: Exception) {
                    // Gestione eventuali errori durante il caricamento
                    println("Errore durante il caricamento del fumetto cercato: ${e.message}")
                }
            }
        }
    }

    //  Per caricare la lista dei personaggi preferiti
    fun loadFavouriteCharacters() {

        //  Prima di caricare nuovi dati, vengono rimossi quelli presenti nella lista corrente
        _characterList.clear()

        viewModelScope.launch {
            try {

                //  Istanza del database dall'AppDatabase
                val appDatabase = AppDatabase.getDatabase(context)

                //  Istanza per accedere ai dati del database
                val databaseAccess = DatabaseAccess(appDatabase)

                //  Elenco degli ID dei personaggi preferiti dal database locale
                val idList = databaseAccess.getAllFavouriteCharacters()

                //  Per ciascun personaggio, chiamate alle API per ottenerne i dettagli
                idList.forEach { id ->
                    val characterResponse = charactersRepository.getChar_api(id = id)
                    val characters = characterResponse.body()?.characterData?.results

                    //  Aggiunta dei personaggi ottenuti alla lista visualizzata
                    if (characters != null) {
                        _characterList.addAll(characters.toMutableList())
                    }
                }

            } catch (e: Exception) {
                // Gestisco eventuali errori durante il caricamento
                println("Errore durante il caricamento dei personaggi: ${e.message}")
            }
        }
    }

    //  Per "pulire" la lista dei personaggi dai personaggi preferiti
    fun unloadFavouriteCharacters() {
        offset = 0
        _characterList.clear()
        loadCharacterList()
    }
}