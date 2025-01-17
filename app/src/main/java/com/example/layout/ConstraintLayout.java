package com.example.layout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.Executors;

public class ConstraintLayout extends AppCompatActivity {

    private EditText etName, etRollNumber, etId;
    private StudentDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_constraint);

        etName = findViewById(R.id.et_name);
        etRollNumber = findViewById(R.id.et_rollnumber);
        etId = findViewById(R.id.et_id);

        database = StudentDatabase.getInstance(this);

        findViewById(R.id.btn_addDetails).setOnClickListener(v -> {
            String name = etName.getText().toString();
            String rollNumberText = etRollNumber.getText().toString();
            String studentIdText = etId.getText().toString();

            if (!name.isEmpty() && !rollNumberText.isEmpty() && !studentIdText.isEmpty()) {


                int rollNumber = Integer.parseInt(rollNumberText);
                int studentId = Integer.parseInt(studentIdText);

                Student student = new Student();
                student.setName(name);
                student.setRollNumber(rollNumber);
                student.setId(studentId);

                // Add student to the database
                Executors.newSingleThreadExecutor().execute(() -> {
                    database.studentDao().insertStudent(student);

                    // Navigate to RecyclerView Activity
                    runOnUiThread(() -> {
                        Toast.makeText(this, "Student Added!", Toast.LENGTH_SHORT).show();
                    });
                });
            }
            else {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            }
        });


        findViewById(R.id.btn_switchtolinearlayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConstraintLayout.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
