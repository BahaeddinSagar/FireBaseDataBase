package com.example.firebasedatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "CHILDREN";
    FirebaseDatabase database ;
    DatabaseReference reference;
    DatabaseReference reference2;


    int i =1 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database = FirebaseDatabase.getInstance();
        database.setPersistenceEnabled(true);
        reference = database.getReference();
        reference2 = database.getReference("Readme");
      //  reference2 = database.getReference().child("Readme").child("a");
      //  reference2 = database.getReference().child("Readme/a");


        // another way to define refrence
        //reference2 = database.getReference().child("baha");
    }

    public void addContent(View view) {
        //reference.child("madeit?").setValue("we made it!");
        String key = reference.push().getKey();
        reference.child(key).setValue("Unique key" + i).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Add completed", Toast.LENGTH_SHORT).show();
                    i++;
                } else{
                    Toast.makeText(MainActivity.this, "Error adding2", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "onComplete: "+ task.getException());
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, "Error adding", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onFailure: "+e);
            }
        });



    }

    public void readContent(View view) {
        /* Read
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String key = dataSnapshot.getKey();
                String value = dataSnapshot.toString();
                TextView textView = findViewById(R.id.Contents);
                textView.setText(key+"\n"+value);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
*/

        reference2.addValueEventListener(new ValueEventListener() {
            TextView textView = findViewById(R.id.Contents);
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            for ( DataSnapshot child : dataSnapshot.getChildren()){
                Log.d(TAG, "onDataChange: "+ child.getKey() + child.getValue());

                textView.append( "\n"+ child.getKey() +" "+ child.getValue());

            }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG, "onCancelled: ");
            }
        });




    }
}
