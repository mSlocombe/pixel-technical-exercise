package io.github.mslocombe.pixeltechnicalexercise.storage

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.test.core.app.ApplicationProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
val datastoreScope = UnconfinedTestDispatcher()

private val Context.testDatastore: DataStore<Preferences> by preferencesDataStore(
    name = "FollowDatastoreTestDatastore",
    scope = CoroutineScope(datastoreScope)
)

class FollowDatastoreTest {

    @After
    fun cleanup() {
        runBlocking {
            ApplicationProvider.getApplicationContext<Context>().testDatastore.updateData {
                it.toMutablePreferences().also {
                    it.clear()
                }
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun saveFollowerAddsToDatastore() = runTest {
        val theDatastore = FollowDatastoreImpl(
            ApplicationProvider.getApplicationContext<Context>().testDatastore,
        )

        theDatastore.saveFollow(1)
        val followedUsers = theDatastore.getFollows().first()

        assert(followedUsers == setOf("1"))
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun removeFollowRemovesFromDatastore() = runTest {
        val theDatastore = FollowDatastoreImpl(
            ApplicationProvider.getApplicationContext<Context>().testDatastore,
        )
        theDatastore.saveFollow(1)
        theDatastore.saveFollow(2)
        theDatastore.saveFollow(3)
        assert(theDatastore.getFollows().first() == setOf("1", "2", "3"))

        theDatastore.removeFollow(2)
        val followedUsers = theDatastore.getFollows().first()

        assert(followedUsers == setOf("1", "3"))
    }
}