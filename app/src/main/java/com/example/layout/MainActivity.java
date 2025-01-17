package com.example.layout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private android.widget.Button button;

    private RecyclerView recyclerView;
    private StudentAdapter adapter;
    private StudentDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.ll_main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent intent = new Intent(MainActivity.this , Relative_layout.class);

        button = findViewById(R.id.btn_switchtorelativelayout);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
                finish();
            }
        });


        recyclerView = findViewById(R.id.rv_students);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        database = StudentDatabase.getInstance(this);

        // Fetch data from the database
        loadStudents();

    }

    @Override
    protected void onResume() {
        super.onResume();
        loadStudents(); // Reload data when returning to the activity
    }

    private void loadStudents() {
        Executors.newSingleThreadExecutor().execute(() -> {
            List<Student> allStudents = database.studentDao().getAllStudents();

            runOnUiThread(() -> {
                adapter = new StudentAdapter(allStudents);
                recyclerView.setAdapter(adapter);
            });
        });
    }
}