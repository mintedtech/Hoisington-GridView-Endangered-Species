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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    // array of images - each image is automatically assigned an ID, so we use type int below
    private final int[] ANIMAL_DRAWABLES =
            {R.drawable.elephant, R.drawable.gorilla, R.drawable.leopard,
                    R.drawable.panda, R.drawable.polar, R.drawable.rhino};

    // parallel array - name of each image corresponding to image in array above
    private final String[] ANIMAL_NAMES = {"Elephant", "Gorilla", "Leopard",
            "Panda", "Polar Bear", "Rhino"};

    private ShapeableImageView largeImage; // imgLarge (bottom half of the screen)
    private ShapeAppearanceModel roundedCornersShapeAppearanceModel;

    private final int NO_IMAGE_SELECTED = -99;
    private int currentSelectedAnimal = NO_IMAGE_SELECTED;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupImageCornerShape();
        setupImageView();
        setupGridView();
    }

    private void setupImageCornerShape() {
        float cornerRadius = getResources().getDimension(R.dimen.standard_margin);
        roundedCornersShapeAppearanceModel=
                ShapeAppearanceModel.builder().build().withCornerSize(cornerRadius);
    }

    private void setupImageView() {
        largeImage = findViewById(R.id.imgLarge);
        largeImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
        largeImage.setShapeAppearanceModel(roundedCornersShapeAppearanceModel);
    }

    private void setupGridView() {
        GridView grid = findViewById(R.id.gridView);
        grid.setAdapter(new ImageAdapter());
        grid.setOnItemClickListener((parent, view, position, id) -> handleItemClick(view, position));
    }

    private void handleItemClick(View view, int position) {
        showMessageOfCurrentAnimalAndPosition(view, position);
        setLargeImageToAnimalImageNumber(position);
    }

    private void showMessageOfCurrentAnimalAndPosition(View view, int position) {
        Snackbar.make(view,"Selected: " + (position + 1) + " - " + ANIMAL_NAMES[position],
                        Snackbar.LENGTH_LONG)
                .show();
    }

    private void setLargeImageToAnimalImageNumber(int position) {
        largeImage.setImageResource(ANIMAL_DRAWABLES[position]);
        currentSelectedAnimal = position;
    }

    // This is an inner-class, as in starting before we close the outer class (MainActivity)
    private class ImageAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return ANIMAL_DRAWABLES.length; // this equals six, per her design -- number of animals in Grid
        }

        @Override
        public Object getItem(int position) {
            return ANIMAL_NAMES[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // If this method was called to fill an empty portion of the grid then create new...
            // but if this is updating due to scrolling, then use the existing ImageView in the grid
            ShapeableImageView gridImage = convertView == null ?
                    new ShapeableImageView(MainActivity.this) :
                    (ShapeableImageView) convertView;

            // Update the image in this ImageView to the currently clicked animal
            gridImage.setImageResource(ANIMAL_DRAWABLES[position]);
            gridImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
            gridImage.setShapeAppearanceModel(roundedCornersShapeAppearanceModel);

            return gridImage;
        }
    }   // end of adapter class

    // Back in MainActivity,these two methods handle saving/restoring the current image
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("CURRENT", currentSelectedAnimal);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        int position = savedInstanceState.getInt("CURRENT");
        if (position != NO_IMAGE_SELECTED) {
            setLargeImageToAnimalImageNumber(position);
        }
    }

}   // end of outer class (MainActivity)
