//package com.example.SpinIt;
//
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.Set;
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//import org.junit.Test;
//import static org.junit.Assert.*;
//
//public class YelpApiTest {
//    @Test
//    public void testingJSONParser(){
//        //This is to test the JSON, by copying the code onto here and printing out the items
//        String responseBody = "{\"businesses\": [{\"id\": \"CzrxICTtbTVt7s7Dt0WrPQ\", \"alias\": \"pho-saigon-port-hueneme-3\", \"name\": \"Pho Saigon\", \"image_url\": \"https://s3-media4.fl.yelpcdn.com/bphoto/SqFDsdVsxdQ0t-Os3xYx0Q/o.jpg\", \"is_closed\": false, \"url\": \"https://www.yelp.com/biz/pho-saigon-port-hueneme-3?adjust_creative=88599ZZIEZq8IAulbNmDhw&utm_campaign=yelp_api_v3&utm_medium=api_v3_business_search&utm_source=88599ZZIEZq8IAulbNmDhw\", \"review_count\": 942, \"categories\": [{\"alias\": \"vietnamese\", \"title\": \"Vietnamese\"}], \"rating\": 4.5, \"coordinates\": {\"latitude\": 34.155306195583, \"longitude\": -119.19472103921}, \"transactions\": [\"pickup\", \"delivery\"], \"price\": \"$$\", \"location\": {\"address1\": \"826 N Ventura Rd\", \"address2\": \"\", \"address3\": \"\", \"city\": \"Port Hueneme\", \"zip_code\": \"93041\", \"country\": \"US\", \"state\": \"CA\", \"display_address\": [\"826 N Ventura Rd\", \"Port Hueneme, CA 93041\"]}, \"phone\": \"+18052409334\", \"display_phone\": \"(805) 240-9334\", \"distance\": 23.387626632454257}, {\"id\": \"815O9uD-87sHVtB224CZYg\", \"alias\": \"the-best-breakfast-cafe-oxnard\", \"name\": \"The Best Breakfast Cafe\", \"image_url\": \"https://s3-media2.fl.yelpcdn.com/bphoto/VanVT7PNOnzRqyY491AlVA/o.jpg\", \"is_closed\": false, \"url\": \"https://www.yelp.com/biz/the-best-breakfast-cafe-oxnard?adjust_creative=88599ZZIEZq8IAulbNmDhw&utm_campaign=yelp_api_v3&utm_medium=api_v3_business_search&utm_source=88599ZZIEZq8IAulbNmDhw\", \"review_count\": 454, \"categories\": [{\"alias\": \"tradamerican\", \"title\": \"American (Traditional)\"}, {\"alias\": \"breakfast_brunch\", \"title\": \"Breakfast & Brunch\"}], \"rating\": 4.5, \"coordinates\": {\"latitude\": 34.15406, \"longitude\": -119.17813}, \"transactions\": [], \"price\": \"$$\", \"location\": {\"address1\": \"5141 Saviers Rd\", \"address2\": \"\", \"address3\": \"\", \"city\": \"Oxnard\", \"zip_code\": \"93033\", \"country\": \"US\", \"state\": \"CA\", \"display_address\": [\"5141 Saviers Rd\", \"Oxnard, CA 93033\"]}, \"phone\": \"+18052711113\", \"display_phone\": \"(805) 271-1113\", \"distance\": 1540.9678574762322}], \"total\": 215, \"region\": {\"center\": {\"longitude\": -119.194739, \"latitude\": 34.155516}}}\n";
//        try {
//            ArrayList<Place> currentListOfPlaces = new ArrayList<Place>();
//            JSONObject yelp = new JSONObject(responseBody);
//            JSONArray businesses = yelp.getJSONArray("businesses");
//            for (int i = 0; i < businesses.length(); i++) {
//                JSONObject currentBusiness = businesses.getJSONObject(i);
//                String currentUrl = currentBusiness.getString("url");
//                JSONObject currentCoordinates = currentBusiness.getJSONObject("coordinates");
//                double currentLongitude = currentCoordinates.getDouble("longitude");
//                double currentLatitude = currentCoordinates.getDouble("latitude");
//
//                Place currentPlace = new Place(currentLongitude, currentLatitude, currentUrl);
//                currentListOfPlaces.add(currentPlace);
//            }
//            for(int i = 0; i < currentListOfPlaces.size(); i++){
//                System.out.println(currentListOfPlaces.get(i).getURL());
//                System.out.println(currentListOfPlaces.get(i).getLatitude());
//                System.out.println(currentListOfPlaces.get(i).getLongitude());
//            }
//            //this.listOfPlaces = currentListOfPlaces;
//        }catch (JSONException e) {
//            //some exception handler code.
//            System.out.println("Failed at Parsing Json");
//            assertEquals(1,2);
//            //This assertion will cause our test to fail, which means that we failed parsing a JSON.
//        }
//
//    }
//    @Test
//    public void testingYelpAPI(){
//
//        //First create an empty person then we can create a Spin for that person
//        ArrayList<String> listOfStatus = new ArrayList<String>();
//        Set<String> listOfFriends = new HashSet<String>();
//        String username = "briantdu777";
//
//        ArrayList<Place> listOfSpunPlaces = new ArrayList<Place>();
//        ArrayList<Place> listOfCheckPlaces = new ArrayList<Place>();
//        ArrayList<String> dietaryList = new ArrayList<String>();
//        SpunPlaces sp1 = new SpunPlaces(listOfCheckPlaces, listOfSpunPlaces);
//
//        Person per1 = new Person(listOfStatus, listOfFriends, username, sp1, dietaryList);
//
//        Spin spin = new Spin(per1);
//
//        boolean checker;
//        //This should fail because we haven't set a longitude and/or a latitude
////        checker = spin.setPlaces();
////        assertEquals(false, checker);
//
//        spin.setLocation(34.155516, -119.194739);
//        spin.setRadius(1000);
//
//        checker = spin.setPlaces();
//        assertEquals(true, checker);
//
//        ArrayList<Place> temp = spin.getListOfPlaces();
//
//        for(int i = 0; i < temp.size(); i++){
//            System.out.println(temp.get(i).getURL());
//            System.out.println(temp.get(i).getLatitude());
//            System.out.println(temp.get(i).getLongitude());
//        }
//    }
//
//}
