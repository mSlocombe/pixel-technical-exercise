package io.github.mslocombe.pixeltechnicalexercise.storage

import androidx.test.core.app.ApplicationProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

class FollowDatastoreTest {

    @Before
    @After
    fun datastoreCleanup() = runTest {
        val theDatastore = FollowDatastoreImpl(
            ApplicationProvider.getApplicationContext(),
            backgroundScope
        )

        theDatastore.emptyDatastore()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun saveFollowerAddsToDatastore() = runTest(UnconfinedTestDispatcher()) {
        val theDatastore = FollowDatastoreImpl(
            ApplicationProvider.getApplicationContext(),
            backgroundScope
        )

        theDatastore.saveFollow(1)
        val followedUsers = theDatastore.getFollows().first()

        assert(followedUsers == setOf("1"))
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun removeFollowRemovesFromDatastore() = runTest(UnconfinedTestDispatcher()) {
        val theDatastore = FollowDatastoreImpl(
            ApplicationProvider.getApplicationContext(),
            backgroundScope
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