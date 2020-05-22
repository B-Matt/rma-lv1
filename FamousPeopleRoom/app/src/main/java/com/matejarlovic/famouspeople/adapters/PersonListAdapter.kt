package com.matejarlovic.famouspeople.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.matejarlovic.famouspeople.R
import com.matejarlovic.famouspeople.room.InspiringPerson
import kotlinx.android.synthetic.main.person_list_item.view.*

enum class ItemClickType {
    REMOVE,
    EDIT
}

class PersonListAdapter(
    private val personData: MutableList<InspiringPerson>?,
    private val listener: ContentListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder
    {
        return PersonViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.person_list_item, parent, false))
    }

    override fun getItemCount(): Int
    {
        return personData?.size ?: 0
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int)
    {
        when(holder)
        {
            is PersonViewHolder -> {
                this.personData?.get(position)?.let { holder.bind(position, it, listener) }
            }
        }
    }

    fun removeItem(index: Int) {

        personData?.removeAt(index)
        notifyItemRemoved(index)
        notifyItemRangeChanged(index, personData?.size ?: 0)
    }

    class PersonViewHolder constructor(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val personPhoto: ImageView = itemView.personPhoto
        private val personName: TextView = itemView.personName
        private val personDates: TextView = itemView.personDates
        private val personDescription: TextView = itemView.personDescription
        private val removeButton: TextView = itemView.removeButton

        fun bind(
            position: Int,
            person: InspiringPerson,
            listener: ContentListener
        )
        {
            personName.text = person.name
            personDates.text = person.lifeDates()
            personDescription.text = person.description

            val requestOptions = RequestOptions()
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)

            Glide.with(itemView.context)
                .applyDefaultRequestOptions(requestOptions)
                .load(person.photoUrl)
                .into(personPhoto)

            personPhoto.setOnClickListener {
                Toast.makeText(itemView.context, person.quotes.shuffled().take(1)[0], Toast.LENGTH_SHORT).show()
            }

            removeButton.setOnClickListener {
                listener.onItemClick(position, person.name, ItemClickType.REMOVE)
            }

            itemView.setOnClickListener {
                listener.onItemClick(position, person.name, ItemClickType.EDIT)
            }
        }
    }

    interface ContentListener {
        fun onItemClick(id: Int, name: String, clickType: ItemClickType)
    }
}