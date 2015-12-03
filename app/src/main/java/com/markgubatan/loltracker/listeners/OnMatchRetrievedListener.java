package com.markgubatan.loltracker.listeners;

import com.markgubatan.loltracker.Match;

import org.json.JSONException;

import java.io.IOException;

/**
 * Interface that fragments will implement for UI changes when match information is retrieved.
 */
public interface OnMatchRetrievedListener {
    void onComplete(Match match) throws IOException, JSONException;
}
