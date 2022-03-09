package com.harman.drivinglessons;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class RegisterActivity extends AppCompatActivity {

    EditText Name,Email,Pass,ConPass;
    Button reg_button;
    ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        pb=(ProgressBar) findViewById(R.id.progressBar);
        Name=(EditText) findViewById(R.id.reg_name);
        Email=(EditText) findViewById(R.id.reg_email);
        Pass=(EditText) findViewById(R.id.reg_password);
        ConPass=(EditText) findViewById(R.id.reg_con_password);
        reg_button=(Button) findViewById(R.id.reg_button);
        reg_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                if (Name.getText().toString().equals("")||Email.getText().toString().equals("")||Pass.getText().toString().equals("")){

                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Please fill all the fields", Toast.LENGTH_SHORT);
                    toast.show();
                }

                else if(!(Pass.getText().toString().equals(ConPass.getText().toString()))){

                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Your passwords are not matching", Toast.LENGTH_SHORT);
                    toast.show();

                }
                else{

                    SecondTask backgroundTask = new SecondTask(RegisterActivity.this);
                    backgroundTask.execute("register",Name.getText().toString(),Email.getText().toString(),Pass.getText().toString());
                }
            }
        });
    }

    class SecondTask extends AsyncTask<String,Void,String> {

        String register_url = "http://localhost/login/register.php";
        Context ctx;
        Activity activity;

        public SecondTask(Context ctx){
            this.ctx=ctx;
            activity = (Activity) ctx;
        }

        @Override
        protected void onPreExecute() {

            pb.setVisibility(View.VISIBLE);

        }

        @Override
        protected String doInBackground(String... params) {
            String method = params[0];

            if(method.equals("register")){
                try {
                    URL url = new URL(register_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoInput(true);
                    httpURLConnection.setDoOutput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String name = params[1];
                    String email = params[2];
                    String password = params[3];
                    String data = URLEncoder.encode("name","UTF-8")+"="+URLEncoder.encode(name,"UTF-8")+"&"+
                            URLEncoder.encode("email","UTF-8")+"="+URLEncoder.encode(email,"UTF-8")+"&"+
                            URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(password,"UTF-8");
                    bufferedWriter.write(data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();
                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line = "";
                    while ((line=bufferedReader.readLine())!=null){
                        stringBuilder.append(line+"\n");
                    }
                    httpURLConnection.disconnect();
                    Thread.sleep(1500);
                    return stringBuilder.toString().trim();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String json) {
            try {

                pb.setVisibility(View.INVISIBLE);
                JSONObject jsonObject = new JSONObject(json);
                JSONArray jsonArray = jsonObject.getJSONArray("server_response");
                JSONObject JO = jsonArray.getJSONObject(0);
                String code = JO.getString("code");
                String message = JO.getString("message");
                if(code.equals("reg_true")){
                    Toast toast = Toast.makeText(this.activity,
                            "Registration success", Toast.LENGTH_SHORT);
                    toast.show();
                    activity.finish();
                } else if(code.equals("reg_false")){
                    Toast toast = Toast.makeText(this.activity,
                            "Registration failed", Toast.LENGTH_SHORT);
                    toast.show();
                    activity.finish();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }
}
