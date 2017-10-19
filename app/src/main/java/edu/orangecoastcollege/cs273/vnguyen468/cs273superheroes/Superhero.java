package edu.orangecoastcollege.cs273.vnguyen468.cs273superheroes;

/**
 * Created by Nghir on 10/18/2017.
 */

public class Superhero {

    private String mName;
    private String mPower;
    private String mThing;
    private String mFileName;

    public Superhero(String name, String power, String thing, String fileName) {
        mName = name;
        mPower = power;
        mThing = thing;
        mFileName = fileName + ".png";
    }

    public String getName() {
        return mName;
    }

    public String getPower() {
        return mPower;
    }

    public String getThing() {
        return mThing;
    }

    public String getFileName() {
        return mFileName;
    }
}