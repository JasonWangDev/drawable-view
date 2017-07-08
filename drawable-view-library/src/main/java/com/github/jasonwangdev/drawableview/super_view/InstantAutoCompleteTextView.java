package com.github.jasonwangdev.drawableview.super_view;

import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.text.InputType;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.ListPopupWindow;
import android.widget.PopupWindow;

import java.lang.reflect.Field;


/**
 *
 * 純下拉(不能輸入)
 * 建議下拉(根據輸入的內容給予建議資料)
 *
 * Created by jason on 2017/7/5.
 */

public class InstantAutoCompleteTextView extends android.support.v7.widget.AppCompatAutoCompleteTextView {

    /*************************************** 私有介面物件宣告 ***************************************/

    private OnClickListener onClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            showDropDownIfFocused();
        }
    };

    private OnDismissListener onDismissListenerForApi17 = new OnDismissListener() {
        @Override
        public void onDismiss() { }
    };

    private PopupWindow.OnDismissListener onDismissListener = new PopupWindow.OnDismissListener() {
        @Override
        public void onDismiss() { }
    };


    /******************************************** 建構子 ********************************************/

    public InstantAutoCompleteTextView(Context context) {
        super(context);

        init();
    }

    public InstantAutoCompleteTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    public InstantAutoCompleteTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }


    /******************************************* 生命週期 *******************************************/

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        registerOnClickListener();
        registerOnDismissListener();
    }

    @Override
    protected void onDetachedFromWindow() {
        unregisterOnCLickListener();
        unregisterOnDismissListener();

        super.onDetachedFromWindow();
    }


    /******************************************* Callback ********************************************/

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
    public boolean onKeyPreIme(int keyCode, KeyEvent event) {
        // 當 Back 鍵被點擊
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP)
        {
            // 確認列表視窗已顯示
            if (isPopupShowing())
            {
                // 關閉軟體鍵盤，並停止傳送 onKeyPreIme 事件
                // P.S. 預設處理事件是按下 Back 鍵後先關閉列表視窗(我們想要的效果是先關閉鍵盤)
                InputMethodManager inputManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (inputManager.hideSoftInputFromWindow(findFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS))
                    return true;
            }
        }

        return super.onKeyPreIme(keyCode, event);
    }

    /******************************************* 公有方法 *******************************************/

    public void setCanInput(boolean canInput) {
        if (!canInput)
            setInputType(InputType.TYPE_NULL);
        else
            setInputType(InputType.TYPE_CLASS_TEXT);
    }


    /******************************************* 私有方法 *******************************************/

    private void init() {
        registerOnDismissListener();
        registerOnClickListener();

        setMaxLines(1);
        setSingleLine(true);
    }


    private void registerOnClickListener() {
        setOnClickListener(onClickListener);
    }

    private void unregisterOnCLickListener() {
        setOnClickListener(null);
    }


    private void registerOnDismissListener() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
            setOnDismissListener(onDismissListenerForApi17);
        else
            setOnDismissListenerByReflection(onDismissListener);
    }

    private void unregisterOnDismissListener() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
            setOnDismissListener(null);
        else
            setOnDismissListenerByReflection(null);
    }


    private void setOnDismissListenerByReflection(PopupWindow.OnDismissListener listener) {
        try
        {
            Field field = AutoCompleteTextView.class.getDeclaredField("mPopup");
            field.setAccessible(true);

            ListPopupWindow popupWindow = (ListPopupWindow) field.get(this);
            popupWindow.setOnDismissListener(listener);
        }
        catch (NoSuchFieldException e)
        {
            Log.e("TAG", e.getMessage());
        }
        catch (IllegalAccessException e)
        {
            Log.e("TAG", e.getMessage());
        }
    }


    private void showDropDownIfFocused() {
        if (isFocused() && getWindowVisibility() == View.VISIBLE)
            showDropDown();
    }

}
