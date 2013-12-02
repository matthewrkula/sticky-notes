package com.mattkula.DemoApp.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import com.mattkula.DemoApp.R;
import com.mattkula.DemoApp.WindowService;
import com.mattkula.DemoApp.data.Note;
import com.mattkula.DemoApp.dialogs.ColorPickerDialog;

/**
 * Created with IntelliJ IDEA.
 * User: Matt
 * Date: 12/1/13
 * Time: 5:01 PM
 */
public class NewNoteFragment extends Fragment {

    private EditText editNoteText;
    private Button btnAddNote;
    private Button btnChooseColor;

    ColorPickerDialog.Color chosenColor = ColorPickerDialog.Color.COLOR_1;
    OnNewNoteAddedListener newNoteAddedListener;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().startService(new Intent(getActivity(), WindowService.class));
        newNoteAddedListener = (OnNewNoteAddedListener)getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_new_note, container, false);

        btnAddNote = (Button)v.findViewById(R.id.btn_add_note);
        editNoteText = (EditText)v.findViewById(R.id.text_input);
        btnChooseColor = (Button)v.findViewById(R.id.btn_choose_color);

        btnAddNote.setOnClickListener(clickListener);
        btnChooseColor.setOnClickListener(clickListener);

        return v;
    }

    private View.OnClickListener clickListener = new View.OnClickListener(){
        @Override
        public void onClick(View view){
            if(view == btnAddNote){
                if(editNoteText.getText().toString().length() > 0){
                    Intent i = new Intent(WindowService.ACTION);
                    i.putExtra(WindowService.DATA, editNoteText.getText().toString());
                    i.putExtra(WindowService.COLOR, chosenColor.ordinal());
                    getActivity().sendBroadcast(i);
                    newNoteAddedListener.onNewNoteAdded(new Note());
                }
            }

            if(view == btnChooseColor) {
                ColorPickerDialog diag = new ColorPickerDialog();
                diag.setColorPickerListener(colorListener);
                diag.show(getFragmentManager(), "colors");
            }
        }
    };

    private ColorPickerDialog.ColorPickerListener colorListener = new ColorPickerDialog.ColorPickerListener() {
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
    };

    private void setColorBackground(int drawable){
        btnChooseColor.setBackgroundDrawable(getResources().getDrawable(drawable));
    }


    public interface OnNewNoteAddedListener{
        public void onNewNoteAdded(Note n);
    }
}
