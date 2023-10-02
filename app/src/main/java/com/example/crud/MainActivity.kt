package com.example.crud

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.crud.databinding.ActivityMainBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val userList = mutableListOf<User>()
    private lateinit var db: FirebaseFirestore
    private lateinit var userRecyclerViewContainer: LinearLayout
    private lateinit var userAdapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userRecyclerViewContainer = binding.recyclerViewContainer // Use the correct ID here
        userAdapter = UserAdapter(userList)

        db = Firebase.firestore
        db.collection("users")
            .get()
            .addOnSuccessListener { querySnapshot ->
                userList.clear()
                for (document in querySnapshot) {
                    val user = document.toObject(User::class.java)
                    Log.d(TAG, "Retrieved user with ID: ${user.id}")

                    // Handle the case where the id is null


                    userList.add(user)
                    addUserRecyclerView(user)
                }
                userAdapter.notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
                Log.e(TAG, "Error getting documents: $exception")
                Toast.makeText(this, "Failed to get documents", Toast.LENGTH_SHORT).show()
            }
    }




    private fun addUserRecyclerView(user: User) {
        val recyclerView = RecyclerView(this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = UserAdapter(mutableListOf(user))

        // Add item decoration
        recyclerView.addItemDecoration(ItemDecoration(this, resources.getDimensionPixelSize(R.dimen.item_space)))

        // Set rounded background and shadow for the RecyclerView
        recyclerView.setBackgroundResource(R.drawable.rounded_background)

        userRecyclerViewContainer.addView(recyclerView)
    }



}
