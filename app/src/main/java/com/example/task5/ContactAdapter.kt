package com.example.task5

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class ContactAdapter(
    private val onClickListener: (Contact) -> Unit,
    private val onLongClickListener: (Contact) -> Boolean
) : RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {

    var contacts: List<Contact> = emptyList()
        set(newValue) {
            val diffCallBack = ContactDiffUtilCallback(field, newValue)
            val diffResult = DiffUtil.calculateDiff(diffCallBack)
            field = newValue
            diffResult.dispatchUpdatesTo(this)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_contact, parent, false)
        return ContactViewHolder(v)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val currentContact = contacts[position]
        holder.apply {
            itemView.setOnClickListener { onClickListener(currentContact) }
            itemView.setOnLongClickListener { onLongClickListener(currentContact) }
            bind(currentContact)
        }
    }

    override fun getItemCount(): Int = contacts.size

    class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var phoneNumber: TextView
        private var firstName: TextView
        private var lastName: TextView
        private var image: ImageView

        init {
            itemView.apply {
                phoneNumber = findViewById(R.id.contact_phoneNumber)
                firstName = findViewById(R.id.contact_firstName)
                lastName = findViewById(R.id.contact_lastName)
                image = findViewById(R.id.contact_image)
            }
        }

        fun bind(contact: Contact) {
            phoneNumber.text = contact.phoneNumber
            firstName.text = contact.firstName
            lastName.text = contact.lastName
            image.setImageDrawable(null)
            Picasso
                .get()
                .load(ContactsProvider.BASE_URL_IMAGES + (contact.id + 70))
                .placeholder(R.drawable.ic_image)
                .error(R.drawable.ic_image)
                .into(image)
        }
    }


}