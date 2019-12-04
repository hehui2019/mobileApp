package com.example.SpinIt;


import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;

import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class Spinner extends AppCompatActivity implements Animation.AnimationListener {
    boolean blnButtonRotation = true;
    int intNumber = 1;
    long lngDegrees = 0;
    ImageView selected,imageRoulette;
    LinearLayout linearFoodPlace1 , linearFoodPlace2, linearFoodPlace3;
    private double savedDegree;
    private String currentGroupName;
    private String[] spinnerChoices = {"Bplate", "In and out", "What a burger", "Subway", "Something really really long", "six", "seven", "eight"};

    ObjectAnimator boxes;
    Button b_start, b_increase, b_decrease,btn;
    TextView first, second, third, fourth, fifth, sixth, seventh, eight;
    TextView popup;
    int childCount = 0;
    private Person currentPerson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().addFlags(1024);
        requestWindowFeature(1);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spinner);
        b_start = (Button)findViewById(R.id.spinItBTN);
        b_decrease = (Button)findViewById(R.id.subtractBTN);
        b_increase = (Button)findViewById(R.id.addStuffBTN);
        btn = (Button)findViewById(R.id.back_btn);
        selected = (ImageView)findViewById(R.id.imageSelected);
        imageRoulette = (ImageView)findViewById(R.id.roulette);
        first = (TextView)findViewById(R.id.firstLine);
        second = (TextView)findViewById(R.id.secondLine);
        third = (TextView)findViewById(R.id.thirdLine);
        fourth = (TextView)findViewById(R.id.fourthLine);
        fifth = (TextView)findViewById(R.id.fifthLine);
        sixth = (TextView)findViewById(R.id.sixthLine);
        seventh = (TextView)findViewById(R.id.seventhLine);
        eight = (TextView)findViewById(R.id.eighthLine);
        linearFoodPlace3 = (LinearLayout) findViewById(R.id.foodPlaceImage3);
        linearFoodPlace2 = (LinearLayout) findViewById(R.id.foodPlaceImage2);
        linearFoodPlace1 = (LinearLayout) findViewById(R.id.foodPlaceImage1);
        currentGroupName = getIntent().getExtras().get("groupName").toString();

        Intent mIntent = getIntent();
        currentPerson = (Person) mIntent.getParcelableExtra("Person");


        // Drawable d = Drawable.createFromPath()

        //linearFoodPlace.setBackgroundResource(R.drawable.foodplace1);
        //   imageRoulette.setImageDrawable(getResources().getDrawable(R.drawable.spin3));
        setImageRoulette(this.intNumber);

