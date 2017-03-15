package com.santiagogil.a100units.view.onboarding;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.santiagogil.a100units.R;
import com.santiagogil.a100units.view.MainActivity;

public class OnboardingActivity extends AppCompatActivity implements LoginFragment.CallRegisterListerner {

    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseAuth mAuth;
    private FragmentManager fragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);

        fragmentManager = getSupportFragmentManager();
        mAuth = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser() == null){

            Fragment loginFragment = new LoginFragment();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_holder, loginFragment);
            fragmentTransaction.commit();

        } else{
            Intent intent = new Intent(OnboardingActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }


    @Override
    public void goToRegister(String email) {

        Fragment registerFragment = new RegisterFragment();
        Bundle bundle = new Bundle();
        bundle.putString(RegisterFragment.EMAIL, email);
        registerFragment.setArguments(bundle);
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_holder, registerFragment);
        fragmentTransaction.commit();

    }

}
