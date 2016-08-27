package com.picmap.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.picmap.R;

/**
 * Created by Yur on 27/08/16.
 */
public class CloudLogin extends AppCompatActivity {
    private String mTrueMail;
    public static final String EXTRA_EMAIL = "email";
    public static final String EXTRA_PASSWORD = "password";

    public static final String SHARED_SKIP = "shared_skip";

    public static final String SHARED_NAME = "shared_name";
    public static final String SHARED_PHOTO_URL = "shared_photo_url";

  /*  private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()) {
                case R.id.button_create_account_login:
                    break;
            }
        }
    };*/

    private EditText mEmailEditText;
    private EditText mPasswordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cloud_login);

        mEmailEditText = (EditText)findViewById(R.id.cloud_login_mail_edt);
        mPasswordEditText = (EditText)findViewById(R.id.cloud_login_password_edt);

        findViewById(R.id.cloud_login_signin_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                FirebaseAuth.getInstance()
                        .signInWithEmailAndPassword(mEmailEditText.getText().toString(), mPasswordEditText.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> pTask) {
                                if(pTask.isSuccessful()){
                                    mTrueMail=mEmailEditText.getText().toString();
                                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                                    final DatabaseReference ref = FirebaseDatabase.getInstance().getReference()
                                            .child(user.getUid()).child("Name");

                                    ref.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot pDataSnapshot) {
                                            getSharedPreferences("users", MODE_PRIVATE).edit().putString(SHARED_NAME, pDataSnapshot.getValue(String.class)).commit();
                                            ref.removeEventListener(this);

                                        }

                                        @Override
                                        public void onCancelled(DatabaseError pDatabaseError) {
                                        }
                                    });
                                    Intent intent = new Intent(CloudLogin.this,CloudFirebase.class);
                                    intent.putExtra("mail",mTrueMail);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(CloudLogin.this, "Try Again", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });

        findViewById(R.id.cloud_login_registration_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                Intent intent = new Intent(CloudLogin.this, CloudRegistration.class);
                intent.putExtra(EXTRA_EMAIL, mEmailEditText.getText().toString());
                intent.putExtra(EXTRA_PASSWORD, mPasswordEditText.getText().toString());
                startActivity(intent);
                finish();
            }
        });

       /* findViewById(R.id.text_view_skip).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                getSharedPreferences("pref", MODE_PRIVATE).edit().putBoolean(SHARED_SKIP, true).commit();
                startActivity(new Intent(CloudLogin.this, MainActivity.class));
                finish();
            }
        });*/
    }
}
