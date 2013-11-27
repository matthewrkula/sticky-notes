package com.mattkula.DemoApp.interfaces;

import com.mattkula.DemoApp.dialogs.ColorPickerDialog;

/**
 * Created with IntelliJ IDEA.
 * User: Matt
 * Date: 11/26/13
 * Time: 5:40 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ColorPickerListener {
    public void onColorPicked(ColorPickerDialog.Color c);
}