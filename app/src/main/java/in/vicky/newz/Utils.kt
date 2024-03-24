package `in`.vicky.newz

import android.os.Handler
import android.os.Looper
import android.util.Log
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

object Utils {
    fun streamToJSON(responseStream: InputStream): JSONObject {
        return try {
            val reader = BufferedReader(InputStreamReader(responseStream))
            val responseBuilder = StringBuilder()
            var line: String?
            while (reader.readLine().also { line = it } != null) {
                responseBuilder.append(line)
            }
            reader.close()
            JSONObject(responseBuilder.toString())
        } catch (e: Exception) {
            Log.e("UTILS", " streamToJSON -> This happened - $e")
            JSONObject()
        }
    }

    fun runOnUiThread(runnable: Runnable) {
        val handler = Handler(Looper.getMainLooper())
        handler.post(runnable)
    }
    fun getCurrentUTC() : String{
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale("en", "US"))
        dateFormat.timeZone = TimeZone.getTimeZone("UTC")
        val getCurrTime = dateFormat.format(Date())
        return getCurrTime.toString()
    }
}