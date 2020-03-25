package `in`.galaxyofandroid.spinerdialog

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

internal class DefaultRecyclerAdapter(
    private val _items: List<String>,
    private val onItemClicked: ((String, Int) -> (Unit))?
) :
    SearchableRecyclerAdapter<String>(_items.toMutableList()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.item_default, parent, false))
    }

    override fun getItemCount(): Int = items.size


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView = view.findViewById<TextView>(R.id.basic_item_tv)
        fun bind(value: String) {
            textView.text = value
        }
    }

    override fun search(searchQuery: String, item: String): Boolean {
        return item.toLowerCase().contains(searchQuery.toLowerCase())
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bind(items[position])
        holder.itemView.setOnClickListener { onItemClicked?.invoke(items[position], position) }
    }
}