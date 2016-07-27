package net.ltfc.chinaartgallery.detail.view;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.view.ViewPropertyAnimatorCompatSet;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;

import com.cocosw.bottomsheet.BottomSheet;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.bean.SHARE_MEDIA;

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

public class DetailActivity extends BaseActivity implements View.OnSystemUiVisibilityChangeListener, View.OnClickListener, MenuItem.OnMenuItemClickListener {
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

    private int lastSystemUiVis;

    private static final int SHOW_HIDE_ANIM_DURATION = 250;
    private static final Interpolator sHideInterpolator = new AccelerateInterpolator();
    private static final Interpolator sShowInterpolator = new DecelerateInterpolator();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);

        setContentView(R.layout.activity_detail);
        StatusBarCompat.compat(this, getResources().getColor(R.color.colorPrimaryDark));

        initializeInjector();

        ButterKnife.bind(this);
        frameLayout.setOnSystemUiVisibilityChangeListener(this);
        setSupportActionBar(toolbar);
        fab.setOnClickListener(this);
        setFabLocation(fab, getResources().getConfiguration().orientation);

        Painting painting = null;
        Bundle bundle = getIntent().getExtras();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.frameLayout, DetailFragment.newInstance(bundle), "detail").commit();

        if (bundle != null) {
            painting = bundle.getParcelable(Constants.KEY_PAINTING);
        }

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null && painting != null) {
            String paintingName = painting.getPaintingName();
            if (!TextUtils.isEmpty(paintingName)) {
                actionBar.setTitle(paintingName);
            }
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setShowHideAnimationEnabled(true);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            toastUtils.showShort("landscape");
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            toastUtils.showShort("portrait");
        }
    }

    private void initializeInjector() {
        ActivityComponent activityComponent = DaggerActivityComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .build();
        activityComponent.inject(this);
    }

    @Override
    public void onSystemUiVisibilityChange(int visibility) {
        Log.i("DetailActivity", "visibility:" + visibility);
        int diff = lastSystemUiVis ^ visibility;
        lastSystemUiVis = visibility;
        if ((diff & View.SYSTEM_UI_FLAG_HIDE_NAVIGATION) != 0
                && (visibility & View.SYSTEM_UI_FLAG_HIDE_NAVIGATION) == 0) {
            showTitleBar(appBarLayout);
            //for next version
            //fab.show();
        } else {
            hideTitleBar(appBarLayout);
            //for next version
            //fab.hide();
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
        anim.play(a);
        anim.setInterpolator(sShowInterpolator);
        anim.setDuration(SHOW_HIDE_ANIM_DURATION);
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
        anim.setDuration(SHOW_HIDE_ANIM_DURATION);
        anim.start();
    }

    private void setFabLocation(FloatingActionButton fab, int orientation) {
        //hide fab, for next version
        fab.setVisibility(View.GONE);

        int navBarHeight = navBarHeight(getResources());
        if (hasNavBar(getResources())) {
            return;
        }
        FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) fab.getLayoutParams();
        int margin = getResources().getDimensionPixelSize(R.dimen.fab_margin);
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            lp.setMargins(margin, margin, margin, margin + navBarHeight);
        } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            lp.setMargins(margin, margin, margin + navBarHeight, margin);
        }
    }

    public boolean hasNavBar(Resources resources) {
        int id = resources.getIdentifier("config_showNavigationBar", "bool", "android");
        return id > 0 && resources.getBoolean(id);
    }

    public int navBarHeight(Resources resources) {
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            return resources.getDimensionPixelSize(resourceId);
        }
        return 0;
    }

    @Override
    public void onClick(View v) {
        new BottomSheet.Builder(this)
                .sheet(R.menu.share_grid)
                .grid()
                .listener(this)
                .show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        ShareAction shareAction = new ShareAction(this);
        switch (item.getItemId()) {
            case R.id.share_weixin:
                shareAction.setPlatform(SHARE_MEDIA.WEIXIN);
                break;
            case R.id.share_weixincircle:
                shareAction.setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE);
                break;
            case R.id.share_sina_weibo:
                shareAction.setPlatform(SHARE_MEDIA.SINA);
                break;
            case R.id.share_qq:
                shareAction.setPlatform(SHARE_MEDIA.QQ);
                break;
            case R.id.share_qzone:
                shareAction.setPlatform(SHARE_MEDIA.QZONE);
                break;
        }
        toastUtils.showShort("onClick which:" + item.getTitle());
        if (shareAction.getPlatform() != null) {

        }
        return false;
    }
}
