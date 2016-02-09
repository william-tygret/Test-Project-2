package com.example.williamtygret.pokedex;

/**
 * Created by williamtygret on 2/5/16.
 */
public class Pokemon {
    public int id;
    public String name;
    public String description;
    public String hp;
    public String type;
    public int favorite;

    public Pokemon(int id, String name, String description, String hp, String type, int favorite){
        this.id = id;
        this.name = name;
        this.description = description;
        this.hp = hp;
        this.type = type;
        this.favorite = favorite;

    }
}
