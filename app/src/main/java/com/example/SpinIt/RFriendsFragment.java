package com.example.SpinIt;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


/**
 * A simple {@link Fragment} subclass.
 */
public class RFriendsFragment extends Fragment {
    private View groupFragmentView;
    private ListView list_view;
    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> list_of_groups = new ArrayList<>();
    private FirebaseAuth mAuth;
    private DatabaseReference GroupRef,myRef;
    private String currentUserID,currentGroupName;
    public RFriendsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        groupFragmentView = inflater.inflate(R.layout.fragment_friends3, container, false);
        GroupRef = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserID).child("Friendlist");
        myRef = FirebaseDatabase.getInstance().getReference();
        currentGroupName = getActivity().getIntent().getExtras().getString("groupName");
        //myRef.child("Groups").child("groupName").toString();
        //String Item = getActivity().getIntent().get("groupName").toString();
        //getIntent().getExtras().get("groupName").toString();
        IntializeFields();
        RetrieveAndDisplayGroups();
        list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id)
            {

                final String cName = adapterView.getItemAtPosition(position).toString();

                DatabaseReference mdatabaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
                mdatabaseReference.orderByChild("name").equalTo(cName).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            for (DataSnapshot childSnapshot: dataSnapshot.getChildren()) {
                                String uid = childSnapshot.getKey();
                                Log.d("tag","test value1: "+ uid);
                                CreateNewGroup(uid,cName);

                                break;
                            }
                        }

                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) { // ToDo: Do something for errors too
                    }
                });

                //Intent groupChatIntent = new Intent(getContext(), GroupChatActivity.class);
                //groupChatIntent.putExtra("groupName" , currentGroupName);
                //startActivity(groupChatIntent);
            }
        });
        return groupFragmentView;
    }

    private void CreateNewGroup(final String memberName,final String c)
    {
        Log.d("tag","test value3: "+ currentGroupName);
        String currentUserID = mAuth.getCurrentUser().getUid();
        //UsersRef.child("Groups").child(groupName).child("Host").setValue(currentUserID);
        myRef.child("Groups").child(currentGroupName).child("Member").child(memberName).removeValue()
                //myRef.child("Groups").child(currentGroupName).child("Member").child(memberName).setValue("")
                //UsersRef.child("Groups").child(groupName).child("Message").setValue("")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        Toast.makeText(getActivity(), c + " People is added Successfully...", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private void RetrieveAndDisplayGroups() {
        myRef.child("Groups").child(currentGroupName).child("Member").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                Set<String> set = new HashSet<>();




                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    set.add(ds.getValue().toString());


                }

                list_of_groups.clear();

                list_of_groups.addAll(set);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void IntializeFields()
    {
        list_view = (ListView) groupFragmentView.findViewById(R.id.list_view);
        arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, list_of_groups);
        list_view.setAdapter(arrayAdapter);
    }


}
