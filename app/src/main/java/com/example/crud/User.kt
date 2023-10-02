package com.example.crud

import com.google.firebase.firestore.DocumentId

data class User(
    @DocumentId val id: String ="",
    val name: String = "",
    val course: String = "",
    val email: String =""
)
