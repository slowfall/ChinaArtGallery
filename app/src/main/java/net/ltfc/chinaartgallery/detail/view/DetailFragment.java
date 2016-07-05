package net.ltfc.chinaartgallery.detail.view;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.SeekBar;
import android.widget.TextView;

import net.ltfc.chinaartgallery.R;
import net.ltfc.chinaartgallery.base.Constants;
import net.ltfc.chinaartgallery.base.model.entities.Painting;
import net.ltfc.chinaartgallery.base.model.rest.CAGServerURL;
import net.ltfc.chinaartgallery.base.view.BaseFragment;
import net.ltfc.chinaartgallery.detail.di.DetailComponent;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends BaseFragment {
    private Painting painting;
    @Bind(R.id.wvContent)
    WebView wvContent;

    public static DetailFragment newInstance(Bundle args) {
        DetailFragment detailFragment = new DetailFragment();
        detailFragment.setArguments(args);
        return detailFragment;
    }

    public DetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            painting = getArguments().getParcelable(Constants.KEY_PAINTING);
        }
//        DetailComponent detailComponent = Da
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        ButterKnife.bind(this, view);
        if (painting != null) {
            wvContent.loadUrl(CAGServerURL.getPaintingOutlineURL(painting.get_id()));
        }
        initWebView(wvContent);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private void initWebView(WebView webView) {
        webView.setWebViewClient(new WebViewClient());
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);

    }

    /**
     * Implementation of a view for displaying immersive content, using system UI
     * flags to transition in and out of modes where the user is focused on that
     * content.
     */
//BEGIN_INCLUDE(content)
    public static class Content extends WebView
            implements View.OnSystemUiVisibilityChangeListener, View.OnClickListener {
        TextView mText;
        TextView mTitleView;
        SeekBar mSeekView;
        boolean mNavVisible;
        int mBaseSystemUiVisibility = SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | SYSTEM_UI_FLAG_LAYOUT_STABLE | SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
        int mLastSystemUiVis;

        Runnable mNavHider = new Runnable() {
            @Override
            public void run() {
                setNavVisibility(false);
            }
        };

        public Content(Context context, AttributeSet attrs) {
            super(context, attrs);

            setOnSystemUiVisibilityChangeListener(this);
        }

        public void init() {
            setNavVisibility(true);
        }

        public void init(TextView title, SeekBar seek) {
            // This called by the containing activity to supply the surrounding
            // state of the content browser that it will interact with.
            mTitleView = title;
            mSeekView = seek;
            setNavVisibility(true);
        }

        @Override
        public void onSystemUiVisibilityChange(int visibility) {
            // Detect when we go out of low-profile mode, to also go out
            // of full screen.  We only do this when the low profile mode
            // is changing from its last state, and turning off.
            int diff = mLastSystemUiVis ^ visibility;
            mLastSystemUiVis = visibility;
            if ((diff & SYSTEM_UI_FLAG_LOW_PROFILE) != 0
                    && (visibility & SYSTEM_UI_FLAG_LOW_PROFILE) == 0) {
                setNavVisibility(true);
            }
        }

        @Override
        protected void onWindowVisibilityChanged(int visibility) {
            super.onWindowVisibilityChanged(visibility);

            // When we become visible, we show our navigation elements briefly
            // before hiding them.
            setNavVisibility(true);
            getHandler().postDelayed(mNavHider, 2000);
        }

        @Override
        protected void onScrollChanged(int l, int t, int oldl, int oldt) {
            super.onScrollChanged(l, t, oldl, oldt);

            // When the user scrolls, we hide navigation elements.
            setNavVisibility(false);
        }

        @Override
        public void onClick(View v) {
            // When the user clicks, we toggle the visibility of navigation elements.
            int curVis = getSystemUiVisibility();
            setNavVisibility((curVis & SYSTEM_UI_FLAG_LOW_PROFILE) != 0);
        }

        void setBaseSystemUiVisibility(int visibility) {
            mBaseSystemUiVisibility = visibility;
        }

        void setNavVisibility(boolean visible) {
            int newVis = mBaseSystemUiVisibility;
            if (!visible) {
                newVis |= SYSTEM_UI_FLAG_LOW_PROFILE | SYSTEM_UI_FLAG_FULLSCREEN
                        | SYSTEM_UI_FLAG_HIDE_NAVIGATION | SYSTEM_UI_FLAG_IMMERSIVE;
            }
            final boolean changed = newVis == getSystemUiVisibility();

            // Unschedule any pending event to hide navigation if we are
            // changing the visibility, or making the UI visible.
            if (changed || visible) {
                Handler h = getHandler();
                if (h != null) {
                    h.removeCallbacks(mNavHider);
                }
            }

            // Set the new desired visibility.
            setSystemUiVisibility(newVis);
            if (mTitleView != null) {
                mTitleView.setVisibility(visible ? VISIBLE : INVISIBLE);
            }
            if (mSeekView != null) {
                mSeekView.setVisibility(visible ? VISIBLE : INVISIBLE);
            }
        }
    }
//END_INCLUDE(content)

}
