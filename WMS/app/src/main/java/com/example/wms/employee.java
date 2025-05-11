package com.example.wms;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;



public class employee extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.employee);
        CardView cardschedule=findViewById(R.id.card_workschedule);
        CardView carddutyhours=findViewById(R.id.card_dutyhours);
        CardView cardworkhistory=findViewById(R.id.card_workhistory);
        CardView cardassignedwork=findViewById(R.id.card_assignedwork);
        CardView cardapplyleave=findViewById(R.id.card_applyleave);
        CardView cardtransfer=findViewById(R.id.card_transfer);

        cardschedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent scheduleintent=new Intent(employee.this, workschedule.class);
                startActivity(scheduleintent);
            }
        });

        carddutyhours.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent dutyintent=new Intent(employee.this, workschedule.class);
                startActivity(dutyintent);
            }
        });

        cardworkhistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent historyintent=new Intent(employee.this, workhistory.class);
                startActivity(historyintent);
            }
        });
        cardassignedwork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent assignedintent=new Intent(employee.this, assignedwork.class);
                startActivity(assignedintent);
            }
        });

        cardapplyleave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent leaveintent=new Intent(employee.this, leave.class);
                startActivity(leaveintent);
            }
        });


        cardtransfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent transferintent=new Intent(employee.this, transfer.class);
                startActivity(transferintent);
            }
        });








    }
}