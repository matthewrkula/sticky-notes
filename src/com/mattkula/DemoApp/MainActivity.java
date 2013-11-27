package com.mattkula.DemoApp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.mattkula.DemoApp.dialogs.ColorPickerDialog;
import com.mattkula.DemoApp.interfaces.ColorPickerListener;

public class MainActivity extends Activity implements View.OnClickListener, ColorPickerListener{

    private EditText editNoteText;
    private Button btnAddNote;
    private Button btnChooseColor;

    ColorPickerDialog.Color chosenColor = ColorPickerDialog.Color.RED;

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
        }else if(view == btnChooseColor){
            ColorPickerDialog diag = new ColorPickerDialog();
            diag.show(getFragmentManager(), "colors");
        }
    }

    @Override
    public void onColorPicked(ColorPickerDialog.Color c) {
        chosenColor = c;
        if(c.equals(ColorPickerDialog.Color.RED))
            btnChooseColor.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_red));
        if(c.equals(ColorPickerDialog.Color.GREEN))
            btnChooseColor.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_green));
        if(c.equals(ColorPickerDialog.Color.BLUE))
            btnChooseColor.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_blue));
        if(c.equals(ColorPickerDialog.Color.PURPLE))
            btnChooseColor.setBackgroundDrawable(getResources().getDrawable(R.drawable.circle_purple));
    }
}
