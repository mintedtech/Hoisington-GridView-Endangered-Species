package com.example.endangeredspecies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.google.android.material.snackbar.Snackbar;


public class MainActivity extends AppCompatActivity {

    // array of images - each image is automatically assigned an ID, so we use type int below
    int[] animals = {R.drawable.eagle, R.drawable.elephant, R.drawable.gorilla,
            R.drawable.panda, R.drawable.panther, R.drawable.polar};

    String [] animalNames = {"Eagle", "Elephant", "Gorilla",
            "Panda", "Panther", "Polar Bear"};

    ImageView pic; // imgLarge (bottom half of the screen)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GridView grid = findViewById(R.id.gridView);
        pic = findViewById(R.id.imgLarge);

        grid.setAdapter(new ImageAdapter(this));
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Tell the user which image number they clicked on in the GridView
                Snackbar.make(view,
                        "Selected Species: " + (position+1) + ": " + animalNames[position],
                        Snackbar.LENGTH_LONG)
                        .show();
                // Set the bottom ImageView's image to the image they clicked on in the GridView
                pic.setImageResource(animals[position]);
            }
        });

    }

    private class ImageAdapter extends BaseAdapter {

        private Context context;

        public ImageAdapter(Context context) {
            this.context = context;
        }

        @Override
        public int getCount() {
            return animals.length; // this equals six, per her design -- number of animals in Grid
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView pic = new ImageView(context);
            pic.setImageResource(animals[position]);
            pic.setScaleType(ImageView.ScaleType.FIT_CENTER);
            pic.setLayoutParams(new GridView.LayoutParams(250,250));

            return pic;
        }
    }
}
