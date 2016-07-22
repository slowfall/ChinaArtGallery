package net.ltfc.chinaartgallery.detail.view;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v7.view.ViewPropertyAnimatorCompatSet;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;

import net.ltfc.chinaartgallery.R;
import net.ltfc.chinaartgallery.base.Constants;
import net.ltfc.chinaartgallery.base.model.entities.Painting;
import net.ltfc.chinaartgallery.base.view.BaseActivity;
import net.ltfc.chinaartgallery.base.view.StatusBarCompat;
import net.ltfc.chinaartgallery.common.ToastUtils;
import net.ltfc.chinaartgallery.di.components.ActivityComponent;
import net.ltfc.chinaartgallery.di.components.DaggerActivityComponent;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DetailActivity extends BaseActivity implements View.OnSystemUiVisibilityChangeListener, View.OnClickListener {
    @Inject
    ToastUtils toastUtils;
    @Bind(R.id.appBarLayout)
    AppBarLayout appBarLayout;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.frameLayout)
    FrameLayout frameLayout;
    @Bind(R.id.fab)
    FloatingActionButton fab;

    private DetailFragment detailFragment;
    private ActivityComponent activityComponent;
    private int lastSystemUiVis;

    private static final Interpolator sHideInterpolator = new AccelerateInterpolator();
    private static final Interpolator sShowInterpolator = new DecelerateInterpolator();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);

        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        frameLayout.setOnSystemUiVisibilityChangeListener(this);
        setSupportActionBar(toolbar);
        fab.setOnClickListener(this);
        StatusBarCompat.compat(this, getResources().getColor(R.color.colorPrimaryDark));
        if (savedInstanceState == null) {
            Bundle bundle = getIntent().getExtras();
            detailFragment = DetailFragment.newInstance(bundle);
            getSupportFragmentManager().beginTransaction().
                    add(R.id.frameLayout, detailFragment).commit();
            if (bundle != null) {
                Painting painting = bundle.getParcelable(Constants.KEY_PAINTING);
                if (painting != null) {
                    String paintingName = painting.getPaintingName();
                    if (!TextUtils.isEmpty(paintingName)) {
                        getSupportActionBar().setTitle(paintingName);
                    }
                }
            }
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setShowHideAnimationEnabled(true);
        initializeInjector();
    }

    private void initializeInjector() {
        activityComponent = DaggerActivityComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .build();
        activityComponent.inject(this);
    }

    @Override
    public void onSystemUiVisibilityChange(int visibility) {
        Log.i("DetailFragment", "visibility:" + visibility);
        // Detect when we go out of low-profile mode, to also go out
        // of full screen.  We only do this when the low profile mode
        // is changing from its last state, and turning off.
        int diff = lastSystemUiVis ^ visibility;
        lastSystemUiVis = visibility;
        if ((diff & View.SYSTEM_UI_FLAG_HIDE_NAVIGATION) != 0
                && (visibility & View.SYSTEM_UI_FLAG_HIDE_NAVIGATION) == 0) {
            showTitleBar(appBarLayout);
            fab.show();
        } else {
            hideTitleBar(appBarLayout);
//            appBarLayout.setVisibility(View.GONE);
            fab.hide();
        }
    }

    private void showTitleBar(View titleBar) {
        titleBar.setVisibility(View.VISIBLE);
        ViewCompat.setTranslationY(titleBar, 0f);
        float startingY = -titleBar.getHeight();
        int topLeft[] = {0, 0};
        titleBar.getLocationInWindow(topLeft);
        startingY -= topLeft[1];
        ViewCompat.setTranslationY(titleBar, startingY);
        ViewPropertyAnimatorCompatSet anim = new ViewPropertyAnimatorCompatSet();
        ViewPropertyAnimatorCompat a = ViewCompat.animate(titleBar).translationY(0f);
//        a.setUpdateListener(mUpdateListener);
        anim.play(a);
        anim.setInterpolator(sShowInterpolator);
        anim.setDuration(250);
        anim.start();
    }

    private void hideTitleBar(View titleBar) {
        ViewCompat.setAlpha(titleBar, 1f);
        ViewPropertyAnimatorCompatSet anim = new ViewPropertyAnimatorCompatSet();
        float endingY = -titleBar.getHeight();
        int topLeft[] = {0, 0};
        titleBar.getLocationInWindow(topLeft);
        endingY -= topLeft[1];
        ViewPropertyAnimatorCompat a = ViewCompat.animate(titleBar).translationY(endingY);
        anim.setInterpolator(sHideInterpolator);
        anim.setDuration(250);
//        anim.setListener(mHideListener);
//        mCurrentShowAnim = anim;
        anim.start();
    }

    @Override
    public void onClick(View v) {
        toastUtils.showShort("share");
    }
}
