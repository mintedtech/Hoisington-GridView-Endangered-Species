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
    private final int[] ANIMAL_DRAWABLES =
            {R.drawable.elephant, R.drawable.gorilla, R.drawable.leopard,
            R.drawable.panda, R.drawable.polar, R.drawable.rhino};

    // parallel array - name of each image corresponding to image in array above
    private final String[] ANIMAL_NAMES = {"Elephant", "Gorilla", "Leopard",
            "Panda", "Polar Bear", "Rhino"};

    private ImageView largeImage; // imgLarge (bottom half of the screen)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        largeImage = findViewById(R.id.imgLarge);
        setupGridView();
    }

    private void setupGridView() {
        GridView grid = findViewById(R.id.gridView);
        grid.setAdapter(new ImageAdapter());
        grid.setOnItemClickListener((parent, view, position, id) -> handleItemClick(view, position));
    }

    private void handleItemClick(View view, int position) {
        // Tell the user which image number they clicked on in the GridView
        Snackbar.make(view,
                        "Selected Species: " + (position + 1) + " - " + ANIMAL_NAMES[position],
                        Snackbar.LENGTH_LONG)
                .show();
        // Set the bottom ImageView's image to the image they clicked on in the GridView
        largeImage.setImageResource(ANIMAL_DRAWABLES[position]);
    }

    private class ImageAdapter extends BaseAdapter {
        private final ShapeAppearanceModel ROUNDED_CORNERS_SHAPE_APPEARANCE_MODEL;

        public ImageAdapter() {
            float cornerRadius = getResources().getDimension(R.dimen.standard_margin);
            ROUNDED_CORNERS_SHAPE_APPEARANCE_MODEL =
                    ShapeAppearanceModel.builder().build().withCornerSize(cornerRadius);
        }

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
            gridImage.setShapeAppearanceModel(ROUNDED_CORNERS_SHAPE_APPEARANCE_MODEL);

            return gridImage;
        }
    }
}
