package com.mattkula.DemoApp.fragments;

import android.R;
import android.app.ListFragment;
import android.os.Bundle;
import android.widget.ArrayAdapter;

/**
 * Created with IntelliJ IDEA.
 * User: Matt
 * Date: 12/1/13
 * Time: 5:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class NoteListFragment extends ListFragment {

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setListAdapter(new ArrayAdapter<String>(getActivity(), R.layout.simple_list_item_1, new String[]{"First", "Second", "Third", "Fourth", "Fifth"}));
    }
}
