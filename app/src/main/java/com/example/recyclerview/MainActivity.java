package com.example.recyclerview;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends ActionBarActivity implements DataFinishedListener {

    private static final String TAG_DATALOADER_FRAGMENT = "dataloader_fragment";

    private DataLoaderFragment mDataLoaderFragment;
    private android.app.FragmentManager mFragmentManager;
    private RecyclerView recyclerView;
    private ItemAdapter mAdapter;
    private ItemData[] mData = new ItemData[0];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ItemAdapter(mData);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        // Reset instance state on reconfiguration
        mFragmentManager = getFragmentManager();
        if (null != savedInstanceState) {
            restoreState(savedInstanceState);
        } else {
            setupFragments();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void notifyDataLoaded(ItemData[] data) {
        mAdapter = new ItemAdapter(data);
        recyclerView.swapAdapter(mAdapter, true);
    }

    private void setupFragments() {
        installDataLoaderTaskFragment();
    }

    private void installDataLoaderTaskFragment() {
        mDataLoaderFragment = new DataLoaderFragment();
        mFragmentManager.beginTransaction()
                .add(mDataLoaderFragment, TAG_DATALOADER_FRAGMENT).commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        if(null != mDataLoaderFragment) {
            savedInstanceState.putString(TAG_DATALOADER_FRAGMENT,
                    mDataLoaderFragment.getTag());
        }

        super.onSaveInstanceState(savedInstanceState);
    }

    private void restoreState(Bundle savedInstanceState) {
        mDataLoaderFragment = (DataLoaderFragment) mFragmentManager
                .findFragmentByTag(savedInstanceState
                        .getString(TAG_DATALOADER_FRAGMENT));
    }
}
