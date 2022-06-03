package com.example.task5

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Contact(
    val id: Int,
    var firstName: String,
    var lastName: String,
    var phoneNumber: String
) : Parcelable