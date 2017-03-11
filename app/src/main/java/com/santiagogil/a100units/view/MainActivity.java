package com.santiagogil.a100units.view;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.santiagogil.a100units.R;
import com.santiagogil.a100units.model.pojos.Unit;

public class MainActivity extends AppCompatActivity implements FragmentMain.ActivityCommunicator, FragmentUnitDetail.OnSaveChangesListener {

    private FragmentManager fragmentManager;
    private FragmentMain mainFragment;
    private FragmentUnitDetail fragmentUnitDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();

        loadMainFragment();

        loadLittleFragment();

    }

    private void loadLittleFragment() {

        fragmentUnitDetail = new FragmentUnitDetail();
        fragmentUnitDetail.setOnSaveChangesListener(MainActivity.this);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.little_fragment_holder, fragmentUnitDetail);
        fragmentTransaction.commit();

    }

    private void loadMainFragment() {

        mainFragment = new FragmentMain();
        mainFragment.setActivityCommunicator(MainActivity.this);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_holder, mainFragment);
        fragmentTransaction.commit();

    }



    private void updateLittleFragment(Unit unit, Integer position){

        fragmentUnitDetail = new FragmentUnitDetail();
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

    @Override
    public void onUnitTouched(Unit unit, Integer position) {

        if(fragmentUnitDetail.getOnEditMode()){
            fragmentUnitDetail.saveChanges();
        }
        updateLittleFragment(unit, position);
    }
}
