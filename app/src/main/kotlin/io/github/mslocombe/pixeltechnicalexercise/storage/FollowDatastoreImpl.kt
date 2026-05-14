package io.github.mslocombe.pixeltechnicalexercise.storage

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.collections.toMutableSet

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = "follow_datastore"
)

class FollowDatastoreImpl(private val localDatastore: DataStore<Preferences>) :
    FollowDatastore {
    companion object {
        const val KEY = "followed_users"
    }


    val followKey = stringSetPreferencesKey(KEY)

    override suspend fun saveFollow(userId: Int) {
        localDatastore.updateData {
            it.toMutablePreferences().also { preferences ->
                val currentFollows = preferences[followKey]
                val updatedFollows =
                    currentFollows?.toMutableSet() ?: mutableSetOf()
                updatedFollows.add(userId.toString())

                preferences[followKey] = updatedFollows
            }
        }
    }

    override fun getFollows(): Flow<Set<String>> {
        return localDatastore.data.map { preferences ->
            preferences[followKey] ?: emptySet()
        }
    }

    override suspend fun removeFollow(userId: Int) {
        localDatastore.updateData {
            it.toMutablePreferences().also { preferences ->
                val currentFollows = preferences[followKey]
                val updatedFollows =
                    currentFollows?.toMutableSet() ?: mutableSetOf()
                updatedFollows.remove(userId.toString())

                preferences[followKey] = updatedFollows
            }
        }
    }
}