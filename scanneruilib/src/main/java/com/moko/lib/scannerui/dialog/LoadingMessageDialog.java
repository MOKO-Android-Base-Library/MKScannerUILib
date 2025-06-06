package com.moko.lib.scannerui.dialog;

import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.moko.lib.scannerui.R;
import com.moko.lib.scannerui.databinding.DialogLoadingMessageBinding;
import com.moko.lib.scannerui.view.ProgressDrawable;

import androidx.annotation.StringRes;
import androidx.core.content.ContextCompat;

public class LoadingMessageDialog extends MokoBaseDialog<DialogLoadingMessageBinding> {
    private static final int DIALOG_DISMISS_DELAY_TIME = 5000;
    public static final String TAG = LoadingMessageDialog.class.getSimpleName();
    private String message;
    private int messageId = -1;
    private boolean isAutoDismiss = true;

    @Override
    protected DialogLoadingMessageBinding getViewBind(LayoutInflater inflater, ViewGroup container) {
        return DialogLoadingMessageBinding.inflate(inflater, container, false);
    }

    @Override
    protected void onCreateView() {
        ProgressDrawable progressDrawable = new ProgressDrawable();
        progressDrawable.setColor(ContextCompat.getColor(getContext(), R.color.black_333333));
        mBind.ivLoading.setImageDrawable(progressDrawable);
        progressDrawable.start();
        if (messageId > 0) {
            message = getString(messageId);
        }
        if (TextUtils.isEmpty(message)) {
            message = getString(R.string.setting_syncing);
        }
        mBind.tvLoadingMessage.setText(message);
        if (!isAutoDismiss)
            return;
        mBind.tvLoadingMessage.postDelayed(() -> {
            if (isVisible()) {
                dismissAllowingStateLoss();
                if (callback != null) {
                    callback.onOvertimeDismiss();
                }
            }
        }, DIALOG_DISMISS_DELAY_TIME);
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (callback != null) {
            callback.onDismiss();
        }
    }

    @Override
    public int getDialogStyle() {
        return R.style.CenterDialog;
    }

    @Override
    public int getGravity() {
        return Gravity.CENTER;
    }

    @Override
    public String getFragmentTag() {
        return TAG;
    }

    @Override
    public float getDimAmount() {
        return 0.7f;
    }

    @Override
    public boolean getCancelOutside() {
        return false;
    }

    @Override
    public boolean getCancellable() {
        return true;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ((ProgressDrawable) mBind.ivLoading.getDrawable()).stop();
    }

    public void setMessage(String message) {
        this.message = message;
        if (mBind == null) return;
        mBind.tvLoadingMessage.setText(message);
    }

    public void setMessage(@StringRes int messageId) {
        this.messageId = messageId;
    }

    public void setAutoDismiss(boolean autoDismiss) {
        isAutoDismiss = autoDismiss;
    }

    private DialogDismissCallback callback;

    public void setDialogDismissCallback(final DialogDismissCallback callback) {
        this.callback = callback;
    }

    public interface DialogDismissCallback {
        void onOvertimeDismiss();

        void onDismiss();
    }
}
