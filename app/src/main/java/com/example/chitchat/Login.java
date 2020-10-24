package com.example.chitchat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity implements View.OnClickListener {
    TextView registerClick, resetpassword;;
    EditText loginemail, loginpassword;
    Button loginButton;

    FirebaseAuth logAuth;
    DatabaseReference logDataRefernce;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        try {
            this.getSupportActionBar().hide();
        }catch (NullPointerException e){}

        registerClick = (TextView) findViewById(R.id.registerloginID);
        resetpassword = (TextView) findViewById(R.id.forgetpasswordID);
        loginemail = (EditText) findViewById(R.id.loginemailID);
        loginpassword = (EditText) findViewById(R.id.loginpasswordID);
        loginButton = (Button) findViewById(R.id.loginbuttonID);

        logAuth = FirebaseAuth.getInstance();
        logDataRefernce = FirebaseDatabase.getInstance().getReference().child("User Profiles");
        progressDialog = new ProgressDialog(this);

        registerClick.setOnClickListener(this);
        loginButton.setOnClickListener(this);
        resetpassword.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.registerloginID){
            finish();
            startActivity(new Intent(Login.this, Registration.class));
        }
        else if(view.getId()==R.id.forgetpasswordID){
            finish();
            startActivity(new Intent(Login.this, resetpassword1.class));
        }
        else if(view.getId()==R.id.loginbuttonID){
            String email = loginemail.getText().toString().trim();
            String password = loginpassword.getText().toString().trim();

            if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)){

                login_check(email, password);

            }else {
                Toast toast = Toast.makeText(Login.this, "Empty field found", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        }
    }

    private void login_check(String email, String password) {
        progressDialog.setMessage("Logging In");
        progressDialog.show();

        logAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    userExistenceCheck();
                }else{
                    progressDialog.dismiss();
                    Toast toast = Toast.makeText( Login.this, "Invalid email/password", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER,0,0);
                    toast.show();
                }
            }
        });
    }

    private void userExistenceCheck() {
        final String userID = logAuth.getCurrentUser().getUid();
        logDataRefernce.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChild(userID)){
                    progressDialog.dismiss();

                   if(logAuth.getCurrentUser().isEmailVerified()){
                       Intent intent = new Intent(Login.this, MainActivity.class);
                       intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                       startActivity(intent);
                   }else {

                       Toast toast = Toast.makeText(Login.this, "Verify your account first", Toast.LENGTH_SHORT);
                       toast.setGravity(Gravity.CENTER, 0, 0);
                       toast.show();
                   }
                }else {
                    progressDialog.dismiss();

                    Toast toast = Toast.makeText(Login.this, "Invalid email/password", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}