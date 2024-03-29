package com.randomappsinc.carcassonnetracker;

import android.content.Context;

import androidx.annotation.NonNull;

/**
 * Created by alexanderchiou on 4/16/16.
 */
public class Tile implements Comparable<Tile>{
    private String name;
    private int resourceId;
    private int initialAmount;
    private int numRemaining;
    private int numInPlay;

    public Tile(String name, int resourceId, int initialAmount) {
        this.name = name;
        this.resourceId = resourceId;
        this.initialAmount = initialAmount;
        this.numRemaining = initialAmount;
    }

    public Tile(Tile tile) {
        this.name = tile.getName();
        this.resourceId = tile.getResourceId();
        this.initialAmount = tile.getInitialAmount();
        this.numRemaining = this.initialAmount;
    }

    public String getName() {
        return name;
    }

    public int getResourceId() {
        return resourceId;
    }

    public int getInitialAmount() {
        return initialAmount;
    }

    public int getNumRemaining() {
        return numRemaining;
    }

    public void decreaseNumRemaining() {
        if (numRemaining > 0) {
            numRemaining--;
            numInPlay++;
        }
    }

    public void increaseNumRemaining() {
        if (numRemaining < initialAmount) {
            numRemaining++;
            numInPlay--;
        }
    }

    public String getInfoBlurb(Context context) {
        return "<p><b>" + context.getString(R.string.name) + "</b>" + name + "</p>"
                + "<p><b>" + context.getString(R.string.starting_amount) + "</b>" + String.valueOf(initialAmount) + "</p>"
                + "<p><b>" + context.getString(R.string.remaining) + "</b>" + String.valueOf(numRemaining) + "</p>"
                + "<b>" + context.getString(R.string.in_play) + "</b>" + String.valueOf(numInPlay);
    }

    @Override
    public int compareTo(@NonNull Tile another) {
        return another.getNumRemaining() - this.numRemaining;
    }
}
