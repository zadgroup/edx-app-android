package org.edx.mobile.view.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.widget.RatingBar;

import com.google.inject.Inject;

import org.edx.mobile.BuildConfig;
import org.edx.mobile.R;
import org.edx.mobile.base.MainApplication;
import org.edx.mobile.module.prefs.PrefManager;
import org.edx.mobile.view.Router;

import roboguice.fragment.RoboDialogFragment;

public class RatingDialogFragment extends RoboDialogFragment {
    @Inject
    private Router mRouter;

    public static RatingDialogFragment newInstance() {
        return new RatingDialogFragment();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                .setPositiveButton(getString(R.string.label_submit), new DialogInterface.OnClickListener() {
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
        // Next action
        if (ratingBar.getRating() <=3 ) {
            showFeedbackDialog(getActivity());
        } else {
            showRateTheAppDialog();
        }
        // Close dialog
        dialog.dismiss();
    }

    public void showFeedbackDialog(final FragmentActivity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(R.string.feedback_dialog_title);
        builder.setMessage(R.string.feedback_dialog_message);
        builder.setPositiveButton(R.string.label_send_feedback, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Submit feedback
                mRouter.showFeedbackScreen(activity);
            }
        });
        builder.setNegativeButton(R.string.label_maybe_later, null);
        builder.create().show();
    }

    public void showRateTheAppDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(R.string.rate_app_dialog_title);
        builder.setMessage(R.string.rate_app_dialog_message);
        builder.setPositiveButton(R.string.label_rate_the_app, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Open app in market
                mRouter.openAppPageInPlayStore(((Dialog) dialog).getContext());
            }
        });
        builder.setNegativeButton(R.string.label_maybe_later, null);
        builder.create().show();
    }
}
