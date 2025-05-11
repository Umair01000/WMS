package com.example.wms;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;


public class homepage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.homepage);
        CardView cardwaterbill=findViewById(R.id.card_waterbill);
        CardView cardcomplaint=findViewById(R.id.card_complaints);
        CardView cardapplynewmeter=findViewById(R.id.card_applymeter);
        CardView cardwatertiming=findViewById(R.id.card_waterTiming);
        CardView cardhelpline=findViewById(R.id.card_helpline);
        CardView cardofficelcation=findViewById(R.id.card_Officelocations);
        CardView cardjobvacancies=findViewById(R.id.card_jobvacancies);



        cardwaterbill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent waterintent=new Intent(homepage.this, waterbill.class);
                startActivity(waterintent);
            }
        });

cardwatertiming.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

        Intent timingtintent=new Intent(homepage.this,watertiming.class);
        startActivity(timingtintent);

    }
});
        cardcomplaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent complaintintent=new Intent(homepage.this,complaints.class);
                startActivity(complaintintent);
            }
        });




        cardapplynewmeter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent newmeterintent=new Intent(homepage.this,applynewmeter.class);
                startActivity(newmeterintent);
            }
        });
        cardhelpline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent helplineintent=new Intent(homepage.this,helpline.class);
                startActivity(helplineintent);
            }
        });
        cardofficelcation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent officelocationintent=new Intent(homepage.this, officelocations.class);
                startActivity(officelocationintent);
            }
        });
        cardjobvacancies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent jobintent=new Intent(homepage.this, jobvacancies.class);
                startActivity(jobintent);
            }
        });

    }
}