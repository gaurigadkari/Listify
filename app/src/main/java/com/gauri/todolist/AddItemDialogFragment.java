package com.gauri.todolist;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static android.app.Activity.RESULT_OK;

public class AddItemDialogFragment extends DialogFragment {
    private EditText editText;
    private Button btnAdd;
    private ImageView cameraBttn;
    private ImageView displayImage;
    private Spinner spinnerPriority;
    int priority;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final Uri mLocationForPhotos = null;

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
                int addPriority = spinnerPriority.getSelectedItemPosition();
                addNewTask(addTask, addPriority);
                dismiss();
            }
        });
    }

    public void capturePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // getPackageManager() needs context hence calling getActivity()
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            //Bitmap thumbnail = data.("data");
            // Do other work with full size photo saved in mLocationForPhotos
            onCaptureImageResult(data);
        }
    }

    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
//        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
//        File destination = new File(Environment.getExternalStorageDirectory(),
//                System.currentTimeMillis() + ".jpg");
//        FileOutputStream fo;
//        try {
//            destination.createNewFile();
//            fo = new FileOutputStream(destination);
//            fo.write(bytes.toByteArray());
//            fo.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        cameraBttn.setImageBitmap(thumbnail);
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
        spinnerPriority.setSelection(priority);
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
        public abstract void onAddComplete(String taskName, int priority);
    }

    public void addNewTask(String addTask, int addPriority) {
        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.onAddComplete(addTask, addPriority);
    }
}
