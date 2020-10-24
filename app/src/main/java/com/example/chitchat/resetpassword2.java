package com.example.chitchat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class resetpassword2 extends AppCompatActivity implements View.OnClickListener {
    Button okButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resetpassword2);

        try {
            this.getSupportActionBar().hide();
        }catch (NullPointerException e){}

        okButton = (Button) findViewById(R.id.okID);

        okButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.okID){
            finish();
            startActivity(new Intent(resetpassword2.this, Login.class));
        }
    }
}