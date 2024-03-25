package `in`.vicky.newz


// An interface to share the news article based on user click.
fun interface ShareLinkAction {
    fun shareLink(data:NewsItem)
}