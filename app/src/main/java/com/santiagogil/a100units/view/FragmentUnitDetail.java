package com.santiagogil.a100units.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.santiagogil.a100units.R;

import org.w3c.dom.Text;


public class FragmentUnitDetail extends Fragment {


    private ViewSwitcher viewSwitcher;
    private TextView textViewUnitDescription;
    private TextView textViewUnitID;
    private EditText editTextUnitDescripton;
    private Button buttonSaveChanges;
    private OnSaveChangesListener onSaveChangesListener;
    private Boolean onEditMode = true;
    private Bundle bundle;

    public static final String UNITDESCRIPTION = "UnitDescription";
    public static final String UNITID = "UnitID";
    public static final String UNITPOSITION = "UnitPosition";

    public void setOnSaveChangesListener(OnSaveChangesListener onSaveChangesListener) {
        this.onSaveChangesListener = onSaveChangesListener;
    }




    public FragmentUnitDetail() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_unit_detail, container, false);

        textViewUnitDescription = (TextView) view.findViewById(R.id.text_view_unit_description);
        textViewUnitID = (TextView) view.findViewById(R.id.text_view_unit_id);
        editTextUnitDescripton = (EditText) view.findViewById(R.id.edit_text_unit_description);
        buttonSaveChanges = (Button) view.findViewById(R.id.button_save_changes);
        viewSwitcher = (ViewSwitcher) view.findViewById(R.id.view_switcher);

        viewSwitcher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bundle != null){

                switchToEditView(bundle.getString(UNITDESCRIPTION));

                }else{
                    switchToEditView("");
                }
            }
        });

        updateWithInfoFromBundle();

        return view;
    }

    private void updateWithInfoFromBundle() {

        bundle = getArguments();

        if(bundle != null){

            setButtonToSwitchToEditView();

            textViewUnitID.setText("Unit " + bundle.getString(UNITID));

            String unitDescritption = bundle.getString(UNITDESCRIPTION);

            if(unitDescritption.equals("") || unitDescritption.equals(null)){

                switchToEditView(unitDescritption);

            }else {

                textViewUnitDescription.setText(unitDescritption);

            }
        } else{
            onEditMode = false;
            buttonSaveChanges.setVisibility(View.INVISIBLE);
            textViewUnitID.setText("Touch a unit");
            textViewUnitDescription.setVisibility(View.INVISIBLE);
        }
    }

    public Boolean getOnEditMode() {
        return onEditMode;
    }

    public void saveChanges() {

        onEditMode = false;
        if(bundle != null) {
            String updatedDescription = editTextUnitDescripton.getText().toString();
            onSaveChangesListener
                    .saveChanges(bundle.getInt(UNITPOSITION), updatedDescription);
            textViewUnitDescription.setText(updatedDescription);
            viewSwitcher.showPrevious();
            setButtonToSwitchToEditView();
        } else{
           Log.v("PROBLEM", "Bundle is null");
        }

    }

    private void setButtonToSwitchToEditView(){
        buttonSaveChanges.setText("Edit");
        buttonSaveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchToEditView(bundle.getString(UNITDESCRIPTION));
            }
        });
    }

    private void setButtonToSaveChanges() {

        buttonSaveChanges.setText("Save");

        buttonSaveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                saveChanges();
            }
        });

    }

    private void switchToEditView(String description) {

        onEditMode = true;
        buttonSaveChanges.setVisibility(View.VISIBLE);
        viewSwitcher.showNext();

        if (description.equals("")){
            editTextUnitDescripton.setHint("Add a Description");
        } else {
            editTextUnitDescripton.setText(description);
        }

        setButtonToSaveChanges();

    }

    public interface OnSaveChangesListener{
        void saveChanges(Integer position, String description);
    }
}
