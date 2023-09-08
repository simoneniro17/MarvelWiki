package com.LCDP.marvelwiki.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import java.math.BigInteger
import java.security.MessageDigest
import java.sql.Timestamp

class Constant {
    companion object {
        const val BASE_URL = "https://gateway.marvel.com/v1/public/"  //  URL di base per le chiamate alle API di Marvel

        val ts = Timestamp(System.currentTimeMillis()).time.toString()  // Timestamp corrente convertito in una stringa
        const val PUBLIC_KEY = "802a20828584c07127363491a6e1a431"
        const val PRIVATE_KEY = "19b4bbe049302f56eb3f60963965fef177f01dd0"
        const val limit = 100

        // Simone PUBLIC_KEY: "802a20828584c07127363491a6e1a431"
        // Simone PRIVATE_KEY: "19b4bbe049302f56eb3f60963965fef177f01dd0"

        // Lorenzo PUBLIC_KEY: "12826ed96e16fb06ba5a0d7cfb710e3a"
        // Lorenzo PRIVATE_KEY: "f88133fa86e3d4ab83967622c275755dc4f34553"

        // Eduard PUBLIC_KEY: ""
        // Eduard PRIVATE_KEY: ""

        // Federico PUBLIC_KEY: ""
        // Federico PRIVATE_KEY: ""

        // Alessandro PUBLIC_KEY: ""
        // Alessandro PRIVATE_KEY: ""

        // Calcolo hash tramite MD5 utilizzando il timestamp, la chiave privata e la chiave pubblica
        fun hash(): String {
            val input = "$ts$PRIVATE_KEY$PUBLIC_KEY"

            // Creiamo un'istanza dell'oggetto 'MessageDigest' utilizzato per l'hashing dei dati
            val md = MessageDigest.getInstance("MD5")

            /*  BigInteger rappresenta un intero di grandi dimensioni. Il primo parametro ('1') indica il segno
                del numero (in questo caso positivo), il secondo parametro rappresenta i byte dell'array restituito
                dal metodo digest()

                digest() è un metodo che prende in input un array di byte, ossia il messaggio di cui trovare l'hash.
                La stringa di input viene quindi convertita in un array di byte

                toString() converte il BigInteger in una stringa (esadecimale, vista la base 16)

                padStart() assicura che la stringa esadecimale risultante abbia una lunghezza fissa di 32
                Infatti, il primo parametro indica la lunghezza desiderata della stringa, mentre il secondo
                indica il carattere con cui riempirla nel caso in cui non rispetti le dimensioni

                In sintesi, questa riga di codice esegue l'hash MD5 della stringa di input e restituisce l'hash
                risultante come una stringa esadecimale di 32 caratteri */
            return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
        }


        // Viene controllato lo stato della connessione di rete
        fun isNetworkConnected(context: Context): Boolean {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            /*  Se la versione di Android in esecuzione è >= ad Android 6.0 viene utilizzata 'NetworkCapabilites'.
                Altrimenti, viene utilizzato l'oggetto 'activeNetworkInfo' deprecato             */
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