package com.example.wms.ui.user

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.example.wms.ApplyMeterActivity
import com.example.wms.R
import com.example.wms.complaints
import com.example.wms.databinding.ActivityUserHomeBinding
import com.example.wms.helpline
import com.example.wms.jobvacancies
import com.example.wms.officelocations
import com.example.wms.waterbill
import com.example.wms.watertiming

class UserHomeActivity : AppCompatActivity() {

    lateinit var binding: ActivityUserHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.enableEdgeToEdge()
        binding = ActivityUserHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

       binding.cardWaterbill.setOnClickListener {
           val waterIntent = Intent(
                this@UserHomeActivity,
                waterbill::class.java
            )
            startActivity(waterIntent)
        }

        binding.cardWaterTiming.setOnClickListener { view: View? ->
            val timingIntent = Intent(
                this@UserHomeActivity,
                watertiming::class.java
            )
            startActivity(timingIntent)
        }
        binding.cardComplaints.setOnClickListener { view: View? ->
            val complaintintent = Intent(
                this@UserHomeActivity,
                complaints::class.java
            )
            startActivity(complaintintent)
        }


        binding.cardApplymeter.setOnClickListener {
            val newmeterintent = Intent(
                this@UserHomeActivity,
                ApplyMeterActivity::class.java
            )
            startActivity(newmeterintent)
        }
        binding.cardHelpline.setOnClickListener {
            val helplineintent = Intent(
                this@UserHomeActivity,
                helpline::class.java
            )
            startActivity(helplineintent)
        }
        binding.cardOfficelocations.setOnClickListener {
            val officelocationintent = Intent(
                this@UserHomeActivity,
                officelocations::class.java
            )
            startActivity(officelocationintent)
        }
        binding.cardJobvacancies.setOnClickListener {
            val jobintent = Intent(
                this@UserHomeActivity,
                jobvacancies::class.java
            )
            startActivity(jobintent)
        }
    }
}