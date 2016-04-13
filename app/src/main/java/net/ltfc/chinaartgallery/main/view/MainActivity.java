package net.ltfc.chinaartgallery.main.view;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.transition.TransitionManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.FrameLayout;

import net.ltfc.chinaartgallery.R;
import net.ltfc.chinaartgallery.base.view.BaseActivity;
import net.ltfc.chinaartgallery.base.view.StatusBarCompat;
import net.ltfc.chinaartgallery.di.components.ActivityComponent;
import net.ltfc.chinaartgallery.di.components.DaggerActivityComponent;
import net.ltfc.chinaartgallery.search.view.SearchFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements MenuItemCompat.OnActionExpandListener,
        SearchView.OnQueryTextListener, SearchView.OnCloseListener {
    private static final String BACK_STACK_PREFS = "main:prefs";
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.appBarLayout)
    AppBarLayout appBarLayout;
    @Bind(R.id.frameLayout)
    FrameLayout frameLayout;

    SearchView searchView;
    MenuItem searchMenuItem;
    MainFragment mainFragment;
    SearchFragment searchFragment;
    private boolean needRevetToInitialFragment = false;

    private ActivityComponent activityComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        StatusBarCompat.compat(this, getResources().getColor(R.color.colorPrimaryDark));
        if (savedInstanceState == null) {
            mainFragment = new MainFragment();
            getSupportFragmentManager().beginTransaction().
                    add(R.id.frameLayout, mainFragment).commit();
        }
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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        searchMenuItem = menu.findItem(R.id.action_search);
        if (searchMenuItem == null) {
            return false;
        }
        searchView = (SearchView) searchMenuItem.getActionView();
        if (searchView == null) {
            return false;
        }
        MenuItemCompat.setOnActionExpandListener(searchMenuItem, this);
        searchView.setOnQueryTextListener(this);
        searchView.setOnCloseListener(this);
        return true;
    }

    private void expandAppBar() {
        if (appBarLayout != null) {
            appBarLayout.setExpanded(true, true);
        }
    }

    @Override
    public boolean onMenuItemActionExpand(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                expandAppBar();
                switchToSearchFragmentIfNeeded();
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public boolean onMenuItemActionCollapse(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                if (needRevetToInitialFragment) {
                    revertToInitialFragment();
                }
                break;
            default:
                break;
        }
        return true;
    }

    private void switchToSearchFragmentIfNeeded() {
        if (searchFragment != null) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            appBarLayout.setElevation(getResources().getDimension(R.dimen.toolbar_elevation_default));
        }
        Fragment current = getSupportFragmentManager().findFragmentById(R.id.frameLayout);
        if (current != null && current instanceof SearchFragment) {
            searchFragment = (SearchFragment) current;
        } else {
            searchFragment = new SearchFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frameLayout, searchFragment);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                TransitionManager.beginDelayedTransition(frameLayout);
            }
            transaction.addToBackStack(MainActivity.BACK_STACK_PREFS);
            transaction.commitAllowingStateLoss();
            getSupportFragmentManager().executePendingTransactions();
        }
        needRevetToInitialFragment = true;
    }

    private void revertToInitialFragment() {
        needRevetToInitialFragment = false;
        searchFragment = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            appBarLayout.setElevation(getResources().getDimension(R.dimen.toolbar_elevation_zero));
        }
        getSupportFragmentManager().popBackStackImmediate(MainActivity.BACK_STACK_PREFS,
                FragmentManager.POP_BACK_STACK_INCLUSIVE);
        if (searchMenuItem != null) {
            searchMenuItem.collapseActionView();
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public boolean onClose() {
        return false;
    }

}
