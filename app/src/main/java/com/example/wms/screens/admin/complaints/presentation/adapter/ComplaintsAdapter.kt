package com.example.wms.screens.admin.complaints.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.wms.databinding.ItemComplaintBinding
import com.example.wms.screens.admin.complaints.domain.model.UserComplaint

class ComplaintsAdapter(
    private val onComplaintClick: (UserComplaint) -> Unit
) : ListAdapter<UserComplaint, ComplaintsAdapter.ComplaintViewHolder>(ComplaintDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComplaintViewHolder {
        val binding =
            ItemComplaintBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ComplaintViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ComplaintViewHolder, position: Int) {
        val complaint = getItem(position)
        holder.bind(complaint)
    }

    inner class ComplaintViewHolder(private val binding: ItemComplaintBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(complaint: UserComplaint) {
            binding.apply {
                cv.setOnClickListener {
                    onComplaintClick(complaint)
                }
                complaintName.text = complaint.name
                complaintConsumerNumber.text = complaint.consumerNumber
                complaintPhoneNumber.text = complaint.phoneNumber
                complaintDetails.text = complaint.complaint
            }
        }
    }

    class ComplaintDiffCallback : DiffUtil.ItemCallback<UserComplaint>() {
        override fun areItemsTheSame(oldItem: UserComplaint, newItem: UserComplaint): Boolean {
            return oldItem.deviceId == newItem.deviceId
        }

        override fun areContentsTheSame(oldItem: UserComplaint, newItem: UserComplaint): Boolean {
            return oldItem == newItem
        }
    }
}
