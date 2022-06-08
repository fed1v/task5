package com.example.task5

import com.github.javafaker.Faker
import java.util.*

class ContactsProvider {
    companion object {
        var contactsList = generateContacts()

        private fun generateContacts(): MutableList<Contact> {
            val contacts = mutableListOf<Contact>()
            val faker = Faker(Locale("ru"))
            (1..111).forEach {
                contacts.add(
                    Contact(
                        it,
                        faker.name().firstName(),
                        faker.name().lastName(),
                        faker.phoneNumber().phoneNumber()
                    )
                )
            }
            return contacts
        }

        const val BASE_URL_IMAGES = "https://picsum.photos/"
    }
}