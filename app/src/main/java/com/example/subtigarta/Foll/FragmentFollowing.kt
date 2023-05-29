package com.example.subtigarta.Foll

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.subtigarta.DataClass.User
import com.example.subtigarta.R
import com.example.subtigarta.adapter.MainAdapter
import com.example.subtigarta.databinding.FragmentFollBinding
import com.example.subtigarta.detail.DetailActivity

class FragmentFollowing : Fragment(R.layout.fragment_foll){
    private var _binding : FragmentFollBinding? =null
    private val binding get() = _binding!!
    private lateinit var viewModel: FollViewModel
    private lateinit var adapter: MainAdapter
    private lateinit var username: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding= FragmentFollBinding.bind(view)

        username = arguments?.getString(DetailActivity.DATA).toString()

        adapter = MainAdapter()
        adapter.notifyDataSetChanged()

        Log.e("kenapa", username)

        binding?.apply {
            rvSatu.layoutManager = LinearLayoutManager(requireContext())
            rvSatu.adapter = adapter
            rvSatu.setHasFixedSize(true)
        }

        showLoading(true)
        viewModel = ViewModelProvider(this).get(FollViewModel::class.java)

        viewModel.setFollowingUser(username)
        viewModel.getFollowingUser().observe(viewLifecycleOwner) {
            if (it != null) {
                showLoading(false)
                Log.e("rusak", "$it")
                adapter.setdata(it as ArrayList<User>)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.loading.visibility = View.VISIBLE
        } else {
            binding.loading.visibility = View.GONE
        }
    }

}