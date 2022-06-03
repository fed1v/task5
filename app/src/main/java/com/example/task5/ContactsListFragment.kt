package com.example.task5

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment

class ContactsListFragment : Fragment() {

    private lateinit var item_contact1: ConstraintLayout
    private lateinit var item_contact2: ConstraintLayout
    private lateinit var item_contact3: ConstraintLayout
    private lateinit var item_contact4: ConstraintLayout

    private lateinit var contactsList: List<Contact>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_contacts_list, container, false)

        contactsList = ContactsProvider.contactsList
        initView(v)

        showContacts(contactsList)

        return v
    }

    private fun initView(v: View) {
        item_contact1 = v.findViewById(R.id.item_contact1)
        item_contact2 = v.findViewById(R.id.item_contact2)
        item_contact3 = v.findViewById(R.id.item_contact3)
        item_contact4 = v.findViewById(R.id.item_contact4)

        item_contact1.setOnClickListener {
            openContactFragment(contactsList[0])
        }

        item_contact2.setOnClickListener {
            openContactFragment(contactsList[1])
        }

        item_contact3.setOnClickListener {
            openContactFragment(contactsList[2])
        }

        item_contact4.setOnClickListener {
            openContactFragment(contactsList[3])
        }
    }

    private fun openContactFragment(contact: Contact) {
        val bundle = Bundle()
        bundle.putParcelable("Contact", contact)
        val contactFragment = ContactFragment()
        contactFragment.arguments = bundle

        (activity as MainActivity)
            .replaceFragments(contactFragment, "ContactFragment")
    }

    private fun showContacts(contacts: List<Contact>) {
        val contact1_phoneNumber: TextView = item_contact1.findViewById(R.id.contact1_phoneNumber)
        val contact1_firstName: TextView = item_contact1.findViewById(R.id.contact1_firstName)
        val contact1_lastName: TextView = item_contact1.findViewById(R.id.contact1_lastName)

        assignContactToItem(
            contacts[0],
            contact1_phoneNumber,
            contact1_firstName,
            contact1_lastName
        )

        val contact2_phoneNumber: TextView = item_contact2.findViewById(R.id.contact2_phoneNumber)
        val contact2_firstName: TextView = item_contact2.findViewById(R.id.contact2_firstName)
        val contact2_lastName: TextView = item_contact2.findViewById(R.id.contact2_lastName)

        assignContactToItem(
            contacts[1],
            contact2_phoneNumber,
            contact2_firstName,
            contact2_lastName
        )

        val contact3_phoneNumber: TextView = item_contact3.findViewById(R.id.contact3_phoneNumber)
        val contact3_firstName: TextView = item_contact3.findViewById(R.id.contact3_firstName)
        val contact3_lastName: TextView = item_contact3.findViewById(R.id.contact3_lastName)

        assignContactToItem(
            contacts[2],
            contact3_phoneNumber,
            contact3_firstName,
            contact3_lastName
        )

        val contact4_phoneNumber: TextView = item_contact4.findViewById(R.id.contact4_phoneNumber)
        val contact4_firstName: TextView = item_contact4.findViewById(R.id.contact4_firstName)
        val contact4_lastName: TextView = item_contact4.findViewById(R.id.contact4_lastName)

        assignContactToItem(
            contacts[3],
            contact4_phoneNumber,
            contact4_firstName,
            contact4_lastName
        )
    }

    private fun assignContactToItem(
        contact: Contact,
        itemPhoneNumber: TextView,
        itemFirstName: TextView,
        itemLastName: TextView
    ) {
        itemPhoneNumber.text = contact.phoneNumber
        itemFirstName.text = contact.firstName
        itemLastName.text = contact.lastName
    }

}