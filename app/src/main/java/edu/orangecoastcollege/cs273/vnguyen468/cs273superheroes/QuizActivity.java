package edu.orangecoastcollege.cs273.vnguyen468.cs273superheroes;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuizActivity extends AppCompatActivity {

    private static final int HEROES_IN_QUIZ = 10;

    private Button[] mButtons = new Button[4];
    private List<Superhero> mAllSuperheroesList;
    private List<Superhero> mQuizSuperheroesList;
    private int mTotalGuesses;
    private int mCorrectGuesses;
    private Superhero mCorrectHero;
    private SecureRandom rng;
    private Handler mHandler;

    private String mQuizType;

    private TextView mQuestionNumberTextView;
    private ImageView mSuperheroImageView;
    private TextView mQuizTypeTextView;
    private TextView mAnswerTextView;

    private static final String QUIZ_TYPE ="pref_quiz_type";

    /**
     * This is called when the app starts up
     * @param savedInstanceState the current loaded instance
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        //TODO: preference listener
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        preferences.registerOnSharedPreferenceChangeListener(mPreferenceChangeListener);

        mQuizSuperheroesList = new ArrayList<>(HEROES_IN_QUIZ);
        rng = new SecureRandom();
        mHandler = new Handler();

        //connect View to Control
        mQuestionNumberTextView = (TextView) findViewById(R.id.questionNumberTextVuew);
        mSuperheroImageView = (ImageView) findViewById(R.id.superheroImageView);
        mQuizTypeTextView = (TextView) findViewById(R.id.quizTypeTextView);
        mAnswerTextView = (TextView) findViewById(R.id.answerTextView);

        mButtons[0] = (Button) findViewById(R.id.button);
        mButtons[1] = (Button) findViewById(R.id.button2);
        mButtons[2] = (Button) findViewById(R.id.button3);
        mButtons[3] = (Button) findViewById(R.id.button4);

        mQuestionNumberTextView.setText(getString(R.string.question,1,HEROES_IN_QUIZ));

        try
        {
            mAllSuperheroesList = JSONLoader.loadJSONFromAsset(this);
        }
        catch (IOException e)
        {
            Log.e("Quiz Activity", "Error printing JSON File",e);
        }

        mQuizType = preferences.getString(QUIZ_TYPE, "All");

        resetQuiz();
    }

    /**
     * Resets the quiz. Sets everything to 0 and start with the first question. Calls loadNextQuestion()
     */
    public void resetQuiz()
    {
        mCorrectGuesses = 0;
        mTotalGuesses = 0;
        mQuizSuperheroesList.clear();

        Superhero newHero;
        int size = mAllSuperheroesList.size();
        int randomPosition;
        while (mQuizSuperheroesList.size() < HEROES_IN_QUIZ) {
            randomPosition = rng.nextInt(size);
            newHero = mAllSuperheroesList.get(randomPosition);
            if (!mQuizSuperheroesList.contains(newHero))
            {
                mQuizSuperheroesList.add(newHero);
            }
        }
        loadNextQuestion();
    }

    private void loadNextQuestion()
    {
        mCorrectHero = mQuizSuperheroesList.remove(0);
        mAnswerTextView.setText("");

        int questionNumber = HEROES_IN_QUIZ - mQuizSuperheroesList.size();

        mQuestionNumberTextView.setText(getString(R.string.question,questionNumber,HEROES_IN_QUIZ));

        switch(mQuizType)
        {
            case "Name":
                mQuizTypeTextView.setText(getString(R.string.question_superhero));
                break;
            case "Superpower":
                mQuizTypeTextView.setText(getString(R.string.question_superpower));
                break;
            case "Thing":
                mQuizTypeTextView.setText(getString(R.string.question_thing));
                break;
        }

        AssetManager am = getAssets();
        try
        {
            InputStream stream = am.open(mCorrectHero.getFileName());
            Drawable image = Drawable.createFromStream(stream, mCorrectHero.getFileName());
            mSuperheroImageView.setImageDrawable(image);
        }
        catch (IOException e)
        {
            Log.e("Quiz Activity","Error loading the image", e);
        }

        do {
            Collections.shuffle(mAllSuperheroesList);
        }
        while (mAllSuperheroesList.subList(0, 4).contains(mCorrectHero));

        switch(mQuizType)
        {
            case "Name":
                for (int i = 0; i < 4; i++)
                {
                    mButtons[i].setEnabled(true);
                    mButtons[i].setText(mAllSuperheroesList.get(i).getName());
                }

                mButtons[rng.nextInt(4)].setText(mCorrectHero.getName());
                break;
            case "Superpower":
                for (int i = 0; i < 4; i++)
                {
                    mButtons[i].setEnabled(true);
                    mButtons[i].setText(mAllSuperheroesList.get(i).getPower());
                }

                mButtons[rng.nextInt(4)].setText(mCorrectHero.getPower());
                break;
            case "Thing":
                for (int i = 0; i < 4; i++)
                {
                    mButtons[i].setEnabled(true);
                    mButtons[i].setText(mAllSuperheroesList.get(i).getThing());
                }

                mButtons[rng.nextInt(4)].setText(mCorrectHero.getThing());
                break;
        }

    }

    /**
     * Infates the option menu
     * @param menu the menu to be displayed
     * @return the menu is visible
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * Check if the options have changed
     * @param item the option that is changed
     * @return the item changed, and reload the quiz
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Make intent going to settings activity.
        Intent settingsIntent = new Intent(this, SettingsActivity.class);
        startActivity(settingsIntent);
        return super.onOptionsItemSelected(item);
    }

    SharedPreferences.OnSharedPreferenceChangeListener mPreferenceChangeListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            //Lets figure out what key changed.

            switch (key)
            {
                case QUIZ_TYPE:
                    mQuizType = sharedPreferences.getString(QUIZ_TYPE, "NAME");

                    resetQuiz();
                    break;
            }
            Toast.makeText(QuizActivity.this,getString(R.string.restarting_quiz),Toast.LENGTH_SHORT).show();
        }
    };

    /**
     * Make a guess, determines if the answer is correct, and changes button accordingly. If quiz is done
     * dispplay score.
     * @param v the clicked button/answer
     */
    public void makeGuess(View v) {
        // TODO: Downcast the View v into a Button (since it's one of the 4 buttons)
        Button clickedButton = (Button) v;
        // TODO: Get the country's name from the text of the button
        String guess = clickedButton.getText().toString();
        // TODO: If the guess matches the correct country's name, increment the number of correct guesses,
        mTotalGuesses++;
        if (guess.equals(mCorrectHero.getName()) || guess.equals(mCorrectHero.getThing()) || guess.equals(mCorrectHero.getPower())) {
            //Disable All buttons.
            for (Button b : mButtons)
                b.setEnabled(false);

            mCorrectGuesses++;
            mAnswerTextView.setText(mCorrectHero.getName());
            mAnswerTextView.setTextColor(ContextCompat.getColor(this, R.color.correct_answer));

            if (mCorrectGuesses < HEROES_IN_QUIZ) {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadNextQuestion();
                    }
                }, 2000);
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                //builder.setMessage(getString(R.string.results,mTotalGuesses, (double) mCorrectGuesses / mTotalGuesses));
                builder.setMessage(getString(R.string.results, mTotalGuesses, (double) mCorrectGuesses / mTotalGuesses));
                builder.setPositiveButton(R.string.reset_quiz, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        resetQuiz();
                    }
                });
                builder.setCancelable(false);
                builder.create();
                builder.show();
            }
        } else {
            mAnswerTextView.setText(getString(R.string.incorrect_answer));
            mAnswerTextView.setTextColor(ContextCompat.getColor(this, R.color.incorrect_answer));
            clickedButton.setEnabled(false);
        }
    }

}