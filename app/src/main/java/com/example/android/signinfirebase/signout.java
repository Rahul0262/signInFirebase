package com.example.android.signinfirebase;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

public class signout extends AppCompatActivity {

    private Button signoutbtn;
    private TextView textViewEmail;
    private TextView textViewPhone;
    private TextView textViewName;
    private ImageView imageViewPhoto;
    private FirebaseAuth mAuth;
    private FirebaseUser mFirebaseUser;
    private FirebaseAuth.AuthStateListener mAuthListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signout);

        signoutbtn = (Button) findViewById(R.id.signout_btn);
        textViewEmail = (TextView) findViewById(R.id.email);
        textViewPhone = (TextView) findViewById(R.id.phone);
        textViewName = (TextView) findViewById(R.id.name);
        imageViewPhoto = (ImageView) findViewById(R.id.profilePic);

//        Bundle bundle = getIntent().getExtras();
//
//        String img_url = bundle.getString("IMAGE");
//        textViewEmail.setText(bundle.getString("EMAIL"));
//        textViewName.setText(bundle.getString("NAME"));
//        textViewPhone.setText(bundle.getString("PHONE"));
        mAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mAuth.getCurrentUser();
        if(mFirebaseUser != null)
        {
            String name = mFirebaseUser.getDisplayName();
            String email = mFirebaseUser.getEmail();
            String phoneNumber = mFirebaseUser.getUid();
            String personPhoto = String.valueOf(mFirebaseUser.getPhotoUrl());
            textViewName.setText(name);
            textViewEmail.setText(email);
            textViewPhone.setText(phoneNumber);
            Picasso.with(this).load(personPhoto).into(imageViewPhoto);

        }
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() == null)
                {
                    startActivity(new Intent(signout.this,MainActivity.class));
                }
            }
        };

        signoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.getInstance().signOut();
            }
        });
    }

    protected void onStart()
    {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }
}
