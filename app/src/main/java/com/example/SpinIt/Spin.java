package com.example.SpinIt;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.GenericTypeIndicator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.ArrayList;
import java.util.Random;

import static java.lang.Math.abs;


public class Spin implements Parcelable {
    private ArrayList<Place> listOfPlaces = new ArrayList<Place>();
    private double longitude;
    private double latitude;
    private int radius;
    private Person person;
    private String location;
    private ArrayList<Person> groupOfPeople = new ArrayList<Person>();
    private String currentUID;
    /**************************************************/
//This is all for Parcelable stuff

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeTypedList(listOfPlaces);
        out.writeTypedList(groupOfPeople);
        out.writeParcelable(person, flags);
        out.writeDouble(longitude);
        out.writeDouble(latitude);
        out.writeInt(radius);
        out.writeString(location);
        out.writeString(currentUID);
    }
    public static final Parcelable.Creator<Spin> CREATOR = new Parcelable.Creator<Spin>() {
        public Spin createFromParcel(Parcel in) {
            return new Spin(in);
        }

        public Spin[] newArray(int size) {
            return new Spin[size];
        }
    };
    private Spin(Parcel in) {
        in.readTypedList(listOfPlaces,Place.CREATOR);
        in.readTypedList(groupOfPeople,Person.CREATOR);
        person = in.readParcelable(Person.class.getClassLoader());
        longitude = in.readDouble();
        latitude = in.readDouble();
        radius = in.readInt();
        location = in.readString();
        currentUID = in.readString();
    }
/************************************************/
Spin(DataSnapshot snapshot){
    if(snapshot.child("latitude").exists())
        latitude = snapshot.child("latitude").getValue(double.class);
    if(snapshot.child("longitude").exists())
        longitude = snapshot.child("longitude").getValue(double.class);
    if(snapshot.child("radius").exists())
        radius = snapshot.child("radius").getValue(int.class);
    if(snapshot.child("location").exists())
        location = snapshot.child("location").getValue(String.class);
    if(snapshot.child("listOfPlaces").exists()){
        ArrayList<Place> tempPlace = new ArrayList<>();
        for (DataSnapshot temp : snapshot.child("listOfPlaces").getChildren()) {
            double lat = temp.child("latitude").getValue(double.class);
            double longi = temp.child("longitude").getValue(double.class);
            String url = temp.child("url").getValue(String.class);
            String name = temp.child("url").getValue(String.class);
            String location = temp.child("url").getValue(String.class);
            //EXPANSION OF PLACE
            Place tp = new Place(longi, lat, url, name, location);
            tempPlace.add(tp);
        }
    }
    if(snapshot.child("person").exists()){
        person = new Person(snapshot.child("Person"));
    }
    currentUID = snapshot.child("currentUID").getValue(String.class);

}

    /**
     * <p>
     *     This is the constructor for a single spinner instance, this will instantiate a blank spinner
     *     where the user needs to instantiate a longitude, latitude, and a radius before beginning
     * </p>
     * @param person This person is the one that owns the spinner, and will get the places spun into their own list
     */
    public Spin(Person person, String currentUID){
        this.person = person;
        //invalid longitude and latitude for temp value
        this.longitude = 999;
        this.latitude = 999;
        this.location = null;
        this.radius = 0;
        this.listOfPlaces = new ArrayList<Place>();
        this.groupOfPeople = null;
        //this.setSpinBehavior("group");
        this.currentUID = currentUID;
    }

    /**
     * <p>
     *     This is the constructor for a group spinner instance, this will instantiate a blank room spinner
     *     where the main user needs to instantiate a longitude, latitude, and a radius before beginning
     * </p>
     * @param groupOfPeople This group of people will be a class that will contain an amount of people that when spun will all get the places spun into their own list
     */
    public Spin(ArrayList<Person> groupOfPeople){
        this.person = null;
        //invalid longitude and latitude for temp value
        this.longitude = 999;
        this.latitude = 999;
        this.radius = 0;
        this.listOfPlaces = new ArrayList<Place>();
        this.groupOfPeople = groupOfPeople;
        //this.setSpinBehavior("room");
        //this.currentUID = currentUID;
    }

    /**
     * Set the location of where the center of the yelp api call is (latitude, longitude)
     * @param latitude latitude of the center of the api call
     * @param longitude longitude of the center of the api call
     * @return True if the location was able to be set sucessfully, false otherwise
     */
    public boolean setLocation(double latitude, double longitude) {
        //Set the longitude and latitude
        if(abs(latitude) < 90 && abs(longitude) < 180) {
            this.longitude = longitude;
            this.latitude = latitude;
            return true;
        }
        else{
            System.out.println("Longitude/Latitude is out of range");
            return false;
        }

    }

    /**
     * This will set the radius of how far we are willing to travel from the set (latitude, longitude)
     * @param radius The max is 40000 meters
     */
    public void setRadius(int radius){
        this.radius = radius;
        //Max is 40000 meters
    }

