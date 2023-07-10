package com.LCDP.marvelwiki.usefulStuff

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import java.math.BigInteger
import java.security.MessageDigest
import java.sql.Timestamp

class Constant {
    companion object {
        const val BASE_URL = "http://gateway.marvel.com/v1/public/characters/"  //  URL di base per le chiamate alle API di Marvel

        val ts = Timestamp(System.currentTimeMillis()).time.toString()  // Timestamp corrente convertito in una stringa
        const val PUBLIC_KEY = "802a20828584c07127363491a6e1a431"
        const val PRIVATE_KEY = "19b4bbe049302f56eb3f60963965fef177f01dd0"

        const val AVENGERS_URL = "http://gateway.marvel.com/v1/public/events/29/"

        // Simone PUBLIC_KEY: "802a20828584c07127363491a6e1a431"
        // Simone PRIVATE_KEY: "19b4bbe049302f56eb3f60963965fef177f01dd0"

        // Lorenzo PUBLIC_KEY: ""
        // Lorenzo PRIVATE_KEY: ""

        // Eduard PUBLIC_KEY: ""
        // Eduard PRIVATE_KEY: ""

        // Federico PUBLIC_KEY: ""
        // Federico PRIVATE_KEY: ""

        // Alessandro PUBLIC_KEY: ""
        // Alessandro PRIVATE_KEY: ""

        /*
            Funzione che calcola l'hash utilizzando il timestamp, la chiave privata e la chiave pubblica.
            Viene impiegato l'algoritmo di hashing MD5 per calcolare l'hash.
         */
        fun hash(): String {
            val input = "$ts$PRIVATE_KEY$PUBLIC_KEY"

            // Creiamo un'istanza dell'oggetto 'MessageDigest' utilizzato per l'hashing dei dati
            val md = MessageDigest.getInstance("MD5")

            /*
                BigInteger rappresenta un intero di grandi dimensioni. Il primo parametro ('1') indica il segno
                del numero (in questo caso positivo), il secondo parametro rappresenta i byte dell'array restituito
                dal metodo digest().

                digest() è un metodo che prende in input un array di byte, equivalente al messaggio da hashare. La
                stringa di input viene quindi convertita in un array di byte.

                Il metodo toString() converte il BigInteger in una stringa (esadecimale, vista la base 16).

                Il metodo padStart() assicura che la stringa esadecimale risultante abbia una lunghezza fissa di
                32 caratteri. Infatti, il primo parametro indica la lunghezza desiderata della stringa, mentre
                il secondo indica il carattere con cui riempirla.

                In sintesi, questa riga di codice esegue l'hash MD5 della stringa di input e restituisce l'hash
                risultante come una stringa esadecimale di 32 caratteri, garantendo che sia riempita con zeri iniziali se necessario.
             */
            return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
        }


        // Viene controllato lo stato della connessione di rete.
        fun isNetworkConnected(context: Context): Boolean {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            /*
                Se la versione di Android in esecuzione è >= ad Android 6.0 viene utilizzata 'NetworkCapabilites'.
                Altrimenti, viene utilizzato l'oggetto 'activeNetworkInfo' depcreato.
             */
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                // network = 'null' indica l'assenza di una connessione di rete
                val network = cm.activeNetwork ?: return false

                // Vengono ottenute le capacità di rete (tipologia, velocità, latenza, ...), se disponibili
                val capabilities = cm.getNetworkCapabilities(network) ?: return false

                return capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
            } else {
                val activeNetwork = cm.activeNetworkInfo
                return activeNetwork?.isConnectedOrConnecting == true
            }
        }
    }
}