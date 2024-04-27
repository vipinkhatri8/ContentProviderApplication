package com.example.contentproviderapplication;

import android.content.ContentResolver;
import android.os.Bundle;

import androidx.fragment.app.ListFragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import java.util.List;

/**
 * Created by Balraj on 12/11/16.
 */
public class CountryListFragment extends ListFragment implements LoaderManager.LoaderCallbacks<List<Country>> {

    private static final String LOG_TAG = CountryListFragment.class.getSimpleName();
    private CountryCustomAdapter mAdapter;
    private static final int LOADER_ID = 1;
    private ContentResolver mContentResolver;
    private List<Country> mCountry;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        mContentResolver = getActivity().getContentResolver();
        mAdapter = new CountryCustomAdapter(getActivity(), getChildFragmentManager());
        setEmptyText("No Country");
        setListAdapter(mAdapter);
        setListShown(false);

        getLoaderManager().initLoader(LOADER_ID, null, this);
    }

    @Override
    public Loader<List<Country>> onCreateLoader(int i, Bundle args) {
        mContentResolver = getActivity().getContentResolver();
        return new CountryListLoader(getActivity(), CountryContract.URI_TABLE, mContentResolver);
    }

    @Override
    public void onLoadFinished(Loader<List<Country>> loader, List<Country> Country) {
        mAdapter.setData(Country);
        mCountry = Country;
        if (isResumed()) {
            setListShown(true);
        } else {
            setListShownNoAnimation(true);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Country>> loader) {
        mAdapter.setData(null);
    }

}