package com.example.storescontrol.view.task;

import android.database.DatabaseUtils;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.storescontrol.R;
import com.example.storescontrol.Url.Request;
import com.example.storescontrol.Url.Untils;
import com.example.storescontrol.Url.iUrl;
import com.example.storescontrol.bean.LoginBean;
import com.example.storescontrol.bean.TaskBean;
import com.example.storescontrol.databinding.ActivityTaskBinding;
import com.example.storescontrol.view.BaseActivity;
import com.example.storescontrol.view.ProductionwarehousingActivity;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class TaskActivity extends BaseActivity {
    ActivityTaskBinding binding;
    TaskBean.Data data;
    String isMoq="是";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         binding= DataBindingUtil.setContentView(TaskActivity.this,R.layout.activity_task);
        data=getIntent().getParcelableExtra("taskBean");
        binding.setBean(data);
        Untils.initTitle("详细单据",this);
        binding.bAgree.setOnClickListener(onClickListener);
        binding.bDisagree.setOnClickListener(onClickListener);
        binding.bExit.setOnClickListener(onClickListener);
        int listtype=getIntent().getIntExtra("type",-1);
        switch (listtype){
            case -1:
                binding.rlBottom.setVisibility(View.GONE);
                break;
            case 0:
                binding.bAgree.setVisibility(View.VISIBLE);
                binding.bDisagree.setVisibility(View.VISIBLE);
                break;
            case 1:
                binding.bExit.setVisibility(View.VISIBLE);
                break;
        }

        if(data.getP_RowNo().equals("1")){
            isMoq="";
            binding.lMoq.setVisibility(View.VISIBLE);
        }
        binding.rgMoq.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                Log.i("i",i+"");

                switch (i){
                    case R.id.rb_yes:
                        isMoq="是";
                        break;
                    case R.id.rb_no:
                        isMoq="否";
                        break;
                }
                updateMoq();
            }
        });
    }
    View.OnClickListener onClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Log.i("is moq",isMoq.isEmpty()+"");

            if(isMoq.equals("是")) {
                Button button = findViewById(view.getId());
                putData(button.getText().toString());
            }else {
                Toast.makeText(TaskActivity.this,"当前不可审核",Toast.LENGTH_LONG).show();
            }
        }
    };
    private void updateMoq( ) {

        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put(" P_IsMOQ",isMoq);
            jsonObject.put("P_Id",data.getP_id());

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
        Call<ResponseBody> call = login.updateMoq(body);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(retrofit2.Call<ResponseBody> call, Response<ResponseBody> response) {

                try {
                    LoginBean bean=new Gson().fromJson(response.body().string(),LoginBean.class);
                    Toast.makeText(TaskActivity.this,bean.getResultMessage(),Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(retrofit2.Call<ResponseBody> call, Throwable t) {

            } });
    }
    private void putData(String auditStatus ) {

        if(auditStatus.equals("同意")){
            auditStatus="已审核";
        }else  if(auditStatus.equals("不同意")){
            auditStatus="已拒收";
        }else  if(auditStatus.equals("撤回")){
            auditStatus="未审核";
        }
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("P_AuditStatus",auditStatus);
            jsonObject.put("P_AuditMemo",binding.etPAuditMemo.getText().toString());
            jsonObject.put("P_Id",data.getP_id());
            jsonObject.put("S_Id",data.getS_Id());
            jsonObject.put("P_RowNo",Integer.parseInt(data.getP_RowNo())+1);
            jsonObject.put("S_QuotationID",data.getS_QuotationID());
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
        Call<ResponseBody> call = login.taskApproval(body);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(retrofit2.Call<ResponseBody> call, Response<ResponseBody> response) {

                try {
                    LoginBean bean=new Gson().fromJson(response.body().string(),LoginBean.class);

                    Toast.makeText(TaskActivity.this,bean.getResultMessage(),Toast.LENGTH_LONG).show();
                    if(bean.getResultcode().equals("200")){

                        finish();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(retrofit2.Call<ResponseBody> call, Throwable t) {

            } });
    }
}
