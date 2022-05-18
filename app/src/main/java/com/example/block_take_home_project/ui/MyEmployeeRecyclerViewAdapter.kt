package com.example.block_take_home_project.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.block_take_home_project.R
import com.example.block_take_home_project.databinding.FragmentEmployeeBinding
import com.example.block_take_home_project.model.Employee

class MyEmployeeRecyclerViewAdapter(
    private val employees: List<Employee>
) : RecyclerView.Adapter<MyEmployeeRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentEmployeeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = employees[position]

        // Technical requirement: Glide automatically caches to disk by default
        Glide
            .with(holder.itemView.context)
            .load(item.photo_url_small)
            .centerCrop()
            .placeholder(holder.itemView.context.getDrawable(R.drawable.ic_baseline_person_24))
            .into(holder.photoView)

        holder.fullNameView.text = item.full_name
        holder.biographyView.text = item.biography
        holder.teamView.text = item.team
    }

    override fun getItemCount(): Int = employees.size

    inner class ViewHolder(binding: FragmentEmployeeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val photoView: ImageView = binding.employeePhotoUrlSmall
        val fullNameView: TextView = binding.employeeFullName
        val biographyView: TextView = binding.employeeBiography
        val teamView: TextView = binding.employeeTeam
    }

}