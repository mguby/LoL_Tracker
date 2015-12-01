package com.markgubatan.loltracker.ui.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.markgubatan.loltracker.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private static final String BASE_URL = "http://na.leagueoflegends.com/en/news/game-updates/patch/patch-";
    private static final String URL_FOOTER = "-notes";

    public static MainActivityFragment newInstance() {
        return new MainActivityFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        WebView patchNotes = (WebView) view.findViewById(R.id.patch_notes);
        patchNotes.getSettings().setBuiltInZoomControls(true);
        patchNotes.loadUrl(BASE_URL + 522 + URL_FOOTER);
        return view;
    }
}
