package com.buu.se.s55160026.mywebdata_json;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class DetailActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent data = getIntent();
        Student student = (Student) data.getSerializableExtra("student");
        TextView textViewId = (TextView) findViewById(R.id.textViewId);
        TextView textViewName = (TextView) findViewById(R.id.textViewName);

        textViewName.setText(student.getName().toString());
        textViewId.setText(student.getId()+"");


        String[] S = student.getSubject();
        String[] G = student.getGrade();
        int count = S.length;
        List<Grade> myL = new ArrayList<Grade>();
        try {
            for (int i = 0; i < count; i++) {
                Grade myGrade = new Grade();
                myGrade.setSubject(S[i]);
                myGrade.setGrade(G[i]);
                myL.add(i, myGrade);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        ListView listView = (ListView) findViewById(R.id.listView2);
        ArrayAdapter<Grade> adapter = new ArrayAdapter<Grade>(this, android.R.layout.simple_list_item_1, myL);
        listView.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);
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

    public void onBackClick(View v){
        finish();
    }
}
