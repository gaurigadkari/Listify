package com.gauri.todolist;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.app.Activity.RESULT_OK;

public class AddItemDialogFragment extends DialogFragment {
    private EditText editText;
    private Button btnAdd;
    private ImageView cameraBttn;
    private ImageView displayImage;
    private Spinner spinnerPriority;
    DatePicker datePicker;
    //int priority;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final Uri mLocationForPhotos = null;
    String mCurrentPhotoPath;
    String date;
    public AddItemDialogFragment() {

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


    public void onSave() {
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String addTask = editText.getText().toString();
                if(addTask.isEmpty()) {
                    Toast.makeText((AddItemDialogFragment.this).getContext(), "Task name cant be empty",Toast.LENGTH_LONG).show();
                    return;
                }

                //Due Date


                int day = datePicker.getDayOfMonth();
                int month = datePicker.getMonth() + 1;
                int year = datePicker.getYear();

                //tvDueDate.setText(listItem.date.toString());
                date = month + "/" + day + "/" + year;


                int addPriority = spinnerPriority.getSelectedItemPosition();
                addNewTask(addTask, addPriority, mCurrentPhotoPath,date);
                dismiss();
            }
        });
    }

    public void capturePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // getPackageManager() needs context hence calling getActivity()
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = Uri.fromFile(photoFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            //Bitmap thumbnail = data.("data");
            // Do other work with full size photo saved in mLocationForPhotos
            setPic();
        }
    }


    private void setPic() {
        // Get the dimensions of the View
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



    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
//        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.add_item_dialog_fragment,container);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        editText = (EditText) view.findViewById(R.id.etAddTask);
        btnAdd = (Button) view.findViewById(R.id.btnAdd);
        onSave();
        spinnerPriority = (Spinner) view.findViewById(R.id.addSpinnerPriority);
        datePicker = (DatePicker) view.findViewById (R.id.addDatePicker);
        //spinnerPriority.setSelection(priority);
        cameraBttn = (ImageView) view.findViewById(R.id.chooseImage);
//        displayImage = (ImageView) view.findViewById(R.id.displayImage);
        cameraBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                capturePhoto();
            }
        });
    }

    public static interface OnAddCompleteListener {
        public abstract void onAddComplete(String taskName, int priority, String imagePath, String date);
    }

    public void addNewTask(String addTask, int addPriority, String imagePath, String date) {
        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.onAddComplete(addTask, addPriority, imagePath, date);
    }
}
