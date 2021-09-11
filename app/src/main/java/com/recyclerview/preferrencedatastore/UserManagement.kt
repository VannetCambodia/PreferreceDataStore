package com.recyclerview.preferrencedatastore

import android.content.Context
import androidx.datastore.DataStore
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.preferencesKey
import kotlinx.coroutines.flow.map
import org.intellij.lang.annotations.Flow

class UserManagement(context: Context) {

    private val dataStore: DataStore<androidx.datastore.preferences.Preferences> = context.createDataStore(name = "user_prefs")

    companion object{
        val USER_NAME = preferencesKey<String>(name = "USER_NAME")
        val EMAIL = preferencesKey<String>(name = "EMAIL")
    }

    suspend fun storeUserInfo(name:String, email:String){
        dataStore.edit {
            it[USER_NAME] = name
            it[EMAIL] = email
        }
    }

    val nameFlow: kotlinx.coroutines.flow.Flow<String> = dataStore.data.map {
        it[USER_NAME] ?: ""
    }

    val emailFlow: kotlinx.coroutines.flow.Flow<String> = dataStore.data.map {
        it[EMAIL] ?: ""
    }

}