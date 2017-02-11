package com.gauri.todolist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AddItemDialogFragment extends DialogFragment {
    private EditText editText;
    private Button btnAdd;
    private Spinner spinnerPriority;
    int priority, position;
    String task;
    public AddItemDialogFragment() {
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
    }

    public static interface OnAddCompleteListener {
        public abstract void onAddComplete(String taskName, int priority);
    }

    public void addNewTask(String addTask, int addPriority) {
        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.onAddComplete(addTask, addPriority);
    }
}
