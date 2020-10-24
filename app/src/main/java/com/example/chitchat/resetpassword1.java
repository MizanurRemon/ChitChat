package com.example.chitchat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class resetpassword1 extends AppCompatActivity implements View.OnClickListener {

    EditText getEmail;
    Button submitButton;
    ImageButton backButton;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resetpassword1);

        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }

        getEmail = (EditText) findViewById(R.id.resetemail);
        submitButton = (Button) findViewById(R.id.submitresetID);
        backButton = (ImageButton) findViewById(R.id.backID);

        mAuth = FirebaseAuth.getInstance();

        submitButton.setOnClickListener(this);
        backButton.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        finish();
        startActivity(new Intent(resetpassword1.this, Login.class));
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.submitresetID) {
            String email = getEmail.getText().toString().trim();

            if (!TextUtils.isEmpty(email)) {
                reset_link_sent(email);
            } else {
                Toast toast = Toast.makeText(resetpassword1.this, "Enter Your mail", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }

        }
        else if(v.getId()==R.id.backID){
            finish();
            startActivity(new Intent(resetpassword1.this, Login.class));
        }
    }

    private void reset_link_sent(String email) {
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast toast = Toast.makeText(resetpassword1.this, "A link has been sent to your mail to reset your password", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();

                    startActivity(new Intent(resetpassword1.this, resetpassword2.class));
                } else {
                    Toast toast = Toast.makeText(resetpassword1.this, "Error occured", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            }
        });
    }

}

