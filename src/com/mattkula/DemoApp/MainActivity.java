package com.mattkula.DemoApp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.mattkula.DemoApp.dialogs.ColorPickerDialog;

public class MainActivity extends Activity implements View.OnClickListener, ColorPickerDialog.ColorPickerListener{

    private EditText editNoteText;
    private Button btnAddNote;
    private Button btnChooseColor;

    ColorPickerDialog.Color chosenColor = ColorPickerDialog.Color.COLOR_1;

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
                i.putExtra(WindowService.COLOR, chosenColor.ordinal());
                sendBroadcast(i);
            }
        }

        if(view == btnChooseColor) {
            ColorPickerDialog diag = new ColorPickerDialog();
            diag.show(getFragmentManager(), "colors");
        }
    }

    @Override
    public void onColorPicked(ColorPickerDialog.Color c) {
        chosenColor = c;
        if(c.equals(ColorPickerDialog.Color.COLOR_1))
            setColorBackground(R.drawable.circle_color_1);
        if(c.equals(ColorPickerDialog.Color.COLOR_2))
            setColorBackground(R.drawable.circle_color_2);
        if(c.equals(ColorPickerDialog.Color.COLOR_3))
            setColorBackground(R.drawable.circle_color_3);
        if(c.equals(ColorPickerDialog.Color.COLOR_4))
            setColorBackground(R.drawable.circle_color_4);
    }

    private void setColorBackground(int drawable){
        btnChooseColor.setBackgroundDrawable(getResources().getDrawable(drawable));
    }
}
