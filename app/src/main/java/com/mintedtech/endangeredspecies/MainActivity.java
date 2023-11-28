package com.mintedtech.endangeredspecies;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    // array of images - each image is automatically assigned an ID, so we use type int below
    int[] animals = {R.drawable.elephant, R.drawable.gorilla, R.drawable.leopard,
            R.drawable.panda, R.drawable.polar, R.drawable.rhino};

    String[] animalNames = {"Elephant", "Gorilla", "Leopard",
            "Panda", "Polar Bear", "Rhino"};

    ImageView pic; // imgLarge (bottom half of the screen)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GridView grid = findViewById(R.id.gridView);
        pic = findViewById(R.id.imgLarge);

        grid.setAdapter(new ImageAdapter());
        grid.setOnItemClickListener((parent, view, position, id) -> handleItemClick(view, position));

    }

    private void handleItemClick(View view, int position) {
        // Tell the user which image number they clicked on in the GridView
        Snackbar.make(view,
                        "Selected Species: " + (position + 1) + " - " + animalNames[position],
                        Snackbar.LENGTH_LONG)
                .show();
        // Set the bottom ImageView's image to the image they clicked on in the GridView
        pic.setImageResource(animals[position]);
    }

    private class ImageAdapter extends BaseAdapter {
        float cornerRadius;
        ShapeAppearanceModel roundedCornersShapeAppearanceModel;

        public ImageAdapter() {
            cornerRadius = getResources().getDimension(R.dimen.standard_margin);
            roundedCornersShapeAppearanceModel =
                    ShapeAppearanceModel.builder().build().withCornerSize(cornerRadius);
        }

        @Override
        public int getCount() {
            return animals.length; // this equals six, per her design -- number of animals in Grid
        }

        @Override
        public Object getItem(int position) {
            return animalNames[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // If this method was called to fill an empty portion of the grid then create new...
            // but if this is updating due to scrolling, then use the existing ImageView in the grid
            ShapeableImageView pic = convertView == null ?
                    new ShapeableImageView(MainActivity.this) :
                    (ShapeableImageView) convertView;

            // Update the image in this ImageView to the currently clicked animal
            pic.setImageResource(animals[position]);
            pic.setScaleType(ImageView.ScaleType.CENTER_CROP);
            pic.setShapeAppearanceModel(roundedCornersShapeAppearanceModel);

            return pic;
        }
    }
}
