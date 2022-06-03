package com.example.task5

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class ContactsProvider : Parcelable {
    companion object {
        val contact1: Contact = Contact(0, "firstName1", "lastName1", "111")
        val contact2: Contact = Contact(1, "firstName2", "lastName2", "222")
        val contact3: Contact = Contact(2, "firstName3", "lastName3", "333")
        val contact4: Contact = Contact(3, "firstName4", "lastName4", "444")
        val contactsList = listOf(
            contact1,
            contact2,
            contact3,
            contact4,
        )
    }
}