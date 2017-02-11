package com.gauri.todolist;

import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class EditItemDialogFragment extends DialogFragment {
    private EditText editText;
    private Button btnSave;
    private Spinner spinnerPriority;
    int priority, position;
    String task;
    public EditItemDialogFragment() {
    }

    public static EditItemDialogFragment newInstance(String taskName, int position, int priority) {
        EditItemDialogFragment fragment = new EditItemDialogFragment();
        Bundle extras = new Bundle();
        extras.putString("TaskName",taskName);
        extras.putInt("Position", position);
        extras.putInt("Priority", priority);
        fragment.setArguments(extras);
        return fragment;
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
                saveChanges(updatedTask, updatedPriority);
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
        task = getArguments().getString("TaskName");
        position = getArguments().getInt("Position");
        priority = getArguments().getInt("Priority");
        editText = (EditText) view.findViewById(R.id.etTask);
        editText.setText(task);
        btnSave = (Button) view.findViewById(R.id.btnSave);
        onSave();
        spinnerPriority = (Spinner) view.findViewById(R.id.spinnerPriority);
        spinnerPriority.setSelection(priority);
    }

    public static interface OnEditCompleteListener {
        public abstract void onEditComplete(String taskName, int priority, int position);
    }

    public void saveChanges(String updatedTask, int updatedPriority) {
        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.onEditComplete(updatedTask, updatedPriority, position);
    }

}