//    /**
//     * This will ensure this has the correct spin behavior for either the single person or the room works
//     * @param roomOrSingle "room" if you want the room implementation, "single" if you want the single implementation
//     * @return True if the change of behavior works, False otherwise
//     */
//    public boolean setSpinBehavior(String roomOrSingle){
//        boolean checker;
//        if(roomOrSingle.equals("room")){
//            this.spinBehavior = new RoomSpinBehavior();
//            checker = true;
//        }
//        else if (roomOrSingle.equals("single")){
//            this.spinBehavior = new SingleSpinBehavior();
//            checker = true;
//        }
//        else{
//            System.out.println("not a proper spin behavior");
//            checker = false;
//        }
//        return checker;
//    }

//    /**
//     * This will just do the spinFunc() based off the spin's behavior
//     * @return True if the spinBehavior's function executed successfully, false otherwise
//     */
//    public boolean spinFunc(){
//        return spinBehavior.spinFunc(this);
//    }

    /**
     * <p>
     *     This is the Yelp API call, where all of the data members come into play,
     *     the location (latitude, longitude) is going to the center of the yelp API,
     *     the radius is how far from the location willing to travel,
     *     the foodPref is the types of food willing to eat,
     *     and the person will have their own dietary needs.
     *
     *     This will then take the result and put it into our parsing function.
     * </p>
     * @return True if the API succeeded, False if it failed due to failure parsing the JSON returned from Yelp's API OR if there was a timeout on the GET request
     */
    public boolean setPlaces(){
        if(this.latitude == 999 && this.longitude == 999){
            System.out.println("Please set Latitude and Longitude");
            return false;
        }
        //This is where we will do an api call with the longitude, latitude, radius, dietary restrictions, and food preference that is given to us
        boolean checker = false;
        HttpURLConnection con = null;
        BufferedReader reader;
        String line;
        StringBuffer responseContent = new StringBuffer();
        String API_KEY = "5fY2dEN5tT1qdS-5X25kkGcDRp71bv1AG2WURiYMaZxN9t0N51sMcZ8HEPP9SvIXkDHHrzGXj0ka5W1n9FUEfjzi2_so8QEWF7pKFF4LV4vj0w09cWyYJ2JAMBHJXXYx";
        try{
            String yelp = "https://api.yelp.com/v3/businesses/search";
            yelp = yelp + "?longitude=" + String.valueOf(this.longitude)+ "&latitude=" +
                    String.valueOf(this.latitude) + "&radius" + String.valueOf(this.radius);

            ArrayList<String> foodList = person.getPrefList().getFoodPref();
            for(int i = 0; i <  foodList.size(); i++)
                yelp = yelp + "?categories=" + person.getPrefList().getFoodPref().get(i);

            ArrayList<String> dietaryList = person.getPrefList().getDietaryPref();
            for(int i = 0; i < dietaryList.size(); i++)
                yelp = yelp + "?categories=" + dietaryList.get(i);

            URL url = new URL(yelp);
            con = (HttpURLConnection) url.openConnection();

            //Set up for the getRequest
            con.setRequestMethod("GET");
            con.setRequestProperty("Authorization", "bearer " + API_KEY);
            con.setConnectTimeout(10000);
            con.setReadTimeout(10000);

            int status = con.getResponseCode();

            if(status > 299){
                reader = new BufferedReader(new InputStreamReader(con.getErrorStream()));
                while((line=reader.readLine()) != null){
                    responseContent.append(line);
                }
                reader.close();
            }
            else{
                reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                while((line = reader.readLine()) != null){
                    responseContent.append(line);
                }
                reader.close();
            }
            checker = parse(responseContent.toString());
        }
        catch(MalformedURLException e){
            e.printStackTrace();
        }
        catch(IOException e){
            e.printStackTrace();
        }
        finally{
            if(con != null)
                con.disconnect();
        }
        return checker;
    }

    /**
     * <p>
     *     This will parse the response from yelp's api and for each restaurant, a new place will be populated into a new array, then once all of them have been recorded, it will replace the Spinner's list of places
     * </p>
     * @param responseBody The JSON from the Yelp API
     * @return True if it parsed correctly, false if an error occurred
     */
    private boolean parse(String responseBody){
       try {
           ArrayList<Place> currentListOfPlaces = new ArrayList<Place>();
           JSONObject yelp = new JSONObject(responseBody);
           JSONArray businesses = yelp.getJSONArray("businesses");
           for (int i = 0; i < businesses.length(); i++) {
               JSONObject currentBusiness = businesses.getJSONObject(i);
               String currentUrl = currentBusiness.getString("url");
               JSONObject currentCoordinates = currentBusiness.getJSONObject("coordinates");
               double currentLongitude = currentCoordinates.getDouble("longitude");
               double currentLatitude = currentCoordinates.getDouble("latitude");

               String currentName = currentBusiness.getString("name");

               JSONObject currentLocation = currentBusiness.getJSONObject("location");
               String currentAddress = currentLocation.getString("address1") +
                       currentLocation.getString("city") +
                       currentLocation.getString("country") +
                       currentLocation.getString("zip_code");

               Place currentPlace = new Place(currentLongitude, currentLatitude, currentUrl, currentName, currentAddress);
               currentListOfPlaces.add(currentPlace);
           }
           this.listOfPlaces = currentListOfPlaces;
           return true;
       }catch (JSONException e) {
           //some exception handler code.
           System.out.println("Failed at Parsing Json");
       }
       return false;
    }
    /**
     * getter for the list of spinnable places
     * @return list of spinnable places
     */
    public ArrayList<Place> getListOfPlaces(){return this.listOfPlaces;}
    /**
     * getter for the current Longitude
     * @return the current Longitude
     */
    public double getLongitude(){return this.longitude;}
    /**
     * getter for the the current Latitude
     * @return the current Latitude
     */
    public double getLatitude(){return this.latitude;}
    /**
     * getter for the current radius
     * @return the current radius
     */
    public int getRadius(){return this.radius;}
