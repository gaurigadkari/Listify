package com.gauri.todolist;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class ItemAdapter extends ArrayAdapter<ListItem> {
    ArrayList<ListItem> listItems = new ArrayList<>();
    public ItemAdapter(Context context, ArrayList<ListItem> listItems) {
        super(context, 0 , listItems);
        this.listItems = listItems;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ListItem listItem = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_layout, parent, false);
        }
        // Lookup view for data population
        TextView tvItem = (TextView) convertView.findViewById(R.id.tvItem);
        ImageView imgDelete = (ImageView) convertView.findViewById(R.id.imgDelete);
        // Populate the data into the template view using the data object
        tvItem.setText(listItem.taskName);
        imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(view.getContext(),"Testing delete", Toast.LENGTH_LONG).show();
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Confirm Delete");
                builder
                        .setMessage("Are you sure you want to delete "+listItem.taskName)
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                listItems.remove(position);
                                notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });

                // create alert dialog
                AlertDialog alertDialog = builder.create();

                // show it
                alertDialog.show();

                notifyDataSetChanged();
                // TODO: Write it to file.
            }
        });
        // Return the completed view to render on screen
        return convertView;

    }
}
