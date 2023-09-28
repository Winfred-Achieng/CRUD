package com.example.crud

import android.content.ContentValues.TAG
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class UserAdapter(private val userList: List<User>): RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
        val nameTextView: TextView = itemView.findViewById(R.id.tvName)
        val courseTextView: TextView = itemView.findViewById(R.id.tvCourse)
        val emailTextView: TextView = itemView.findViewById(R.id.tvEmail)
        val deleteButton: Button = itemView.findViewById(R.id.deleteBtn)
        val editButton: Button = itemView.findViewById(R.id.editBtn)

        init {
            deleteButton.setOnClickListener {
                val user = userList[adapterPosition]
                val userId = user.id

                val db =Firebase.firestore
                db.collection("users").document(userId)
                    .delete()
                    .addOnSuccessListener {
                        // Delete successful, update the UI or reload data
                        //userList.removeAt(adapterPosition)
                        notifyItemRemoved(adapterPosition)
                    }
                    .addOnFailureListener { e ->
                        Log.e(TAG, "Error deleting document", e)
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