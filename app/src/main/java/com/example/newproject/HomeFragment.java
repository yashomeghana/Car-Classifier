package com.example.newproject;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import java.io.ByteArrayOutputStream;

public class HomeFragment extends Fragment {

    private static final int GALLERY_REQUEST_CODE = 1;
    private static final int CAMERA_REQUEST_CODE = 2;
    private static final int CAMERA_PERMISSION_CODE = 100;

    private Button btnUpload, btnCamera;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        btnUpload = view.findViewById(R.id.btnUpload);
        btnCamera = view.findViewById(R.id.btnCamera);

        btnUpload.setOnClickListener(v -> openGallery());
        btnCamera.setOnClickListener(v -> openCamera());

        return view;
    }

    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE);
    }

    private void openCamera() {
        // Check camera permission
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            // Request permission
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
        } else {
            // Open the camera
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == getActivity().RESULT_OK) {
            if (requestCode == GALLERY_REQUEST_CODE && data != null) {
                Uri selectedImageUri = data.getData();
                if (selectedImageUri != null) {
                    Intent intent = new Intent(getActivity(), DisplayImage.class);
                    intent.putExtra("imageUri", selectedImageUri.toString());
                    startActivity(intent);
                }
            } else if (requestCode == CAMERA_REQUEST_CODE && data != null) {
                Bitmap photo = (Bitmap) data.getExtras().get("data");

                // Convert Bitmap to URI
                Uri imageUri = getImageUriFromBitmap(photo);
                Intent intent = new Intent(getActivity(), DisplayImage.class);
                intent.putExtra("imageUri", imageUri.toString());
                startActivity(intent);
            }
        }
    }

    private Uri getImageUriFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(requireContext().getContentResolver(), bitmap, "CapturedImage", null);
        return Uri.parse(path);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera();
            } else {
                // Permission denied
            }
        }
    }
}
