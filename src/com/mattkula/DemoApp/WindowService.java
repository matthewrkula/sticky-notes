package com.mattkula.DemoApp;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.ArrayList;

public class WindowService extends Service {

    public static final String ACTION = "com.mattkula.stickynotes.CREATE_NOTE";
    public static final String DATA = "DATA";
    public static final String COLOR = "COLOR";

    private WindowManager windowManager;
    private BroadcastReceiver receiver;
    private ArrayList<View> notes;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        notes = new ArrayList<View>();
        windowManager = (WindowManager)getSystemService(WINDOW_SERVICE);
        receiver = new DataReceiver();

        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION);
        registerReceiver(receiver, filter);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        for(View v : notes)
            if (v != null)
                windowManager.removeView(v);
    }

    private class DataReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            final View note = View.inflate(context, R.layout.sticky_note, null);
            ((TextView)note.findViewById(R.id.note_text)).setText(intent.getStringExtra(DATA));
            TextView shadow = (TextView)note.findViewById(R.id.note_invisible_text);
            shadow.setText(intent.getStringExtra(DATA));
            View background = note.findViewById(R.id.note_text);
            switch(intent.getIntExtra(COLOR, 0)){
                case 0:
                    background.setBackgroundColor(context.getResources().getColor(R.color.color_1));
                    break;
                case 1:
                    background.setBackgroundColor(context.getResources().getColor(R.color.color_2));
                    break;
                case 2:
                    background.setBackgroundColor(context.getResources().getColor(R.color.color_3));
                    break;
                case 3:
                    background.setBackgroundColor(context.getResources().getColor(R.color.color_4));
                    break;
            }

            final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.TYPE_PHONE,
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                    PixelFormat.TRANSLUCENT
            );

            params.gravity = Gravity.TOP | Gravity.LEFT;
            params.x = 0;
            params.y = -100;

            ValueAnimator animator = ObjectAnimator.ofInt(0, 100);
            animator.setDuration(300);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    params.y = (int)(valueAnimator.getAnimatedFraction() * 100);
                    windowManager.updateViewLayout(note, params);
                }
            });

            note.setOnTouchListener(new View.OnTouchListener() {
                private int initialX;
                private int initialY;
                private float initialTouchX;
                private float initialTouchY;

                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    switch(motionEvent.getAction()){
                        case MotionEvent.ACTION_DOWN:
                            initialX = params.x;
                            initialY = params.y;
                            initialTouchX = motionEvent.getRawX();
                            initialTouchY = motionEvent.getRawY();
                            return true;
                        case MotionEvent.ACTION_UP:
                            return true;
                        case MotionEvent.ACTION_MOVE:
                            params.x = initialX + (int) (motionEvent.getRawX() - initialTouchX);
                            params.y = initialY + (int) (motionEvent.getRawY() - initialTouchY);
                            windowManager.updateViewLayout(note, params);
                            if(params.y > windowManager.getDefaultDisplay().getHeight() - 150){
                                windowManager.removeView(note);
                                notes.remove(note);
                            }
                            return true;
                    }
                    return false;
                }
            });

            notes.add(note);
            windowManager.addView(note, params);
            animator.start();
        }
    }
}