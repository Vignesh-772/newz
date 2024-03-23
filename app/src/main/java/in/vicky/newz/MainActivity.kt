package `in`.vicky.newz

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.LinearLayout
import android.widget.ScrollView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getContentAndShowShimmer();
    }
    private fun getContentAndShowShimmer () {
        val shimmer = findViewById<ScrollView>(R.id.shimmerLayout);
        shimmer.visibility = View.VISIBLE
        getNewsContent(shimmer);
    }
    private fun getNewsContent(shimmer: ScrollView) {
        Thread {
            val url: URL =
                URL("https://candidate-test-data-moengage.s3.amazonaws.com/Android/news-api-feed/staticResponse.json")
            val connection = url.openConnection() as HttpURLConnection;
            connection.requestMethod = "GET";
//            connection.doOutput = true
            connection.connect();
            val response = Utils.streamToJSON(connection.inputStream);
            val mainLayout = findViewById<LinearLayout>(R.id.mainLayout);
            Utils.runOnUiThread{
                shimmer.visibility = View.GONE
                mainLayout.visibility = View.VISIBLE
            }
        }.start();
    }
}