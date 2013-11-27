package com.mattkula.DemoApp.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import com.mattkula.DemoApp.R;
import com.mattkula.DemoApp.interfaces.ColorPickerListener;

/**
 * Created with IntelliJ IDEA.
 * User: Matt
 * Date: 11/26/13
 * Time: 5:59 PM
 * To change this template use File | Settings | File Templates.
 */
public class ColorPickerDialog extends DialogFragment implements View.OnClickListener{

    public enum Color {
        RED, GREEN, BLUE, PURPLE
    }

    ColorPickerListener listener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        listener = (ColorPickerListener) activity;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_color_picker, null);
        v.findViewById(R.id.btn_color_red).setOnClickListener(this);
        v.findViewById(R.id.btn_color_blue).setOnClickListener(this);
        v.findViewById(R.id.btn_color_green).setOnClickListener(this);
        v.findViewById(R.id.btn_color_purple).setOnClickListener(this);
        builder.setView(v);
        return builder.create();
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.btn_color_red:
                listener.onColorPicked(Color.RED);
                break;
            case R.id.btn_color_green:
                listener.onColorPicked(Color.GREEN);
                break;
            case R.id.btn_color_blue:
                listener.onColorPicked(Color.BLUE);
                break;
            case R.id.btn_color_purple:
                listener.onColorPicked(Color.PURPLE);
                break;
        }
        this.getDialog().cancel();
    }
}
