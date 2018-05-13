package com.sandiprai.weatheria.UI;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;

import com.sandiprai.weatheria.R;

/**
 * Created by Sandip on 5/7/2018.
 */

public class AlertDialogFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Context context = getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.error)
        .setMessage(R.string.error_msg)
        .setPositiveButton(R.string.error_ok_button,null);

        return builder.create();
    }
}
