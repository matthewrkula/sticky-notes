package com.mattkula.DemoApp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.mattkula.DemoApp.interfaces.ColorPickerListener;

public class MainActivity extends Activity implements View.OnClickListener, ColorPickerListener{

    public enum Color {
        RED, GREEN, BLUE, PURPLE
    }

    private EditText editNoteText;
    private Button btnAddNote;
    private Button btnChooseColor;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        btnAddNote = (Button)findViewById(R.id.btn_add_note);
        editNoteText = (EditText)findViewById(R.id.text_input);
        btnChooseColor = (Button)findViewById(R.id.btn_choose_color);

        btnAddNote.setOnClickListener(this);
        btnChooseColor.setOnClickListener(this);

        startService(new Intent(this, WindowService.class));
    }

    @Override
    public void onClick(View view) {
        if(view == btnAddNote){
            if(editNoteText.getText().toString().length() > 0){
                Intent i = new Intent(WindowService.ACTION);
                i.putExtra(WindowService.DATA, editNoteText.getText().toString());
                sendBroadcast(i);
            }
        }else if(view == btnChooseColor){
            ColorPickerDialog diag = new ColorPickerDialog();
            diag.show(getFragmentManager(), "colors");
        }
    }

    @Override
    public void onColorPicked(MainActivity.Color c) {
        if(c.equals(Color.RED))
            btnChooseColor.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_red));
        if(c.equals(Color.GREEN))
            btnChooseColor.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_green));
        if(c.equals(Color.BLUE))
            btnChooseColor.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_blue));
        if(c.equals(Color.PURPLE))
            btnChooseColor.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_purple));
    }

    private class ColorPickerDialog extends DialogFragment implements View.OnClickListener{

        ColorPickerListener listener;

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            listener = (ColorPickerListener) activity;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            View v = getLayoutInflater().inflate(R.layout.dialog_color_picker, null);
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
}
