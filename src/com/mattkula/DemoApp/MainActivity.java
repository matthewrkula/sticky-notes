package com.mattkula.DemoApp;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import com.mattkula.DemoApp.data.Note;
import com.mattkula.DemoApp.fragments.NewNoteFragment;
import com.mattkula.DemoApp.fragments.NoteListFragment;

/**
 * Created with IntelliJ IDEA.
 * User: Matt
 * Date: 12/1/13
 * Time: 4:41 PM
 */
public class MainActivity extends Activity implements NewNoteFragment.OnNewNoteAddedListener{

    NoteListFragment fragment;
    View shadowOverlay;

    boolean newNoteIsShowing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragment = new NoteListFragment();
        shadowOverlay = findViewById(R.id.shadow);

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.add(R.id.fragment_holder, fragment);
        ft.commit();
    }

    private void slideIn(){
        if(!newNoteIsShowing){
            View v = fragment.getView();

            PropertyValuesHolder scaleX =  PropertyValuesHolder.ofFloat("scaleX", 0.8f);
            PropertyValuesHolder scaleY =  PropertyValuesHolder.ofFloat("scaleY", 0.8f);
            ObjectAnimator anim = ObjectAnimator.ofPropertyValuesHolder(v, scaleX, scaleY);
            anim.setDuration(300);

            ObjectAnimator anim2 = ObjectAnimator.ofFloat(shadowOverlay, "alpha", 1.0f);
            anim2.setDuration(300);

            AnimatorSet set = new AnimatorSet();
            set.playTogether(anim, anim2);
            set.start();

            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.setCustomAnimations(R.animator.slide_in, 0, 0, R.animator.slide_out);
            ft.add(R.id.move_to_back_container, new NewNoteFragment());
            ft.addToBackStack(null);
            ft.commit();

            newNoteIsShowing = true;
        }
    }

    private void slideOut(){
        View v = fragment.getView();

        PropertyValuesHolder scaleX =  PropertyValuesHolder.ofFloat("scaleX", 1f);
        PropertyValuesHolder scaleY =  PropertyValuesHolder.ofFloat("scaleY", 1f);
        ObjectAnimator anim = ObjectAnimator.ofPropertyValuesHolder(v, scaleX, scaleY);
        anim.setDuration(300);

        ObjectAnimator anim2 = ObjectAnimator.ofFloat(shadowOverlay, "alpha", 0);
        anim.setDuration(300);

        AnimatorSet set = new AnimatorSet();
        set.playTogether(anim, anim2);
        set.start();

        newNoteIsShowing = false;
    }

    @Override
    public void onBackPressed() {
        if(newNoteIsShowing){
            slideOut();
        }
        super.onBackPressed();
    }

    @Override
    public void onNewNoteAdded(Note n) {
        if(newNoteIsShowing){
            getFragmentManager().popBackStack();
            slideOut();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.menu_new_note:
                slideIn();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
