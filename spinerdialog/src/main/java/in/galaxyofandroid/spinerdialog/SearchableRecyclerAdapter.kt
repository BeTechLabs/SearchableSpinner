package `in`.galaxyofandroid.spinerdialog

import android.support.v7.widget.RecyclerView


abstract class SearchableRecyclerAdapter<Model>(val items: MutableList<Model>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val _items = items.toMutableList()
    abstract fun search(searchQuery: String, item: Model): Boolean

    internal fun applySearch(searchQuery: String) {
        val filteredList = _items.filter {
            search(searchQuery, it)
        }
        items.clear()
        items.addAll(filteredList)
        notifyDataSetChanged()
    }

    internal fun resetItems() {
        items.clear()
        items.addAll(_items)
    }
}