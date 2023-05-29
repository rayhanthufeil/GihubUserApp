package com.example.subtigarta

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.subtigarta.DataClass.User
import com.example.subtigarta.adapter.MainAdapter
import com.example.subtigarta.databinding.ActivityMainBinding
import com.example.subtigarta.detail.DetailActivity
import com.example.subtigarta.fav.FavActivity
import com.example.subtigarta.theme.SettingThemes
import com.example.subtigarta.theme.ThemeActivity
import com.example.subtigarta.theme.ViewModelFactory
import com.example.subtigarta.theme.ViewModelTheme
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: ViewModelUser
    private lateinit var adapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = MainAdapter()
        adapter.notifyDataSetChanged()

        val pref = SettingThemes.getInstance(dataStore)
        val mainViewModelTheme = ViewModelProvider(this, ViewModelFactory(pref)).get(
            ViewModelTheme::class.java
        )
        mainViewModelTheme.getThemeSettings().observe(this,
            { isDarkModeActive: Boolean ->
                if (isDarkModeActive) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

                }
            })

        adapter.setOnItemClickCallback(object : MainAdapter.OnItemClickCallBack{
            override fun onItemClicked(data: User) {
                Intent(this@MainActivity, DetailActivity::class.java).also {
                    it.putExtra(DetailActivity.DATA, data.login)
                    it.putExtra(DetailActivity.ID, data.id)
                    it.putExtra(DetailActivity.Aang, data.avatar_url)
                    startActivity(it)
                }
            }

        })
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(ViewModelUser::class.java)

        binding.apply {
            rvSatu.layoutManager = LinearLayoutManager(this@MainActivity)
            rvSatu.setHasFixedSize(true)
            rvSatu.adapter = adapter

            searchView.setOnQueryTextListener(object :
                androidx.appcompat.widget.SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    if (query != null && query.isNotEmpty()) {
                        searchUser()
                    }
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }
            })

            viewModel.getCariUser().observe(this@MainActivity) {
                if (it != null) {
                    showLoading(false)
                    adapter.setdata(it as ArrayList<User>)
                    binding.rvSatu.visibility = View.VISIBLE
                }

            }
        }
    }

    private fun searchUser(){
        binding.apply {
            val query = searchView.query.toString()
            if (query.isEmpty()) return
            binding.rvSatu.visibility = View.GONE
            showLoading(true)
            viewModel.setCariUser(query)

        }
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.loading.visibility = View.VISIBLE
        } else {
            binding.loading.visibility = View.GONE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_bar,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu1 -> {
                Intent(this, FavActivity:: class.java).also{
                    startActivity(it)
                }
            }

            R.id.menu2 -> {
                Intent(this, ThemeActivity:: class.java).also{
                    startActivity(it)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
}