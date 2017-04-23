package com.kuena.kinket.ard_duino;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;

import static com.kuena.kinket.ard_duino.R.id.imageView;
import static com.kuena.kinket.ard_duino.R.id.imageView2;

public class sh_basic extends AppCompatActivity {
private TextView Tjudul;
private TextView Timage;
private TextView Talat_bahan;
private TextView Tcode;
private TextView Tlangkah;
private ImageView imageView;
    ProgressDialog pd;

    Context context;
    private String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sh_basic);
        Intent intent = getIntent();
        id = intent.getStringExtra(config.EMP_ID);

     Tjudul=(TextView)findViewById(R.id.judul);
     Talat_bahan=(TextView)findViewById(R.id.alat);
     Tcode=(TextView)findViewById(R.id.code);
     Tlangkah=(TextView)findViewById(R.id.langkah);
        imageView = (ImageView) findViewById(R.id.imageView2);
        pd = new ProgressDialog(this);

        getEmployee();

    }
    private void getEmployee(){
        class GetEmployee extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(sh_basic.this,"Menampilkan Data...","Loading...",false,false);
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                showEmployee(s);
            }
            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(config.URL_SHOW_BASIC, id);
                return s;
            }
        }
        GetEmployee ge = new GetEmployee();
        ge.execute();
    }
    private void showEmployee(String json){
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(config.TAG_JSON_ARRAY);
            JSONObject c = result.getJSONObject(0);
            String id = c.getString(config.TAG_ID);
            String judul = c.getString(config.TAG_JUDUL);
            String image = c.getString(config.TAG_IMAGE);
            String alat_bahan = c.getString(config.TAG_ALAT_BAHAN);
            String code = c.getString(config.TAG_CODE);
            String langkah = c.getString(config.TAG_LANGKAH);

        Tjudul.setText(judul);
        Talat_bahan.setText(alat_bahan);
        Tcode.setText(code);
        Tlangkah.setText(langkah);

            new DownloadImageTask(imageView).execute(image);



        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            pd.show();
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            pd.dismiss();
            bmImage.setImageBitmap(result);
        }
    }
}
