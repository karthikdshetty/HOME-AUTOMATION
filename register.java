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

public class Register extends AppCompatActivity {

    EditText et_phone, et_password, et_confirmpass, et_name;
    Button bt_register, bt_login;

    String url_register = "https://db.tinkerspace.in/Srinivas_mukka/karthik/register%20and%20login/register.php";

    String jsonResponse = "";
    RequestQueue requestQueue;
    Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        et_password = findViewById(R.id.et_password);
        et_name = findViewById(R.id.et_name);
        et_confirmpass = findViewById(R.id.et_confirmpass);
        et_phone = findViewById(R.id.et_phone);
        bt_register = findViewById(R.id.bt_register);


        this.mHandler = new Handler();
        m_Runnable.run();
        try {
            volleyUpdateData();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private final Runnable m_Runnable = new Runnable() {
        public void run() {
            try {
                volleyUpdateData();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Register.this.mHandler.postDelayed(m_Runnable, 5000);
        }
    };


    public void volleyUpdateData() throws JSONException {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_register,
                new Response.Listener<String>() {
                    JSONObject res = null;

                    @Override
                    public void onResponse(String ServerResponse) {
                        try {
                            res = new JSONObject(ServerResponse);
                            //Toast.makeText(getApplicationContext(), ServerResponse, Toast.LENGTH_SHORT).show();

                            if (res.getString("error").length() == 0) {
                                Toast.makeText(Register.this, res.getString("success"), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
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
                params.put("name", et_name.getText().toString().trim());
                params.put("phone", et_phone.getText().toString().trim());
                params.put("pw", et_password.getText().toString().trim());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
        bt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Register.this, Login.class);
                startActivity(intent);
            }
        });
    }

}


















