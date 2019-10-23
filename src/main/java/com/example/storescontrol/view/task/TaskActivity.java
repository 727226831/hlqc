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
    }
    View.OnClickListener onClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Button button=findViewById(view.getId());
          putData(button.getText().toString());
        }
    };
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
            jsonObject.put("P_Id",acccode);
            jsonObject.put("S_Id",data.getS_Id());
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
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(retrofit2.Call<ResponseBody> call, Throwable t) {

            } });
    }
}
