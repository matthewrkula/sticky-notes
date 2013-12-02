package com.mattkula.DemoApp.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import com.mattkula.DemoApp.R;

/**
 * User: Matt
 * Date: 11/26/13
 * Time: 5:59 PM
 */

public class ColorPickerDialog extends DialogFragment implements View.OnClickListener{

    public enum Color {
        COLOR_1, COLOR_2, COLOR_3, COLOR_4
    }

    public interface ColorPickerListener {
        public void onColorPicked(ColorPickerDialog.Color c);
    }

    ColorPickerListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_color_picker, null);
        v.findViewById(R.id.btn_color_red).setOnClickListener(this);
        v.findViewById(R.id.btn_color_blue).setOnClickListener(this);
        v.findViewById(R.id.btn_color_green).setOnClickListener(this);
        v.findViewById(R.id.btn_color_purple).setOnClickListener(this);
        builder.setView(v).setTitle("Choose a color");
        return builder.create();
    }

    public void setColorPickerListener(ColorPickerListener l){
        this.listener = l;
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.btn_color_red:
                listener.onColorPicked(Color.COLOR_1);
                break;
            case R.id.btn_color_green:
                listener.onColorPicked(Color.COLOR_2);
                break;
            case R.id.btn_color_blue:
                listener.onColorPicked(Color.COLOR_3);
                break;
            case R.id.btn_color_purple:
                listener.onColorPicked(Color.COLOR_4);
                break;
        }
        this.getDialog().cancel();
    }
}
