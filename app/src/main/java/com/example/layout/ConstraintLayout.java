package com.example.layout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.Executors;

public class ConstraintLayout extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    public Button switchtolinear, gotoauth;

    private EditText etName, etRollNumber, etId;
    private Uri selectedImageUri;
    private StudentDatabase database;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_constraint);

        etName = findViewById(R.id.et_name);
        etRollNumber = findViewById(R.id.et_rollnumber);
        etId = findViewById(R.id.et_id);

        switchtolinear = findViewById(R.id.btn_switchtolinearlayout);

        gotoauth = findViewById(R.id.btn_gotoauth);
        Intent gintent = new Intent(ConstraintLayout.this, MicrosoftAuth.class);

        gotoauth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(gintent);
            }
        });

        database = StudentDatabase.getInstance(this);

        findViewById(R.id.btn_select_photo).setOnClickListener(v -> openImagePicker());

        findViewById(R.id.btn_addDetails).setOnClickListener(v -> saveStudentDetails());

        Intent intent = new Intent(ConstraintLayout.this, MainActivity.class);
        switchtolinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
                finish();
            }
        });
    }

    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            selectedImageUri = data.getData();
            Toast.makeText(this, "Image Selected", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveStudentDetails() {
        String name = etName.getText().toString();
        String rollNumberText = etRollNumber.getText().toString();
        String studentIdText = etId.getText().toString();

        if (!name.isEmpty() && !rollNumberText.isEmpty() && !studentIdText.isEmpty() && selectedImageUri != null) {
            int rollNumber = Integer.parseInt(rollNumberText);
            int studentId = Integer.parseInt(studentIdText);

            Student student = new Student();
            student.setName(name);
            student.setRollNumber(rollNumber);
            student.setId(studentId);
            student.setImageUri(selectedImageUri.toString());

            Executors.newSingleThreadExecutor().execute(() -> {
                database.studentDao().insertStudent(student);
                runOnUiThread(() -> Toast.makeText(this, "Student Added!", Toast.LENGTH_SHORT).show());
            });
        } else {
            Toast.makeText(this, "Please fill all fields and select an image", Toast.LENGTH_SHORT).show();
        }
    }
}
