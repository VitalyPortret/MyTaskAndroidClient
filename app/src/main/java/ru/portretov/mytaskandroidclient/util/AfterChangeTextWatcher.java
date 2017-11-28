package ru.portretov.mytaskandroidclient.util;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * Created by adminvp on 11/28/17.
 */

public abstract class AfterChangeTextWatcher implements TextWatcher {
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {}

    @Override
    public abstract void afterTextChanged(Editable s);
}
