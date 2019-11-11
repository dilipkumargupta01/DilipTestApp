package com.example.diliptestapp.data.repositories


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.diliptestapp.data.db.AppDatabase
import com.example.diliptestapp.data.db.entities.User
import com.example.diliptestapp.data.network.MyUserApi
import com.example.diliptestapp.data.network.SafeApiRequest
import com.example.diliptestapp.data.preferences.PreferenceProvider
import com.example.diliptestapp.util.Coroutines
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit


private val MINIMUM_INTERVAL = 6000

class UserRepository(
    private val api: MyUserApi,
    private val db: AppDatabase,
    private val prefs: PreferenceProvider
) : SafeApiRequest() {

    private val users = MutableLiveData<List<User>>()


    init {
        users.observeForever {
            if (it != null) {
                saveUsers(it)

            }
        }


    }

    suspend fun getUsers(): LiveData<List<User>> {

        return withContext(Dispatchers.IO) {
            fetchUsers()
            db.getUserDao().getUser()
        }
    }

    private suspend fun fetchUsers() {
        val lastSavedAt = prefs.getLastSavedAt()

        if (lastSavedAt == null || isFetchNeeded(LocalDateTime.parse(lastSavedAt))) {
            try {
                val response = apiRequest { api.getUserResponse() }
                users.postValue(response)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


    private fun isFetchNeeded(savedAt: LocalDateTime): Boolean {
        return ChronoUnit.SECONDS.between(savedAt, LocalDateTime.now()) > MINIMUM_INTERVAL
    }


    private fun saveUsers(records: List<User?>) {
        Coroutines.io {
            prefs.savelastSavedAt(LocalDateTime.now().toString())
            db.getUserDao().saveAllUsers(records)
        }
    }

    fun saveUser(record: User?) {
        Coroutines.io {
            prefs.savelastSavedAt(LocalDateTime.now().toString())
            db.getUserDao().saveUser(record)
        }
    }


    fun deleteUser(id: Int?) {
        Coroutines.io {
            //prefs.savelastSavedAt(LocalDateTime.now().toString())
            db.getUserDao().deleteUser(id)
        }
    }
}