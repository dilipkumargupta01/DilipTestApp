package com.example.diliptestapp.ui

import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.diliptestapp.data.db.entities.User
import com.example.diliptestapp.data.repositories.UserRepository
import com.example.diliptestapp.ui.main.MainActivity

import com.example.diliptestapp.util.lazyDeferred

class RecordViewModel(
    private val repository: UserRepository
) : ViewModel() {

    val records by lazyDeferred {
        repository.getUsers()
    }

    fun addUser(view: View) {
        val rnds = (45..101).random()
        val user = User(
            "https://avatars0.githubusercontent.com/u/1?v=4",
            "",
            "",
            "",
            "",
            "",
            "",
            rnds,
            "Dilip" + rnds,
            "",
            "",
            "",
            "",
            false,
            "",
            "",
            "user" + rnds,
            ""
        )
        repository.saveUser(user)
        Toast.makeText(view.context, "User is added", Toast.LENGTH_LONG).show()

    }

    fun deleteUser(view: View, id: Int) {
        repository.deleteUser(id)
        Toast.makeText(view.context, "User is deleted", Toast.LENGTH_LONG).show()
        view.context.startActivity(Intent(view.context, MainActivity::class.java))
    }


}
