package com.example.jose.todolist;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

public class UpdateTodoFragment extends DialogFragment {

    //variables
    private EditText toDo;
    private DatePicker dp;
    private Button add;
    private Spinner categorySpinner;
    private ArrayAdapter<CharSequence> spinnerAdapter;
    private final String TAG = "update todo fragment";
    private long id;
    int year,day,month, categoryPos;
    String description, category;

    public UpdateTodoFragment() {
    }

    public static UpdateTodoFragment newInstance(int year, int month, int day, String description, String category, long id) {

        UpdateTodoFragment frag = new UpdateTodoFragment();

        Bundle args = new Bundle();
        args.putInt("year", year);
        args.putInt("month", month);
        args.putInt("day", day);
        args.putLong("id", id);
        args.putString("description", description);
        args.putString("category", category);

        frag.setArguments(args);

        return frag;
    }


    public interface OnUpdateDialogCloseListener {

        void closeUpdateDialog(int year, int month, int day, String description, String category, long id);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //re assembles the view after the update of the item-todo has been change
        View view = inflater.inflate(R.layout.fragment_to_do_adder, container, false);
        toDo = (EditText) view.findViewById(R.id.toDo);
        dp = (DatePicker) view.findViewById(R.id.datePicker);
        add = (Button) view.findViewById(R.id.add);
        categorySpinner = (Spinner) view.findViewById(R.id.category_spinner);
        spinnerAdapter = ArrayAdapter.createFromResource(this.getContext(), R.array.set_todo_category, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(spinnerAdapter);

         year = getArguments().getInt("year");
         month = getArguments().getInt("month");
         day = getArguments().getInt("day");
         id = getArguments().getLong("id");
         dp.updateDate(year, month, day);

        description = getArguments().getString("description");
        category = getArguments().getString("category");


        toDo.setText(description);
        add.setText("Update");

        add.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                UpdateTodoFragment.OnUpdateDialogCloseListener activity = (UpdateTodoFragment.OnUpdateDialogCloseListener) getActivity();
                Log.d(TAG, "id: " + id);
                activity.closeUpdateDialog(dp.getYear(), dp.getMonth(), dp.getDayOfMonth(), toDo.getText().toString(), categorySpinner.getSelectedItem().toString(), id);
                UpdateTodoFragment.this.dismiss();
            }
        });

        categoryPos = spinnerAdapter.getPosition(category);
        categorySpinner.setSelection(categoryPos);
        return view;
    }

}