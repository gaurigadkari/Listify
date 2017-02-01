package com.gauri.todolist;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class ItemAdapter extends ArrayAdapter<ListItem> {
    ArrayList<ListItem> listItems = new ArrayList<>();
    ItemClick listener;
    int priority;
    TextView displayPriority;
    public ItemAdapter(Context context, ArrayList<ListItem> listItems, ItemClick listener) {
        super(context, 0 , listItems);
        this.listItems = listItems;
        this.listener = listener;
    }
    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        final ListItem listItem = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_layout, parent, false);
        }
        // Lookup view for data population
        final TextView tvItem = (TextView) convertView.findViewById(R.id.tvItem);
        tvItem.setText(listItem.taskName);
        TextView tvPriority = (TextView) convertView.findViewById(R.id.tvPriority);
        if(listItem.priority == 0){
            tvPriority.setText("LOW");
            tvPriority.setTextColor(Color.GREEN);
        }else if(listItem.priority == 1){
            tvPriority.setText("MEDIUM");
            tvPriority.setTextColor(Color.BLUE);
        }else{
            tvPriority.setText("HIGH");
            tvPriority.setTextColor(Color.RED);
        }


        ImageView imgDelete = (ImageView) convertView.findViewById(R.id.imgDelete);
        // Populate the data into the template view using the data object
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

        convertView.findViewById(R.id.listItemContainer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.startEdit(position, listItem.priority);
            }
        });

        final CheckBox chkbox = (CheckBox) convertView.findViewById(R.id.checkBox);
        chkbox.setTag(position);
        if(listItem.isChecked){
            tvItem.setPaintFlags(tvItem.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            chkbox.setChecked(true);
        }else{
            tvItem.setPaintFlags(tvItem.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
            chkbox.setChecked(false);
        }
        chkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean flag) {
                ListItem completedItem = listItems.get(position);
                int selPosition = (int)compoundButton.getTag();
                if(flag){
//                    listItems.remove(position);
//                    listItems.add(listItems.size()-1, completedItem);
//                    listItems.get(listItems.size()-1).isChecked = true;
                    completedItem.isChecked = true;
                    /*ItemAdapter.this.remove(completedItem);
                    ItemAdapter.this.add(completedItem);*/
                    listItems.remove(selPosition);
                    listItems.add(listItems.size(), completedItem);
                    notifyDataSetChanged();
//                    ((TextView)parent.getChildAt(listItems.size()).findViewById(R.id.tvItem)).setPaintFlags(tvItem.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
//                    ((CheckBox)parent.getChildAt(listItems.size()).findViewById(R.id.checkBox)).setChecked(true);
                    //tvItem.setPaintFlags(tvItem.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    //chkbox.setChecked(true);
                }else{
                    completedItem.isChecked = false;
//                    ((TextView)parent.getChildAt(selPosition).findViewById(R.id.tvItem)).setPaintFlags(tvItem.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
//                    ((CheckBox)parent.getChildAt(selPosition).findViewById(R.id.checkBox)).setChecked(false);
                }
            }
        });
        // Return the completed view to render on screen
        return convertView;
    }

    public void setPriority() {

    }


    interface ItemClick{
        void startEdit(int position,int priority);
    }
}
