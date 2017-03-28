package com.kuena.kinket.ard_duino;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Basic extends AppCompatActivity implements ListView.OnItemClickListener{
    private ListView listView;
    private String JSON_STRING;
    private TextView text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic);

        text = (TextView)findViewById(R.id.textView3);
        listView = (ListView) findViewById(R.id.list);
        listView.setOnItemClickListener(this);
        getJSON();
    }
    private void getJSON(){
        class GetJSON extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Basic.this,"Mengambil Data","Loading...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                showEmployee();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(config.URL_GET_BASIC);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }
    private void showEmployee(){

        JSONObject jsonObject = null;
        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(config.TAG_JSON_ARRAY);
            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);
                String id = jo.getString(config.TAG_ID);
                String judul = jo.getString(config.TAG_JUDUL);
                String image = jo.getString(config.TAG_IMAGE);
                String alat_bahan = jo.getString(config.TAG_ALAT_BAHAN);
                String code = jo.getString(config.TAG_CODE);
                String langkah = jo.getString(config.TAG_LANGKAH);
                String hasil = jo.getString(config.TAG_HASIL);



                Toast.makeText(this, judul, Toast.LENGTH_SHORT).show();
                HashMap<String,String> employees = new HashMap<>();
                employees.put(config.TAG_JUDUL,judul);

                list.add(employees);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ListAdapter adapter = new SimpleAdapter(
                Basic.this, list, R.layout.filter,
                new String[]{config.TAG_JUDUL},
                new int[]{R.id.judul});

        listView.setAdapter(adapter);

    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
