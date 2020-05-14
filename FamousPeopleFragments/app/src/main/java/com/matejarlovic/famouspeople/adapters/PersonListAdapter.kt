package com.matejarlovic.famouspeople

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.person_list_item.view.*

enum class ItemClickType {
    REMOVE,
    EDIT
}

class PersonListAdapter(val listener: ContentListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>()
{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder
    {
        return PersonViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.person_list_item, parent, false))
    }

    override fun getItemCount(): Int
    {
        return PeopleRepository.instance.length()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int)
    {
        when(holder)
        {
            is PersonViewHolder -> {
                holder.bind(PeopleRepository.instance.getPersons()[position], position, listener)
            }
        }
    }

    class PersonViewHolder constructor(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val personPhoto: ImageView = itemView.personPhoto
        private val personName: TextView = itemView.personName
        private val personDates: TextView = itemView.personDates
        private val personDescription: TextView = itemView.personDescription
        private val removeButton: TextView = itemView.removeButton

        fun bind(person: InspiringPerson, personId: Int, listener: ContentListener)
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
                Toast.makeText(itemView.context, person.quotes.shuffled().take(1)[0].quote, Toast.LENGTH_SHORT).show()
            }

            removeButton.setOnClickListener {
                listener.onItemClick(personId, ItemClickType.REMOVE)
            }

            itemView.setOnClickListener {
                listener.onItemClick(personId, ItemClickType.EDIT)
            }
        }
    }

    interface ContentListener {
        fun onItemClick(id: Int, clickType: ItemClickType)
    }
}