package com.example.SpinIt;

import android.os.Parcel;
import android.os.Parcelable;

import java.lang.reflect.Array;
import java.util.ArrayList;
public class PrefList implements Parcelable {
    /**************************************************/
//This is all for Parcelable stuff

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeStringList(dietaryPref);
        out.writeStringList(foodPref);
    }

    public static final Parcelable.Creator<PrefList> CREATOR = new Parcelable.Creator<PrefList>() {
        public PrefList createFromParcel(Parcel in) {
            return new PrefList(in);
        }

        public PrefList[] newArray(int size) {
            return new PrefList[size];
        }
    };

    private PrefList(Parcel in) {
        in.readStringList(dietaryPref);
        in.readStringList(foodPref);
    }
    /************************************************/
    private ArrayList<String> dietaryPref = new ArrayList<>();
    private ArrayList<String> foodPref = new ArrayList<>();
    PrefList()
    {
        this.dietaryPref = new ArrayList<>();
        this.foodPref = new ArrayList<>();
    }

    public void setDietaryPref(ArrayList<String> list)
    {
        this.dietaryPref = list;
    }
    public void setFoodPref(ArrayList<String> list){this.foodPref = list;};

    public ArrayList<String> getDietaryPref()
    {
        return this.dietaryPref;
    }
    public ArrayList<String> getFoodPref() { return this.foodPref; }
}

