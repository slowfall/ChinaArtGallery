package net.ltfc.chinaartgallery.base.rx;

import net.ltfc.chinaartgallery.common.ToastUtils;

import rx.Subscriber;

/**
 * Created by zack on 2016/4/14.
 */
public class BaseSubscriber<T> extends Subscriber<T> {
    private ToastUtils toastUtils = null;
    private OnNextListener<T> onNextListener;

    public BaseSubscriber(ToastUtils toastUtils) {
        this.toastUtils = toastUtils;
        this.onNextListener = null;
    }

    public void setOnNextListener(OnNextListener<T> onNextListener) {
        this.onNextListener = onNextListener;
    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        if (this.toastUtils != null) {
            this.toastUtils.showShort(e.getMessage());
        }
    }

    @Override
    public void onNext(T t) {
        if (onNextListener != null) {
            onNextListener.onNext(t);
        }
    }
}
