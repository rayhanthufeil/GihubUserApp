package com.example.subtigarta.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.subtigarta.Foll.SectionPagerAdapter
import com.example.subtigarta.R
import com.example.subtigarta.databinding.ActivityDetailBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailActivity : AppCompatActivity() {

    companion object {
        const val DATA = "data"
        const val ID = "id_extra"
        const val Aang = "avatar_extra"

        @StringRes
        private val TAB_TITLES = intArrayOf(R.string.tab_text_1, R.string.tab_text_2)
    }

    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(DATA)
        val id = intent.getIntExtra(ID,0)
        val foto = intent.getStringExtra(Aang)
        Log.e("gatau", username.toString(),)

        showLoading(state = true)
        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)

        username?.let { viewModel.setDetailUser(it) }
        showTabLayout()
        viewModel.getUserDetail().observe(this) {
            if (it != null) {
                showLoading(state = false)
                binding.detailUsername.text = it.login
                binding.detailNama.text = it.name
                binding.detailCompany.text = it.company
                binding.detailLocation.text = it.location
                binding.label1.text = "Repository ${it.public_repos} "
                binding.label2.text = "Followers ${it.followers} "
                binding.label3.text = "Following ${it.following} "
                Glide.with(this)
                    .load(it.avatar_url)
                    .centerCrop()
                    .into(binding.detailFoto)
            }

        }

        var _isChecked = false
        CoroutineScope(Dispatchers.IO).launch {
            val cek = viewModel.cekfavorite(id)
            withContext(Dispatchers.Main) {
                if (cek != null) {
                    if (cek > 0) {
                        binding.toggleButton.isChecked = true
                        _isChecked = true
                    } else {
                        binding.toggleButton.isChecked = false
                        _isChecked = false
                    }
                }
            }
        }

        binding.toggleButton.setOnClickListener {
            _isChecked = !_isChecked
            if (_isChecked) {
                viewModel.addfavorite(username.toString(), id, foto.toString())
                Log.e("gatau", username.toString())
                Log.e("gatau", id.toString())
                Log.e("gatau", foto.toString(),)
            } else {
                viewModel.deletefavorite(id)
            }
            binding.toggleButton.isChecked = _isChecked
        }


    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.loading.visibility = View.VISIBLE
        } else {
            binding.loading.visibility = View.GONE
        }
    }

    private fun showTabLayout() {
        val username = intent.getStringExtra(DATA)
        val bundle = Bundle()
        bundle.putString(DATA, username)
        val sectionsPagerAdapter = SectionPagerAdapter(this, bundle)
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tb
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }
}

