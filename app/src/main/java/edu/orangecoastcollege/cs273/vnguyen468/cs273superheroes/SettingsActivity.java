package edu.orangecoastcollege.cs273.vnguyen468.cs273superheroes;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import edu.orangecoastcollege.cs273.vnguyen468.cs273superheroes.R;

/**
 * displays the option menu for user to change quiz type
 */
public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Set the toolbar to the one in activity settings

        //Enable Home Button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //Use our fragment to fill out the content.
        getFragmentManager()
                .beginTransaction()
                .replace(android.R.id.content, new SettingsActivityFragment())
                .commit();

    }

    public static class SettingsActivityFragment extends PreferenceFragment {
        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);
        }
    }

}
