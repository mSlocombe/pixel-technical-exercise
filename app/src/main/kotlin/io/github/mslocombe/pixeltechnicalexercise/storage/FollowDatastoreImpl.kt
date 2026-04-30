package io.github.mslocombe.pixeltechnicalexercise.storage

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.jetbrains.annotations.TestOnly
import kotlin.collections.toMutableSet

class FollowDatastoreImpl(private val context: Context, private val scope: CoroutineScope = CoroutineScope(Dispatchers.IO + SupervisorJob())) :
    FollowDatastore {
    companion object {
        const val KEY = "followed_users"
    }

    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
        name = "follow_datastore",
        scope = scope
    )

    val followKey = stringSetPreferencesKey(KEY)

    override suspend fun saveFollow(userId: Int) {
        context.dataStore.updateData {
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
        return context.dataStore.data.map { preferences ->
            preferences[followKey] ?: emptySet()
        }
    }

    override suspend fun removeFollow(userId: Int) {
        context.dataStore.updateData {
            it.toMutablePreferences().also { preferences ->
                val currentFollows = preferences[followKey]
                val updatedFollows =
                    currentFollows?.toMutableSet() ?: mutableSetOf()
                updatedFollows.remove(userId.toString())

                preferences[followKey] = updatedFollows
            }
        }
    }

    @TestOnly
    suspend fun emptyDatastore() {
        context.dataStore.updateData {
            it.toMutablePreferences().also { preferences ->
                preferences[followKey] = emptySet()
            }
        }
    }
}