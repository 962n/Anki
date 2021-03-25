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

    enum class NavigationItem(val id: Int) {
        HOME(R.id.navigation_home) {
            override fun getFragment(): Fragment {
                return AnkiSwipeFragment()
            }
        },
        NOTIFICATION(R.id.navigation_dashboard){
            override fun getFragment(): Fragment {
                return WordListFragment()
            }
        },
        MY_PAGE(R.id.navigation_notifications){
            override fun getFragment(): Fragment {
                return SettingsFragment()
            }
        };
        abstract fun getFragment(): Fragment

        companion object {
            fun of(id: Int): NavigationItem? {
                return values().firstOrNull { it.id == id }
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
        switchFragment(NavigationItem.HOME)

    }

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->

        val navigationItem = NavigationItem.of(item.itemId)
        if (navigationItem != null){
            switchFragment(navigationItem)
            return@OnNavigationItemSelectedListener true
        }
        false
    }

    private fun switchFragment(item: NavigationItem) {

        val transaction = childFragmentManager.beginTransaction()
        childFragmentManager.primaryNavigationFragment?.apply {
            transaction.detach(this)
        }
        var fragment = childFragmentManager.findFragmentByTag(item.id.toString())
        if (fragment == null) {
            fragment = item.getFragment()
            transaction.add(R.id.container, fragment,item.id.toString())
        } else {
            transaction.attach(fragment)
        }
        transaction.setPrimaryNavigationFragment(fragment)
        transaction.setReorderingAllowed(true)
        transaction.commit()
    }

}