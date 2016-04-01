package com.google.android.gms.samples.vision.barcodereader.ui.camera;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.samples.vision.barcodereader.R;

/**
 * Created by kartik on 30/3/16.
 */


public class PlaceDialogBox extends DialogFragment {

    private Button mDestinationButton;
    private EditText mDestinationEditText;
    private String mDestination="null";
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //return super.onCreateDialog(savedInstanceState);
        final Context context = getActivity();
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_home_out, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                                      .setView(view);

        mDestinationButton= (Button) view.findViewById(R.id.destinationButton);
        mDestinationEditText = (EditText) view.findViewById(R.id.destinationEditText);




        mDestinationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*     sample text to check if the mDestinationButton is accessible
                mDestination = mDestinationEditText.getText().toString();
                Toast toast =  Toast.makeText(context,mDestination,Toast.LENGTH_LONG);
                toast.show();
                */


            }
        });




        AlertDialog dialog = builder.create();
        return dialog;
    }
}
