package com.example.subtigarta.fav

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.subtigarta.DataClass.User
import com.example.subtigarta.adapter.MainAdapter
import com.example.subtigarta.databinding.ActivityFavBinding
import com.example.subtigarta.detail.DetailActivity
import com.example.subtigarta.room.Favorite

class FavActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavBinding
    private lateinit var adapter: MainAdapter
    private lateinit var viewModel: ViewModelFav

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = MainAdapter()
        adapter.notifyDataSetChanged()

        viewModel = ViewModelProvider(this).get(ViewModelFav::class.java)

        adapter.setOnItemClickCallback(object : MainAdapter.OnItemClickCallBack {
            override fun onItemClicked(data: User) {
                Intent(this@FavActivity, DetailActivity::class.java).also {
                    it.putExtra(DetailActivity.DATA, data.login)
                    it.putExtra(DetailActivity.ID, data.id)
                    it.putExtra(DetailActivity.Aang, data.avatar_url)
                    startActivity(it)
                }
            }

        })

        binding.apply {
            rvSatu.setHasFixedSize(true)
            rvSatu.layoutManager = LinearLayoutManager(this@FavActivity)
            rvSatu.adapter = adapter
        }

        viewModel.getFavorite()?.observe(this) {
            if (it != null) {
                val list = listmap(it)
                adapter.setdata(list)
            }
        }
    }

    private fun listmap(users: List<Favorite>): ArrayList<User> {
        val listUsers = ArrayList<User>()
        for (user in users ) {
            val mappedUser = User(
                user.login,
                user.id,
                user.avatar_url
            )
            listUsers.add(mappedUser)
        }
        return listUsers
    }
}
