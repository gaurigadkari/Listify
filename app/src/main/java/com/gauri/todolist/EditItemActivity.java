package com.gauri.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class EditItemActivity extends AppCompatActivity {
    EditText etTask;
    int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        String taskName = getIntent().getStringExtra("taskName");
        position = getIntent().getIntExtra("position",0);
        etTask = (EditText) findViewById(R.id.etEditTask);
        etTask.setText(taskName);
    }

    public void onEditTask(View view){
        EditText etEditTask = (EditText) findViewById(R.id.etEditTask);
        Intent data = new Intent();
        data.putExtra("EditedTask", etEditTask.getText().toString());
        data.putExtra("position", position);
        data.putExtra("code",2);
        setResult(RESULT_OK, data);
        this.finish();
    }


}
