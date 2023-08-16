package com.LCDP.marvelwiki.usefulStuff
import android.os.Handler
import android.os.Looper

class Debouncer(private val delayMillis: Long) {
    private val handler = Handler(Looper.getMainLooper())
    private var runnable: Runnable? = null

    fun debounce(action: () -> Unit) {
        // Rimuovi il callback precedente
        runnable?.let { handler.removeCallbacks(it) }

        // Crea un nuovo callback che eseguirà l'azione dopo il ritardo specificato
        runnable = Runnable {
            action.invoke()
        }

        // Pianifica l'esecuzione del nuovo callback dopo il ritardo specificato
        handler.postDelayed(runnable!!, delayMillis)
    }
}