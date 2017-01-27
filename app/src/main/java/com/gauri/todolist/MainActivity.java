package com.gauri.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<ListItem> todoItems = new ArrayList<ListItem>();
    ItemAdapter custItemAdapter;
    ListView lvItems;
    EditText etNewTask;
    private final int REQUEST_CODE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        populateArrayItems();
        lvItems = (ListView) findViewById(R.id.lvItems);
        lvItems.setAdapter(custItemAdapter);
        etNewTask = (EditText) findViewById(R.id.etNewTask);

        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                todoItems.remove(position);
                custItemAdapter.notifyDataSetChanged();
                writeItems();
                return true;
            }
        });
        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent i = new Intent(MainActivity.this, EditItemActivity.class);

                i.putExtra("taskName", todoItems.get(position).taskName);
                i.putExtra("position", position);
                startActivityForResult(i, REQUEST_CODE);
            }
        });
    }
    public void populateArrayItems(){
        readItems();
        custItemAdapter = new ItemAdapter(this, todoItems);
    }


    private void readItems() {
        File fileDir = getFilesDir();
        File file = new File(fileDir, "todo.txt");
        try {
            //todoItems = new ArrayList<String> FileUtils.readLines(file));
            ArrayList<String> toDoItemStrings = new ArrayList<String>(FileUtils.readLines(file));

            for(String temp : toDoItemStrings){
                ListItem item = new ListItem(temp);
                todoItems.add(item);
            }

        }catch (IOException e) {

        }
    }
    private void writeItems() {
        File fileDir = getFilesDir();
        File file = new File(fileDir, "todo.txt");
        try {
            FileUtils.writeLines(file, todoItems);

        }catch (IOException e) {

        }
    }
    public void onAddTask(View view) {
//        aToDoAdapter.add(etNewTask.getText().toString());
        ListItem addListItem = new ListItem(etNewTask.getText().toString());
        todoItems.add(addListItem);
        custItemAdapter.notifyDataSetChanged();
        etNewTask.setText("");
        writeItems();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            String name = data.getExtras().getString("EditedTask");
            int position = data.getExtras().getInt("position");
            int code = data.getExtras().getInt("code", 0);
            Toast.makeText(this, name, Toast.LENGTH_SHORT).show();
            ListItem setListItem = new ListItem(name);
            todoItems.set(position, setListItem);
            writeItems();
            custItemAdapter.notifyDataSetChanged();
        }
    }
}
