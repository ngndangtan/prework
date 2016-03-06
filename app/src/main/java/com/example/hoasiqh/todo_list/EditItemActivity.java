package com.example.hoasiqh.todo_list;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

public class EditItemActivity extends AppCompatActivity {
    EditText etItemName;
    int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        String item_name = getIntent().getStringExtra("Item");
        index=getIntent().getIntExtra("index",-1);
        etItemName = (EditText)findViewById(R.id.etItem_name);
        etItemName.setText(item_name);
    }
    public void Save_edit(View view) {
        etItemName = (EditText)findViewById(R.id.etItem_name);
        String item_name = etItemName.getText().toString();
        Intent i = new Intent(EditItemActivity.this, Todo_Active.class);
        i.putExtra("name_fixed",item_name);
        i.putExtra("index", index);
        startActivityForResult(i,20);
        // closes the activity and returns to first screen
        this.finish();
    }
}
