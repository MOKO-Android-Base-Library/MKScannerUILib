package com.moko.lib.scannerui.dialog;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;


import com.moko.lib.scannerui.databinding.DialogBottomBinding;

import java.util.List;

public class BottomDialog extends MokoBaseDialog<DialogBottomBinding> {
    private List<String> mDatas;
    private int mIndex;

    @Override
    protected DialogBottomBinding getViewBind(LayoutInflater inflater, ViewGroup container) {
        return DialogBottomBinding.inflate(inflater, container, false);
    }

    @Override
    protected void onCreateView() {
        mBind.wvBottom.setData(mDatas);
        mBind.wvBottom.setDefault(mIndex);
        mBind.tvCancel.setOnClickListener(v -> dismiss());
        mBind.tvConfirm.setOnClickListener(v -> {
            if (TextUtils.isEmpty(mBind.wvBottom.getSelectedText())) {
                return;
            }
            dismiss();
            final int selected = mBind.wvBottom.getSelected();
            if (listener != null) {
                listener.onValueSelected(selected);
            }
        });
        super.onCreateView();
    }

    @Override
    public float getDimAmount() {
        return 0.7f;
    }

    public void setDatas(List<String> datas, int index) {
        this.mDatas = datas;
        this.mIndex = index;
    }

    private OnBottomListener listener;

    public void setListener(OnBottomListener listener) {
        this.listener = listener;
    }

    public interface OnBottomListener {
        void onValueSelected(int value);
    }
}
