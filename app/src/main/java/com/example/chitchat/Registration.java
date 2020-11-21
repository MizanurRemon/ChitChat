package com.example.chitchat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Registration extends AppCompatActivity {
    //AutoCompleteTextView gender;
    EditText first_name, last_name, emailtext, passwordtext, rt_pass_text, contact;
    Button submitButton;
    ImageButton backButton;
    FirebaseAuth mAuth;
    DatabaseReference registerData, newDatarefer;
    ProgressDialog progressDialog;

    String userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }

        submitButton = (Button) findViewById(R.id.submitbuttonID);
        first_name = (EditText) findViewById(R.id.fnameID);
        last_name = (EditText) findViewById(R.id.lnameID);
        emailtext = (EditText) findViewById(R.id.register_emailID);
        passwordtext = (EditText) findViewById(R.id.register_pass_ID);
        rt_pass_text = (EditText) findViewById(R.id.register_repass_ID);
        contact = (EditText) findViewById(R.id.contactID);
        backButton = (ImageButton) findViewById(R.id.backButtonID);

        progressDialog = new ProgressDialog(this);
        registerData = FirebaseDatabase.getInstance().getReference().child("User Profiles");
        mAuth = FirebaseAuth.getInstance();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(Registration.this, Login.class));
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String firstName = first_name.getText().toString().trim();
                String lastName = last_name.getText().toString().trim();
                String email = emailtext.getText().toString().trim();
                String password = passwordtext.getText().toString().trim();
                String r_password = rt_pass_text.getText().toString().trim();
                String phoneNumber = contact.getText().toString().trim();
                String name = firstName + " " + lastName;

                if (!TextUtils.isEmpty(firstName) && !TextUtils.isEmpty(lastName) && !TextUtils.isEmpty(email) &&
                        !TextUtils.isEmpty(password) && !TextUtils.isEmpty(r_password) && !TextUtils.isEmpty(phoneNumber)) {
                    if (password.length() < 6) {
                        Toast toast = Toast.makeText(Registration.this, "Password too short, MIN: 6", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();

                    } else {
                        if (!password.equals(r_password)) {
                            Toast toast = Toast.makeText(Registration.this, "Password don't match", Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();

                        } else {
                            startregister(name, email, password, phoneNumber);
                        }
                    }

                } else {
                    Toast toast = Toast.makeText(Registration.this, "Enpty Field Found", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }

            }
        });


    }

    private void startregister(final String name, final String email, String password, final String phoneNumber) {

        progressDialog.setMessage("Uploading");
        progressDialog.show();

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    userID = mAuth.getCurrentUser().getUid();
                    newDatarefer = registerData.child(userID);


                    mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {

                                newDatarefer.child("id").setValue(userID);
                                newDatarefer.child("name").setValue(name);
                                newDatarefer.child("email").setValue(email);
                                newDatarefer.child("contact").setValue(phoneNumber);

                                progressDialog.dismiss();
                                Toast toast = Toast.makeText(Registration.this, "Verify your account first", Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();


                            } else {
                                progressDialog.dismiss();

                                Toast toast = Toast.makeText(Registration.this, "Invalid Email", Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                            }

                        }
                    });


                } else {
                    progressDialog.dismiss();

                    Toast toast = Toast.makeText(Registration.this, "Error- Not Registered", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            }
        });
    }


}