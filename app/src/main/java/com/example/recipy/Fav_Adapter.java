package com.example.recipy;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Fav_Adapter extends RecyclerView.Adapter<Fav_Adapter.ViewHolder> {

    private List<Fav_Model> favModels;
    private DatabaseReference likeRefrence;
    private FirebaseAuth firebaseAuth;
    private boolean likeVlaue = false;
    private FirebaseFirestore firestore;


    public Fav_Adapter(List<Fav_Model> favModels) {
        this.favModels = favModels;
    }

    @NonNull
    @Override
    public Fav_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fav_layout,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Fav_Adapter.ViewHolder holder, int position) {
        String documentId = favModels.get(position).getDocumentId();
        String image = favModels.get(position).getImage();
        String index = favModels.get(position).getIndex();

        holder.getImage(image);
        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        holder.imageView.setOnClickListener(view -> {
            Intent intent = new Intent(holder.imageView.getContext(), Preview_Activity.class);
            intent.putExtra("documentId", documentId);
            intent.putExtra("image", image);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            holder.imageView.getContext().startActivity(intent);

        });

        String userid = firebaseAuth.getCurrentUser().getUid();

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
                            user.put("index", index);
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

    }

    @Override
    public int getItemCount() {
        return favModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView, favImg;
        private LinearLayout btn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.fav_image);
            btn = itemView.findViewById(R.id.favBtnLayout);
            favImg = itemView.findViewById(R.id.favImgLayout);

        }
        private void getImage(String image){
            Glide.with(itemView.getContext()).load(image).centerCrop().apply(new RequestOptions().placeholder(R.drawable.placeholder)).into(imageView);
        }
    }
}
