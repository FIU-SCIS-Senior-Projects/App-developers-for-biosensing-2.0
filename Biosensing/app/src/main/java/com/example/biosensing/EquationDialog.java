package com.example.biosensing;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android .view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

/**
 * Created by Zero on 3/28/2017.
 */



public class EquationDialog extends DialogFragment {

    private EditText vRef_divView;
    private EditText rTiaView;
    private double vRef_div;
    private double rTia;

    public interface EquationDialogListener {
        public void onDialogPositiveClick(DialogFragment dialog);
        //public void onDialogNegativeClick(DialogFragment dialog);
    }

    EquationDialogListener eListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.equation_settings, null);

        vRef_divView = (EditText) view.findViewById(R.id.vrefText);
        rTiaView = (EditText) view.findViewById(R.id.rtiaText);

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(view)

                .setTitle("Equation Settings")
                // Add action buttons
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        String vRefStr = vRef_divView.getText().toString();
                        String rTiaStr = rTiaView.getText().toString();


                        if(!vRefStr.isEmpty()) {
                            vRef_div = Double.parseDouble(vRefStr);
                        }

                        if(!rTiaStr.isEmpty()) {
                            rTia = Double.parseDouble(rTiaStr);
                        }
                        eListener.onDialogPositiveClick(EquationDialog.this);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //cancel
                    }
                });

        return builder.create();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            eListener = (EquationDialogListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(context.toString()
                    + " must implement NoticeDialogListener");
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            // Verify that the host activity implements the callback interface
            try {
                // Instantiate the NoticeDialogListener so we can send events to the host
                eListener = (EquationDialogListener) activity;
            } catch (ClassCastException e) {
                // The activity doesn't implement the interface, throw exception
                throw new ClassCastException(activity.toString()
                        + " must implement NoticeDialogListener");
            }
        }
    }

    public double getVref()
    {
        return vRef_div;
    }

    public double getRtia()
    {
        return rTia;
    }
}
