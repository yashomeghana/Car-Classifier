package com.example.newproject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.newproject.ml.CarClassificationModelMnv2;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.image.TensorImage;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public class DisplayImage extends AppCompatActivity {

    private ImageView imageView;
    private TextView itemName;
    private Button predict;
    private Bitmap img;

    // Array containing class names, assuming there are 7 classes
    private String[] carClasses = {"Audi", "Hyundai", "Mahindra Scorpio", "Rolls Royce", "Swift", "Tata Safari", "Toyota Innova"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_image);

        imageView = findViewById(R.id.imageView);
        itemName = findViewById(R.id.itemName);
        predict = findViewById(R.id.button);

        // Get the image URI from the intent
        Intent intent = getIntent();
        String imageUriString = intent.getStringExtra("imageUri");
        Uri imageUri = Uri.parse(imageUriString);

        // Display the selected image
        try {
            InputStream imageStream = getContentResolver().openInputStream(imageUri);
            Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
            img = Bitmap.createScaledBitmap(selectedImage, 224, 224, true); // Resize image for model
            imageView.setImageBitmap(selectedImage);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        predict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    CarClassificationModelMnv2 model = CarClassificationModelMnv2.newInstance(getApplicationContext());

                    // Prepare input for the model
                    TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 224, 224, 3}, DataType.FLOAT32);
                    TensorImage tensorImage = new TensorImage(DataType.FLOAT32);
                    tensorImage.load(img);
                    ByteBuffer byteBuffer = tensorImage.getBuffer();
                    inputFeature0.loadBuffer(byteBuffer);

                    // Run model inference and get results
                    CarClassificationModelMnv2.Outputs outputs = model.process(inputFeature0);
                    TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();

                    // Get the array of probabilities
                    float[] probabilities = outputFeature0.getFloatArray();

                    // Find the index of the highest probability
                    int maxIndex = 0;
                    for (int i = 1; i < probabilities.length; i++) {
                        if (probabilities[i] > probabilities[maxIndex]) {
                            maxIndex = i;
                        }
                    }

                    // Set the predicted car class as the item name
                    itemName.setText("Car Name: " + carClasses[maxIndex]);

                    // Release model resources if no longer used
                    model.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
