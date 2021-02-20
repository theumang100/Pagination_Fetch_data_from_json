package com.wmt.jsonfetch;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private ExampleAdapter mExampleAdapter;
    private ArrayList<ExampleItem> mExampleList;
    private RequestQueue mRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mExampleList = new ArrayList<>();
        mRequestQueue = Volley.newRequestQueue(this);
        parseJSON();
    }

    private void parseJSON() {
        String url = "https://randomuser.me/api/?page=1&results=25";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("results");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject hit = jsonArray.getJSONObject(i);

                                JSONObject img_data = hit.getJSONObject("picture");
                                JSONObject name_data = hit.getJSONObject("name");
                                JSONObject location = hit.getJSONObject("location");
                                JSONObject date_of_birth = hit.getJSONObject("dob");

                                String imageUrl = img_data.getString("medium");
                                String name = name_data.getString("first").trim() + " " + name_data.getString("last").trim();
                                String country = location.getString("country");
                                int dob = date_of_birth.getInt("age");
                                mExampleList.add(new ExampleItem(imageUrl, name, country, dob));
                            }
                            mExampleAdapter = new ExampleAdapter(MainActivity.this, mExampleList);
                            mRecyclerView.setAdapter(mExampleAdapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mRequestQueue.add(request);
    }
}

//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.widget.NestedScrollView;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.ProgressBar;
//import android.widget.TextView;
//
//import com.android.volley.Request;
//import com.android.volley.RequestQueue;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.JsonObjectRequest;
//import com.android.volley.toolbox.Volley;
//import com.squareup.picasso.Picasso;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.util.ArrayList;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Retrofit;
//import retrofit2.converter.scalars.ScalarsConverterFactory;
//
//
//public class MainActivity extends AppCompatActivity {
//    //    private TextView parse_text_result;
////    private ImageView image_view;
//    NestedScrollView nestedScrollView;
//    RecyclerView recyclerView;
//    ProgressBar progressBar;
//
//    ArrayList<MainData> dataArrayList = new ArrayList<>();
//    MainAdapter adapter;
//    int page = 1, limit = 10;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        nestedScrollView = findViewById(R.id.scroll_view);
//        recyclerView = findViewById(R.id.recycler_view);
//        progressBar = findViewById(R.id.progress_bar);
//
//        adapter = new MainAdapter(MainActivity.this, dataArrayList);
//
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//        recyclerView.setAdapter(adapter);
//
//        getData(page, limit);
//        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
//            @Override
//            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//                if(scrollY==v.getChildAt(0).getMeasuredHeight()-v.getMeasuredHeight()){
//                    page++;
//                    progressBar.setVisibility(View.VISIBLE);
//                    getData(page,limit);
//                }
//            }
//        });
////        parse_text_result = findViewById(R.id.parse_text_result);
////        image_view = findViewById(R.id.image_view);
//
////        RequestQueue requestQueue;
////        requestQueue = Volley.newRequestQueue(this);
////
////        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, "https://randomuser.me/api/?page=1&results=25", null, new Response.Listener<JSONObject>() {
////            @Override
////            public void onResponse(JSONObject response) {
////                try {
////                    JSONArray jsonArray = response.getJSONArray("results");
////
////                    for (int i = 0; i < jsonArray.length(); i++) {
////                        JSONObject user_data = jsonArray.getJSONObject(i);
////                        JSONObject name_data = user_data.getJSONObject("name");
//////                        JSONObject picture_obj = user_data.getJSONObject("picture");
//////
//////                        String img_url = picture_obj.getString("medium");
////                        String gender = user_data.getString("gender");
////                        String names = name_data.getString("first");
////
//////                        URL url=new URL(img_url);
//////                        Bitmap bmp= BitmapFactory.decodeStream(url.openConnection().getInputStream());
//////                        imageView.setImageBitmap(bmp);
////
//////                        Picasso.get().load(img_url).into(image_view);
//////                        Picasso.get().load(img_url).fit().centerCrop()
//////                                .into(image_view);
////                        parse_text_result.append("First Name : " + names + "\n");
////                        parse_text_result.append("Gender : " + gender + "\n");
//////                        parse_text_result.append(img_url+"\n");
////                        parse_text_result.append("\n");
////
////
//////                        Log.d("myapp","the response is"+gender);
////                    }
////
////
////                } catch (JSONException e) {
////                    e.printStackTrace();
////                }
//////                catch (MalformedURLException e) {
//////                    e.printStackTrace();
//////                } catch (IOException e) {
//////                    e.printStackTrace();
//////                }
////            }
////        }, new Response.ErrorListener() {
////            @Override
////            public void onErrorResponse(VolleyError error) {
////                Log.d("myapp", "Something went wrong");
////            }
////        });
////
////        requestQueue.add(jsonObjectRequest);
//    }
//
//    private void getData(int page, int limit) {
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("https://picsum.photos/")
//                .addConverterFactory(ScalarsConverterFactory.create())
//                .build();
//
//        MainInterface mainInterface = retrofit.create(MainInterface.class);
//
//        Call<String> call = mainInterface.STRING_CALL(page, limit);
//
//        call.enqueue(new Callback<String>() {
//            @Override
//            public void onResponse(Call<String> call, retrofit2.Response<String> response) {
//                if (response.isSuccessful() && response.body() != null) {
//                    progressBar.setVisibility(View.GONE);
//
//                    try {
//                        JSONArray jsonArray=new JSONArray(response.body());
//                        parseResult(jsonArray);
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<String> call, Throwable t) {
//
//            }
//        });
//    }
//
//    private void parseResult(JSONArray jsonArray) {
//        for(int i=0;i<jsonArray.length();i++){
//            try {
//                JSONObject object=jsonArray.getJSONObject(i);
//
//                MainData data=new MainData();
//                data.setImage(object.getString("download_url"));
//                data.setName(object.getString("author"));
//
//                dataArrayList.add(data);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//            adapter=new MainAdapter(MainActivity.this,dataArrayList);
//            recyclerView.setAdapter(adapter);
//        }
//    }
//}