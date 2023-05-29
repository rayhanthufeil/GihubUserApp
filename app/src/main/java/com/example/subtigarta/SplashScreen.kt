package com.example.subtigarta

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.example.subtigarta.theme.SettingThemes
import com.example.subtigarta.theme.ViewModelFactory
import com.example.subtigarta.theme.ViewModelTheme

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)



        val imageView= findViewById<ImageView>(R.id.cover)

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


        imageView.alpha= 0F
        imageView.animate().setDuration(1500).alpha(1f).withEndAction{
            val awal = Intent(this,MainActivity::class.java)
            startActivity(awal)
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
            finish()
        }
    }
}