package `in`.vicky.newz

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class NewsListAdapter(private val context: Context, private val list: ArrayList<NewsItem>) : BaseAdapter() {
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
            val view = LayoutInflater.from(context).inflate(R.layout.news_cell,null)
            view.tag = Holder(view)
            updateLatestData(view,position)
            view;
        } else {
            updateLatestData(convertView, position)
            convertView
        }
    }

    private fun updateLatestData(view:View, position: Int) {
        val holder = view.tag as Holder?
        if (holder != null) {
            val data = list[position]
            holder.title.text = data.title
            holder.subTitle.text = data.description
//        holder.thumbNailView
        }
    }

    class Holder(view:View) {
        var title:TextView = view.findViewById(R.id.cardTitle)
        var subTitle:TextView = view.findViewById(R.id.cardSubTitle)
        var thumbNailView:ImageView = view.findViewById(R.id.cardThumbnail)
    }
}