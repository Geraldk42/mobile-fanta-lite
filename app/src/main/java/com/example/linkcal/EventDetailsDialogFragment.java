package com.example.linkcal;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class EventDetailsDialogFragment extends DialogFragment {

    private static final String ARG_DATE = "date";
    private static final String ARG_DAY = "day";
    private static final String ARG_MONTH = "month";
    private static final String ARG_TITLE = "title";
    private static final String ARG_OWNER = "owner";

    public static EventDetailsDialogFragment newInstance(String date, String day, String month, String title, String owner) {
        EventDetailsDialogFragment fragment = new EventDetailsDialogFragment();
        Bundle args = new Bundle();
        args.putString(ARG_DATE, date);
        args.putString(ARG_DAY, day);
        args.putString(ARG_MONTH, month);
        args.putString(ARG_TITLE, title);
        args.putString(ARG_OWNER, owner);
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_event_details, null);

        TextView dateText = view.findViewById(R.id.detailsDateText);
        TextView dayText = view.findViewById(R.id.detailsDayText);
        TextView monthText = view.findViewById(R.id.detailsMonthText);
        TextView titleText = view.findViewById(R.id.detailsTitleText);
        TextView ownerText = view.findViewById(R.id.detailsOwnerText);

        Bundle args = getArguments();
        if (args != null) {
            dateText.setText(args.getString(ARG_DATE));
            dayText.setText(args.getString(ARG_DAY));
            monthText.setText(args.getString(ARG_MONTH));
            titleText.setText(args.getString(ARG_TITLE));
            ownerText.setText(getString(R.string.event_owner, args.getString(ARG_OWNER)));
        }

        builder.setView(view)
                .setPositiveButton("Close", (dialog, id) -> dialog.dismiss());

        return builder.create();
    }
}