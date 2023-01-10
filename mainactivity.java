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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    ArrayList<String> list_name = new ArrayList<>();
    ArrayList<String> list_value = new ArrayList<>();
    String st_sensor = "";
    String st_data = "";
    Button bt_button;
    RecyclerView rv_list2;
    MyAdapter myAdapter;
    String url_get_data = "https://db.tinkerspace.in/Srinivas_mukka/karthik/get_data.php";
    String url_update_data = "https://db.tinkerspace.in/Srinivas_mukka/karthik/update_data.php";
    String jsonResponse = "";
    RequestQueue requestQueue;
    Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestQueue = Volley.newRequestQueue(this);
        rv_list2 = findViewById(R.id.rv_list2);

        this.mHandler = new Handler();
        m_Runnable.run();
        try {
            volleyGetData();
        } catch (JSONException e) {
            e.printStackTrace();
        }

//           LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
//        rv_list2.setLayoutManager(linearLayoutManager);
        @SuppressLint("WrongConstant") GridLayoutManager gridLayoutManager = new GridLayoutManager(MainActivity.this, 2, LinearLayoutManager.VERTICAL, false);
        rv_list2.setLayoutManager(gridLayoutManager);

    }


    private final Runnable m_Runnable = new Runnable() {
        public void run() {
            try {
                volleyGetData();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            MainActivity.this.mHandler.postDelayed(m_Runnable, 5000);
        }
    };


    public class MyAdapter extends RecyclerView.Adapter<MainActivity.MyAdapter.MyViewHolder> {
        public class MyViewHolder extends RecyclerView.ViewHolder {
            //based on row.XML
            public TextView tv_name;
            public ImageView iv_image;
            public Button bt_button;
            LinearLayout ll_parent;


            public MyViewHolder(View itemView) {
                super(itemView);

                tv_name = itemView.findViewById(R.id.tv_name);
                bt_button = itemView.findViewById(R.id.bt_button);
                iv_image = itemView.findViewById(R.id.iv_image);
                ll_parent = itemView.findViewById(R.id.ll_parent);


            }
        }

        @Override
        public MainActivity.MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = getLayoutInflater().inflate(R.layout.row3, parent, false);

            MainActivity.MyAdapter.MyViewHolder myViewHolder = new MyViewHolder(v);

            return myViewHolder;
        }

        @Override
        public void onBindViewHolder(MainActivity.MyAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") final int position) // this will be called fromlayout manager
        {
            holder.tv_name.setText(list_name.get(position));
            if (list_name.get(position).equals("CC")) {
                if (list_value.get(position).equals("1")) {
                    holder.iv_image.setImageResource(R.drawable.cc_on);
                    holder.ll_parent.setBackgroundColor(Color.parseColor("#00FA2B"));
                } else if (list_value.get(position).equals("0")) {
                    holder.iv_image.setImageResource(R.drawable.cc_off);
                    holder.ll_parent.setBackgroundColor(Color.parseColor("#FF2B3D"));
                }
            }
            holder.tv_name.setText(list_name.get(position));
            if (list_name.get(position).equals("BULB")) {
                if (list_value.get(position).equals("1")) {
                    holder.iv_image.setImageResource(R.drawable.bulb_on);
                    holder.ll_parent.setBackgroundColor(Color.parseColor("#00FA2B"));
                } else if (list_value.get(position).equals("0")) {
                    holder.iv_image.setImageResource(R.drawable.bulb_off);
                    holder.ll_parent.setBackgroundColor(Color.parseColor("#FF2B3D"));
                }
            }
            holder.tv_name.setText(list_name.get(position));
            if (list_name.get(position).equals("DOOR")) {
                if (list_value.get(position).equals("1")) {
                    holder.iv_image.setImageResource(R.drawable.door_on);
                    holder.ll_parent.setBackgroundColor(Color.parseColor("#00FA2B"));
                } else if (list_value.get(position).equals("0")) {
                    holder.iv_image.setImageResource(R.drawable.door_off);
                    holder.ll_parent.setBackgroundColor(Color.parseColor("#FF2B3D"));
                }
            }


            holder.tv_name.setText(list_name.get(position));
            if (list_name.get(position).equals("FAN")) {
                if (list_value.get(position).equals("1")) {
                    holder.iv_image.setImageResource(R.drawable.fan_on);
                    holder.ll_parent.setBackgroundColor(Color.parseColor("#00FA2B"));

                } else if (list_value.get(position).equals("0")) {
                    holder.iv_image.setImageResource(R.drawable.fan_off);
                    holder.ll_parent.setBackgroundColor(Color.parseColor("#FF2B3D"));
                }
            }
            holder.tv_name.setText(list_name.get(position));
            if (list_name.get(position).equals("HOOD")) {
                if (list_value.get(position).equals("1")) {
                    holder.iv_image.setImageResource(R.drawable.hood_on);
                    holder.ll_parent.setBackgroundColor(Color.parseColor("#00FA2B"));
                } else if (list_value.get(position).equals("0")) {
                    holder.iv_image.setImageResource(R.drawable.hood_off);
                    holder.ll_parent.setBackgroundColor(Color.parseColor("#FF2B3D"));
                }
            }
            holder.tv_name.setText(list_name.get(position));
            if (list_name.get(position).equals("TAP")) {
                if (list_value.get(position).equals("1")) {
                    holder.iv_image.setImageResource(R.drawable.tap_on);
                    holder.ll_parent.setBackgroundColor(Color.parseColor("#00FA2B"));
                } else if (list_value.get(position).equals("0")) {
                    holder.iv_image.setImageResource(R.drawable.tap_off);
                    holder.ll_parent.setBackgroundColor(Color.parseColor("#FF2B3D"));
                }
            }
            holder.tv_name.setText(list_name.get(position));
            if (list_name.get(position).equals("AC")) {
                if (list_value.get(position).equals("1")) {
                    holder.iv_image.setImageResource(R.drawable.ac_on);
                    holder.ll_parent.setBackgroundColor(Color.parseColor("#00FA2B"));
                } else if (list_value.get(position).equals("0")) {
                    holder.iv_image.setImageResource(R.drawable.ac_off);
                    holder.ll_parent.setBackgroundColor(Color.parseColor("#FF2B3D"));
                }
            }
            holder.tv_name.setText(list_name.get(position));
            if (list_name.get(position).equals("TV")) {
                if (list_value.get(position).equals("1")) {
                    holder.iv_image.setImageResource(R.drawable.tv_on);
                    holder.ll_parent.setBackgroundColor(Color.parseColor("#00FA2B"));
                } else if (list_value.get(position).equals("0")) {
                    holder.iv_image.setImageResource(R.drawable.tv_off);
                    holder.ll_parent.setBackgroundColor(Color.parseColor("#FF2B3D"));
                }
            }

            holder.iv_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    st_sensor = list_name.get(position);
                    st_data = list_value.get(position);
                    if (st_data.equals("1")) {
                        st_data = "0";
                    } else {
                        st_data = "1";
                    }

                    try {
                        volleyUpdateData();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

            holder.bt_button.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    Intent intent=new Intent(MainActivity.this,Home.class);
                    intent.putExtra("device", list_name.get(position));
                    startActivity(intent);

                    return false;
                }
            });

        }

        @Override
        public int getItemCount() {
            return list_name.size();
        }

    }

    public void volleyGetData() throws JSONException {
        JsonArrayRequest req = new JsonArrayRequest(url_get_data,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        try {
                            jsonResponse = "";
                            int i;
                            list_name.clear();
                            list_value.clear();
                            for (i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                                list_name.add(jsonObject.getString("device"));
                                list_value.add(jsonObject.getString("status"));
                            }

//                            update_ui();
//                            Toast.makeText(GridActivity.this, "No of rows received "+i, Toast.LENGTH_SHORT).show();

                            myAdapter = new MyAdapter();
                            rv_list2.setAdapter(myAdapter);

                        } catch (JSONException e) {
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

    public void volleyUpdateData() throws JSONException {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_update_data,
                new Response.Listener<String>() {
                    JSONObject res = null;

                    @Override
                    public void onResponse(String ServerResponse) {
                        try {
                            res = new JSONObject(ServerResponse);
                            //Toast.makeText(getApplicationContext(), ServerResponse, Toast.LENGTH_SHORT).show();

                            if (res.getString("error").length() == 0) {
                                Toast.makeText(MainActivity.this, res.getString("success"), Toast.LENGTH_SHORT).show();
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
                params.put("device", st_sensor);
                params.put("status", st_data);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

}


















