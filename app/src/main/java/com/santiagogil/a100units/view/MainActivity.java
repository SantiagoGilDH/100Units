package com.santiagogil.a100units.view;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.santiagogil.a100units.R;
import com.santiagogil.a100units.model.pojos.Unit;

public class MainActivity extends AppCompatActivity implements FragmentUnitRecyclerView.ActivityCommunicator, FragmentUnitDetail.OnSaveChangesListener {

    private FragmentManager fragmentManager;
    private FragmentUnitRecyclerView fragmentUnitRecyclerView;
    private FragmentUnitDetail fragmentUnitDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar miToolbar = (Toolbar) findViewById(R.id.toolbar);
        miToolbar.setTitle("100Blocks");
        setSupportActionBar(miToolbar);

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

        fragmentUnitRecyclerView = new FragmentUnitRecyclerView();
        fragmentUnitRecyclerView.setActivityCommunicator(MainActivity.this);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_holder, fragmentUnitRecyclerView);
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

        fragmentUnitRecyclerView.updateUnit(position, description);

    }

    @Override
    public void onUnitTouched(Unit unit, Integer position) {

        if(fragmentUnitDetail.getOnEditMode()){
            fragmentUnitDetail.saveChanges();
        }
        updateLittleFragment(unit, position);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.switch_view_button:

                fragmentUnitRecyclerView.switchViewMode();

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
