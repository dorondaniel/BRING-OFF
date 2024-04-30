package com.neave.mobilenet.vision;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.neave.mobilenet.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoadActivity2 extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    ImageView imageView;

    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.offloading_image);

        // Initialize ApiService
        apiService = ApiClient.getClient().create(ApiService.class);

        // Call the method to select and upload image
        selectImage();
    }

    private void selectImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            if (selectedImageUri != null) {
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
                    imageView = findViewById(R.id.img_iv);
                    imageView.setImageURI(selectedImageUri);
                    File file = createImageFile(bitmap);
                    uploadImage(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private File createImageFile(Bitmap bitmap) throws IOException {
        File file = new File(getCacheDir(), "temp_image.jpg");
        OutputStream os = new FileOutputStream(file);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
        os.flush();
        os.close();

        return file;

    }

    private void uploadImage(File file) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part imagePart = MultipartBody.Part.createFormData("image", file.getName(), requestBody);
        Call<UploadResponse> call = apiService.uploadImage(imagePart);
        call.enqueue(new Callback<UploadResponse>() {
            @Override
            public void onResponse(@NonNull Call<UploadResponse> call, @NonNull Response<UploadResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    UploadResponse uploadResponse = response.body();
                    // Handle the upload response here
                    Log.d("Upload Response", "Response: " + uploadResponse.toString());
                    // Process the prediction results
                    String[] predictions = uploadResponse.getPredictions();
                    for (String prediction : predictions) {
                        Log.d("Prediction", prediction);
                        Toast.makeText(LoadActivity2.this,prediction,Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoadActivity2.this, "Upload failed!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<UploadResponse> call, @NonNull Throwable t) {
                Log.d("Ermsg",t.getMessage());
                Toast.makeText(LoadActivity2.this, "Error uploading image: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
