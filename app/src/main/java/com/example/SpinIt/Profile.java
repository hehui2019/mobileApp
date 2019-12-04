package com.example.SpinIt;


import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import android.content.DialogInterface;

import java.util.ArrayList;

public class Profile extends AppCompatActivity {

    Button foodBtn;
    TextView showSelectedFood;
    String[] listItems;
    boolean[] checkedItems;
    ArrayList<Integer> kUserItems = new ArrayList<>();
    ArrayList<String> foodListChosen = new ArrayList<>();


    Button dietaryBtn;
    TextView showSelectedDietary;
    String[] dietaryItems;
    boolean[] checkedDietaryItems;
    ArrayList<Integer> kUserItems2 = new ArrayList<>();
    ArrayList<String> dietaryListChosen = new ArrayList<>();

    private Person currentPerson;
    private Spin currentSpin = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        foodBtn = (Button) findViewById(R.id.foodTypeBtn);
        showSelectedFood = (TextView) findViewById((R.id.selectedFood));
        showSelectedFood.setMovementMethod(new ScrollingMovementMethod());
        listItems = getResources().getStringArray(R.array.food_types);
        checkedItems = new boolean[listItems.length];

        Intent mIntent = getIntent();
        currentPerson = (Person) mIntent.getParcelableExtra("Person");
        currentSpin = (Spin)mIntent.getParcelableExtra("Spin");

        if(currentSpin != null)
        {
            Log.d("tag", "In get(), after get the arrList " + currentSpin.getLatitude());
        }
        if(currentSpin.getPerson() != null)
        {
            Log.d("tag", "In get(), after get the arrList " + currentSpin.getPerson().getCurrentUID());
        }

        // UPDATE already added boxes
        ArrayList<String> tempFoodList = new ArrayList<>();
        if(currentPerson.getPrefList() != null && currentPerson.getPrefList().getFoodPref() != null) {
            tempFoodList = currentPerson.getPrefList().getFoodPref();
        }
        else {
            tempFoodList = new ArrayList<>();
        }

        for(String s: tempFoodList)
        {

            for(int i=0; i<listItems.length; i++)
            {
                if(s.equals(listItems[i]))
                {
                    checkedItems[i] = true;
                    kUserItems.add(i);
//                    System.out.println(i);
                    break;
                }

            }
        }

        String item = "";
        for(int i=0; i<kUserItems.size(); i++)
        {
            item = item + listItems[kUserItems.get(i)];
            if(i!=kUserItems.size()-1)  // not last item, add comma
            {
                item = item + "\n";
            }

        }
        showSelectedFood.setText(item);

        foodBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(Profile.this);
                mBuilder.setTitle("Available Food Types");
                mBuilder.setMultiChoiceItems(listItems, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        if(isChecked) {
                            kUserItems.add(which);
                            foodListChosen.add(listItems[which]);
                        }
                        else if(kUserItems.contains(which))
                        {
                            foodListChosen.remove(listItems[which]);
                            kUserItems.remove(Integer.valueOf(which));

                        }

                    }
                });
                mBuilder.setCancelable(false);
                mBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String item = "";
                        ArrayList<String> tempList = new ArrayList<>();
                        for(int i=0; i<kUserItems.size(); i++)
                        {
                            item = item + listItems[kUserItems.get(i)];
                            tempList.add(listItems[kUserItems.get(i)]);
                            if(i!=kUserItems.size()-1)  // not last item, add comma
                            {
                                item = item + "\n";
                            }

                        }
                        showSelectedFood.setText(item);
                        PrefList tempPL = new PrefList();
                        tempPL.setFoodPref(tempList);
                        tempPL.setDietaryPref(currentPerson.getPrefList().getDietaryPref());
                        currentPerson.updatePrefList(tempPL);
                    }
                });

                mBuilder.setNeutralButton("Clear", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        for(int i=0; i<checkedItems.length; i++)
                        {
                            checkedItems[i] = false;
                            kUserItems.clear();
                            showSelectedFood.setText("");
                            foodListChosen.clear();
                        }
                        PrefList empty = new PrefList();
                        empty.setDietaryPref(currentPerson.getPrefList().getDietaryPref());
                        currentPerson.updatePrefList(empty);
                    }
                });

                mBuilder.setNegativeButton("Dimiss", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog foodDialog = mBuilder.create();
                foodDialog.show();

            }
        });

        dietaryBtn = (Button) findViewById(R.id.dietaryBtn);
        showSelectedDietary = (TextView) findViewById((R.id.selectedDietary));
        showSelectedDietary.setMovementMethod(new ScrollingMovementMethod());
        dietaryItems = getResources().getStringArray(R.array.dietary_types);
        checkedDietaryItems = new boolean[dietaryItems.length];


