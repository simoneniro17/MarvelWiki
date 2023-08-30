package com.LCDP.marvelwiki

import android.app.AlertDialog
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.LCDP.marvelwiki.ui.screen.Navigation
import com.LCDP.marvelwiki.usefulStuff.Constant.Companion.isNetworkConnected

class MainActivity : ComponentActivity() {

    //Inizializzazione del media player per riprodurre musica
    private var mediaPlayer : MediaPlayer?= null

    //APP LAUNCH
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Setup del media player
        mediaPlayer = MediaPlayer.create(this, R.raw.superhero_theme)
        mediaPlayer?.isLooping = true
        mediaPlayer?.start()

        // Controllo della connessione a Internet
        checkNetworkConnection()

        setContent {
            val context = this
            Navigation(context)
        }
    }

    //Gestione del media player nel caso in cui l' applicazione venga mandata in background
    override fun onPause() {
        super.onPause()
        mediaPlayer?.pause()
    }

    //Gestione del media player nel caso in cui l' applicazione venga ripresa dal background
    override fun onResume() {
        super.onResume()
        mediaPlayer?.start()
    }

    //Gestione del media player nel caso in cui l' applicazione venga chiusa
    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
    }

    //  Controlla se il dispositivo non è connesso ad internet
    private fun checkNetworkConnection() {
        if (!isNetworkConnected(this)) {
            showNetworkAlert()
        }
    }

    //TODO rendere stringhe "scalabili"
    //  Per mostrare un alert all'utente nel caso di mancata connessione ad Internet
    private fun showNetworkAlert() {
        val builder = AlertDialog.Builder(this)

        builder.setTitle("Connessione a Internet assente")  // Titolo dell'alert
        builder.setMessage("Il dispositivo non è connesso a Internet. Controlla la connessione e riprova.") // Messaggio informativo
        builder.setPositiveButton("Ok") { dialog, _ ->
            dialog.dismiss()    // Chiusura dell'alert
            finish()    // Chiusura dell'applicazione
        }
        builder.setCancelable(false)    // L'utente non può annullare l'alert cliccando al di fuori

        val dialog = builder.create()
        dialog.show()   // Viene mostrato l'alert all'utente
    }
}