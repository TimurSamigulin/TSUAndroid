package com.example.tsuandroid.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tsuandroid.R
import com.example.tsuandroid.room.entity.Element

class ElementAdapter(): RecyclerView.Adapter<ElementAdapter.ElementHolder>() {
    private var elements: List<Element> = listOf()
    var onItemClick: ((Element) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ElementHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.element_item, parent, false)
        return ElementHolder(itemView)
    }

    override fun getItemCount(): Int {
        return elements.size
    }

    override fun onBindViewHolder(holder: ElementHolder, position: Int) {
        val currentElement: Element = elements.get(position)
        holder.textViewName.text = currentElement.name
        holder.textViewRating.text = String.format("%.1f", currentElement.rating)
        Glide.with(holder.imageView)
            .load(currentElement.icon)
            .into(holder.imageView)
    }

    fun setElements(elements: List<Element>) {
        this.elements = elements
        notifyDataSetChanged()
    }


    inner class ElementHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var textViewName: TextView
        var textViewRating: TextView
        var imageView: ImageView

        init {
            textViewName = itemView.findViewById(R.id.element_name)
            textViewRating = itemView.findViewById(R.id.element_rating)
            imageView = itemView.findViewById(R.id.element_image)

            itemView.setOnClickListener {
                onItemClick?.invoke(elements[adapterPosition])
            }
        }
    }


}