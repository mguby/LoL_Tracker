package com.markgubatan.loltracker.listeners;

import com.markgubatan.loltracker.Match;

/**
 * Interface that fragments will implement for UI changes when match information is retrieved.
 */
public interface OnMatchRetrievedListener {
    void onComplete(Match match);
}
