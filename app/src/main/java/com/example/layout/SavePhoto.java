package com.example.layout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class SavePhoto extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private static final String PREFS_NAME = "MyPrefs";
    private static final String IMAGE_URI_KEY = "image_uri";

    private ImageView imageView;
    private Button pickImageButton;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_savephoto);


        imageView = findViewById(R.id.iv_image);
        pickImageButton = findViewById(R.id.btn_choose_photo);

        sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        // Load the saved image URI if available
        loadSavedImage();

        // Set button click listener to open the gallery
        pickImageButton.setOnClickListener(v -> openGallery());

    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();

            if (selectedImageUri != null) {
                // Display the image using Glide
                Glide.with(this).load(selectedImageUri).into(imageView);

                // Save the URI in SharedPreferences
                saveImageUri(selectedImageUri.toString());
            }
        }
    }

    private void saveImageUri(String uri) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(IMAGE_URI_KEY, uri);
        editor.apply();
        Toast.makeText(this, "Image saved successfully!", Toast.LENGTH_SHORT).show();
    }

    private void loadSavedImage() {
        String savedImageUri = sharedPreferences.getString(IMAGE_URI_KEY, null);

        if (savedImageUri != null) {
            Uri imageUri = Uri.parse(savedImageUri);
            // Display the image using Glide
            Glide.with(this).load(imageUri).into(imageView);
        }
    }
}
