package `in`.vicky.newz

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.LinearLayout
import android.widget.TextView
import com.google.android.material.imageview.ShapeableImageView
import java.io.ByteArrayOutputStream
import java.net.HttpURLConnection
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

// Adapter to handle the articles in list view.
class NewsListAdapter(
    private val context: Context,
    val list: ArrayList<NewsItem>,
    private val shareLinkAction: ShareLinkAction,
) : BaseAdapter() {
    // An dedicated thread pool to download the images from remote.
    private var executorService: ExecutorService = Executors.newFixedThreadPool(5)
    companion object {
        const val MAX_PNG = 100 * 1024 * 1024 // 100 MB

    }
    // An Hashmap to store the downloaded Images for future use.
    private val imageMap: HashMap<String, Bitmap> = HashMap()
    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(position: Int): Any {
        return list[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        return if (convertView == null) {
            val view = LayoutInflater.from(context).inflate(R.layout.news_cell, null)
            updateLatestData(view, position)
            view;
        } else {
            updateLatestData(convertView, position)
            convertView
        }
    }

    // To shutdown the image download thread pool
    private fun shutDownImageDownload() {
        if (!executorService.isShutdown){
            executorService.shutdownNow()
        }

    }
    // To recreate the image download thread pool
    private fun createImageDownload() {
        if (executorService.isShutdown && executorService.isTerminated)
            executorService = Executors.newFixedThreadPool(5)
    }

    // Update the views based on the data.
    private fun updateLatestData(view: View, position: Int) {
        val data = list[position]
        val thumbNailView = view.findViewById<ShapeableImageView>(R.id.cardThumbnail)
        (view.findViewById<TextView>(R.id.cardTitle)).text = data.title
        (view.findViewById<TextView>(R.id.cardSubTitle)).text = data.description
        (view.findViewById<TextView>(R.id.source)).text = data.source.second.replaceFirstChar{ch -> ch.uppercase()}
        (view.findViewById<TextView>(R.id.publishedAt)).text = Utils.utcToIst(data.publishedAt)
        (view.findViewById<LinearLayout>(R.id.shareNews)).setOnClickListener {
            shareLinkAction.shareLink(
                data
            )
        }
        if (imageMap.containsKey(data.imageUrl)) {
            thumbNailView.setImageBitmap(imageMap[data.imageUrl])
        } else {
            executorService.execute {
                if (!data.didTryDownload) {
                    Utils.runOnUiThread { thumbNailView.setImageResource(R.drawable.ic_loading) }
                    val responsePair = Utils.makeApiCall(data.imageUrl, "GET")
                    if (responsePair.second == HttpURLConnection.HTTP_OK) {
                        var bitmap = BitmapFactory.decodeStream(responsePair.first)
                        val outStream = ByteArrayOutputStream()
                        while (bitmap.byteCount > MAX_PNG) {
                            bitmap.compress(Bitmap.CompressFormat.PNG, 50, outStream)
                            bitmap = BitmapFactory.decodeByteArray(
                                outStream.toByteArray(),
                                0,
                                outStream.size()
                            )
                        }
                        imageMap[data.imageUrl] = bitmap
                        data.didTryDownload = true
                        Utils.runOnUiThread { this.notifyDataSetChanged() }
                    } else {
                        Utils.runOnUiThread { thumbNailView.setImageResource(R.drawable.ic_image_default_thumbnail) }
                    }
                } else {
                    Utils.runOnUiThread {thumbNailView.setImageResource(R.drawable.ic_image_default_thumbnail)}
                }
            }
        }
    }
}