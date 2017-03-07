package com.santiagogil.a100units.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.santiagogil.a100units.R;
import com.santiagogil.a100units.pojos.Unit;

public class MainActivity extends AppCompatActivity implements FragmentMain.ActivityCommunicator{

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();

        loadMainFragment();

        loadLittleFragment();
    }

    private void loadLittleFragment() {

        Fragment littleFragment = new FragmentUnitDetail();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.little_fragment_holder, littleFragment);
        fragmentTransaction.commit();

    }

    private void loadMainFragment() {

        FragmentMain mainFragment = new FragmentMain();
        mainFragment.setActivityCommunicator(MainActivity.this);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_holder, mainFragment);
        fragmentTransaction.commit();

    }


    @Override
    public void onUnitTouched(Unit unit) {

        updateLittleFragment(unit);
    }

    private void updateLittleFragment(Unit unit){

        FragmentUnitDetail fragmentUnitDetail = new FragmentUnitDetail();
        Bundle bundle = new Bundle();
        bundle.putString("", unit.getDescription())
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.little_fragment_holder, fragmentUnitDetail);
        fragmentTransaction.commit()


    }
}
