package com.example.recipy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Splash_Activity extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        if (auth.getCurrentUser() != null){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(Splash_Activity.this, MainActivity.class));
                    Splash_Activity.this.finish();
                }
            },2000);
        }else {
            auth.signInAnonymously().addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        String userid = auth.getCurrentUser().getUid();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                DocumentReference reference = firestore.collection("Users").document(userid);
                                Map<String, Object> user = new HashMap<>();
                                user.put("userId",userid);
                                reference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                    }
                                });
                                startActivity(new Intent(Splash_Activity.this, MainActivity.class));
                                Splash_Activity.this.finish();
                            }
                        },2000);

                    }
                }
            });

        }





    }
}