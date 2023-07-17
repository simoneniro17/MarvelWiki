package com.LCDP.marvelwiki.ui.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.LCDP.marvelwiki.data.model.CharacterResponse
import com.LCDP.marvelwiki.data.repository.CharactersRepository
import com.LCDP.marvelwiki.usefulStuff.Constant
import com.LCDP.marvelwiki.usefulStuff.Resource
import kotlinx.coroutines.launch
import retrofit2.Response
import com.LCDP.marvelwiki.data.model.Character

//In questa classe chiamiamo tutti i metodi relativi alla pi dei characters
class CharactersViewModel(private val charactersRepository: CharactersRepository) : ViewModel() {
    val _characterList = mutableStateListOf<Character>()                                            //è una mutable list, quando la lista viene modificata, Compose rileva i cambiamenti e aggiorna automaticamente l'interfaccia utente.
    val characterList: List<Character> get() = _characterList                                       //prendiamo i dati dalla lista come lista immutabile

    private var offset = 0                                                                          //offset per comunicare alla api da quale punto deve iniziare a prendere gli eroi
    fun loadCharacterList() {                                                                       //carica la lista di personaggi 100 alla volta
        viewModelScope.launch {
            try {
                val charactersResponse = charactersRepository.getChar_api(offset)                   //viene fatta una chiamata alla api specificando l'offset
                val characters = charactersResponse.body()?.characterData?.results                  //prendo il corpo della  risposta che contiene i dati

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