package com.example.crud

import android.content.ContentValues.TAG
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class UserAdapter(private val userList: MutableList<User>): RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
        val nameTextView: TextView = itemView.findViewById(R.id.tvName)
        val courseTextView: TextView = itemView.findViewById(R.id.tvCourse)
        val emailTextView: TextView = itemView.findViewById(R.id.tvEmail)
        val deleteButton: Button = itemView.findViewById(R.id.deleteBtn)
        val editButton: Button = itemView.findViewById(R.id.editBtn)

        init {
            deleteButton.setOnClickListener {
                val user = userList[adapterPosition]
                val userId = user.id ?: ""

                Log.d(TAG, "Deleting user with ID: $userId")

                val db = Firebase.firestore

                if (userId.isNotBlank()) {
                    db.collection("users")
                        .document(userId)
                        .delete()
                        .addOnSuccessListener {
                            userList.removeAt(adapterPosition)
                            notifyItemRemoved(adapterPosition)

                            Log.d(TAG, "User deleted successfully")


                        }
                        .addOnFailureListener { exception ->
                            Log.e(TAG, "Error deleting document", exception)
                        }
                } else {
                    Log.e(TAG, "Invalid user ID")
                }
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.user_item,parent,false)
        return ViewHolder(view)

    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = userList[position]
        holder.nameTextView.text = user.name
        holder.courseTextView.text = user.course
        holder.emailTextView.text= user.email
    }
}