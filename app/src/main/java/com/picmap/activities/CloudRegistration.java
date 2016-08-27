package com.picmap.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.picmap.R;


/**
 * Created by Yur on 27/08/16.
 */
public class CloudRegistration extends AppCompatActivity {
    private EditText mNameEditText;
    private EditText mEmailEditText;
    private EditText mPasswordEditText;
    private EditText mPasswordRepeatEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cloud_register);


        mNameEditText = (EditText)findViewById(R.id.cloud_register_name_edt);
        mEmailEditText = (EditText)findViewById(R.id.cloud_register_mail_edt);
        mPasswordEditText = (EditText)findViewById(R.id.cloud_register_password_edt);
        mPasswordRepeatEditText = (EditText)findViewById(R.id.cloud_register_password_retype_edt);

        String emailLogin = getIntent().getStringExtra(CloudLogin.EXTRA_EMAIL);
        if(!TextUtils.isEmpty(emailLogin))
            mEmailEditText.setText(emailLogin);

        String passwordLogin = getIntent().getStringExtra(CloudLogin.EXTRA_PASSWORD);
        if(!TextUtils.isEmpty(passwordLogin))
            mPasswordEditText.setText(passwordLogin);

        findViewById(R.id.cloud_register_register_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                if(!isValidName(mNameEditText.getText().toString())){
                    Toast.makeText(CloudRegistration.this, "Incorrect Name", Toast.LENGTH_LONG).show();
                    return;
                }
                if(!isValidEmail(mEmailEditText.getText().toString())){
                    Toast.makeText(CloudRegistration.this, "Incorrect Email", Toast.LENGTH_LONG).show();
                    return;
                }
                if(!isValidPassword(mPasswordEditText.getText().toString())){
                    Toast.makeText(CloudRegistration.this, "Incorrect Password", Toast.LENGTH_LONG).show();
                    return;
                }
                if(!mPasswordEditText.getText().toString().equals(mPasswordRepeatEditText.getText().toString())){
                    Toast.makeText(CloudRegistration.this, "Repeated Password != Password", Toast.LENGTH_LONG).show();
                    return;
                }

                final FirebaseAuth auth = FirebaseAuth.getInstance();

                auth.createUserWithEmailAndPassword(mEmailEditText.getText().toString(), mPasswordEditText.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> pTask) {
                                if(pTask.isSuccessful()){
                                    FirebaseDatabase.getInstance().getReference().child("Users").child(pTask.getResult().getUser().getUid())
                                            .child("Name").setValue(mNameEditText.getText().toString());

                                    getSharedPreferences("users", MODE_PRIVATE).edit().putString(CloudLogin.SHARED_NAME, mNameEditText.getText().toString()).commit();

                                    Toast.makeText(CloudRegistration.this, "Account Was Created!", Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(CloudRegistration.this, MainActivity.class));
                                    finish();
                                } else {
                                    Toast.makeText(CloudRegistration.this, pTask.getException().toString(), Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });
    }

    private boolean isValidName(String pName) {
        if(TextUtils.isEmpty(pName))
            return false;
        return true;
    }

    private boolean isValidEmail(String pEmail) {
        if(TextUtils.isEmpty(pEmail))
            return false;
        return true;
    }

    private boolean isValidPassword(String pPassword) {
        if(TextUtils.isEmpty(pPassword))
            return false;
        return true;
    }
}
