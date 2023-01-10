package com.example.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.wifi.hotspot2.pps.HomeSp;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Home extends AppCompatActivity {
    ArrayList<String> list_date = new ArrayList<>();
    ArrayList<String> list_time = new ArrayList<>();
    ArrayList<String> list_status = new ArrayList<>();
    ArrayList<String> list_device = new ArrayList<>();


    RecyclerView rv_list3;
    RequestQueue requestQueue;
    MyAdapter myAdapter;
    String jsonResponse="";
    String device = "";


    String url_get_dataa="https://db.tinkerspace.in/Srinivas_mukka/karthik/get_history.php?device=";
//    String url_insert_data="https://db.tinkerspace.in/Srinivas_mukka/karthik/insert_data.php";

    Handler mHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        rv_list3=findViewById(R.id.rv_list3);
        requestQueue = Volley.newRequestQueue(this);
        Intent i = getIntent();
        Bundle b = i.getExtras();
        device = b.getString("device","");


        this.mHandler = new Handler();
        m_Runnable.run();
        try {
            volleyGetData();
        }catch (JSONException e){
            e.printStackTrace();
        }


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        rv_list3.setLayoutManager(linearLayoutManager);
    }

    private final Runnable m_Runnable = new Runnable()
    {
        public void run()
        {
            try {
                volleyGetData();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Home.this.mHandler.postDelayed(m_Runnable,5000);
        }
    };

    public void volleyGetData() throws JSONException
    {
        JsonArrayRequest req = new JsonArrayRequest(url_get_dataa+device,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray)
                    {
                        try {
                            jsonResponse = "";

                            list_date.clear();
                            list_time.clear();
                            list_status.clear();
                            list_device.clear();

                            for (int i = 0; i < jsonArray.length(); i++)
                            {
                                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                                list_date.add(jsonObject.getString("date"));
                                list_time.add(jsonObject.getString("time"));
                                list_device.add(jsonObject.getString("device"));
                                list_status.add(jsonObject.getString("status"));
                            }

                            myAdapter = new MyAdapter();
                            rv_list3.setAdapter(myAdapter);

                        } catch (JSONException e)
                        {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),
                                    "Error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(req);
    }
    public class MyAdapter extends RecyclerView.Adapter<Home.MyAdapter.MyViewHolder>{
        public class MyViewHolder extends RecyclerView.ViewHolder{
            public TextView tv_date,tv_time,tv_status,tv_device;

            public MyViewHolder(View itemView){
                super(itemView);
                tv_date=itemView.findViewById(R.id.tv_date);
                tv_time=itemView.findViewById(R.id.tv_time);
                tv_status=itemView.findViewById(R.id.tv_status);
                tv_device=itemView.findViewById(R.id.tv_device);
            }
        }
        @Override
        public Home.MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
            View v = getLayoutInflater().inflate(R.layout.row4,parent,false);

            Home.MyAdapter.MyViewHolder myViewHolder = new MyViewHolder(v);
            return myViewHolder;
        }

        public void onBindViewHolder(MyAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") final int position)
        {
            holder.tv_device.setText(list_device.get(position));

                holder.tv_date.setText("Date:" + list_date.get(position));
                holder.tv_time.setText("Time:" + list_time.get(position));
                holder.tv_status.setText("Status:" + list_status.get(position));
                holder.tv_device.setText("Device:" + list_device.get(position));

//            try {
//                volleyUpdateData();
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }

        }


        @Override
        public int getItemCount() {
            return list_device.size();
        }

        ;
    }
//    public void volleyUpdateData() throws JSONException {
//
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_insert_data,
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
//                                Toast.makeText(Home.this, res.getString("success"), Toast.LENGTH_SHORT).show();
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
//            @Override
//            protected Map<String, String> getParams() {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("device", st_device);
//                params.put("status", st_status);
//                return params;
//            }
//        };
//        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
//        requestQueue.add(stringRequest);
//    }



}