package com.example.SpinIt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;
import android.util.Log;



public class AddFriendActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private ViewPager myViewPager;
    private TabLayout myTabLayout;
    private NewAccessorAdapter myTabsAccessorAdapter;
    private FirebaseUser currentuser;
    private FirebaseAuth mAuth;
    private DatabaseReference RootRef;
    private Person newp;
    private String testValue,currentGroupName;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend2);
        mToolbar =(Toolbar)findViewById(R.id.main_page_toolbar);
        myViewPager =(ViewPager)findViewById(R.id.main_tabs_pager);
        myTabsAccessorAdapter = new NewAccessorAdapter(getSupportFragmentManager());
        myViewPager.setAdapter(myTabsAccessorAdapter);
        myTabLayout =(TabLayout)findViewById(R.id.main_tabs);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("AddFriend");
        myTabLayout.setupWithViewPager(myViewPager);
        mAuth = FirebaseAuth.getInstance();
        currentuser = mAuth.getCurrentUser();
        RootRef = FirebaseDatabase.getInstance().getReference();
        currentGroupName = getIntent().getExtras().get("groupName").toString();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.option_people, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);

        if (item.getItemId() == R.id.main_logout_option)
        {
            //updateUserStatus("offline");
            mAuth.signOut();
            SendUserToLoginActivity();
        }
        if (item.getItemId() == R.id.back_to_room_option)
        {

            Intent loginIntent = new Intent(AddFriendActivity.this,GroupChatActivity.class);
            loginIntent.putExtra("groupName" , currentGroupName);
            loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(loginIntent);
            finish();
        }


        return true;
    }

    private void SendUserToLoginActivity(){
        Intent loginIntent = new Intent(AddFriendActivity.this,LoginActivity.class);
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(loginIntent);
        finish();
    }



}
