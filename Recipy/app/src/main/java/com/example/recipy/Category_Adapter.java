package com.example.recipy;

import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresPermission;
import androidx.constraintlayout.helper.widget.Layer;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Document;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Category_Adapter extends RecyclerView.Adapter<Category_Adapter.ViewHolder> {

    private List<Category_Model> category_models;
    private FirebaseAuth auth;
    private FirebaseFirestore firestore;
    private boolean likeVlaue = false;
    private DatabaseReference likeRefrence;
    private Calendar clender;
    private SimpleDateFormat simpleDateFormat;


    public Category_Adapter(List<Category_Model> category_models) {
        this.category_models = category_models;
    }

    @NonNull
    @Override
    public Category_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rec_layout, viewGroup, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull Category_Adapter.ViewHolder holder, int position) {
        String image = category_models.get(position).getImage();
        String documentId = category_models.get(position).getDocumentId();

        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        String userid = auth.getCurrentUser().getUid();

        clender = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");


        likeRefrence = FirebaseDatabase.getInstance().getReference("User_Likes");

        likeRefrence.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(documentId).hasChild(userid)) {
                    holder.favImg.setImageResource(R.drawable.ic_baseline_favorite_24);
                } else {
                    holder.favImg.setImageResource(R.drawable.ic_baseline_favorite_border_24);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        holder.getImage(image);
        DocumentReference reference = firestore.collection("Users").document(userid).collection("Favourite").document(documentId);
        holder.btn.setOnClickListener(view -> {
            likeVlaue = true;
            likeRefrence.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (likeVlaue == true) {
                        if (snapshot.child(documentId).hasChild(userid)) {
                            likeRefrence.child(documentId).removeValue();
                            likeVlaue = false;
                            reference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                }
                            });
                        } else {
                            likeRefrence.child(documentId).child(userid).setValue(true);
                            likeVlaue = false;
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


        holder.imageView.setOnClickListener(view -> {
            Intent intent = new Intent(holder.imageView.getContext(), Preview_Activity.class);
            intent.putExtra("documentId", category_models.get(position).getDocumentId());
            intent.putExtra("image", category_models.get(position).getImage());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            holder.imageView.getContext().startActivity(intent);

        });


    }

    @Override
    public int getItemCount()
    {
        return category_models.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView, favImg;
        private LinearLayout btn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            btn = itemView.findViewById(R.id.favBtn);
            favImg = itemView.findViewById(R.id.favImage);
            imageView = itemView.findViewById(R.id.cat_image);
        }

        private void getImage(String image) {
            Glide.with(itemView.getContext()).load(image).centerCrop().apply(new RequestOptions().placeholder(R.drawable.placeholder)).into(imageView);
        }
    }
}
