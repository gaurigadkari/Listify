package com.gauri.todolist;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

public class EditItemDialogFragment extends DialogFragment {
    private EditText editText;
    private Button btnSave;
    private Spinner spinnerPriority;
    int priority, position;
    String task, imagePath;
    String mCurrentPhotoPath;
    private ImageView cameraBttn;
    private DatePicker datePicker;
    String month, year, day, date;

    public EditItemDialogFragment() {
    }

    public static EditItemDialogFragment newInstance(String taskName, int position, int priority, String imageURL, String dueDate) {
        EditItemDialogFragment fragment = new EditItemDialogFragment();
        Bundle extras = new Bundle();
        extras.putString("TaskName",taskName);
        extras.putInt("Position", position);
        extras.putInt("Priority", priority);
        extras.putString("ImageUrl",imageURL);
        extras.putString("DueDate",dueDate);
        fragment.setArguments(extras);
        return fragment;
    }

    @Override
    public void onStart()
    {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null)
        {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }
    }


    private void setPic() {
        // Get the dimensions of the View
        mCurrentPhotoPath = imagePath;
        int targetW = cameraBttn.getWidth();
        int targetH = cameraBttn.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        cameraBttn.setImageBitmap(bitmap);
    }

    public void onSave() {
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String updatedTask = editText.getText().toString();

                if(updatedTask.isEmpty()) {
                    Toast.makeText((EditItemDialogFragment.this).getContext(), "Task name cant be empty",Toast.LENGTH_LONG).show();
                    return;
                }
                int updatedPriority = spinnerPriority.getSelectedItemPosition();
                int day = datePicker.getDayOfMonth();
                int month = datePicker.getMonth() + 1;
                int year = datePicker.getYear();

                //tvDueDate.setText(listItem.date.toString());
                String date = month + "/" + day + "/" + year;
                saveChanges(updatedTask, updatedPriority, date);
                dismiss();
            }
        });
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.edit_item_dialog_fragment,container);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        cameraBttn = (ImageView) view.findViewById(R.id.editImage);
        task = getArguments().getString("TaskName");
        position = getArguments().getInt("Position");
        priority = getArguments().getInt("Priority");
        imagePath = getArguments().getString("ImageUrl");
        date = getArguments().getString("DueDate");
        String[] dateArr = date.split("/");

        editText = (EditText) view.findViewById(R.id.etTask);
        editText.setText(task);
        btnSave = (Button) view.findViewById(R.id.btnSave);
        onSave();
        spinnerPriority = (Spinner) view.findViewById(R.id.spinnerPriority);
        spinnerPriority.setSelection(priority);
        datePicker = (DatePicker) view.findViewById(R.id.datePicker);
        datePicker.updateDate(Integer.parseInt(dateArr[2]), Integer.parseInt(dateArr[0]) - 1, Integer.parseInt(dateArr[1]));

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do something after 100ms
                if(imagePath!=null){
                    setPic();
                }
            }
        }, 10);


    }

    public static interface OnEditCompleteListener {
        public abstract void onEditComplete(String taskName, int priority, int position, String date);
    }

    public void saveChanges(String updatedTask, int updatedPriority, String date) {
        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.onEditComplete(updatedTask, updatedPriority, position, date);
    }

}
