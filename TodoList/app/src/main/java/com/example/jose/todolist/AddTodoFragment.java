package com.example.jose.todolist;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import java.util.Calendar;

public class AddTodoFragment extends DialogFragment{

    //variables
    private EditText toDo;
    private DatePicker dp;
    private Button add;
    private Spinner categorySpinner;
    private ArrayAdapter<CharSequence> spinnerAdapter;
    private final String TAG = "add todo fragment";
    Calendar c;
    int year,month,day;

    public AddTodoFragment() {
    }

    //interface
    public interface OnDialogCloseListener {
        void closeDialog(int year, int month, int day, String description, String category);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_to_do_adder, container, false);
        toDo = (EditText) view.findViewById(R.id.toDo);
        dp = (DatePicker) view.findViewById(R.id.datePicker);
        add = (Button) view.findViewById(R.id.add);

        //populates spinner connects the adapter to the spinner to connect both the string array or categories
        categorySpinner = (Spinner) view.findViewById(R.id.category_spinner);
        spinnerAdapter = ArrayAdapter.createFromResource(this.getContext(), R.array.set_todo_category, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(spinnerAdapter);

        c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        dp.updateDate(year, month, day);

        //click listener to add todo
        add.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                OnDialogCloseListener activity = (OnDialogCloseListener) getActivity();
                activity.closeDialog(dp.getYear(), dp.getMonth(), dp.getDayOfMonth(), toDo.getText().toString(), categorySpinner.getSelectedItem().toString());
                AddTodoFragment.this.dismiss();

            }
        });

        return view;
    }
}



