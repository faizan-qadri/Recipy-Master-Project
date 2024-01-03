package com.example.recipy;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView cat_list;
    private Category_Adapter category_adapter;

    private FirebaseFirestore firestore;

    private TextView recipyBtn;
    private LinearLayout all, veg, nonVeg, sweet, breakFast, junkFood, fabBtn;
    private ImageView all_Img, veg_Img, nonVeg_Img, sweet_Img, breakFast_Img, junkFood_Img;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cat_list = findViewById(R.id.cat_list);
        firestore = FirebaseFirestore.getInstance();

        all_Img = findViewById(R.id.recipes_all);
        nonVeg_Img = findViewById(R.id.recipes_nonVeg);
        veg_Img = findViewById(R.id.recipes_veg);
        sweet_Img = findViewById(R.id.recipes_sweet);
        breakFast_Img = findViewById(R.id.recipes_brakfast);
        junkFood_Img = findViewById(R.id.recipes_junk);

        recipyBtn = findViewById(R.id.recipyBtn);

        fabBtn = findViewById(R.id.main_favBtn);

        fabBtn.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this,favourit_Activity.class);
            startActivity(intent);
        });

        recipyBtn.setOnClickListener(view ->  {
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
        });

        all_Img.setImageResource(R.drawable.logo);
        nonVeg_Img.setImageResource(R.drawable.nonveg);
        veg_Img.setImageResource(R.drawable.veg);
        sweet_Img.setImageResource(R.drawable.sweets);
        breakFast_Img.setImageResource(R.drawable.breakfast);
        junkFood_Img.setImageResource(R.drawable.junkfood);


        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        cat_list.setLayoutManager(manager);
        List<Category_Model> category_models = new ArrayList<>();
        category_adapter = new Category_Adapter(category_models);
        cat_list.setAdapter(category_adapter);

        firestore.collection("Recipe_Category").orderBy("index", Query.Direction.DESCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                for (DocumentChange dc : value.getDocumentChanges()){
                    if (dc.getType() == DocumentChange.Type.ADDED){
                        category_models.add(dc.getDocument().toObject(Category_Model.class));
                    }
                    category_adapter.notifyDataSetChanged();
                }
            }
        });

        all = findViewById(R.id.main_allBtn);
        veg = findViewById(R.id.main_VegBtn);
        nonVeg = findViewById(R.id.main_nonVegBtn);
        sweet = findViewById(R.id.main_sweetBtn);
        breakFast = findViewById(R.id.main_breakFastBtn);
        junkFood = findViewById(R.id.main_JunkfoodBtn);


        all.setOnClickListener(view -> {});

        veg.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, Veg_Activity.class);
            startActivity(intent);
        });

        nonVeg.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, NonVeg_Activity.class);
            startActivity(intent);
        });

        breakFast.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, BreakFast_Activity.class);
            startActivity(intent);
        });

        sweet.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, Sweets_Activity.class);
            startActivity(intent);
        });

        junkFood.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, JunkFood_Activity.class);
            startActivity(intent);
        });


    }
}