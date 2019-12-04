package com.example.SpinIt;


import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import java.util.ArrayList;



public class Person implements Parcelable {
    private ArrayList<String> listOfStatus = new ArrayList<>();
    //private Set<String> listOfFriends;
    private SpunPlaces spunPlaces = new SpunPlaces();
    private PrefList prefList = new PrefList();
    private String currentUID;
    private Spin currentSpin;
/**************************************************/
//This is all for Parcelable stuff

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeParcelable(spunPlaces, flags);
        out.writeParcelable(prefList, flags);
        out.writeString(currentUID);
        out.writeStringList(listOfStatus);
    }
    public static final Parcelable.Creator<Person> CREATOR = new Parcelable.Creator<Person>() {
        public Person createFromParcel(Parcel in) {
            return new Person(in);
        }

        public Person[] newArray(int size) {
            return new Person[size];
        }
    };
    private Person(Parcel in) {
        this.spunPlaces = in.readParcelable(SpunPlaces.class.getClassLoader());
        this.prefList = in.readParcelable(PrefList.class.getClassLoader());
        this.currentUID = in.readString();
        in.readStringList(listOfStatus);
    }
/************************************************/

    Person(String currentUID){
        this.listOfStatus = new ArrayList<>();
        this.prefList = new PrefList();
        this.spunPlaces = new SpunPlaces();
        this.currentUID = currentUID;
    }
    Person(String currentUID, PrefList pl, SpunPlaces sp, ArrayList<String> ls){
        this.listOfStatus = ls;
        this.prefList = pl;
        this.spunPlaces = sp;
        this.currentUID = currentUID;
    }
    /**
     * This is the constructor for a person that has barely created an account and will instantiate everything as empty
     * @param username this is that person's username
     */
    Person(DataSnapshot snapshot){
        //this.listOfFriends = new HashSet<String>();
        GenericTypeIndicator<ArrayList<String>> stringList = new GenericTypeIndicator<ArrayList<String>>(){};
        if(snapshot.child("listOfStatus").exists())
            this.listOfStatus = snapshot.child("listOfStatus").getValue(stringList);
        else
            this.listOfStatus = new ArrayList<>();

        if(snapshot.child("spunPlaces").exists()) {
            ArrayList<Place> tempCheck = new ArrayList<>();
            ArrayList<Place> tempSpun = new ArrayList<>();
            if(snapshot.child("spunPlaces").child("checkPlaces").exists()) {
                for (DataSnapshot temp : snapshot.child("spunPlaces").child("checkPlaces").getChildren()) {
                    double lat = temp.child("latitude").getValue(double.class);
                    double longi = temp.child("longitude").getValue(double.class);
                    String url = temp.child("url").getValue(String.class);
                    String name = temp.child("url").getValue(String.class);
                    String location = temp.child("url").getValue(String.class);
                    //EXPANSION OF PLACE
                    Place tp = new Place(longi, lat, url, name, location);
                    tempCheck.add(tp);
                }
            }
            if(snapshot.child("spunPlaces").child("spunPlaces").exists()) {
                for (DataSnapshot temp : snapshot.child("spunPlaces").child("spunPlaces").getChildren()) {
                    double lat = temp.child("latitude").getValue(double.class);
                    double longi = temp.child("longitude").getValue(double.class);
                    String url = temp.child("url").getValue(String.class);
                    String name = temp.child("url").getValue(String.class);
                    String location = temp.child("url").getValue(String.class);
                    //EXPANSION OF PLACE
                    Place tp = new Place(longi, lat, url, name, location);
                    tempSpun.add(tp);
                }
            }
            this.spunPlaces = new SpunPlaces(tempCheck, tempSpun);
        }
        else
            this.spunPlaces = new SpunPlaces();

        if(snapshot.child("prefList").exists()){
            ArrayList<String> tempDiet = new ArrayList<>();
            ArrayList<String> tempFood = new ArrayList<>();
            if(snapshot.child("prefList").child("dietaryPref").exists()) {
                for (DataSnapshot temp : snapshot.child("prefList").child("dietaryPref").getChildren()) {
                    String tempDietS = temp.getValue(String.class);
                    tempDiet.add(tempDietS);
                }
            }
            if(snapshot.child("prefList").child("foodPref").exists()) {
                for (DataSnapshot temp : snapshot.child("prefList").child("foodPref").getChildren()) {
                    String tempDietS = temp.getValue(String.class);
                    tempFood.add(tempDietS);
                }
            }
            PrefList pl = new PrefList();
            pl.setDietaryPref(tempDiet);
            pl.setFoodPref(tempFood);
            this.prefList = pl;
        }
        else
            this.prefList = new PrefList();

        this.currentUID = snapshot.child("currentUID").getValue(String.class);
    }
    /**
     * Updating a status add a new status onto a person's status, this the updated status will appear on a person's profile
     * @param status The status to be added onto profile
     * @return True if successful, false otherwise
     */
    public boolean updateStatus(String status){
        //Here we will add the status into our arrayList as another entry
        boolean checker = this.listOfStatus.add(status);
        if(!checker) {
            System.out.println("Status cannot be updated");
            return false;
        }
        //****A database call will be needed at the end of this function****
        return true;
    }

    public boolean updatePrefList(PrefList dietaryList){
        //do a database call and then return true
        this.prefList = dietaryList;
        DatabaseReference RootRef = FirebaseDatabase.getInstance().getReference();
        RootRef.child("Users").child(this.currentUID).child("Person").setValue(this);
        RootRef.child("Users").child(this.currentUID).child("Spin").child("Person").setValue(this);
        return true;
    }

    /**
     * getter for the list of statuses
     * @return list of statuses
     */
    public ArrayList<String> getListOfStatus(){return this.listOfStatus;}
    /**
     * getter for the list of friends
     * @return list of friends
     */
//    public Set<String> getListOfFriends(){return this.listOfFriends;}
    /**
     * getter for the SpunPlaces data structure, which contains both a spun place list and a check place list
     * @return SpunPlaces data structure
     */
    public SpunPlaces getSpunPlaces(){return this.spunPlaces;}
    /**
     * getter for the Dietary List
     * @return the Dietary list
     */
    public PrefList getPrefList(){return this.prefList;}
    public String getCurrentUID(){return this.currentUID;}
}
