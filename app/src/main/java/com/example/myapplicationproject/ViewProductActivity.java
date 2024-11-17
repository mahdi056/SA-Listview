package com.example.myapplicationproject;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplicationproject.AcademyAdopter;
import com.example.myapplicationproject.Databasehelper;
import com.example.myapplicationproject.Delete_Academy_Activity;
import com.example.myapplicationproject.UpdateAcademyActivity;

public class ViewProductActivity extends AppCompatActivity {
    private ListView listViewProducts;
    private Databasehelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewproduct);

        listViewProducts = findViewById(R.id.list_view_products);
        Button buttonUpdate = findViewById(R.id.button_update);
        Button buttonDelete = findViewById(R.id.button_delete);
        databaseHelper = new Databasehelper(this);

        displayProducts();

       buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the Update button click
                handleUpdate();
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              // Handle the Delete button click
               handleDelete();
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        // Refresh the displayed products
        displayProducts();
    }

    private void displayProducts() {
        Cursor cursor = databaseHelper.getAllAcademies();
        AcademyAdopter adapter = new AcademyAdopter(this, cursor, 0);
        listViewProducts.setAdapter(adapter);
    }

    private void handleUpdate() {

        Intent intent = new Intent(ViewProductActivity.this, UpdateAcademyActivity.class); // Assuming HomeActivity is the activity after login
        startActivity(intent);
    }

    private void handleDelete() {
        Intent intent = new Intent(ViewProductActivity.this, Delete_Academy_Activity.class); // Assuming HomeActivity is the activity after login
        startActivity(intent);
        Toast.makeText(this, "Delete button clicked", Toast.LENGTH_SHORT).show();
    }
}