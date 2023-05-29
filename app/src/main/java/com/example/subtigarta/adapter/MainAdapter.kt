package com.example.subtigarta.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.subtigarta.DataClass.User
import com.example.subtigarta.databinding.IsianItemBinding

class MainAdapter: RecyclerView.Adapter<MainAdapter.ViewHolder>() {
    private val data = ArrayList<User>()

    private var onItemClickCallBack: OnItemClickCallBack?= null

    fun setOnItemClickCallback(onItemClickCallBack: OnItemClickCallBack){
        this.onItemClickCallBack = onItemClickCallBack
    }

    fun setdata(users: ArrayList<User>){
        data.clear()
        data.addAll(users)
        notifyDataSetChanged()


    }

    inner class ViewHolder(val binding: IsianItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(user: User){
            binding.root.setOnClickListener {
                onItemClickCallBack?.onItemClicked(user)
            }
            binding.apply {
                Glide.with(itemView)
                    .load(user.avatar_url)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .centerCrop()
                    .into(image)
                username.text = user.login
                nama
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = IsianItemBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return ViewHolder((view))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount() : Int = data.size

    interface OnItemClickCallBack{
        fun onItemClicked(data: User)

    }

}