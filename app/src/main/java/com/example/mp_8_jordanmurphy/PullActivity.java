package com.example.mp_8_jordanmurphy;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import android.widget.ArrayAdapter;

public class PullActivity extends AppCompatActivity {

    Button query1Button, query2Button, pushButton, logoutButton;
    EditText IDText;
    RecyclerView myTxt;
    ListView list;
    ArrayList<String> mylist = new ArrayList<String>();
    ArrayAdapter<String> arrayAdapter;
    int studID;
    String name;
    String studentID;



    FirebaseDatabase fbdb;
    DatabaseReference dbrf;

    FirebaseAuth mAuth;
    FirebaseUser user = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pull);


        fbdb = FirebaseDatabase.getInstance();
        dbrf = fbdb.getReference();

        query1Button=findViewById(R.id.button1);
        query2Button=findViewById(R.id.query2);
        pushButton=findViewById(R.id.push);
        logoutButton=findViewById(R.id.logout);
        IDText=findViewById(R.id.userID);
        //myTxt = findViewById(R.id.RecyclerView);


        //mAuth = FirebaseAuth.getInstance();

        //fbdb = FirebaseDatabase.getInstance();
        //dbrf = fbdb.getReference();
        //IDText = findViewById(R.id.userID);
        list = (ListView)findViewById(R.id.list);
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,mylist);
        list.setAdapter(arrayAdapter);

    }

    /*public void CreateClick(View view) {

        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();

        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            Toast.makeText(getApplicationContext(),"Created Account",Toast.LENGTH_SHORT).show();
                            user = mAuth.getCurrentUser(); //The newly created user is already signed in
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"Something went wrong",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    public void LoginClick(View view) {

        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(),"Login Successful!",Toast.LENGTH_SHORT).show();
                            user = mAuth.getCurrentUser(); //The user is signed in
                        } else {
                            Toast.makeText(getApplicationContext(),"Something went wrong",Toast.LENGTH_SHORT).show();
                        }
                    }
                });


    }*/
    /*public void Query2Click(View view) {


        DatabaseReference passID = dbrf.child("simpsons/students/"+ IDText + "/password");
        passID.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot data) {
                String pass = data.getValue().toString();
                myTxt.setText(pass);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //log error
            }
        });

    }*/

    public void query2Click(View view) {



            //studID = Integer.parseInt(IDText.getText().toString());
            studentID = ((EditText) findViewById(R.id.userID)).getText().toString();
            studID = Integer.parseInt(studentID);

            //DatabaseReference gradeKey = dbrf.child("simpsons/grades/");

            //Query query = gradeKey.orderByChild("student_id").equalTo(studID);
            //query.addListenerForSingleValueEvent(valueEventListener);
            //query.addValueEventListener(valueEventListener);
            (dbrf.child("simpsons/grades")).addValueEventListener(valueEventListener);


    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            if (dataSnapshot.exists()) {
                //Toast.makeText(getApplicationContext(),"listening",Toast.LENGTH_SHORT).show();
                mylist.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Grade grade = snapshot.getValue(Grade.class);
                    Toast.makeText(getApplicationContext(),Integer.toString(studID) + Integer.toString(grade.student_id),Toast.LENGTH_SHORT).show();
                    if (grade.student_id >= studID) {
                        if (grade.student_id == 123) {
                            name = "Bart";
                        } else if (grade.student_id == 404) {

                            name = "Ralph";
                        } else if (grade.student_id == 456) {

                            name = "Milhouse";
                        } else if (grade.student_id == 888) {

                            name = "Lisa";
                        }
                        mylist.add(name + ", " + grade.course_name + ", " + grade.grade);

                    }
                }

                arrayAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            //log error

        }
    };

    public void query1Click(View view) {

        if(true) {

            int studID = Integer.parseInt(IDText.getText().toString());

            DatabaseReference gradeKey = dbrf.child("simpsons/grades/");

            Query query = gradeKey.orderByChild("student_id").equalTo(studID);
            //query.addListenerForSingleValueEvent(valueEventListener);
            //mylist = new ArrayList<String>();
            query.addValueEventListener(valueEventListener1);

        }

        else{

            Toast.makeText(getApplicationContext(),"Please login",Toast.LENGTH_SHORT).show();
        }

    }

    ValueEventListener valueEventListener1 = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            if (dataSnapshot.exists()) {
                //Toast.makeText(getApplicationContext(),"listening",Toast.LENGTH_SHORT).show();
                mylist.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Grade grade = snapshot.getValue(Grade.class);
                    mylist.add(grade.course_name + ", " + grade.grade);
                }

                arrayAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            //log error

        }
    };

    public void LogoutClick(View view) {
        mAuth.signOut();
        user =null;
        Toast.makeText(getApplicationContext(),"Logged Out",Toast.LENGTH_SHORT).show();
    }

    public void PushClick(View view) {
        startActivity(new Intent(PullActivity.this, PushActivity.class));
    }

    /*@Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        user = mAuth.getCurrentUser();
    }


    public void gradeClick(View view) {

        if(user!=null) {

            int studID = Integer.parseInt(IDText.getText().toString());

            DatabaseReference gradeKey = dbrf.child("simpsons/grades/");

            Query query = gradeKey.orderByChild("student_id").equalTo(studID);
            //query.addListenerForSingleValueEvent(valueEventListener);

        }

        else{

            Toast.makeText(getApplicationContext(),"Please login",Toast.LENGTH_SHORT).show();
        }

    }*/

    /*ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            if (dataSnapshot.exists()) {
                //Toast.makeText(getApplicationContext(),"listening",Toast.LENGTH_SHORT).show();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Grade grade = snapshot.getValue(Grade.class);
                    if(grade.getcourse_name().equals("Math")) {
                        Toast.makeText(getApplicationContext(),"Math Grade is " + grade.getgrade(),Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            //log error

        }
    };*/

}

