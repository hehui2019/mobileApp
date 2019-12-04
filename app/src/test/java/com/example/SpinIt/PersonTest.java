//package com.example.SpinIt;
//
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.Set;
//import org.junit.Test;
//import static org.junit.Assert.*;
////person test
//public class PersonTest {
//    @Test
//    public void testingAllFunctions() {
//        ArrayList<String> listOfStatus = new ArrayList<String>();
//        listOfStatus.add("Just ate at Diddy Riese and their ice cream sandwhich was amazing");
//
//
//        Set<String> listOfFriends = new HashSet<String>();
//        String username = "briantdu777";
//
//
//        ArrayList<Place> listOfSpunPlaces = new ArrayList<Place>();
//        ArrayList<Place> listOfCheckPlaces = new ArrayList<Place>();
//        Place p1 = new Place(34.063144, -118.446743, "https://www.yelp.com/biz/diddy-riese-cookies-los-angeles-2");
//        Place p2 = new Place(34.061025, -118.446023, "https://www.yelp.com/biz/kopan-ramen-ucla-los-angeles");
//        Place p3 = new Place(34.062541, -118.446794, "https://www.yelp.com/biz/its-boba-time-los-angeles-29?osq=It%27s+Boba+Time");
//
//        listOfCheckPlaces.add(p1);
//        listOfCheckPlaces.add(p2);
//        listOfSpunPlaces.add(p3);
//
//        ArrayList<String> dietaryList = new ArrayList<String>();
//
//        SpunPlaces sp1 = new SpunPlaces(listOfCheckPlaces, listOfSpunPlaces);
//
//        Person per1 = new Person(listOfStatus, listOfFriends, username, sp1, dietaryList);
//
//        //Tests all Place functions
//        assertEquals(34.063144, p1.getLongitude(), 0.1);
//        assertEquals(-118.446743, p1.getLatitude(), 0.1);
//        assertEquals("https://www.yelp.com/biz/diddy-riese-cookies-los-angeles-2", p1.getURL());
//
//        //tests for Spun functions
//        assertEquals(true, per1.getSpunPlaces().remove(p1, "check"));
//        assertEquals(false, per1.getSpunPlaces().remove(p1, "check"));
//        assertEquals(true, per1.getSpunPlaces().checkIn(p3));
//        assertEquals(false, per1.getSpunPlaces().checkIn(p3));
//        assertEquals(true, per1.getSpunPlaces().addPlace(p1));
//
//        //tests for Person class
//        assertEquals(true, per1.addFriend("Defo"));
//        assertEquals(true, per1.addFriend("x86-64"));
//        assertEquals(true, per1.addFriend("HiddenGodHui"));
//        assertEquals(true, per1.setUsername("influentialbeast"));
//        assertEquals(false, per1.setUsername("influentialbeast"));
//        assertEquals(true, per1.updateStatus("That ramen at Kopan ramen was disgusting, 0/10"));
//
//    }
//}