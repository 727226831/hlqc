package com.example.storescontrol.view.task;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.example.storescontrol.R;
import com.example.storescontrol.Url.Request;
import com.example.storescontrol.Url.Untils;
import com.example.storescontrol.Url.iUrl;
import com.example.storescontrol.bean.TROutBywhcodeBean;
import com.example.storescontrol.bean.TaskBean;
import com.example.storescontrol.view.BaseActivity;
import com.example.storescontrol.view.DetailListActivity;
import com.example.storescontrol.view.LoginActivity;
import com.example.storescontrol.view.ProductionListActivity;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class TaskListActivity extends BaseActivity {
    RecyclerView recyclerView;
    private FunctionAdapter functionAdapter;
    private EditText editTextSearch;
    private String menuname;
    SharedPreferences sharedPreferences;
    TabLayout tabLayout;
    int listType=0;
    TaskBean taskBean= null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v=getLayoutInflater().inflate(R.layout.activity_production_list,null,false);
        setContentView(v);

        tabLayout=findViewById(R.id.tl_title);
        editTextSearch=findViewById(R.id.et_search);
        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                List<TaskBean.Data> data=new ArrayList<>();
                Log.i("onTextChanged->",charSequence.toString());
                for (int j = 0; j <taskBean.getData().size() ; j++) {
                    String bean=new Gson().toJson(taskBean.getData().get(j));
                    if(bean.contains(charSequence
                    )){
                        data.add(taskBean.getData().get(j));
                    }
                }
                initAdapter(data);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                getData(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        menuname=getIntent().getStringExtra("menuname");
        Untils.initTitle(menuname,this);

        recyclerView=findViewById(R.id.rv_list);


        sharedPreferences=getSharedPreferences(acccode+"",0);


    }

    @Override
    protected void onStart() {
        super.onStart();
       getData(listType);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void getData(final int type) {
         listType=type;

        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("acccode",acccode);
            jsonObject.put("type",type);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String obj=jsonObject.toString();
        Log.i("json object",obj);

        OkHttpClient client = new OkHttpClient.Builder().
                connectTimeout(120, TimeUnit.SECONDS).
                readTimeout(120, TimeUnit.SECONDS).
                writeTimeout(120, TimeUnit.SECONDS).build();

        Retrofit retrofit=new Retrofit.Builder().client(client).baseUrl(Request.URL).build();
        RequestBody body=RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),obj);
        iUrl login = retrofit.create(iUrl.class);
        Call<ResponseBody> call = login.tasklist(body);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(retrofit2.Call<ResponseBody> call, Response<ResponseBody> response) {


                    if(response.code()==200) {

                        try {
                            taskBean = new Gson().fromJson(response.body().string(), TaskBean.class);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        if(type==0){
                            tabLayout.getTabAt(0).setText("待审批("+taskBean.getData().size()+")");
                        }else if(type==1) {
                            tabLayout.getTabAt(1).setText("已审批("+taskBean.getData().size()+")");
                        }
                        initAdapter(taskBean.getData());


                    }


            }
            @Override
            public void onFailure(retrofit2.Call<ResponseBody> call, Throwable t) {

            } });
    }

    private void initAdapter(List<TaskBean.Data> data) {
        functionAdapter = new FunctionAdapter(data);
        recyclerView.setLayoutManager(new LinearLayoutManager(TaskListActivity.this));
        recyclerView.addItemDecoration(new DividerItemDecoration(TaskListActivity.this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(functionAdapter);
        functionAdapter.notifyDataSetChanged();
    }

    class FunctionAdapter extends RecyclerView.Adapter<FunctionAdapter.VH>{

        @NonNull
        @Override
        public FunctionAdapter.VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View v=getLayoutInflater().inflate(R.layout.item_task,viewGroup,false);
            return new FunctionAdapter.VH(v);


        }

        private List<TaskBean.Data> mDatas;
        public FunctionAdapter(List<TaskBean.Data> data) {
            this.mDatas = data;
        }

        @Override
        public void onBindViewHolder(@NonNull final FunctionAdapter.VH vh, final int i) {

            vh.textViewtag.setText(i+1+"");

            vh.textViewS_QuotationID.setText(mDatas.get(i).getS_QuotationID());
            vh.textViewS_RegisterDate.setText("时间："+mDatas.get(i).getS_RegisterDate());

            vh.textViewS_InvVersion.setText("版本："+mDatas.get(i).getS_InvVersion());
            vh.textViewS_InvName.setText("型号："+mDatas.get(i).getS_InvName());
            vh.textViewR_RecordCompany.setText("客户："+mDatas.get(i).getR_RecordCompany());
            vh.textViewS_Verifyer.setText("销售："+mDatas.get(i).getS_Verifyer());
            vh.textViewM_MSN.setText("供应商："+mDatas.get(i).getM_MSN());


            if(sharedPreferences.getBoolean(mDatas.get(i).getS_QuotationID(),false)){
                vh.imageViewTag.setVisibility(View.GONE);
            }else {

                vh.imageViewTag.setVisibility(View.VISIBLE);
            }



            vh.textViewS_State.setText("状态："+mDatas.get(i).getS_State());
            vh.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sharedPreferences.edit().putBoolean(mDatas.get(i).getS_QuotationID(),true).commit();
                    vh.imageViewTag.setVisibility(View.GONE);

                    if(type==1) {
                        Intent intent = new Intent(TaskListActivity.this, TaskActivity.class);
                        intent.putExtra("taskBean", mDatas.get(i));
                        if(acccode.equals("15")){
                            intent.putExtra("type",-1);
                        }else {
                            intent.putExtra("type",listType);
                        }

                        startActivity(intent);
                    }


                }
            });



        }

        @Override
        public int getItemCount() {
            return mDatas.size();
        }
        class  VH extends RecyclerView.ViewHolder{
            TextView textViewtag,textViewS_QuotationID,textViewR_RecordCompany, textViewS_InvName,
                    textViewS_InvVersion,textViewS_Verifyer,textViewS_State,textViewS_RegisterDate,
                    textViewM_MSN;
            LinearLayout linearLayout;
            ImageView imageViewTag;

            public VH(@NonNull View itemView) {
                super(itemView);
                linearLayout=itemView.findViewById(R.id.l_input);
                textViewtag=itemView.findViewById(R.id.tv_number);
                textViewS_QuotationID=itemView.findViewById(R.id.tv_S_QuotationID);
                textViewR_RecordCompany=itemView.findViewById(R.id.tv_R_RecordCompany);
                textViewS_InvName=itemView.findViewById(R.id.tv_S_InvName);
               textViewS_InvVersion=itemView.findViewById(R.id.tv_S_InvVersion);
               textViewS_Verifyer=itemView.findViewById(R.id.tv_S_Verifyer);
               textViewS_State=itemView.findViewById(R.id.tv_S_State);
               textViewS_RegisterDate=itemView.findViewById(R.id.tv_S_RegisterDate);
               imageViewTag=itemView.findViewById(R.id.iv_tag);
               textViewM_MSN=itemView.findViewById(R.id.tv_M_MSN);





            }
        }
    }
}
