package com.example.recyclerview;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

public class DataLoaderFragment extends Fragment {
    private DataFinishedListener mCallback;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);
    }

    // Assign current hosting Activity to mCallback
    // Store application context for use by downloadTweets()
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // Make sure that the hosting activity has implemented
        // the correct callback interface.
        try {
            mCallback = (DataFinishedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement DownloadFinishedListener");
        }

        new DataLoaderTask().execute();
    }

    // Null out mCallback
    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }

    public class DataLoaderTask extends AsyncTask<Void, Void, ItemData[]> {

        @Override
        protected ItemData[] doInBackground(Void... params) {
            return loadData();
        }

        @Override
        protected void onPostExecute(ItemData[] itemData) {
            mCallback.notifyDataLoaded(itemData);
        }

        private ItemData[] loadData() {
            ItemData itemData[] = { new ItemData("Item 1",R.drawable.ic_number1),
                    new ItemData("Item 2",R.drawable.ic_number2),
                    new ItemData("Item 3",R.drawable.ic_number3),
                    new ItemData("Item 4",R.drawable.ic_number4)};
            return itemData;
        }
    }

}
