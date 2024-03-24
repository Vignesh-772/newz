package `in`.vicky.newz

import org.json.JSONArray
import org.json.JSONObject

// This is the response we are getting from the Endpoint.
// {
//  "status": "ok",
//  "articles": [
//    {
//      "source": {
//        "id": "techcrunch",
//        "name": "TechCrunch"
//      },
//      "author": "Alex Wilhelm",
//      "title": "Is this what an early-stage slowdown looks like?",
//      "description": "Hello and welcome back to our regular morning look at private companies, public markets and the gray space in between. Today we’re exploring some fascinating data from Silicon Valley Bank markets report for Q1 2020. We’re digging into two charts that deal wit…",
//      "url": "http://techcrunch.com/2020/02/10/is-this-what-an-early-stage-slowdown-looks-like/",
//      "urlToImage": "https://techcrunch.com/wp-content/uploads/2020/02/GettyImages-dv1637047.jpg?w=556",
//      "publishedAt": "2020-02-10T17:06:42Z",
//      "content": "Hello and welcome back to our regular morning look at private companies, public markets and the gray space in between.\r\nToday we’re exploring some fascinating data from Silicon Valley Bank markets report for Q1 2020. We’re digging into two charts that deal wi… [+648 chars]"
//    }...]
//    }/

class NewsItem(private val article: JSONObject) {
    companion object {
        private fun convertArticleToNewsItem(response: JSONObject): NewsItem {
            return NewsItem(response)
        }

        fun convertResponseToNewsItems(response: JSONObject): ArrayList<NewsItem> {
            val articles: JSONArray = response.getJSONArray("articles")
            val newsItem: ArrayList<NewsItem> = ArrayList()
            for (i in 0 until articles.length()) {
                newsItem.add(convertArticleToNewsItem(articles.getJSONObject(i)))
            }
            return newsItem
        }
    }

    var title: String
    var author: String
    var description: String
    var location: String
    var imageUrl: String
    var publishedAt: String
    var content: String
    var source: Pair<String, String> = Pair("NA", "NA")

    init {
        title = article.optString("title", "NA")
        author = article.optString("author", "NA")
        description = article.optString("description", "NA")
        location = article.optString("url", "")
        imageUrl = article.optString("urlToImage", "")
        publishedAt = article.optString("publishedAt", Utils.getCurrentUTC())
        content = article.optString("content", "NA")
        val source = article.optJSONObject("source")
        if (source != null) {
            this.source = Pair(source.optString("id", "NA"), source.optString("name", "NA"))
        }
    }

}