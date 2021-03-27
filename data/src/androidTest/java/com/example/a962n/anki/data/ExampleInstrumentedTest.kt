package com.example.a962n.anki.data

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.a962n.anki.data.datastore.room.entity.RWordEntity
import org.junit.Assert

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.a962n.anki.data.test", appContext.packageName)
    }

    @Test
    fun hoge() = roomTesting {
        var exists = it.wordDao().exists("hoge")
        val rWordEntity = RWordEntity(
            name = "hoge",
            meaning = "",
            extra = ""
        )
        assertFalse(exists)
        val ids = it.wordDao().insertAll(rWordEntity)
        ids.forEach {
            println(it.toString())
        }
        exists = it.wordDao().exists("hoge")
        assertTrue(exists)

    }

}