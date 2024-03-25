package `in`.vicky.newz

import android.content.ClipData
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {
    var isPopupOpened  = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // Rending the main layout
        getContentAndShowShimmer()
    }

    private fun getContentAndShowShimmer() {
        val shimmer = findViewById<ScrollView>(R.id.shimmerLayout);
        val sortView = findViewById<TextView>(R.id.sort_btn)
        sortView.alpha= 0.5f
        sortView.isClickable = false
        shimmer.visibility = View.VISIBLE // Adding Shimmer till the API call is done
        getNewsContent();
    }

    private fun getNewsContent() {
        Thread {
            val responsePair = Utils.makeApiCall("https://candidate-test-data-moengage.s3.amazonaws.com/Android/news-api-feed/staticResponse.json","GET") // Making API to get the Data in separate thread. API calls should not happen in main thread.
            val response = Utils.streamToJSON(responsePair.first)
            showNewsList(NewsItem.convertResponseToNewsItems(response))
        }.start()
    }
    // Updating the UI and transforming the data based on API response.
    // also Adding Onclick Listeners
    // /
    private fun showNewsList(list: ArrayList<NewsItem>) {
        val shimmer = findViewById<ScrollView>(R.id.shimmerLayout);
        val mainLayout = findViewById<LinearLayout>(R.id.mainLayout);
        val listView = findViewById<ListView>(R.id.newsListView);
        Utils.runOnUiThread {
            shimmer.visibility = View.GONE
            mainLayout.visibility = View.VISIBLE
            listView.adapter = NewsListAdapter(this, list) { data -> shareNews(data) }
            listView.setOnItemClickListener { _, _, position, _ ->
                Utils.openCustomTab(this, list[position].location)
            }
            val sortView = findViewById<TextView>(R.id.sort_btn)
            val ascending = findViewById<TextView>(R.id.ascending)
            val descending = findViewById<TextView>(R.id.descending)
            sortView.isClickable = true
            sortView.alpha = 1.0f
            sortView.setOnClickListener{
                checkAndClosePopUp()
            }
            ascending.setOnClickListener{
                (listView.adapter as NewsListAdapter).list.sortBy{ it.publishedAt}
                (listView.adapter as NewsListAdapter).notifyDataSetChanged()
                checkAndClosePopUp()
            }
            descending.setOnClickListener{
                (listView.adapter as NewsListAdapter).list.sortBy{ it.publishedAt}
                (listView.adapter as NewsListAdapter).list.reverse()
                (listView.adapter as NewsListAdapter).notifyDataSetChanged()
                checkAndClosePopUp()
            }
        }
    }

    // Function to verify and handle the sort popup
    private fun checkAndClosePopUp() {
        val sortPopup = findViewById<LinearLayout>(R.id.sortPopup)
        if (isPopupOpened) {
            sortPopup.visibility = View.GONE
            isPopupOpened = false
        } else {
            sortPopup.visibility = View.VISIBLE
            isPopupOpened = true
        }
    }

    // Function to Share the news with other Apps.
    private fun shareNews(data:NewsItem) {
        val sendIntent = Intent()
        sendIntent.setAction(Intent.ACTION_SEND)
        sendIntent.putExtra(Intent.EXTRA_TEXT, data.title + "\n\n" + "Click the link below \n\n" + data.location)
        sendIntent.putExtra(Intent.EXTRA_TITLE, getString(R.string.share_news_title))
        sendIntent.setType("text/plain")
        val shareIntent = Intent.createChooser(sendIntent, null)
        shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        this.startActivity(shareIntent)
    }
}