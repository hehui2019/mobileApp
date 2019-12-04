package com.example.SpinIt;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class SpunPlaces implements Parcelable {
    private ArrayList<Place>listOfSpunPlaces = new ArrayList<>();
    private ArrayList<Place>listOfCheckPlaces = new ArrayList<>();

    /**************************************************/
//This is all for Parcelable stuff

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeTypedList(listOfCheckPlaces);
        out.writeTypedList(listOfSpunPlaces);
    }

    public static final Parcelable.Creator<SpunPlaces> CREATOR = new Parcelable.Creator<SpunPlaces>() {
        public SpunPlaces createFromParcel(Parcel in) {
            return new SpunPlaces(in);
        }

        public SpunPlaces[] newArray(int size) {
            return new SpunPlaces[size];
        }
    };
    private SpunPlaces(Parcel in) {
        in.readTypedList(listOfCheckPlaces,Place.CREATOR);
        in.readTypedList(listOfSpunPlaces,Place.CREATOR);
    }
/************************************************/


    /**
     * This is the constructor for users that already used the app prior
     * @param listOfCheckPlaces This is the list that a person has already spun AND Checked out
     * @param listOfSpunPlaces This is the list that a person has already spun
     */
    SpunPlaces(ArrayList<Place>listOfCheckPlaces, ArrayList<Place>listOfSpunPlaces){
        this.listOfCheckPlaces = listOfCheckPlaces;
        this.listOfSpunPlaces = listOfSpunPlaces;
    }

    /**
     * This is the constructor for users that are new and have an empty constructor
     */
    public SpunPlaces(){
        this.listOfCheckPlaces = new ArrayList<Place>();
        this.listOfSpunPlaces = new ArrayList<Place>();
    }
    //SpunPlaces()

    /**
     * <p>
     *    This is used if the user has spun a place and for some reason would
     *    like to remove it from either the Spun list or the Check list
     * </p>
     * @param currentPlace This is the place that wants to be removed
     * @param spunOrCheck "spun" if the need to remove from the Spun list, "check" if the need to remove from the Check list
     * @return True if successful, False otherwise
     */
    public boolean remove(Place currentPlace, String spunOrCheck){
        //This is called in profile if someone want's to clean up their profile of either lists
        boolean checker;
        if(spunOrCheck.equals("spun")){
            //if true remove it from the spun places
            checker = this.listOfSpunPlaces.remove(currentPlace);
        }
        else if(spunOrCheck.equals("check")){
            //else remove if from the check places
            checker = this.listOfCheckPlaces.remove(currentPlace);
        }
        else{
            System.out.println("Invalid String, check SpunPlaces.java");
            return false;
        }
        if(!checker){
            System.out.println("Error with removing a place, check SpunPlaces.java");
            return false;
        }
        //****A database call will be needed at the end of this function****
        return true;
    }

    /**
     * This is used if the user has spun a place and would like to check into the place
     * @param currentPlace This is the place that wants to be checked in, this place must be in the spun places
     * @return True if successful, False otherwise
     */
    public boolean checkIn(Place currentPlace){
        //This is called in profile when we want to check in.
        //This will check if a currentPlace is in listOfSpunPlaces,
        //remove it, then push it into listOfCheckPlaces
        boolean checker;
        checker = this.listOfSpunPlaces.remove(currentPlace);
        if(!checker){
            System.out.println("Place doesn't exist in checked in, check SpunPlaces.java");
            return false;

        }
        checker = this.listOfCheckPlaces.add(currentPlace);
        if(!checker){
            System.out.println("Place can't be added from spun places, check SpunPlaces.java");
            return false;
        }
        //****A database call will be needed at the end of this function****
        return true;
    }

    /**
     * The spin function will call this function and add said place into the Spun list
     * @param currentPlace This is the place that was spun and will be added to the Spun list
     * @return True if successful, False otherwise
     */
    public boolean addPlace(Place currentPlace){
        //When a place is spun, it will call this function.
        //This will add a place into listOfSpunPlaces
        boolean checker;
        checker = this.listOfSpunPlaces.add(currentPlace);
        if(!checker){
            System.out.println("Place can't be added to spun places, check SpunPlaces.java");
            return false;
        }
        //****A database call will be needed at the end of this function****
        return true;
    }

    /**
     * getter for the spun places list
     * @return spun places list
     */
    public ArrayList<Place> getSpunPlaces(){
        return this.listOfSpunPlaces;
    }
    /**
     * getter for the check places list
     * @return check places list
     */
    public ArrayList<Place> getCheckPlaces(){
        return this.listOfCheckPlaces;
    }
}
