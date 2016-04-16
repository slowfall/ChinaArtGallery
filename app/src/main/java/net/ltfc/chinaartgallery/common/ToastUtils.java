package net.ltfc.chinaartgallery.common;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by zack on 2016/4/14.
 */
public class ToastUtils {
    private Context context;
    private Toast toast;

    public ToastUtils(Context context, Toast toast) {
        this.context = context;
        this.toast = toast;
    }

    public void showShort(int resId) {
        showShort(context.getText(resId));
    }

    public void showShort(CharSequence s) {
        show(s, Toast.LENGTH_SHORT);
    }

    public void showLong(int resId) {
        showLong(context.getText(resId));
    }

    public void showLong(CharSequence s) {
        show(s, Toast.LENGTH_LONG);
    }

    private void show(CharSequence s, int duration) {
        toast.cancel();
        toast.setText(s);
        toast.setDuration(duration);
        toast.show();
    }
}
