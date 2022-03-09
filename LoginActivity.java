package com.harman.drivinglessons;

import androidx.activity.ComponentActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
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
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;

public class LoginActivity extends ComponentActivity {

    TextView signup_text;
    EditText email,password;
    Button log_button;
    ProgressBar pb;
    public static String PREFS_NAME="MyPrefsFile";
    private static int SPLASH_TIME_OUT=300000;

    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_login);


        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);

        String name_ = sharedPreferences.getString(KEY_NAME,null);
        String email_ = sharedPreferences.getString(KEY_EMAIL,null);

        if(email_ !=null){

            Intent intent  = new Intent(LoginActivity.this, ProfileActivity.class);
            startActivity(intent);
        }

        pb=(ProgressBar) findViewById(R.id.progressBar);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        signup_text = (TextView) findViewById(R.id.sign_up);
        signup_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });
        log_button = (Button) findViewById(R.id.login_button);
        log_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (email.getText().toString().equals("")||password.getText().toString().equals("")){
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Please fill all the fields", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else{
                      ThirdTask backgroundTask2 = new ThirdTask(LoginActivity.this);
                    backgroundTask2.execute("login",email.getText().toString(),password.getText().toString());
                }


            }
        });
    }

    class ThirdTask extends AsyncTask<String,Void,String> {

        String register_url = "http://localhost/login/register.php";
        String login_url = "http://localhost/login/login.php";
        Context ctx;
        Activity activity;
        ProgressBar progressBar;

        public ThirdTask(Context ctx){
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
                    Thread.sleep(3000);
                    return stringBuilder.toString().trim();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            else if(method.equals("login")){
                try {
                    URL url = new URL(login_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoInput(true);
                    httpURLConnection.setDoOutput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String email,password;
                    email = params[1];
                    password = params[2];
                    String data = URLEncoder.encode("email","UTF-8")+"="+URLEncoder.encode(email,"UTF-8")+"&"+
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
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (ProtocolException e) {
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
                else if(code.equals("login_true")){

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(KEY_EMAIL,email.getText().toString());
                    editor.putString(KEY_PASSWORD,password.getText().toString());
                    editor.apply();
                    Intent intent = new Intent(LoginActivity.this, ProfileActivity.class);
                    startActivity(intent);


                }else if(code.equals("login_false")){
                    Toast toast = Toast.makeText(this.activity,
                            //Toast toast = Toast.makeText(ctx,
                            "Login error", Toast.LENGTH_SHORT);
                    toast.show();
                    EditText email, password;
                    email = (EditText) activity.findViewById(R.id.email);
                    password = (EditText) activity.findViewById(R.id.password);
                    email.setText("");
                    password.setText("");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

    }

}