package com.LCDP.marvelwiki.util

import android.os.Handler
import android.os.Looper

/*  Classe per implementare un meccanismo di debounce, utile a ritardare l'esecuzione di un azione
    dopo che questa è stata attivata, consentendo di annullare l'azione se questa viene innescata
    nuovamente entro un certo periodo di tempo  */
class Debouncer(private val delayMillis: Long) {
    private val handler = Handler(Looper.getMainLooper())
    private var runnable: Runnable? = null

    //  Esegue l'azione specificata dopo il ritardo definito
    fun debounce(action: () -> Unit) {

        // Rimozione il callback precedente
        runnable?.let { handler.removeCallbacks(it) }

        //  Crea un nuovo callback che eseguirà l'azione dopo il ritardo specificato
        runnable = Runnable {
            action.invoke()
        }

        //  Pianifica l'esecuzione del nuovo callback dopo il ritardo specificato
        handler.postDelayed(runnable!!, delayMillis)
    }
}