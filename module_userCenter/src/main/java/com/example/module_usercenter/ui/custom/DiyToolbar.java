package com.example.module_usercenter.ui.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.module_usercenter.R;


public class DiyToolbar extends RelativeLayout {

    private TextView mTitle;
    private ImageView mIcon;
    private TextView mAdd;
    private RelativeLayout mBackground;
    private OnClickListener mOnClickListener=null;
    private OnBackClickListener mOnBackClickListener=null;

    public DiyToolbar(Context context) {
        this(context,null);
    }

    public DiyToolbar(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public DiyToolbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        intView(context);
        initEvent();

    }

    private void initEvent() {

    }

    public void finishActivity(boolean isFragment) {
        mIcon.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnBackClickListener != null) {
                    if (isFragment) {
                        mOnBackClickListener.onFragmentBackClick();
                    } else {
                        mOnBackClickListener.onActivityBackClick();
                    }
                }

            }
        });
    }


    public void setOnBackClickListener(OnBackClickListener listener) {
        this.mOnBackClickListener=listener;
    }

    public interface OnBackClickListener {
        void onActivityBackClick();

        void onFragmentBackClick();
    }




    public void setTitle(String msg) {
        mTitle.setText(msg);
    }

    private void intView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_toolbar, this, true);
        mTitle=view.findViewById(R.id.toolbar_title);
        mIcon=view.findViewById(R.id.toolbar_icon);
        mAdd=view.findViewById(R.id.toolbar_add);
        mBackground = view.findViewById(R.id.base_toolbar);
    }

    public void setIcon(int icon) {
        mIcon.setImageResource(icon);
    }
    public void setTitleColor(int color) {
        mTitle.setTextColor(color);
    }

    public void setAddVis() {
        mAdd.setVisibility(VISIBLE);
    }
    public void setColorBackground(int Color) {
        mBackground.setBackgroundColor(Color);
    }


    public void addOnClick(OnClickListener listener) {
        mAdd.setOnClickListener(listener);
    }
}
