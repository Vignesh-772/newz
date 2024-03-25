package `in`.vicky.newz

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.browser.customtabs.CustomTabsIntent
import androidx.browser.customtabs.CustomTabsIntent.CLOSE_BUTTON_POSITION_START
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

object Utils {
    // Coversion from Stream to JSON Object for better data Handling
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

    // Executes the set of instruction in UI thread
    fun runOnUiThread(runnable: Runnable) {
        val handler = Handler(Looper.getMainLooper())
        handler.post(runnable)
    }
    // to get current time in UTC format
    fun getCurrentUTC() : String{
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale("en", "US"))
        dateFormat.timeZone = TimeZone.getTimeZone("UTC")
        val getCurrTime = dateFormat.format(Date())
        return getCurrTime.toString()
    }
    // Open the link in the browser
    fun openLink(context: Context, link: String) {
        if (link.isNotEmpty()) {
            val uri = Uri.parse(link)
            val intent = Intent(Intent.ACTION_VIEW, uri)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }
    }

    // Open chromes custom tab to show the article
    fun openCustomTab(context: Context, link: String) {
        if (link.isNotEmpty()) {
            try {
                val uri = Uri.parse(link)
                CustomTabsIntent.Builder().build().launchUrl(context, uri)
            } catch (e: Exception) {
                openLink(context, link)
            }
        }
    }

    // Generic function to make API call.
    fun makeApiCall(endpoint: String, method: String): Pair<InputStream,Int>{
        val url = URL(endpoint)
        val connection = url.openConnection() as HttpURLConnection;
        connection.requestMethod = method
        try {
            println("endpoint -> $endpoint");
            connection.connect()
            return if (connection.responseCode in 200..299) {
                Pair(connection.inputStream,connection.responseCode)
            } else {
                Pair(connection.errorStream,connection.responseCode)
            }
        } catch (e:Exception){
            e.printStackTrace()
        }
        return  Pair(connection.errorStream,-1)
    }

    // Converts the UTC to MM-dd-yyyy-HH:mm format
    fun utcToIst (dateString: String) : String {
        return try {
            val date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH)
            date.timeZone = TimeZone.getTimeZone("UTC")
            SimpleDateFormat("MM-dd-yyyy-HH:mm",Locale.ENGLISH).format(date.parse(dateString) as Date)
        } catch (e: Exception) {
            SimpleDateFormat("MM-dd-yyyy-HH:mm",Locale.ENGLISH).format(Date().toString())
        }
    }
}