package com.gauri.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ItemAdapter.HandleAdapterClick, EditItemDialogFragment.OnEditCompleteListener, AddItemDialogFragment.OnAddCompleteListener {
    ArrayList<ListItem> todoItems = new ArrayList<ListItem>();
    ItemAdapter custItemAdapter;
    ListView lvItems;
    ListDatabaseHelper db;
    private static final String STATE_ITEMS = "items";
    private final int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        db = new ListDatabaseHelper(this);

        if (savedInstanceState != null) {
            todoItems = (ArrayList<ListItem>) savedInstanceState.get(STATE_ITEMS);
        }
        populateArrayItems();
        lvItems = (ListView) findViewById(R.id.lvItems);
        lvItems.setAdapter(custItemAdapter);

        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                showEditDialog(todoItems.get(position).taskName, position, todoItems.get(position).priority);
                return true;
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(STATE_ITEMS, todoItems);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_share:
                shareToDoList();
                break;
        }
        return true;
    }

    public void populateArrayItems() {
        todoItems = db.readTask();
        custItemAdapter = new ItemAdapter(this, todoItems, this);
    }

    public void shareToDoList() {
        String subject = "Listify to do list";
        String toDoList = "";
        int i;
        int size = todoItems.size();
        for (i = 0; i < size; i++) {
            toDoList = toDoList + todoItems.get(i).taskName + "\n";
        }
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, toDoList);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    private void showEditDialog(String taskName, int position, int priority) {
        FragmentManager fm = getSupportFragmentManager();
        EditItemDialogFragment editNameDialogFragment = EditItemDialogFragment.newInstance(taskName, position, priority);
        editNameDialogFragment.show(fm, "fragment_edit_name");
    }

    public void showAddDialog(View view) {
        FragmentManager fm = getSupportFragmentManager();
        AddItemDialogFragment addItemDialogFragment = new AddItemDialogFragment();
        addItemDialogFragment.show(fm, "fragment_add_name");
    }

    @Override
    public void startEdit(int position, int priority) {
        showEditDialog(todoItems.get(position).taskName, position, priority);
    }

    public void onEditComplete(String taskName, int priority, int position) {
        custItemAdapter.listItems.get(position).taskName = taskName;
        custItemAdapter.listItems.get(position).priority = priority;
        db.editTask(custItemAdapter.listItems.get(position));
        custItemAdapter.notifyDataSetChanged();
    }

    @Override
    public void onAddComplete(String taskName, int priority) {
        ListItem listItem = new ListItem(taskName, priority);
        listItem.id = db.addTask(listItem);
        custItemAdapter.listItems.add(listItem);
        custItemAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDeleteClick(int position) {
        db.deleteTask(custItemAdapter.listItems.get(position));
    }

    @Override
    public void onCheckedUnchecked(int position) {
        db.editTask(custItemAdapter.listItems.get(position));
    }
}