/*
*     View LinearLayout1 = findViewById(R.id.Layout1);
    ImageView image1 = new ImageView(getApplicationContext());
    String uri = "@drawable/myresource.png"; // Here you can set the name of
                                                // the image dynamically
    int imageResource = getResources().getIdentifier(uri, null,
            getPackageName());
    Drawable res = getResources().getDrawable(imageResource);
    image1.setImageDrawable(res);
    ((ViewGroup) LinearLayout1).addView(image1);
* */




    }

    public void onClickButtonBack(View v)
    {
        Log.d("tag", "Before IF: the value of currentGroupName: " + currentGroupName);
        if (currentGroupName.equals("a")){
            Intent newIntent = new Intent(Spinner.this, MainPageActivity.class);
            //newIntent.putExtra("groupName" , currentGroupName);
            startActivity(newIntent);
            finish();
        }else{
            Intent newIntent = new Intent(Spinner.this, GroupChatActivity.class);
            newIntent.putExtra("groupName" , currentGroupName);
            startActivity(newIntent);
            finish();
        }

    }
    public void onClickButtonRotation(View v)
    {
        if(this.blnButtonRotation)
        {

            int ran = new Random().nextInt(360) + 3600;
            RotateAnimation rotateAnimation = new RotateAnimation((float)this.lngDegrees, (float)
                    (this.lngDegrees + ((long)ran)),1,0.5f,1,0.5f);
            this.lngDegrees = (this.lngDegrees + ((long)ran)) % 360;
            savedDegree = lngDegrees;
            rotateAnimation.setDuration((long)ran);
            rotateAnimation.setFillAfter(true);
            rotateAnimation.setInterpolator((new DecelerateInterpolator()));
            rotateAnimation.setAnimationListener(this);
            imageRoulette.setAnimation(rotateAnimation);
            imageRoulette.startAnimation(rotateAnimation);


        }

    }
    public int outputSelection(int degree, int wheelType)
    {
        if(wheelType == 1)
        {
            if((degree >= 0 && degree <=90) || (degree >= 270 && degree <= 360))
            {
                return 1;
            }
            else
                return 2;
        }
        else if(wheelType == 2)
        {
            if((degree >= 0 && degree <= 90))
                return 2;
            if(degree >= 91 && degree <= 180)
                return 4;
            if(degree >= 181 && degree <= 270)
                return 1;
            if(degree >= 271 && degree <= 360)
                return 3;

        }
        else if(wheelType == 3)
        {
            if(degree >= 0 && degree <= 45)
                return 6;
            if(degree >= 46 && degree <= 90)
                return 5;
            if(degree >= 91 && degree <= 135)
                return 1;
            if(degree >= 136 && degree <= 180)
                return 7;
            if(degree >= 181 && degree <= 225)
                return 2;
            if(degree >=226 && degree <= 270)
                return 8;
            if(degree >= 271 && degree <= 315)
                return 4;
            if(degree >= 316 && degree <= 360)
                return 3;
        }
        return 0;
    }

    @Override
    public void onAnimationStart(Animation animation)
    {
        this.blnButtonRotation = false;
        b_start.setVisibility(View.VISIBLE);

    }
    @Override
    public void onAnimationEnd(Animation animation)
    {
        this.blnButtonRotation = true;
        b_start.setVisibility(View.VISIBLE);

        final int castDegreeToInt = (int)savedDegree;

        //String test = spinnerChoices[outputSelection(castDegreeToInt, intNumber)];
        String valOfSelection =  String.valueOf(spinnerChoices[outputSelection(castDegreeToInt, intNumber)-1]);
        Toast toast = Toast.makeText(this, " " + valOfSelection,0);
        //toast.setGravity(49, 0, 50);
        toast.show();
        //Popup selectedInfo = new Popup(spinnerChoices);

        //TextView winningPlace
        winnerAnimation(intNumber, outputSelection(castDegreeToInt, intNumber)-1);

//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//
//                Intent i=new Intent(SearxhJobs.this,JobsTypes.class);
//                startActivity(i);
//            }
//        }, 5000);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run(){
                Intent displayPopUp= new Intent(Spinner.this, Popup.class);
                displayPopUp.putExtra("info", spinnerChoices[outputSelection(castDegreeToInt, intNumber) -1]);
                startActivity(displayPopUp);
            }
        }, 1000);

        // boxes.end();

        //   Intent displayPopUp= new Intent(Spinner.this, Popup.class);
        // displayPopUp.putExtra("info", spinnerChoices[outputSelection(castDegreeToInt, intNumber) -1]);


        //  startActivity(displayPopUp);

        //  outputSelection(savedDegree, intNumber);

    }

    @Override
    public void onAnimationRepeat(Animation animation)
    {


    }
    public void buttonPlus(View v)
    {
//        if(this.intNumber != 3)
//        {
//            this.intNumber++;
//            setImageRoulette(this.intNumber);
//            b_decrease.setVisibility(View.VISIBLE);
///*            SharedPreferences.Editor editor = this.sharedPreferences.edit();
//           editor.putInt("INT_NUMBER", this.intNumber);
//            editor.commit();*/
//
//        }
//        if(this.intNumber == 3)
//        {
//            b_increase.setVisibility(View.INVISIBLE);
//            //  b_decrease.setVisibility(View.VISIBLE);
//        }
        if(currentPerson != null)
            Log.d("tag", "tests for person transfer " + currentPerson.getCurrentUID());
        else
            Log.d("tag", "failed transfer");
    }

    public void buttonMinus(View v)
    {
        if(this.intNumber != 1)
        {
            intNumber--;
            setImageRoulette(this.intNumber);
            b_increase.setVisibility(View.VISIBLE);
/*            SharedPreferences.Editor editor = this.sharedPreferences.edit();
            editor.putInt("INT_NUMBER", this.intNumber);
            editor.commit();*/
        }
        if(this.intNumber == 1)
        {
            b_decrease.setVisibility(View.INVISIBLE);
            //  b_increase.setVisibility(View.VISIBLE);
        }
    }

    void winnerAnimation(int inputNum, int winner)
    {
        switch(inputNum)
        {
            case 1:
                popup = (TextView)linearFoodPlace1.getChildAt(winner);
                boxes = ObjectAnimator.ofInt(popup, "backgroundColor", Color.BLUE, Color.GREEN, Color.RED, Color.WHITE);
                boxes.setDuration(360);
                first.setVisibility(View.VISIBLE);
                boxes.setEvaluator(new ArgbEvaluator());
                boxes.setRepeatMode(Animation.REVERSE);
                //boxes.setRepeatCount(Animation.INFINITE);
                boxes.start();
                return;
            case 2:
                popup = (TextView)linearFoodPlace2.getChildAt(winner);
                boxes = ObjectAnimator.ofInt(popup, "backgroundColor", Color.BLUE, Color.GREEN, Color.RED, Color.WHITE);
                boxes.setDuration(360);
                first.setVisibility(View.VISIBLE);
                boxes.setEvaluator(new ArgbEvaluator());
                boxes.setRepeatMode(Animation.REVERSE);
                //boxes.setRepeatCount(Animation.INFINITE);
                boxes.start();
                return;
            case 3:
                popup = (TextView)linearFoodPlace3.getChildAt(winner);
                boxes = ObjectAnimator.ofInt(popup, "backgroundColor", Color.BLUE, Color.GREEN, Color.RED, Color.WHITE);
                boxes.setDuration(900);
                first.setVisibility(View.VISIBLE);
                boxes.setEvaluator(new ArgbEvaluator());
                boxes.setRepeatMode(Animation.REVERSE);
                //boxes.setRepeatCount(Animation.INFINITE);
                boxes.start();
                return;
        }
    }

    private void setImageRoulette(int inputNum)
    {


        switch(inputNum)
        {
            case 1:
                linearFoodPlace3.setVisibility(View.INVISIBLE);
                linearFoodPlace2.setVisibility(View.INVISIBLE);
                childCount = linearFoodPlace1.getChildCount();
                imageRoulette.setImageDrawable(getResources().getDrawable(R.drawable.spin1));
                linearFoodPlace1.setBackgroundResource(R.drawable.fp1);
                for(int i =0; i <childCount; i++)
                {
                    TextView tv = (TextView)linearFoodPlace1.getChildAt(i);
                    tv.setText(i+1 +" "+ spinnerChoices[i]);

                }

                //first.setText(spinnerChoices[0]);
                // second.setText(spinnerChoices[1]);
                linearFoodPlace1.setVisibility(View.VISIBLE);
                return;
            case 2:
                childCount = linearFoodPlace2.getChildCount();
                linearFoodPlace1.setVisibility(View.INVISIBLE);
                linearFoodPlace3.setVisibility(View.INVISIBLE);
                imageRoulette.setImageDrawable(getResources().getDrawable(R.drawable.spin2));

                linearFoodPlace2.setBackgroundResource(R.drawable.fp2);
                for(int i = 0; i <childCount; i++)
                {
                    TextView tv = (TextView)linearFoodPlace2.getChildAt(i);
                    tv.setText(i+1 +" "+ spinnerChoices[i]);


                }
                linearFoodPlace2.setVisibility(View.VISIBLE);
                return;
            case 3:
                linearFoodPlace1.setVisibility(View.INVISIBLE);
                linearFoodPlace2.setVisibility(View.INVISIBLE);
                childCount = linearFoodPlace3.getChildCount();
                imageRoulette.setImageDrawable(getResources().getDrawable(R.drawable.spin3));

                linearFoodPlace3.setBackgroundResource(R.drawable.fp3);
                for(int i =0; i <childCount; i++)
                {
                    TextView tv = (TextView)linearFoodPlace3.getChildAt(i);
                    tv.setText(i+1 +" "+ spinnerChoices[i]);

                }
                linearFoodPlace3.setVisibility(View.VISIBLE);
                return;
        }
    }


}