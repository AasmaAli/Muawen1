package com.example.muawen_asma2;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;



import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.HashMap;
import java.util.Map;


import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

public class setup extends AppCompatActivity {
    private EditText UserName , mobile ;
    private Button SaveInformationbuttion;
    private FirebaseAuth mAuth;
    private DatabaseReference UsersRef;
    String currentUserID , currentUserEmail;
    private ProgressDialog loadingBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        currentUserEmail= mAuth.getCurrentUser().getEmail();
        UsersRef = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserID);


        UserName =findViewById(R.id.name);
        mobile =findViewById(R.id.mobile);
        SaveInformationbuttion = findViewById(R.id.buttonSave);


        SaveInformationbuttion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                SaveAccountSetupInformation();
            }
        });

    }

    private void SaveAccountSetupInformation() {
        String username = UserName.getText().toString();
        String usermobile = mobile.getText().toString();

        if(username.isEmpty())
        {
            Toast.makeText(this, "رجاءً أدخل أسم المستخدم", Toast.LENGTH_SHORT).show();
        }
        if(usermobile.isEmpty())
        {
            Toast.makeText(this, "رجاءً أدخل رقم الجوال", Toast.LENGTH_SHORT).show();
        }
        if(!username.isEmpty() && !usermobile.isEmpty())
        {


            // loadingBar.setTitle("Saving Information");
           // loadingBar.setMessage("Please wait, while we are creating your new Account...");
           // loadingBar.show();
           // loadingBar.setCanceledOnTouchOutside(true);

            Map userMap = new HashMap();
            userMap.put("username", username);
            userMap.put("Email", currentUserEmail);
            userMap.put("mobile", usermobile);
            UsersRef.setValue(userMap);
            Intent mainIntent = new Intent(setup.this, MainActivity.class);
            mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(mainIntent);
            finish();

            /*
            UsersRef.updateChildren(userMap).addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task)
                {
                    if(task.isSuccessful())
                    {
                        Intent mainIntent = new Intent(setup.this, MainActivity.class);
                        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(mainIntent);
                        finish();
                        Toast.makeText(setup.this, "لقد تم أنشاء حسابك بنجاح", Toast.LENGTH_LONG).show();
                        //loadingBar.dismiss();
                    }
                    else
                    {
                        String message =  task.getException().getMessage();
                        Toast.makeText(setup.this, "لقد حدث خطاء: " + message, Toast.LENGTH_SHORT).show();
                        loadingBar.dismiss();
                    }
                }
            }); */
        }//end else

    }//Save Account Setup Information
}