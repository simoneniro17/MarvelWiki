package com.LCDP.marvelwiki.ui.viewmodel

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

//import kotlinx.coroutines.flow.internal.NoOpContinuation.context
//import kotlin.coroutines.jvm.internal.CompletedContinuation.context

//In questa classe chiamiamo tutti i metodi relativi alla pi dei characters
class CharactersViewModel(
    private val charactersRepository: CharactersRepository,
    application: Application
) : ViewModel() {
    private val context: Context = application.applicationContext
    private val _searchQuery = mutableStateOf("")

    private val _characterList =
        mutableStateListOf<Character>()                                            //è una mutable list, quando la lista viene modificata, Compose rileva i cambiamenti e aggiorna automaticamente l'interfaccia utente.
    val characterList: List<Character> get() = _characterList                                       //prendiamo i dati dalla lista come lista immutabile


    private var offset =
        0                                                                          //offset per comunicare alla api da quale punto deve iniziare a prendere gli eroi

    fun loadCharacterList() {                                                                       //carica la lista di personaggi 100 alla volta
        viewModelScope.launch {
            try {
                val charactersResponse =
                    charactersRepository.getChar_api(offset)                   //viene fatta una chiamata alla api specificando l'offset
                val characters =
                    charactersResponse.body()?.characterData?.results                  //prendo il corpo della  risposta che contiene i dati

                if (characters != null) {
                    _characterList.addAll(characters.toMutableList())                               //aggiungo alla mutable list gli altri personaggi appena caricati. È importante notare il cast della lista immutabile appena presa
                    // ad una mutabile per potergli inserire i dati
                    offset += characters.size                                                       //aggiorno l'offset
                }
            } catch (e: Exception) {
                // Gestisco eventuali errori durante il caricamento
                println("Errore durante il caricamento dei personaggi: ${e.message}")
            }
        }
    }

    fun loadCharacterByNameList(newQuery: String) { //carica la lista di personaggi 100 alla volta
        _characterList.clear()
        if (newQuery.isEmpty()) {
            offset = 0
            loadCharacterList()
        } else {
            viewModelScope.launch {
                try {
                    _searchQuery.value = newQuery

                    val charactersResponse = charactersRepository.getChar_api(
                        offset = 0,
                        name = newQuery
                    )               //viene fatta una chiamata alla api specificando l'offset
                    val characters =
                        charactersResponse.body()?.characterData?.results                  //prendo il corpo della  risposta che contiene i dati

                    if (characters != null) {
                        _characterList.addAll(characters.toMutableList())                               //aggiungo alla mutable list gli altri personaggi appena caricati. È importante notare il cast della lista immutabile appena presa
                        // ad una mutabile per potergli inserire i dati
                        offset += characters.size                                                       //aggiorno l'offset
                    }

                } catch (e: Exception) {
                    // Gestisco eventuali errori durante il caricamento
                    println("Errore durante il caricamento dei personaggi: ${e.message}")
                }
            }
        }
    }

    fun unloadFavouriteCharacters() {
        offset = 0
        _characterList.clear()
        loadCharacterList()
    }

    fun loadFavouriteCharacters() {
        _characterList.clear()
        viewModelScope.launch {
            try {
                val appDatabase = AppDatabase.getDatabase(context)
                val databaseAccess = DatabaseAccess(appDatabase)
                val idList = databaseAccess.getAllFavouriteCharacters()

                idList.forEach { id ->
                    val characterResponse = charactersRepository.getChar_api(id = id)
                    val characters = characterResponse.body()?.characterData?.results
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


}