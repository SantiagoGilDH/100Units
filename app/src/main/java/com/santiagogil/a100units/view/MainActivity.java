package com.santiagogil.a100units.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.santiagogil.a100units.R;
import com.santiagogil.a100units.pojos.Unit;

public class MainActivity extends AppCompatActivity implements FragmentMain.ActivityCommunicator, FragmentUnitDetail.OnSaveChangesListener {

    private FragmentManager fragmentManager;
    private FragmentMain mainFragment;
    private FragmentUnitDetail littleFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();

        loadMainFragment();

        loadLittleFragment();


    }

    private void loadLittleFragment() {

        littleFragment = new FragmentUnitDetail();
        littleFragment.setOnSaveChangesListener(MainActivity.this);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.little_fragment_holder, littleFragment);
        fragmentTransaction.commit();

    }

    private void loadMainFragment() {

        mainFragment = new FragmentMain();
        mainFragment.setActivityCommunicator(MainActivity.this);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_holder, mainFragment);
        fragmentTransaction.commit();

    }

    @Override
    public void onUnitTouched(Unit unit, Integer position) {

        updateLittleFragment(unit, position);
    }

    private void updateLittleFragment(Unit unit, Integer position){

        FragmentUnitDetail fragmentUnitDetail = new FragmentUnitDetail();
        fragmentUnitDetail.setOnSaveChangesListener(MainActivity.this);
        Bundle bundle = new Bundle();
        bundle.putString(FragmentUnitDetail.UNITDESCRIPTION, unit.getDescription());
        bundle.putString(FragmentUnitDetail.UNITID, unit.getID());
        bundle.putInt(FragmentUnitDetail.UNITPOSITION, position);
        fragmentUnitDetail.setArguments(bundle);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.little_fragment_holder, fragmentUnitDetail);
        fragmentTransaction.commit();

    }

    @Override
    public void saveChanges(Integer position, String description) {

        mainFragment.updateUnit(position, description);

    }
}
