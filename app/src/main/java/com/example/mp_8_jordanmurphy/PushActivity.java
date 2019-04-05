package com.example.mp_8_jordanmurphy;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

public class PushActivity extends AppCompatActivity {


    FirebaseDatabase fbdb;
    DatabaseReference dbrf;
    int radioID = R.id.rad_ralph;
    int dbID = 404;

    private FirebaseUser user = null;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push);


        fbdb = FirebaseDatabase.getInstance();
        dbrf = fbdb.getReference();
        mAuth= FirebaseAuth.getInstance();

        EditText ID=findViewById(R.id.ID);
        EditText name=findViewById(R.id.name);
        EditText grade=findViewById(R.id.grade);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        user = mAuth.getCurrentUser();
    }

    public void radioClick(View view) {
        radioID = view.getId();
        if(radioID==R.id.rad_bart){dbID = 123;}
        if(radioID==R.id.rad_ralph){dbID = 404;}
        if(radioID==R.id.rad_milhouse){dbID = 456;}
        if(radioID==R.id.rad_lisa){dbID = 888;}
    }

    public void buttonClick(View view) {

        if (user != null) {
            EditText courseID = findViewById(R.id.ID);
            String pass = courseID.getText().toString();
            courseID.getText().clear();

            EditText courseName = findViewById(R.id.name);
            String pass1 = courseName.getText().toString();
            courseName.getText().clear();

            EditText courseGrade = findViewById(R.id.grade);
            String pass2 = courseGrade.getText().toString();
            courseGrade.getText().clear();

            Random rand = new Random();
            String grade_id = String.valueOf(rand.nextInt(10000));

            DatabaseReference passID = dbrf.child("simpsons/grades/" + grade_id + "/course_id");
            passID.setValue(Integer.parseInt(pass));

            DatabaseReference passName = dbrf.child("simpsons/grades/" + grade_id + "/course_name");
            passName.setValue(pass1);

            DatabaseReference passGrade = dbrf.child("simpsons/grades/" + grade_id + "/grade");
            passGrade.setValue(pass2);

            DatabaseReference passStudentID = dbrf.child("simpsons/grades/" + grade_id + "/student_id");
            passStudentID.setValue(dbID);


        }
        else {

            Toast.makeText(getApplicationContext(),"Something went wrong",Toast.LENGTH_SHORT).show();

        }

        startActivity(new Intent(PushActivity.this, PullActivity.class));
        // DatabaseReference stud = dbrf.child("simpsons/students/999");
        // stud.child("email").setValue("abc@def.com");
    }

}
