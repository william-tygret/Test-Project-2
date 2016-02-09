package com.example.williamtygret.pokedex;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.LruCache;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.io.InputStream;
import java.net.URL;

public class PokemonActivity extends AppCompatActivity {
    private int mId;



    ImageLoader mImageLoader;
    RequestQueue mRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon);

        ImageView thumbnail = (ImageView)findViewById(R.id.detailImageView);
        String fullResource = "http://assets.pokemon.com/assets/cms2/img/pokedex/full/001.png";
        Picasso.with(PokemonActivity.this).load(fullResource).error(R.drawable.pokelarge)
                .placeholder(R.drawable.pokelarge)
                .into(thumbnail);


//        mRequestQueue = Volley.newRequestQueue(PokemonActivity.this);
//        mImageLoader = new ImageLoader(mRequestQueue, new ImageLoader.ImageCache() {
//            private final LruCache<String, Bitmap> mCache = new LruCache<String, Bitmap>(10);
//            public void putBitmap(String url, Bitmap bitmap) {
//                mCache.put(url, bitmap);
//            }
//            public Bitmap getBitmap(String url) {
//                return mCache.get(url);
//            }
//        });
//
//        NetworkImageView pokeImage = (NetworkImageView)findViewById(R.id.networkImageView);
//        pokeImage.setImageUrl("http://assets.pokemon.com/assets/cms2/img/pokedex/full/001.png",VolleySingleton.getInstance(PokemonActivity.this).getImageLoader());

        int id = getIntent().getIntExtra("id",-1);
        mId=id;
        if(id >= 0){
            PokemonDatabaseHelper dbh = PokemonDatabaseHelper.getInstance(PokemonActivity.this);
            String name = dbh.getPokemonName(id);
            TextView textView = (TextView)findViewById(R.id.pokemonName);
            textView.setText(name);
        }
        if(id >= 0){
            PokemonDatabaseHelper dbh = PokemonDatabaseHelper.getInstance(PokemonActivity.this);
            String description = dbh.getPokemonDescription(id);
            TextView textView = (TextView)findViewById(R.id.pokemonDescription);
            textView.setText(description);
        }
        if(id >= 0){
            PokemonDatabaseHelper dbh = PokemonDatabaseHelper.getInstance(PokemonActivity.this);
            String hp = dbh.getPokemonHp(id);
            TextView textView = (TextView)findViewById(R.id.hpText);
            textView.setText("HP:"+hp);
        }
        if(id >= 0){
            PokemonDatabaseHelper dbh = PokemonDatabaseHelper.getInstance(PokemonActivity.this);
            String type = dbh.getPokemonType(id);
            TextView textView = (TextView)findViewById(R.id.pokemonType);
            textView.setText(type+" Type");
            if(type == "Fire"){
                textView.setTextColor(Color.RED);
            }
        }
//        if(id>=0){
//            PokemonDatabaseHelper dbh = PokemonDatabaseHelper.getInstance(PokemonActivity.this);
//            String img = dbh.getPokemonImg(id);
//            ImageView imageView = (ImageView)findViewById(R.id.pokemonImage);
//            imageView.setImageResource(R.drawable.one);
//            Log.d("imgnumber","value is:"+R.drawable.one);
//        }

        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Pokemon was Caught!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                PokemonDatabaseHelper dbh = PokemonDatabaseHelper.getInstance(PokemonActivity.this);
                String idPoke = Integer.toString(PokemonActivity.this.mId);
                dbh.update(idPoke,1);

            }
        });
    }

}
