package com.github.jasonwangdev.drawableview.super_view;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;


/**
 * Created by jason on 2017/7/5.
 */

public class InstantAutoCompleteTextView extends android.support.v7.widget.AppCompatAutoCompleteTextView implements View.OnClickListener {

    public InstantAutoCompleteTextView(Context context) {
        super(context);

        setOnClickListener(this);
    }

    public InstantAutoCompleteTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        setOnClickListener(this);
    }

    public InstantAutoCompleteTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        setOnClickListener(this);
    }


    @Override
    public boolean enoughToFilter() {
        return true;
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);

        showDropDownIfFocused();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        showDropDownIfFocused();
    }

    @Override
    public void onClick(View view) {
        showDropDownIfFocused();
    }


    private void showDropDownIfFocused() {
        if (isFocused() && getWindowVisibility() == View.VISIBLE)
            showDropDown();
    }

}
