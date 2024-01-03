package com.example.recipy;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class favourit_Activity extends AppCompatActivity {

    private RecyclerView cat_list;
    private Fav_Adapter category_adapter;

    private FirebaseFirestore firestore;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourit);

        cat_list = findViewById(R.id.fav_List);
        firestore = FirebaseFirestore.getInstance();

        Toolbar toolbar = findViewById(R.id.fav_Toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);





        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        cat_list.setLayoutManager(manager);
        List<Fav_Model> category_models = new ArrayList<>();
        category_adapter = new Fav_Adapter(category_models);
        cat_list.setAdapter(category_adapter);

        firebaseAuth = FirebaseAuth.getInstance();

        String userId = firebaseAuth.getCurrentUser().getUid();

        firestore.collection("Users").document(userId).collection("Favourite").orderBy("index", Query.Direction.DESCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                for (DocumentChange dc : value.getDocumentChanges()){
                    if (dc.getType() == DocumentChange.Type.ADDED){
                        category_models.add(dc.getDocument().toObject(Fav_Model.class));
                    }
                    category_adapter.notifyDataSetChanged();
                }
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id==android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);

    }
}