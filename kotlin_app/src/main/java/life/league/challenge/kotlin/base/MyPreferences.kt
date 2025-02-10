package life.league.challenge.kotlin.base

import android.content.Context
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.preferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

const val PREFERENCES_NAME = "my_preferences"


class MyPreferences(context: Context) {
    // Create the dataStore and give it a name same as shared preferences
    private val dataStore = context.createDataStore(name = PREFERENCES_NAME)

    // Create some keys we will use them to store and retrieve the data
    companion object {
        val API_KEY = preferencesKey<String>("API_KEY")
    }

    // Store user data
    // refer to the data store and using edit
    // we can store values using the keys
    suspend fun storeApiKey(apiKey: String) {
        dataStore.edit {
            it[API_KEY] = apiKey
            // here it refers to the preferences we are editing
        }
    }

    // Create an age flow to retrieve age from the preferences
    // flow comes from the kotlin coroutine

    // Create a name flow to retrieve name from the preferences
    val apiKeyFlow: Flow<String> = dataStore.data.map {
        it[API_KEY] ?: ""
    }
}


