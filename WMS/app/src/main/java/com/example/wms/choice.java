package com.example.wms;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class choice extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.choice);

        Button userbutton=findViewById(R.id.btnUser);
        Button employeebutton=findViewById(R.id.btnEmployee);

        userbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent userintent=new Intent(choice.this, SignUpUser.class);
                startActivity(userintent);
            }
        });
        employeebutton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

                Intent employeeintent=new Intent(choice.this, login.class);
               startActivity(employeeintent);
           }
       });
    }
}