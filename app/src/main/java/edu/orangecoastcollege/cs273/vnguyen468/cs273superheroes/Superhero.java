package edu.orangecoastcollege.cs273.vnguyen468.cs273superheroes;

/**
 * Created by Nghir on 10/18/2017.
 */

/**
 * Superhero class that stores name, power, and thing.
 * File name is also stored to load the image
 */
public class Superhero {

    private String mName;
    private String mPower;
    private String mThing;
    private String mFileName;

    /**
     * Overloaded constructor for superhero class
     * @param name hero's name
     * @param power hero's power
     * @param thing hero's one thing
     * @param fileName hero's filename for image
     */
    public Superhero(String name, String power, String thing, String fileName) {
        mName = name;
        mPower = power;
        mThing = thing;
        mFileName = fileName + ".png";
    }

    /**
     * gets the name
     * @return the hero's name
     */
    public String getName() {
        return mName;
    }

    /**
     * gets the hero's power
     * @return the hero's power
     */
    public String getPower() {
        return mPower;
    }

    /**
     * gets the hero's one thing
     * @return the hero's one thing
     */
    public String getThing() {
        return mThing;
    }

    /**
     * gets the hero's file name to load
     * @return filename to load
     */
    public String getFileName() {
        return mFileName;
    }
}