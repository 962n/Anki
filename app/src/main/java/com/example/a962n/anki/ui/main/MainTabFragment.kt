package com.example.a962n.anki.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.a962n.anki.R
import com.example.a962n.anki.databinding.FragmentMainTabBinding
import com.example.a962n.presentation.ankiswipe.AnkiSwipeFragment
import com.example.a962n.presentation.settings.SettingsFragment
import com.example.a962n.presentation.wordlist.WordListFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainTabFragment : Fragment() {

    private lateinit var binding: FragmentMainTabBinding
    private var selectedNavItem: NavigationItem = NavigationItem.ANKI_SWIPE

    enum class NavigationItem(val navId: Int, val titleId: Int) {
        ANKI_SWIPE(R.id.navigation_home, R.string.title_anki_swipe) {
            override fun getFragment(): Fragment {
                return AnkiSwipeFragment()
            }
        },
        WORD_LIST(R.id.navigation_dashboard, R.string.title_word_list) {
            override fun getFragment(): Fragment {
                return WordListFragment()
            }
        },
        SETTINGS(R.id.navigation_notifications, R.string.title_settings) {
            override fun getFragment(): Fragment {
                return SettingsFragment()
            }
        };

        abstract fun getFragment(): Fragment

        companion object {
            fun findByNavId(navId: Int): NavigationItem? {
                return values().firstOrNull { it.navId == navId }
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMainTabBinding.inflate(inflater, container, false)
        initializeView(binding)
        return binding.root
    }

    private fun initializeView(binding: FragmentMainTabBinding) {
        this.binding = binding
        binding.navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        switchFragment(selectedNavItem)

    }

    private val onNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->

            val navigationItem = NavigationItem.findByNavId(item.itemId)
            if (navigationItem != null) {
                switchFragment(navigationItem)
                return@OnNavigationItemSelectedListener true
            }
            false
        }

    private fun switchFragment(item: NavigationItem) {
        selectedNavItem = item

//        binding.layoutToolbar.toolbar.setTitle(item.titleId)

        val transaction = childFragmentManager.beginTransaction()
        childFragmentManager.primaryNavigationFragment?.apply {
            transaction.detach(this)
        }
        var fragment = childFragmentManager.findFragmentByTag(item.navId.toString())
        if (fragment == null) {
            fragment = item.getFragment()
            transaction.add(R.id.container, fragment, item.navId.toString())
        } else {
            transaction.attach(fragment)
        }
        transaction.setPrimaryNavigationFragment(fragment)
        transaction.setReorderingAllowed(true)
        transaction.commit()
    }

}