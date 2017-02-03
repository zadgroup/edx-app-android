package org.edx.mobile.view.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.widget.RatingBar;

import org.edx.mobile.BuildConfig;
import org.edx.mobile.R;
import org.edx.mobile.base.MainApplication;
import org.edx.mobile.module.prefs.PrefManager;

import roboguice.fragment.RoboDialogFragment;

public class RatingDialogFragment extends RoboDialogFragment {
    public static RatingDialogFragment newInstance() {
        return new RatingDialogFragment();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                .setPositiveButton(getString(R.string.submit), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int id) {
                        submit();
                    }
                })
                .setNegativeButton(android.R.string.cancel, null)
                .setView(R.layout.fragment_dialog_rating)
                .setCancelable(true)
                .create();
        return alertDialog;
    }

    public void submit() {
        final Dialog dialog = getDialog();
        final RatingBar ratingBar = (RatingBar) dialog.findViewById(R.id.ratingBar);
        // Persist rating and current version name
        PrefManager.UserPrefManager userPrefs = new PrefManager.UserPrefManager(MainApplication.application);
        userPrefs.setAppRating(ratingBar.getRating());
        userPrefs.setVersionWhenLastRated(BuildConfig.VERSION_NAME);
        // Close dialog
        dialog.dismiss();
    }
}
