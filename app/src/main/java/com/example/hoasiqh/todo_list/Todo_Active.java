package com.example.hoasiqh.todo_list;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Todo_Active extends AppCompatActivity {
    ArrayList<String> items;
    ArrayAdapter<String> itemsAdapter;
    ListView lvItems;
    EditText etNewItems;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo__active);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        lvItems = (ListView) findViewById(R.id.lvItem);
        items = new ArrayList<>();
        readItem();
        String item_name = getIntent().getStringExtra("name_fixed");
        int i=getIntent().getIntExtra("index", -1);
        if(i!=-1){
            items.set(i,item_name);
        }
        Toast.makeText(this,item_name,Toast.LENGTH_SHORT).show();
        itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        lvItems.setAdapter(itemsAdapter);

        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                items.remove(position);
                itemsAdapter.notifyDataSetChanged();
                writeItem();
                return true;
            }
        });

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                launchComposeView(lvItems,position);
            }
        });
    }

    private void readItem() {
        File fileDir = getFilesDir();
        File file = new File(fileDir, "todo.txt");
        try {
            items = new ArrayList<String>(FileUtils.readLines(file));
        } catch (IOException e) {

        }
    }

    private void writeItem() {
        File fileDir = getFilesDir();
        File file = new File(fileDir, "todo.txt");
        try {
            FileUtils.writeLines(file, items);
        } catch (IOException e) {
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_todo__active, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void on_New_Item(View view) {
        String sFieldValue;
        etNewItems = (EditText) findViewById(R.id.etNewItem);
        sFieldValue = etNewItems.getText().toString();
        if (sFieldValue.isEmpty()) {
            Toast.makeText(this, "Please type name of Task", Toast.LENGTH_SHORT).show();
        } else {
            items.add(sFieldValue);
            etNewItems.setText("");
        }
        writeItem();

    }
    public void launchComposeView(ListView lv,int pos) {
        // first parameter is the context, second is the class of the activity to launch
        String str = lv.getItemAtPosition(pos).toString();
        int index =pos;
        Intent i = new Intent(Todo_Active.this, EditItemActivity.class);
        i.putExtra("Item",str);
        i.putExtra("index",index);
        startActivity(i); // brings up the second activity
    }

}
