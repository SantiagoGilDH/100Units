package com.santiagogil.a100units.view.onboarding;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.santiagogil.a100units.R;
import com.santiagogil.a100units.utils.DatabaseHelper;
import com.santiagogil.a100units.view.MainActivity;

public class LoginFragment extends Fragment {

    private EditText editTextEmailField;
    private EditText editTextPasswordField;

    private Button buttonLogin;
    private Button buttonRegister;
    private Button buttonAnonymous;

    private DatabaseReference firebase;
    private FirebaseAuth mAuth;

    private FirebaseAuth.AuthStateListener authStateListener;

    private static final int RC_SIGN_IN = 1;
    private GoogleApiClient googleApiClient;
    private static final String TAG = "LoginActivity";
    private ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        firebase = FirebaseDatabase.getInstance().getReference().child(DatabaseHelper.TABLEUSERS);

        mAuth = FirebaseAuth.getInstance();

        mAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

            }
        });

        editTextEmailField = (EditText) view.findViewById(R.id.email);
        editTextPasswordField = (EditText) view.findViewById(R.id.password);
        buttonLogin = (Button) view.findViewById(R.id.email_sign_in_button);
        buttonRegister = (Button) view.findViewById(R.id.register_button);
        buttonAnonymous = (Button) view.findViewById(R.id.anonymous_button);

        progressDialog = new ProgressDialog(getContext());

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startSingIn();
            }
        });

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CallRegisterListerner callRegisterListerner = (CallRegisterListerner) getActivity();
                callRegisterListerner.goToRegister(editTextEmailField.getText().toString());
            }
        });

        return view;
    }




    private void startSingIn(){

        String email = editTextEmailField.getText().toString();
        String password = editTextPasswordField.getText().toString();

        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){

            Toast.makeText(getContext(), "Fields Are Empty", Toast.LENGTH_SHORT).show();

        } else{

            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if(!task.isSuccessful()){
                        Toast.makeText(getContext(), "Sign In Problem", Toast.LENGTH_LONG).show();
                    } else {
                        checkIfUserExists();
                    }
                }
            });
        }


    }

    private void checkIfUserExists(){

        if(mAuth.getCurrentUser() != null){

            final String userId = mAuth.getCurrentUser().getUid();

            firebase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.hasChild(userId)){

                        Intent mainIntent = new Intent(getContext(), MainActivity.class);
                        mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(mainIntent);

                    } else{
                        Toast.makeText(getContext(), "Login Error", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

    public interface CallRegisterListerner{
        void goToRegister(String string);
    }

    private void signInAnonymously(){
        mAuth.signInAnonymously()
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInAnonymously:onComplete:" + task.isSuccessful());

                        if(task.isSuccessful()){

                            String user_id = mAuth.getCurrentUser().getUid();

                            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
                            DatabaseReference currentUserDB = databaseReference.child(user_id);

                            currentUserDB.child("image").setValue("default");

                            Intent intent = new Intent(getContext(), MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }

                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInAnonymously", task.getException());
                            Toast.makeText(getContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }
}
