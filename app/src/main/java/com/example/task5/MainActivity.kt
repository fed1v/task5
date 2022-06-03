package com.example.task5

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val contactsProvider = ContactsProvider()
        val bundle = Bundle()
        bundle.putParcelable("contactsProvider", contactsProvider)
        val contactsListFragment = ContactsListFragment()
        contactsListFragment.arguments = bundle

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, contactsListFragment)
                .commit()
        }
    }


    fun replaceFragments(fragment: Fragment, tag: String) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment, tag)
            .addToBackStack(tag)
            .commit()
    }

    override fun onBackPressed() {
        val count = supportFragmentManager.backStackEntryCount
        if (count == 0) {
            super.onBackPressed()
        } else {
            supportFragmentManager.popBackStack()
        }
    }
}