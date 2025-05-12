package com.example.wms.screens.admin.meterrequests.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.wms.databinding.ItemMeterRequestBinding
import com.example.wms.screens.admin.meterrequests.domain.model.MeterApplication

class MeterRequestsAdapter(
    private val onApplicationClick: (MeterApplication) -> Unit
) : ListAdapter<MeterApplication, MeterRequestsAdapter.MeterRequestViewHolder>(MeterRequestDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MeterRequestViewHolder {
        val binding = ItemMeterRequestBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MeterRequestViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MeterRequestViewHolder, position: Int) {
        val application = getItem(position)
        holder.bind(application)
    }

    inner class MeterRequestViewHolder(private val binding: ItemMeterRequestBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(application: MeterApplication) {
            binding.apply {
                root.setOnClickListener {
                    onApplicationClick(application)
                }
                meterRequestName.text = application.name
                meterRequestCnic.text = application.cnic
                meterRequestPhoneNumber.text = application.phoneNumber
                meterRequestAddress.text = application.address
            }
        }
    }

    class MeterRequestDiffCallback : DiffUtil.ItemCallback<MeterApplication>() {
        override fun areItemsTheSame(oldItem: MeterApplication, newItem: MeterApplication): Boolean {
            return oldItem.deviceId == newItem.deviceId
        }

        override fun areContentsTheSame(oldItem: MeterApplication, newItem: MeterApplication): Boolean {
            return oldItem == newItem
        }
    }
}
