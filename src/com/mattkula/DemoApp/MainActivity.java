package com.mattkula.DemoApp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity implements View.OnClickListener{

    private EditText editNoteText;
    private Button btnAddNote;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        btnAddNote = (Button)findViewById(R.id.btn_add_note);
        editNoteText = (EditText)findViewById(R.id.text_input);
        btnAddNote.setOnClickListener(this);

        startService(new Intent(this, WindowService.class));
    }

    @Override
    public void onClick(View view) {
        if(editNoteText.getText().toString().length() > 0){
            Intent i = new Intent(WindowService.ACTION);
            i.putExtra(WindowService.DATA, editNoteText.getText().toString());
            sendBroadcast(i);
        }
    }
}
