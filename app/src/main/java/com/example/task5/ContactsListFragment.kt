package com.example.task5

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.content.res.ResourcesCompat
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class ContactsListFragment : Fragment() {

    private lateinit var contactsRecyclerView: RecyclerView
    private lateinit var contactAdapter: ContactAdapter
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    private lateinit var contactsList: MutableList<Contact>

    private var searchView: SearchView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)
        val searchItem = menu.findItem(R.id.searchContact)
        searchView = searchItem.actionView as? SearchView

        searchView?.queryHint = HtmlCompat.fromHtml(
            "<font color = #9E9E9E>" + resources.getString(R.string.hintSearchMessage) + "</font>",
            HtmlCompat.FROM_HTML_MODE_LEGACY
        )
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchContacts(query ?: "")
                return true
            }

            override fun onQueryTextChange(text: String?): Boolean {
                if (text.isNullOrBlank()) {
                    showContacts(ContactsProvider.contactsList)
                } else {
                    searchContacts(text)
                }
                return true
            }
        })
    }

    private fun searchContacts(query: String) {
        val splitQuery = query.split(" ", ignoreCase = true, limit = 2)
        val resultContactsList = mutableListOf<Contact>()
        ContactsProvider.contactsList.forEach { contact ->
            if (splitQuery.size == 1) {
                val q = splitQuery[0]
                if (contact.firstName.contains(q, true) || contact.lastName.contains(q, true)) {
                    resultContactsList.add(contact)
                }
            } else if (splitQuery.size == 2) {
                val query1 = splitQuery[0]
                val query2 = splitQuery[1]
                if ((contact.firstName.contains(query1, true) && contact.lastName.contains(
                        query2,
                        true
                    ))
                    ||
                    (contact.firstName.contains(query2, true) && contact.lastName.contains(
                        query1,
                        true
                    ))
                ) {
                    resultContactsList.add(contact)
                }
            }
        }
        showContacts(resultContactsList)
    }

    private fun initView(v: View) {
        toolbar = v.findViewById(R.id.toolbar_contacts)
        (requireActivity() as MainActivity).setSupportActionBar(toolbar)

        val contactItemDecoration = ContactItemDecoration(
            dividerDrawable = ResourcesCompat.getDrawable(
                resources,
                R.drawable.divider,
                null
            )!!,
            spacing = 1
        )

        contactsRecyclerView = v.findViewById(R.id.recyclerview_contacts)
        contactsRecyclerView.addItemDecoration(contactItemDecoration)
        contactsRecyclerView.layoutManager = LinearLayoutManager(activity)
        contactAdapter = ContactAdapter(
            onClickListener = ::onClickListener,
            onLongClickListener = ::onLongClickListener
        )
        contactsRecyclerView.adapter = contactAdapter
    }

    private fun showContacts(contacts: List<Contact>) {
        val contactsToShow = ArrayList(contacts)
        contactAdapter.contacts = contactsToShow
    }

    private fun onClickListener(contact: Contact) {
        openContactFragment(contact)
    }

    private fun onLongClickListener(contact: Contact): Boolean {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Are you sure you want to delete this contact?")
            .setPositiveButton("Delete") { dialog, _ ->
                deleteContact(contact)
                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .show()

        return true
    }

    private fun deleteContact(contact: Contact) {
        Toast.makeText(requireContext(), "Contact deleted", Toast.LENGTH_SHORT).show()
        ContactsProvider.contactsList = ArrayList(ContactsProvider.contactsList)
        ContactsProvider.contactsList.remove(contact)
        contactAdapter.contacts = ContactsProvider.contactsList

        val searchViewQuery = searchView?.query.toString()
        if (searchViewQuery.isNotBlank()) {
            searchContacts(searchViewQuery)
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
}