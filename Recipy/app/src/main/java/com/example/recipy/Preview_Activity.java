package com.example.recipy;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Preview_Activity extends AppCompatActivity {

    private LinearLayout backBtn;
    private TextView title, nuFacts;
    private ImageView imageView;

    private TextView ing_1, ing_2,ing_3,ing_4,ing_5,ing_6,ing_7,ing_8,ing_9,ing_10,ing_11,ing_12,ing_13,ing_14,ing_15,ing_16,ing_17,ing_18, ing_19, ing_20;
    private TextView det_1, det_2, det_3, det_4, det_5, det_6, det_7, det_8, det_9, det_10, det_11, det_12, det_13, det_14, det_15;

    private FirebaseFirestore firestore;
    private FirebaseAuth firebaseAuth;

    private boolean likeVlaue = false;
    private DatabaseReference likeRefrence;
    private Calendar clender;
    private SimpleDateFormat simpleDateFormat;
    private ImageView likeImg;
    private LinearLayout likeBtn;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);

        backBtn = findViewById(R.id.preview_backBtn);
        title = findViewById(R.id.preview_title);
        imageView = findViewById(R.id.preview_mainImgMain);
        nuFacts = findViewById(R.id.nuFacts);

        clender = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        String documentId = getIntent().getStringExtra("documentId");
        String image = getIntent().getStringExtra("image");

        firebaseAuth = FirebaseAuth.getInstance();

        String userId = firebaseAuth.getCurrentUser().getUid();

        likeRefrence = FirebaseDatabase.getInstance().getReference("User_Likes");
        likeImg = findViewById(R.id.preview_favImage);
        likeBtn = findViewById(R.id.preview_favBtn);


        likeRefrence.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(documentId).hasChild(userId)) {
                    likeImg.setImageResource(R.drawable.ic_baseline_favorite_24);
                } else {
                    likeImg.setImageResource(R.drawable.ic_baseline_favorite_border_24);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        likeBtn.setOnClickListener(view -> {
            likeVlaue = true;
            likeRefrence.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (likeVlaue == true){
                        if (snapshot.child(documentId).hasChild(userId)) {
                            likeRefrence.child(documentId).removeValue();
                            likeVlaue = false;
                            DocumentReference reference = firestore.collection("Users").document(userId).collection("Favourite").document(documentId);
                            reference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                }
                            });

                        } else {
                            likeRefrence.child(documentId).child(userId).setValue(true);
                            likeVlaue = false;
                            DocumentReference reference = firestore.collection("Users").document(userId).collection("Favourite").document(documentId);
                            Map<String, Object> user = new HashMap<>();
                            user.put("documentId", documentId);
                            user.put("index", simpleDateFormat.format(clender.getTime()));
                            user.put("image", image);
                            reference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                }
                            });
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        });

        ing_1 = findViewById(R.id.preview_ing_1);
        ing_2 = findViewById(R.id.preview_ing_2);
        ing_3 = findViewById(R.id.preview_ing_3);
        ing_4 = findViewById(R.id.preview_ing_4);
        ing_5 = findViewById(R.id.preview_ing_5);
        ing_6 = findViewById(R.id.preview_ing_6);
        ing_7 = findViewById(R.id.preview_ing_7);
        ing_8 = findViewById(R.id.preview_ing_8);
        ing_9 = findViewById(R.id.preview_ing_9);
        ing_10 = findViewById(R.id.preview_ing_10);
        ing_11 = findViewById(R.id.preview_ing_11);
        ing_12 = findViewById(R.id.preview_ing_12);
        ing_13 = findViewById(R.id.preview_ing_13);
        ing_14 = findViewById(R.id.preview_ing_14);
        ing_15 = findViewById(R.id.preview_ing_15);
        ing_16 = findViewById(R.id.preview_ing_16);
        ing_17 = findViewById(R.id.preview_ing_17);
        ing_18 = findViewById(R.id.preview_ing_18);
        ing_19 = findViewById(R.id.preview_ing_19);
        ing_20 = findViewById(R.id.preview_ing_20);

        det_1 = findViewById(R.id.preview_det_1);
        det_2 = findViewById(R.id.preview_det_2);
        det_3 = findViewById(R.id.preview_det_3);
        det_4 = findViewById(R.id.preview_det_4);
        det_5 = findViewById(R.id.preview_det_5);
        det_6 = findViewById(R.id.preview_det_6);
        det_7 = findViewById(R.id.preview_det_7);
        det_8 = findViewById(R.id.preview_det_8);
        det_9 = findViewById(R.id.preview_det_9);
        det_10 = findViewById(R.id.preview_det_10);
        det_11 = findViewById(R.id.preview_det_11);
        det_12 = findViewById(R.id.preview_det_12);
        det_13 = findViewById(R.id.preview_det_13);
        det_14 = findViewById(R.id.preview_det_14);
        det_15 = findViewById(R.id.preview_det_15);
        
        firestore =FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();



        try {
            firestore.collection("CATEGORY")
                    .document(documentId)
            .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {


                    title.setText(value.getString("title"));
                    nuFacts.setText(value.getString("nu_Facts"));

                    if (value.getString("ing_1").compareTo("na") == 0){
                        ing_1.setVisibility(View.GONE);
                    }else {
                        ing_1.setText("\uD83D\uDD36 "+value.getString("ing_1"));
                    }

                    if (value.getString("ing_2").compareTo("na") == 0){
                        ing_2.setVisibility(View.GONE);
                    }else {
                        ing_2.setText("\uD83D\uDD36 "+value.getString("ing_2"));
                    }
                    if (value.getString("ing_3").compareTo("na") == 0){
                        ing_3.setVisibility(View.GONE);
                    }else {
                        ing_3.setText("\uD83D\uDD36 "+value.getString("ing_3"));
                    }
                    if (value.getString("ing_4").compareTo("na") == 0){
                        ing_4.setVisibility(View.GONE);
                    }else {
                        ing_4.setText("\uD83D\uDD36 "+value.getString("ing_4"));
                    }
                    if (value.getString("ing_5").compareTo("na") == 0){
                        ing_5.setVisibility(View.GONE);
                    }else {
                        ing_5.setText("\uD83D\uDD36 "+value.getString("ing_5"));
                    }
                    if (value.getString("ing_6").compareTo("na") == 0){
                        ing_6.setVisibility(View.GONE);
                    }else {
                        ing_6.setText("\uD83D\uDD36 "+value.getString("ing_6"));
                    }
                    if (value.getString("ing_7").compareTo("na") == 0){
                        ing_7.setVisibility(View.GONE);
                    }else {
                        ing_7.setText("\uD83D\uDD36 "+value.getString("ing_7"));
                    }
                    if (value.getString("ing_8").compareTo("na") == 0){
                        ing_8.setVisibility(View.GONE);
                    }else {
                        ing_8.setText("\uD83D\uDD36 "+value.getString("ing_8"));
                    }
                    if (value.getString("ing_9").compareTo("na") == 0){
                        ing_9.setVisibility(View.GONE);
                    }else {
                        ing_9.setText("\uD83D\uDD36 "+value.getString("ing_9"));
                    }
                    if (value.getString("ing_10").compareTo("na") == 0){
                        ing_10.setVisibility(View.GONE);
                    }else {
                        ing_10.setText("\uD83D\uDD36 "+value.getString("ing_10"));
                    }
                    if (value.getString("ing_11").compareTo("na") == 0){
                        ing_11.setVisibility(View.GONE);
                    }else {
                        ing_11.setText("\uD83D\uDD36 "+value.getString("ing_11"));
                    }
                    if (value.getString("ing_12").compareTo("na") == 0){
                        ing_12.setVisibility(View.GONE);
                    }else {
                        ing_12.setText("\uD83D\uDD36 "+value.getString("ing_12"));
                    }
                    if (value.getString("ing_13").compareTo("na") == 0){
                        ing_13.setVisibility(View.GONE);
                    }else {
                        ing_13.setText("\uD83D\uDD36 "+value.getString("ing_13"));
                    }
                    if (value.getString("ing_14").compareTo("na") == 0){
                        ing_14.setVisibility(View.GONE);
                    }else {
                        ing_14.setText("\uD83D\uDD36 "+value.getString("ing_14"));
                    }
                    if (value.getString("ing_15").compareTo("na") == 0){
                        ing_15.setVisibility(View.GONE);
                    }else {
                        ing_15.setText("\uD83D\uDD36 "+value.getString("ing_15"));
                    }
                    if (value.getString("ing_16").compareTo("na") == 0){
                        ing_16.setVisibility(View.GONE);
                    }else {
                        ing_16.setText("\uD83D\uDD36 "+value.getString("ing_16"));
                    }
                    if (value.getString("ing_17").compareTo("na") == 0){
                        ing_17.setVisibility(View.GONE);
                    }else {
                        ing_17.setText("\uD83D\uDD36 "+value.getString("ing_17"));
                    }
                    if (value.getString("ing_18").compareTo("na") == 0){
                        ing_18.setVisibility(View.GONE);
                    }else {
                        ing_18.setText("\uD83D\uDD36 "+value.getString("ing_18"));
                    }
                    if (value.getString("ing_19").compareTo("na") == 0){
                        ing_19.setVisibility(View.GONE);
                    }else {
                        ing_19.setText("\uD83D\uDD36 "+value.getString("ing_19"));
                    }
                    if (value.getString("ing_20").compareTo("na") == 0){
                        ing_20.setVisibility(View.GONE);
                    }else {
                        ing_20.setText("\uD83D\uDD36 "+value.getString("ing_20"));
                    }



                    if (value.getString("det_1").compareTo("na") == 0){
                        det_1.setVisibility(View.GONE);
                    }else {
                        det_1.setText("Step 1:\n"+value.getString("det_1"));
                    }
                    if (value.getString("det_2").compareTo("na") == 0){
                        det_2.setVisibility(View.GONE);
                    }else {
                        det_2.setText("Step 2:\n"+value.getString("det_2"));
                    }
                    if (value.getString("det_3").compareTo("na") == 0){
                        det_3.setVisibility(View.GONE);
                    }else {
                        det_3.setText("Step 3:\n"+value.getString("det_3"));
                    }
                    if (value.getString("det_4").compareTo("na") == 0){
                        det_4.setVisibility(View.GONE);
                    }else {
                        det_4.setText("Step 4:\n"+value.getString("det_4"));
                    }
                    if (value.getString("det_5").compareTo("na") == 0){
                        det_5.setVisibility(View.GONE);
                    }else {
                        det_5.setText("Step 5:\n"+value.getString("det_5"));
                    }
                    if (value.getString("det_6").compareTo("na") == 0){
                        det_6.setVisibility(View.GONE);
                    }else {
                        det_6.setText("Step 6:\n"+value.getString("det_6"));
                    }
                    if (value.getString("det_7").compareTo("na") == 0){
                        det_7.setVisibility(View.GONE);
                    }else {
                        det_7.setText("Step 7:\n"+value.getString("det_7"));
                    }
                    if (value.getString("det_8").compareTo("na") == 0){
                        det_8.setVisibility(View.GONE);
                    }else {
                        det_8.setText("Step 8:\n"+value.getString("det_8"));
                    }
                    if (value.getString("det_9").compareTo("na") == 0){
                        det_9.setVisibility(View.GONE);
                    }else {
                        det_9.setText("Step 9:\n"+value.getString("det_9"));
                    }
                    if (value.getString("det_10").compareTo("na") == 0){
                        det_10.setVisibility(View.GONE);
                    }else {
                        det_10.setText("Step 10:\n"+value.getString("det_10"));
                    }
                    if (value.getString("det_11").compareTo("na") == 0){
                        det_11.setVisibility(View.GONE);
                    }else {
                        det_11.setText("Step 11:\n"+value.getString("det_11"));
                    }
                    if (value.getString("det_12").compareTo("na") == 0){
                        det_12.setVisibility(View.GONE);
                    }else {
                        det_12.setText("Step 12:\n"+value.getString("det_12"));
                    }
                    if (value.getString("det_13").compareTo("na") == 0){
                        det_13.setVisibility(View.GONE);
                    }else {
                        det_13.setText("Step 13:\n"+value.getString("det_13"));
                    }
                    if (value.getString("det_14").compareTo("na") == 0){
                        det_14.setVisibility(View.GONE);
                    }else {
                        det_14.setText("Step 14:\n"+value.getString("det_14"));
                    }
                    if (value.getString("det_15").compareTo("na") == 0){
                        det_15.setVisibility(View.GONE);
                    }else {
                        det_15.setText("Step 15:\n"+value.getString("det_15"));
                    }
                    Glide.with(Preview_Activity.this).load(value.getString("image")).centerCrop().apply(new RequestOptions().placeholder(R.drawable.placeholder)).into(imageView);

                }
            });

            }catch (Exception e){
            Toast.makeText(Preview_Activity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }


        backBtn.setOnClickListener(view -> onBackPressed());

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}