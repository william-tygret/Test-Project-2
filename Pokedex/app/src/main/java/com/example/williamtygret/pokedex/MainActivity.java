package com.example.williamtygret.pokedex;

import android.app.SearchManager;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.LruCache;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.sql.Array;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    TextView mTextView;
    ListView mListView;
    CursorAdapter mCursorAdapter;
    PokemonDatabaseHelper mPokemonDatabaseHelper;
    Button mFavoritesButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ImageView pokedexImg = (ImageView)findViewById(R.id.imageView2);
        pokedexImg.setImageResource(R.drawable.pokedex);


        mTextView = (TextView)findViewById(R.id.caughtPokemonTextView);
        mTextView.setText("Welcome To the Wonderful World of Pokemon!");
        mListView = (ListView)findViewById(R.id.pokemonListView);
        mFavoritesButton = (Button)findViewById(R.id.favoritesButton);

        Cursor cursor = null;//PokemonDatabaseHelper.getInstance(this).getPokemonList();
        mCursorAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, cursor, new String[]{PokemonDatabaseHelper.COL_POKEMON_NAME}, new int[]{android.R.id.text1,0});
        mListView.setAdapter(mCursorAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor = mCursorAdapter.getCursor();
                Intent intent = new Intent(MainActivity.this, PokemonActivity.class);
                cursor.moveToPosition(position);
                int theID = cursor.getInt(cursor.getColumnIndex(PokemonDatabaseHelper.COL_ID));//ShoppingSQLiteOpenHelper.getInstance(MainActivity.this).getIdByName(itemName);
                intent.putExtra("id", theID);
                startActivity(intent);
            }
        });

        mFavoritesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PokemonDatabaseHelper dbh = PokemonDatabaseHelper.getInstance(MainActivity.this);
                Cursor curse = dbh.getFavoritesList();
                mCursorAdapter.swapCursor(curse);
                mTextView.setText("My Pokemon:");

            }
        });

        PokemonDatabaseHelper.getInstance(MainActivity.this).insertAllPokemon();



        handleIntent(getIntent());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_options_menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        return true;
    }



    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())){
            // where you do the actual database search
            String query = intent.getStringExtra(SearchManager.QUERY);

            // SELECT * FROM awesometable WHERE name = ? (or WHERE name LIKE ?)
            Cursor cursor = mPokemonDatabaseHelper.getInstance(this).searchPokemon(query);

            mCursorAdapter.swapCursor(cursor);
        }
    }







}
