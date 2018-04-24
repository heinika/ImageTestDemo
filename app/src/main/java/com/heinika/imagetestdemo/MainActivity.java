package com.heinika.imagetestdemo;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.heinika.imagetestdemo.entity.ImageEntity;
import com.heinika.imagetestdemo.http.ImageApi;
import com.heinika.imagetestdemo.view.ImageListAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.widget.LinearLayout.VERTICAL;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    ImageListAdapter mAdapter;
    private int mPage = 0;
    private List<ImageEntity.ListBean> listBeans = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mRecyclerView = findViewById(R.id.recyclerView);
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd hh:mm:ss")
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://image.so.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        final ImageApi imageApi = retrofit.create(ImageApi.class);
        mAdapter = new ImageListAdapter(this);
        final StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(3, VERTICAL);
        layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);

        final EditText editText = findViewById(R.id.editTextSearch);
        findViewById(R.id.buttonSearch).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPage = 0;
                Call<ImageEntity> call = imageApi.getImage(editText.getText().toString(), mPage);
                call.enqueue(new Callback<ImageEntity>() {
                    @Override
                    public void onResponse(Call<ImageEntity> call, Response<ImageEntity> response) {
                        listBeans.clear();
                        ImageEntity imageEntity = response.body();
                        listBeans.addAll(imageEntity.getList());
                        mAdapter.setListBeans(listBeans);
                    }

                    @Override
                    public void onFailure(Call<ImageEntity> call, Throwable t) {

                    }
                });
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mRecyclerView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    int[] ints = new int[3];
                    layoutManager.findLastCompletelyVisibleItemPositions(ints);
                    if(ints[2]> listBeans.size()-6){
                        mPage++;
                        Call<ImageEntity> call = imageApi.getImage(editText.getText().toString(), 50*mPage);
                        call.enqueue(new Callback<ImageEntity>() {
                            @Override
                            public void onResponse(Call<ImageEntity> call, Response<ImageEntity> response) {
                                ImageEntity imageEntity = response.body();
                                listBeans.addAll(imageEntity.getList());
                                mAdapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onFailure(Call<ImageEntity> call, Throwable t) {

                            }
                        });
                    }
                }
            });
        }
    }
}
