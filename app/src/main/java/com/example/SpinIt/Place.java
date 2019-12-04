package com.example.SpinIt;

import android.os.Parcel;
import android.os.Parcelable;

public class Place implements Parcelable{
    private double longitude;
    private double latitude;
    private String url;
    private String name;
    private String address;

    /**************************************************/
//This is all for Parcelable stuff

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeDouble(longitude);
        out.writeDouble(latitude);
        out.writeString(url);
        out.writeString(name);
        out.writeString(address);
    }

    public static final Parcelable.Creator<Place> CREATOR = new Parcelable.Creator<Place>() {
        public Place createFromParcel(Parcel in) {
            return new Place(in);
        }

        public Place[] newArray(int size) {
            return new Place[size];
        }
    };

    private Place(Parcel in) {
        this.longitude = in.readDouble();
        this.latitude = in.readDouble();
        this.url = in.readString();
        this.name = in.readString();
        this.address = in.readString();
    }
    /************************************************/




    Place(double longitude, double latitude, String url, String name, String address)
    {
        this.longitude = longitude;
        this.latitude = latitude;
        this.url = url;
        this.name = name;
        this.address = address;
    }

    /**
     **
     * This is needed for checking equality of Places, which is automatically used
     *
     * @param o This is the other object
     * @return true if the longitude, latitude AND URL are equivalent, otherwise it's false
     *
     */
    public boolean equals(Object o) {
        Place other = (Place) o;
        if(this.longitude == other.getLongitude() && this.latitude == other.getLatitude() && this.url.equals(other.getURL()))
            return true;
        else
            return false;
    }

    /**
     * getter for the longitude of the place
     * @return longitude value
     */
    public double getLongitude(){ return this.longitude;}
    /**
     * getter for the latitude of the place
     * @return latitude value
     */
    public double getLatitude(){ return this.latitude;}
    /**
     * getter for the URL for the yelp of the place
     * @return URL for the yelp of the place
     */
    public String getURL(){ return this.url; }
}
