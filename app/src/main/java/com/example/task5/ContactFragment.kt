package com.example.task5

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment

class ContactFragment : Fragment() {

    private lateinit var et_phoneNumber: EditText
    private lateinit var et_firstName: EditText
    private lateinit var et_lastName: EditText
    private lateinit var btn_save: Button

    private lateinit var contact: Contact

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_contact, container, false)
        initView(v)

        contact = arguments?.getParcelable("Contact") ?: Contact(0, "?", "?", "?")
        showContact()

        return v
    }

    private fun initView(v: View) {
        et_phoneNumber = v.findViewById(R.id.edittext_phoneNumber)
        et_firstName = v.findViewById(R.id.edittext_firstName)
        et_lastName = v.findViewById(R.id.edittext_lastName)
        btn_save = v.findViewById(R.id.button_save)

        btn_save.setOnClickListener {
            val newContact = Contact(
                id = contact.id,
                phoneNumber = et_phoneNumber.text.toString(),
                firstName = et_firstName.text.toString(),
                lastName = et_lastName.text.toString()
            )

            saveContact(newContact)
            Toast.makeText(activity, "Changes saved", Toast.LENGTH_SHORT).show()
            parentFragmentManager.popBackStack()
        }
    }

    private fun saveContact(newContact: Contact) {
        ContactsProvider.contactsList[newContact.id].apply {
            phoneNumber = newContact.phoneNumber
            firstName = newContact.firstName
            lastName = newContact.lastName
        }
    }

    private fun showContact() {
        et_phoneNumber.setText(contact.phoneNumber)
        et_firstName.setText(contact.firstName)
        et_lastName.setText(contact.lastName)
    }

}