//        HERE WE WILL ALSO NEED TO GET THE LIST AND CHECK WHICH BOXES ARE ALREADY CHECKED
//        UPDATE already added boxes
//        if you are not assigning dietaryListChosen to what you get in firebase, you will
//        also need to add the item to dietaryListChosen here.

        ArrayList<String> diet_tempList = new ArrayList<>();
        if(currentPerson.getPrefList() != null && currentPerson.getPrefList().getDietaryPref() != null) {
            diet_tempList = currentPerson.getPrefList().getDietaryPref();
        }
        else {
            diet_tempList = new ArrayList<>();
        }

        for(String s: diet_tempList)
        {
            for(int i=0; i<dietaryItems.length; i++)
            {
                if(s.equals(dietaryItems[i]))
                {
                    checkedDietaryItems[i] = true;
                    kUserItems2.add(i);
                    // dietaryListChosen.add(s);
                    break;
                }
            }
        }
        String item2 = "";
        for(int i=0; i<kUserItems2.size(); i++)
        {
            item2 = item2 + dietaryItems[kUserItems2.get(i)];
            if(i!=kUserItems2.size()-1)  // not last item, add comma
            {
                item2 = item2 + "\n";
            }
        }
        showSelectedDietary.setText(item2);

        dietaryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(Profile.this);
                mBuilder.setTitle("Available Dietary Types");
                mBuilder.setMultiChoiceItems(dietaryItems, checkedDietaryItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        if(isChecked) {
                            kUserItems2.add(which);
                            dietaryListChosen.add(dietaryItems[which]);
                        }
                        else if(kUserItems2.contains(which))
                        {
                            dietaryListChosen.remove(dietaryItems[which]);
                            kUserItems2.remove(Integer.valueOf(which));

                        }

                    }
                });
                mBuilder.setCancelable(false);
                mBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String item = "";
                        ArrayList<String> tempList = new ArrayList<>();
                        for(int i=0; i<kUserItems2.size(); i++)
                        {
                            item = item + dietaryItems[kUserItems2.get(i)];
                            tempList.add(dietaryItems[kUserItems2.get(i)]);
                            if(i!=kUserItems2.size()-1)  // not last item, add comma
                            {
                                item = item + "\n";
                            }

                        }
                        showSelectedDietary.setText(item);
                        PrefList tempPL = new PrefList();
                        tempPL.setDietaryPref(tempList);
                        tempPL.setFoodPref(currentPerson.getPrefList().getFoodPref());
                        currentPerson.updatePrefList(tempPL);
                    }
                });

                mBuilder.setNeutralButton("Clear", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        for(int i=0; i<checkedDietaryItems.length; i++)
                        {
                            checkedDietaryItems[i] = false;
                            kUserItems2.clear();
                            showSelectedDietary.setText("");
                            dietaryListChosen.clear();
                        }
                        PrefList empty = new PrefList();
                        empty.setFoodPref(currentPerson.getPrefList().getFoodPref());
                        currentPerson.updatePrefList(empty);
                    }
                });

                mBuilder.setNegativeButton("Dimiss", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog dietaryDialog = mBuilder.create();
                dietaryDialog.show();

            }
        });


    }


}

