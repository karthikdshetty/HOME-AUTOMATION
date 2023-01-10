package com.example.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {

    EditText et_phone, et_password;
    Button bt_register, bt_login;


    String url_login = "https://db.tinkerspace.in/Srinivas_mukka/karthik/register%20and%20login/login.php";
    String jsonResponse = "";
    RequestQueue requestQueue;
    Handler mHandler;
    String st_phone = "";
    String st_pw = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        et_password = findViewById(R.id.et_pass);
        et_phone = findViewById(R.id.et_phone);
        bt_register = findViewById(R.id.bt_register);
        bt_login = findViewById(R.id.bt_login);


        bt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);
            }
        });
        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                st_phone = et_phone.getText().toString().trim();
                st_pw = et_password.getText().toString().trim();

                if(st_phone.length()!=10)
                {
                    et_phone.setError("Invalid phone number");
                }

                else if(st_pw.length()<4)
                {
                    et_password.setError("PW too short");
                }

                else {
                    try {
                        volleyLogin();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }

    public void volleyLogin() throws JSONException {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_login,
                new Response.Listener<String>() {
                    JSONObject res = null;

                    @Override
                    public void onResponse(String ServerResponse) {
                        try {
                            res = new JSONObject(ServerResponse);
                            //Toast.makeText(getApplicationContext(), ServerResponse, Toast.LENGTH_SHORT).show();

                            if (res.getString("error").length() == 0)
                            {
                                Toast.makeText(Login.this, res.getString("success"), Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Login.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                            else {
                                Toast.makeText(getApplicationContext(), res.getString("error"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(getApplicationContext(), "Error! ", Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("phone", st_phone);
                params.put("pw", st_pw);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

//    public void volleyUpdateData() throws
//            JSONException {
//
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_login,
//                new Response.Listener<String>() {
//                    JSONObject res = null;
//
//                    @Override
//                    public void onResponse(String ServerResponse) {
//                        try {
//                            res = new JSONObject(ServerResponse);
//                            //Toast.makeText(getApplicationContext(), ServerResponse, Toast.LENGTH_SHORT).show();
//
//                            if (res.getString("error").length() == 0) {
//                                Toast.makeText(Login.this, "success", Toast.LENGTH_SHORT).show();
//                                Intent intent = new Intent(Login.this, MainActivity.class);
//                                startActivity(intent);
//
//                            } else {
//                                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
//                            }
//                        } catch (JSONException e) {
//                            Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError volleyError) {
//                        Toast.makeText(getApplicationContext(), "Error! ", Toast.LENGTH_LONG).show();
//                    }
//                }) {
//
//        };
//        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
//        requestQueue.add(stringRequest);
//    }
}



















