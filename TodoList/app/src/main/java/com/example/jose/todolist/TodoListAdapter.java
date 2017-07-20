package com.example.jose.todolist;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.jose.todolist.data.Contract;


public class TodoListAdapter extends RecyclerView.Adapter<TodoListAdapter.ItemHolder> {

    //variables
    private Cursor cursor;
    private ItemClickListener listener;
    private String TAG = "todo list adapter";

    public TodoListAdapter(Cursor cursor, ItemClickListener listener) {
        this.cursor = cursor;
        this.listener = listener;
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item, parent, false);
        ItemHolder holder = new ItemHolder(view);
        return holder;
    }

    @Override public void onBindViewHolder(ItemHolder holder, int position) {
        holder.bind(holder, position);
    }

    @Override public int getItemCount() {
        return cursor.getCount();
    }
    public interface ItemClickListener {
        void onItemClick(int pos, String description, String duedate, String category, long id);
    }

    public void swapCursor(Cursor newCursor) {

        if (cursor != null) {
            cursor.close();
        }
        cursor = newCursor;
        if (newCursor != null) {
            this.notifyDataSetChanged();
        }
    }


    class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        //variables
        TextView descr;
        TextView due;
        TextView cat;
        String duedate;
        String description;
        String category;
        long id;


        ItemHolder(View view) {
            super(view);
            descr = (TextView) view.findViewById(R.id.description);
            due = (TextView) view.findViewById(R.id.dueDate);
            cat = (TextView) view.findViewById(R.id.category);
            view.setOnClickListener(this);
        }

        //biding all items together in the holder object
        public void bind(ItemHolder holder, int pos) {
            cursor.moveToPosition(pos);
            id = cursor.getLong(cursor.getColumnIndex(Contract.TABLE_TODO._ID));
            Log.d(TAG, "deleting id: " + id);
            duedate = cursor.getString(cursor.getColumnIndex(Contract.TABLE_TODO.COLUMN_NAME_DUE_DATE));
            description = cursor.getString(cursor.getColumnIndex(Contract.TABLE_TODO.COLUMN_NAME_DESCRIPTION));
            category = cursor.getString(cursor.getColumnIndex(Contract.TABLE_TODO.COLUMN_NAME_CATEGORY));
            descr.setText(description);
            due.setText(getFormattedDate(duedate));
            cat.setText(category);
            holder.itemView.setTag(id);
        }

        //helper mother to make date look decent and pretty
        private String getFormattedDate(String dateString) {
            try {
                SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
                Date date = sdf1.parse(dateString);
                SimpleDateFormat sdf2 = new SimpleDateFormat("EEE, MMM d, yyyy");
                return sdf2.format(date);
            } catch (ParseException e) {
                Log.e(TAG, "Error parsing date string " + dateString, e);
                return dateString;
            }
        }
        //grabs pos of item on card holder and returns
        @Override public void onClick(View v) {
            int pos = getAdapterPosition();
            listener.onItemClick(pos, description, duedate, category, id);
        }
    }

}