//    /**
//     * getter for the current spin behavior
//     * @return the current spin behavior
//     */
//    public SpinBehavior getSpinBehavior(){return this.spinBehavior;}
    /**
     * getter for the Person that owns the instance of the spinner
     * @return the instance of the spinner
     */
    public Person getPerson(){return this.person;}
    /**
     * getter for the List of people in the room
     * @return List of people in the room
     */
    public ArrayList<Person> getGroupOfPeople(){return this.groupOfPeople;}
}

///**
// * Interface for the strategy design pattern of the Spin Behavior
// */
//interface SpinBehavior{
//    public boolean spinFunc(Spin spin);
//}
//
//class SingleSpinBehavior implements SpinBehavior{
//    /**
//     * The spin func will be in charge of choosing the place and update the person's SpunPlaces
//     * @param spin The spin class that belongs to the person that will get their SpunPlaces populated
//     * @return True if it was able to add the person's sucessfully, false otherwise
//     */
//    public boolean spinFunc(Spin spin){
//        //needs to be implemented
//        //Randomly choose one of the places in the array for now and adds it into the person's class
//        boolean checker;
//        Random random = new Random();
//        Place randomPlace = spin.getListOfPlaces().get(random.nextInt(spin.getListOfPlaces().size()));
//        checker = spin.getPerson().getSpunPlaces().addPlace(randomPlace);
//        if(!checker){
//            System.out.println("Unable to Spin");
//            return false;
//        }
//        return true;
//    }
//}
//
//
//class RoomSpinBehavior implements SpinBehavior{
//    /**
//     * The spin func will be in charge of choosing the place and update the every person in the room's SpunPlaces
//     * @param spin The spin class that belongs to a group of people that will get their SpunPlaces populated
//     * @return True if it was able to add the person's sucessfully, false otherwise
//     */
//    public boolean spinFunc(Spin spin){
//        //needs to be implemented
//        boolean checker;
//        Random random = new Random();
//        Place randomPlace = spin.getListOfPlaces().get(random.nextInt(spin.getListOfPlaces().size()));
//        int sizeOfRoom = spin.getGroupOfPeople().size();
//        for(int i = 0; i < sizeOfRoom; i++)
//        {
//            checker = spin.getGroupOfPeople().get(i).getSpunPlaces().addPlace(randomPlace);
//            if(!checker){
//                System.out.println("Unable to Spin");
//                return false;
//            }
//        }
//        return true;
//    }
//}
