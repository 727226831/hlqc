package com.example.storescontrol.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.storescontrol.R;
import com.example.storescontrol.Url.Request;
import com.example.storescontrol.Url.Untils;
import com.example.storescontrol.bean.ArrivalHeadBean;
import com.example.storescontrol.bean.DetailsBean;
import com.example.storescontrol.view.ProductionwarehousingActivity;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailListActivity extends BaseActivity {
    RecyclerView recyclerView;
    private FunctionAdapter functionAdapter;
    Button buttonSubmit,buttonscan;
    TextView textViewTotal;
    DetailsBean detailsBean=new DetailsBean();
    private  ArrayList<ArrivalHeadBean> arrivalHeadBeans = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_list);
       Untils.initTitle(getIntent().getStringExtra("menuname"),this);
        recyclerView=findViewById(R.id.rv_list);
        buttonSubmit=findViewById(R.id.b_submit);
        textViewTotal=findViewById(R.id.tv_total);
        buttonscan=findViewById(R.id.b_scan);
        getData();
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkData();
            }
        });
        buttonscan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(DetailListActivity.this,ProductionwarehousingActivity.class);
                intent.putExtra("menuname",getIntent().getStringExtra("menuname"));
                intent.putExtra("detailsBean",detailsBean);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        SharedPreferences sharedPreferences = getSharedPreferences("sp", Context.MODE_PRIVATE);
        String stringarrivalHeadBeans = sharedPreferences.getString("checklist", "");
        if (!stringarrivalHeadBeans.equals("")) {
            Gson gson = new Gson();
            JsonArray arry = new JsonParser().parse(stringarrivalHeadBeans).getAsJsonArray();
            for (JsonElement jsonElement : arry) {
                arrivalHeadBeans.add(gson.fromJson(jsonElement, ArrivalHeadBean.class));
            }
            Log.i("arrivalHeadBeans",gson.toJson(arrivalHeadBeans));
        }
    }

    private void checkData() {
        for (int i = 0; i <detailsBean.getData().size() ; i++) {
              if(!detailsBean.getData().get(i).getIncomplete().equals("0")){
                  Toast.makeText(DetailListActivity.this,"有未扫码条目，请完成后再提交",Toast.LENGTH_LONG).show();
                  return;
              }

        }

        JSONObject jsonObject=new JSONObject();
        try {
            if(getIntent().getStringExtra("menuname").equals("调拨入库")){
                jsonObject.put("methodname","CheckTRInByccode");
            }else   if(getIntent().getStringExtra("menuname").equals("调拨出库")){
                jsonObject.put("methodname","CheckTROutByccode");
            } if(getIntent().getStringExtra("menuname").equals("材料出库")){
                jsonObject.put("methodname","CheckMaterialOutByccode");
            }
            jsonObject.put("usercode",usercode);
            jsonObject.put("id",getIntent().getStringExtra("id"));
            jsonObject.put("acccode",acccode);
            jsonObject.put("ccode",getIntent().getStringExtra("ccode"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String obj=jsonObject.toString();
        Log.i("json object",obj);

        Call<ResponseBody> data =Request.getRequestbody(obj);
        data.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(retrofit2.Call<ResponseBody> call, Response<ResponseBody> response) {

                try {
                    if(response.code()==200) {
                        Toast.makeText(DetailListActivity.this,response.body().string(),Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(retrofit2.Call<ResponseBody> call, Throwable t) {

            } });
    }

    private void getData() {

        JSONObject jsonObject=new JSONObject();
        try {
            if(getIntent().getStringExtra("menuname").equals("调拨入库")){
                jsonObject.put("methodname","getTRInDetailsByccode");
            }else   if(getIntent().getStringExtra("menuname").equals("调拨出库")){
                jsonObject.put("methodname","getTROutDetailsByccode");
            } if(getIntent().getStringExtra("menuname").equals("材料出库")){
                jsonObject.put("methodname","getMaterialOutDetailsByccode");
            }
              jsonObject.put("usercode",usercode);
              jsonObject.put("id",getIntent().getStringExtra("id"));
              jsonObject.put("acccode",acccode);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String obj=jsonObject.toString();
        Log.i("json object",obj);

        Call<ResponseBody> data =Request.getRequestbody(obj);
        data.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(retrofit2.Call<ResponseBody> call, Response<ResponseBody> response) {

                try {
                    if(response.code()==200) {
                        detailsBean=new Gson().fromJson(response.body().string(),DetailsBean.class);
                        functionAdapter=new FunctionAdapter(detailsBean.getData());
                        recyclerView.setLayoutManager(new LinearLayoutManager(DetailListActivity.this));
                        recyclerView.addItemDecoration(new DividerItemDecoration(DetailListActivity.this,DividerItemDecoration.VERTICAL));
                        recyclerView.setAdapter(functionAdapter);
                        textViewTotal.setText("总计："+detailsBean.getData().size()+"条");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(retrofit2.Call<ResponseBody> call, Throwable t) {

            } });
    }
    class FunctionAdapter extends RecyclerView.Adapter<FunctionAdapter.VH>{

        @NonNull
        @Override
        public FunctionAdapter.VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View v=getLayoutInflater().inflate(R.layout.item_detail_list,viewGroup,false);
            return new FunctionAdapter.VH(v);
        }

        private List<DetailsBean.Data> mDatas;
        public FunctionAdapter(List<DetailsBean.Data> data) {
            this.mDatas = data;
        }

        @Override
        public void onBindViewHolder(@NonNull FunctionAdapter.VH vh, final int i) {

            vh.textViewDetails.setText(mDatas.get(i).getCInvCode());
            vh.textViewcposition.setText(mDatas.get(i).getCposition());
          //  vh.textViewCInvStd.setText(mDatas.get(i).getCInvStd());
            vh.textViewtag.setText(i+1+"");
            vh.textViewcBatch.setText("批号："+mDatas.get(i).getCBatch());
            vh.textViewcInvName.setText("名称："+mDatas.get(i).getCInvName()+"/"+mDatas.get(i).getCInvStd());
            vh.textViewiQuantity.setText("数量："+mDatas.get(i).getIQuantity());

            if(arrivalHeadBeans.isEmpty()){
                mDatas.get(i).setCompleted("0");
                mDatas.get(i).setIncomplete(mDatas.get(i).getIQuantity());
            }else {
                for (int j = 0; j <arrivalHeadBeans.size() ; j++) {
                    if(arrivalHeadBeans.get(j).getcInvCode().equals(mDatas.get(i).getCInvCode())&&
                            arrivalHeadBeans.get(j).getCposition().equals(mDatas.get(i).getCposition())
                            && arrivalHeadBeans.get(j).getCbatch().equals(mDatas.get(i).getCBatch())){
                        mDatas.get(i).setCompleted(arrivalHeadBeans.get(j).getIquantity());

                        double dcompleted=Double.parseDouble(arrivalHeadBeans.get(j).getIquantity());
                        dcompleted++;
                        double dtotal=Double.parseDouble(mDatas.get(i).getIQuantity());

                        if(dtotal-dcompleted<0){
                            mDatas.get(i).setIncomplete((dtotal-dcompleted)+"");
                        }else {
                            mDatas.get(i).setIncomplete("0");
                        }
                    }
                }

            }

            vh.textViewcompleted.setText("已扫码："+mDatas.get(i).getCompleted());
            vh.textViewincomplete.setText("未扫码："+ mDatas.get(i).getIncomplete());

        }

        @Override
        public int getItemCount() {
            return mDatas.size();
        }
        class  VH extends RecyclerView.ViewHolder{
           TextView textViewcposition,textViewDetails,textViewiQuantity,textViewtag,
                   textViewcBatch,textViewcInvName,textViewcompleted,textViewincomplete,
                   textViewCInvStd;
            LinearLayout linearLayout;
            public VH(@NonNull View itemView) {
                super(itemView);
                linearLayout=itemView.findViewById(R.id.l_input);
               textViewcposition=itemView.findViewById(R.id.tv_cposition);
               textViewDetails=itemView.findViewById(R.id.tv_details);
               textViewiQuantity=itemView.findViewById(R.id.tv_iQuantity);
               textViewtag=itemView.findViewById(R.id.tv_tag);
               textViewcBatch=itemView.findViewById(R.id.tv_cBatch);
               textViewcInvName=itemView.findViewById(R.id.tv_cInvName);
               textViewcompleted=itemView.findViewById(R.id.tv_completed);
               textViewincomplete=itemView.findViewById(R.id.tv_incomplete);
               textViewCInvStd=itemView.findViewById(R.id.tv_CInvStd);
            }
        }
    }
}
