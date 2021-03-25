package com.example.a962n.anki.navigatorImpl

import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.a962n.anki.R
import com.example.a962n.presentation.wordlist.WordListNavigator
import javax.inject.Inject

class WordListNavigatorImpl
@Inject
constructor(
    private val fragment: Fragment
) : WordListNavigator {
    override fun toWordEdit() {
        fragment.findNavController().navigate(
            R.id.navigation_word_edit)
        fragment.context?.apply {
            Toast.makeText(this, "hogehoge", Toast.LENGTH_LONG).show()
        }
    }
}