package com.buu.se.s55160026.mywebdata_json;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    public boolean isInternetConnected(){
        ConnectivityManager connMrg = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMrg.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected()){
            return true;
        } else {
            return false;
        }
    }

    public void onButtonCheckNetworkClick(View v){
        if(isInternetConnected()){
            Toast.makeText(getApplicationContext(), "OK Network is Connect", Toast.LENGTH_SHORT).show();
        }else{
            ShowAlertNotConnectInternetWithToSetting();
        }
    }

    public void ShowAlertNotConnectInternetWithToSetting(){

        AlertDialog.Builder alertDlg = new AlertDialog.Builder(this);
        alertDlg.setMessage("Application require internet. Please on internet..")
                .setTitle("Show usage")
                .setIcon(R.drawable.ic_launcher)
                .setCancelable(false)
                .setPositiveButton("To Network Setting",
                        new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(Settings.ACTION_SETTINGS);
                                startActivityForResult(intent,0);
                            }
                        });
        alertDlg.setNegativeButton("Close",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = alertDlg.create();
        alert.show();
    }

    public void onButtonGetWebClick(View v){
        GetWebDataTask task = new GetWebDataTask();
        task.execute("http://dekdee.buu.ac.th/~55160018/551650018.php");


    }

    public String getWebText(String strUrl) {
        String strResult = "";
        try{
            URL url = new URL(strUrl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            strResult = readStreamData(con.getInputStream());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return strResult;
    }

    private String readStreamData(InputStream in){
        BufferedReader reader = null;
        StringBuilder sb = new StringBuilder();
        try {
            reader = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return sb.toString();
    }

    class GetWebDataTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            return getWebText(urls[0]);
        }


        @Override
        protected void onPostExecute(String result) {
            List<Student> myList = new ArrayList<Student>();
            String data = "";
            try {
                JSONObject json = new JSONObject(result);
                JSONArray students = json.getJSONArray("students");
                int studentCount = students.length();

                for(int j = 0; j < studentCount; j++) {
                    Student stu = new Student();
                    JSONObject student = students.getJSONObject(j);
                    String StudentName = student.getString("name");
                    int StudentId = student.getInt("id");

                    stu.setId(StudentId);
                    stu.setName(StudentName);

                    data += String.format("%d %s\n", StudentId, StudentName);

                    JSONArray grader = student.getJSONArray("grader");
                    int graderCount = grader.length();
                    String[] SubjectArr = new String[graderCount];
                    String[] GradeArr = new String[graderCount];
                    for (int i = 0; i < graderCount; i++) {
                        JSONObject grade = grader.getJSONObject(i);
                        String Subject = grade.getString("subject");
                        String Grade = grade.getString("grade");
                        SubjectArr[i] = Subject;
                        GradeArr[i] = Grade;
                        data += String.format("%s %s\n", Subject, Grade);
                    }
                    stu.setSubject(SubjectArr);
                    stu.setGrade(GradeArr);
                    myList.add(j,stu);
                }

            } catch (Exception e) {
                Log.d("TEST-Exception",data);
                e.printStackTrace();
            }

            Log.d("TEST-Data", data);
            final ListView listView = (ListView) findViewById(R.id.listView);
            ArrayAdapter<Student> adapter = new ArrayAdapter<Student>(MainActivity.this,
                    android.R.layout.simple_list_item_1, myList);
            listView.setAdapter(adapter);
            Log.d("TEST-End","end");
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1,int position, long arg3)
                {
                    //String info = ((TextView) arg1).getText().toString();
                    //Toast.makeText(getBaseContext(), "Item "+position, Toast.LENGTH_LONG).show();
                    //ArrayAdapter<Student> adapter = (ArrayAdapter<Student>) listView.getAdapter();
                    Student stud = null;
                    stud = (Student) listView.getAdapter().getItem(position);
                    Intent data = new Intent(MainActivity.this, DetailActivity.class);
                    data.putExtra("student",stud);
                    startActivity(data);
                }
            });
        }

    }
}


