package edu.orangecoastcollege.cs273.vnguyen468.cs273superheroes;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import edu.orangecoastcollege.cs273.vnguyen468.cs273superheroes.Superhero;

/**
 * Class loads Country data from a formatted JSON (JavaScript Object Notation) file.
 * Populates data model (Country) with data.
 */
public class JSONLoader {

    /**
     * Loads JSON data from a file in the assets directory.
     *
     * @param context The activity from which the data is loaded.
     * @throws IOException If there is an error reading from the JSON file.
     */
    public static List<Superhero> loadJSONFromAsset(Context context) throws IOException {
        List<Superhero> allHeroList = new ArrayList<>();
        String json = null;
        InputStream is = context.getAssets().open("cs273superheroes.json");
        int size = is.available();
        byte[] buffer = new byte[size];
        is.read(buffer);
        is.close();
        json = new String(buffer, "UTF-8");

        try {
            JSONObject jsonRootObject = new JSONObject(json);
            JSONArray allHeroesJSON = jsonRootObject.getJSONArray("CS273Superheroes");

            // TODO: Loop through all the countries in the JSON data, create a Country
            int count = allHeroesJSON.length();
            String heroName;
            String heroPower;
            String heroThing;
            String heroFileName;

            for (int i = 0; i < count; i++)
            {
                JSONObject hero = allHeroesJSON.getJSONObject(i);
                heroName = hero.getString("Name");
                heroPower = hero.getString("Superpower");
                heroThing = hero.getString("OneThing");
                heroFileName = hero.getString("Username");
                Superhero newHero = new Superhero(heroName, heroPower,heroThing,heroFileName);
                allHeroList.add(newHero);

            }
            // TODO: object for each and add the object to the allCountriesList


        } catch (JSONException e) {
            Log.e("Hero","error adding Json",e);
        }

        return allHeroList;
    }
}