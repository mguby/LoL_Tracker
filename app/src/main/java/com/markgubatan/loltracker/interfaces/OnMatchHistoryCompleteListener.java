package com.markgubatan.loltracker.interfaces;

import com.markgubatan.loltracker.Match;

import java.util.List;

/**
 * Created by Mark Gubatan on 11/9/2015.
 */
public interface OnMatchHistoryCompleteListener {
    void onCompleteListener(List<Match> matches);
}
