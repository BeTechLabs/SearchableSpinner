package `in`.galaxyofandroid.searchablespinner

import `in`.galaxyofandroid.spinerdialog.SearchableRecyclerAdapter
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.github.beTechLabs.spinnydialogapp.CustomModel

internal class DefaultRecyclerAdapter(
        private val _items: List<CustomModel>,
        private val onItemClicked: (CustomModel, Int) -> (Unit)
) :
    SearchableRecyclerAdapter<CustomModel>(_items.toMutableList()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.item_custom, parent, false))
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val firstTextView = view.findViewById<TextView>(R.id.first)
        val secondTextView = view.findViewById<TextView>(R.id.second)

        fun bind(value: CustomModel) {
            firstTextView.text = value.first
            secondTextView.text = value.second
        }
    }

    override fun search(searchQuery: String, item: CustomModel): Boolean {
        return item.first.toLowerCase().contains(searchQuery.toLowerCase())
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bind(items[position])
        holder.itemView.setOnClickListener { onItemClicked(items[position], position) }
    }
}