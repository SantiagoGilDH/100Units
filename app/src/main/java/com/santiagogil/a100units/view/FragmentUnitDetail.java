package com.santiagogil.a100units.view;


import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
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

import yuku.ambilwarna.AmbilWarnaDialog;


public class FragmentUnitDetail extends Fragment {


    private ViewSwitcher viewSwitcher;
    private TextView textViewUnitDescription;
    private TextView textViewUnitID;
    private EditText editTextUnitDescripton;
    private Button buttonSaveChanges;
    private OnSaveChangesListener onSaveChangesListener;
    private Boolean onEditMode;
    private Bundle bundle;
    private Button buttonChooseColor;
    private TextView textViewChooseColorPrompt;

    public static final String UNITDESCRIPTION = "UnitDescription";
    public static final String UNITID = "UnitID";
    public static final String UNITPOSITION = "UnitPosition";
    public static final String UNITCOLOR = "UnitColor";

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
        onEditMode = false;

        textViewUnitDescription = (TextView) view.findViewById(R.id.text_view_unit_description);
        textViewUnitID = (TextView) view.findViewById(R.id.text_view_unit_id);
        editTextUnitDescripton = (EditText) view.findViewById(R.id.edit_text_unit_description);
        buttonSaveChanges = (Button) view.findViewById(R.id.button_save_changes);
        viewSwitcher = (ViewSwitcher) view.findViewById(R.id.view_switcher);
        buttonChooseColor = (Button) view.findViewById(R.id.button_color_chooser);
        textViewChooseColorPrompt = (TextView) view.findViewById(R.id.text_view_choose_color_prompt);

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

        buttonChooseColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showChooseColorDialog();

            }

        });

        return view;
    }

    private void showChooseColorDialog(){

        AmbilWarnaDialog dialog = new AmbilWarnaDialog(getContext(), bundle.getInt(UNITCOLOR) , new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                // color is the color selected by the user.
                onSaveChangesListener.updateUnitColor(bundle.getInt(UNITPOSITION), color);
                buttonChooseColor.setBackgroundColor(color);
            }

            @Override
            public void onCancel(AmbilWarnaDialog dialog) {
                // cancel was selected by the user
            }
        });

        dialog.show();

    }

    private void updateWithInfoFromBundle() {

        bundle = getArguments();

        if(bundle != null){

            textViewUnitID.setText("Unit " + bundle.getString(UNITID));

            String unitDescritption = bundle.getString(UNITDESCRIPTION);

            buttonChooseColor.setBackgroundColor(bundle.getInt(UNITCOLOR));

            if(unitDescritption.equals("") || unitDescritption.equals(null)){

                switchToEditView(unitDescritption);

            }else {

                textViewUnitDescription.setText(unitDescritption);
                setButtonToSwitchToEditView();
                viewSwitcher.setVisibility(View.VISIBLE);


            }
        } else{
            onEditMode = false;
            buttonSaveChanges.setVisibility(View.INVISIBLE);
            textViewUnitID.setText("Touch a unit");
            textViewUnitDescription.setVisibility(View.INVISIBLE);
            buttonChooseColor.setVisibility(View.INVISIBLE);
            textViewChooseColorPrompt.setVisibility(View.INVISIBLE);
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
                    .saveChanges(bundle.getInt(UNITPOSITION), updatedDescription, getCurrentChosenColor());
            textViewUnitDescription.setText(updatedDescription);
            viewSwitcher.setVisibility(View.VISIBLE);
            buttonChooseColor.setVisibility(View.VISIBLE);
            textViewChooseColorPrompt.setVisibility(View.VISIBLE);
            if(!updatedDescription.equals("")){
                viewSwitcher.showPrevious();
                setButtonToSwitchToEditView();
            }
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
        viewSwitcher.showNext();

        if (description.equals("")){
            editTextUnitDescripton.setHint("Add a Description");
        } else {
            editTextUnitDescripton.setText(description);
        }
        setButtonToSaveChanges();

        buttonSaveChanges.setVisibility(View.VISIBLE);
        viewSwitcher.setVisibility(View.VISIBLE);
        buttonChooseColor.setVisibility(View.VISIBLE);
        textViewChooseColorPrompt.setVisibility(View.VISIBLE);
    }

    public interface OnSaveChangesListener{
        void saveChanges(Integer position, String description, Integer color);
        void updateUnitColor(Integer position, Integer color);
    }

    public Integer getCurrentChosenColor() {

        ColorDrawable buttonColor = (ColorDrawable) buttonChooseColor.getBackground();
        return (Integer) buttonColor.getColor();

    }
}

