package `in`.vicky.newz

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.ScrollView
import androidx.appcompat.app.AppCompatActivity
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getContentAndShowShimmer();
    }

    private fun getContentAndShowShimmer() {
        val shimmer = findViewById<ScrollView>(R.id.shimmerLayout);
        shimmer.visibility = View.VISIBLE
        getNewsContent();

    }

    private fun getNewsContent() {
        Thread {
            val url: URL =
                URL("https://candidate-test-data-moengage.s3.amazonaws.com/Android/news-api-feed/staticResponse.json")
            val connection = url.openConnection() as HttpURLConnection;
            connection.requestMethod = "GET";
            connection.connect();
            val response = Utils.streamToJSON(connection.inputStream)
            showNewsList(NewsItem.convertResponseToNewsItems(response))
        }.start();
    }

    private fun showNewsList(list: ArrayList<NewsItem>) {
        val shimmer = findViewById<ScrollView>(R.id.shimmerLayout);
        val mainLayout = findViewById<LinearLayout>(R.id.mainLayout);
        val listView = findViewById<ListView>(R.id.newsListView);
        Utils.runOnUiThread {
            shimmer.visibility = View.GONE
            mainLayout.visibility = View.VISIBLE
            listView.adapter = NewsListAdapter(this, list)
        }
    }
}