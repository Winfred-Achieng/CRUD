package com.example.crud

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.crud.databinding.ActivityCreateRecordBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.app
import org.checkerframework.checker.units.qual.Length

class CreateRecordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateRecordBinding
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateRecordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = Firebase.firestore

        binding.btnMain.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }

        binding.saveBtn.setOnClickListener {
            val name = binding.etName.text.toString()
            val email = binding.etEmail.text.toString()
            val course = binding.etCourse.text.toString()

            val user = hashMapOf(
                "name" to name,
                "email" to email,
                "course" to course )
            db.collection("users")
                .add(user)
                .addOnSuccessListener { documentReference ->
                    val userId =documentReference.id
                    binding.etName.text.clear()
                    binding.etEmail.text.clear()
                    binding.etCourse.text.clear()

                    Toast.makeText(this,"New record added",Toast.LENGTH_SHORT).show()
                    val intent = Intent(this,MainActivity::class.java)
                    startActivity(intent)

                }
                .addOnFailureListener{e->
                    Log.w(TAG, "Error adding document", e)
                    Toast.makeText(this,"Failed to add a new record",Toast.LENGTH_SHORT).show()

                }
        }
    }
}