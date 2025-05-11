package com.example.wms.ui.employee

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.example.wms.R
import com.example.wms.assignedwork
import com.example.wms.leave
import com.example.wms.transfer
import com.example.wms.workhistory
import com.example.wms.workschedule

class employee : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.enableEdgeToEdge()
        setContentView(R.layout.employee)
        val cardschedule = findViewById<CardView>(R.id.card_workschedule)
        val carddutyhours = findViewById<CardView>(R.id.card_dutyhours)
        val cardworkhistory = findViewById<CardView>(R.id.card_workhistory)
        val cardassignedwork = findViewById<CardView>(R.id.card_assignedwork)
        val cardapplyleave = findViewById<CardView>(R.id.card_applyleave)
        val cardtransfer = findViewById<CardView>(R.id.card_transfer)

        cardschedule.setOnClickListener {
            val scheduleintent = Intent(
                this@employee,
                workschedule::class.java
            )
            startActivity(scheduleintent)
        }

        carddutyhours.setOnClickListener {
            val dutyintent = Intent(
                this@employee,
                workschedule::class.java
            )
            startActivity(dutyintent)
        }

        cardworkhistory.setOnClickListener {
            val historyintent = Intent(
                this@employee,
                workhistory::class.java
            )
            startActivity(historyintent)
        }
        cardassignedwork.setOnClickListener {
            val assignedintent = Intent(
                this@employee,
                assignedwork::class.java
            )
            startActivity(assignedintent)
        }

        cardapplyleave.setOnClickListener {
            val leaveintent = Intent(
                this@employee,
                leave::class.java
            )
            startActivity(leaveintent)
        }


        cardtransfer.setOnClickListener {
            val transferintent = Intent(
                this@employee,
                transfer::class.java
            )
            startActivity(transferintent)
        }
    }
}