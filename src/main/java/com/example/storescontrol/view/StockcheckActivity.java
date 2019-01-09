package com.example.storescontrol.view;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.storescontrol.R;
import com.example.storescontrol.Url.Request;
import com.example.storescontrol.Url.Untils;
import com.example.storescontrol.bean.StockcheckBean;
import com.example.storescontrol.databinding.ActivityStockcheckBinding;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StockcheckActivity extends BaseActivity {
    ActivityStockcheckBinding binding;
    FunctionAdapter functionAdapter;
    StockcheckBean stockcheckBean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=DataBindingUtil.setContentView(this,R.layout.activity_stockcheck);
        Untils.initTitle(getIntent().getStringExtra("menuname"),this);
        binding.bSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCurrentStockInfo();
            }
        });


    }

    private void getCurrentStockInfo() {
        JSONObject jsonObject=new JSONObject();
        try {

            jsonObject.put("methodname","getCurrentStockInfo");
            jsonObject.put("acccode",acccode);
            jsonObject.put("usercode",usercode);
            jsonObject.put("warehouse",binding.etWarehouse.getText().toString());
            jsonObject.put("position",binding.etPosition.getText().toString());
            jsonObject.put("inventory",binding.etInventory.getText().toString());
            jsonObject.put("batch",binding.etBatch.getText().toString());


        } catch (JSONException e) {
            e.printStackTrace();
        }
        String obj=jsonObject.toString();
        Log.i("getCurrentStockInfo",obj);

        Call<ResponseBody> data =Request.getRequestbody(obj);
        data.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(retrofit2.Call<ResponseBody> call, Response<ResponseBody> response) {

                try {
                    if(response.code()==200) {
                        stockcheckBean=new Gson().fromJson(response.body().string(),StockcheckBean.class);
                        functionAdapter=new FunctionAdapter(stockcheckBean.getData());
                        binding.rvList.setLayoutManager(new LinearLayoutManager(StockcheckActivity.this));
                        binding.rvList.addItemDecoration(new DividerItemDecoration(StockcheckActivity.this,DividerItemDecoration.VERTICAL));
                        binding.rvList.setAdapter(functionAdapter);
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
            View v=getLayoutInflater().inflate(R.layout.item_stockcheck,viewGroup,false);
            return new FunctionAdapter.VH(v);


        }

        private List<StockcheckBean.Data> mDatas;
        public FunctionAdapter(List<StockcheckBean.Data> data) {
            this.mDatas = data;
        }

        @Override
        public void onBindViewHolder(@NonNull  FunctionAdapter.VH vh,final int i) {
            vh.textViewiQuantity.setText("数量："+mDatas.get(i).getIQuantity());
            vh.textViewcBatch.setText("批号："+mDatas.get(i).getCBatch());
            vh.textViewcInvStd.setText("描述："+mDatas.get(i).getCInvName()+"/"+mDatas.get(i).getCInvStd());
            vh.textViewcInvCode.setText("料号："+mDatas.get(i).getCInvCode());
            vh.textViewcPosName.setText("货位："+mDatas.get(i).getCPosName());
            vh.textViewcWhName.setText("仓库："+mDatas.get(i).getCWhName());
            vh.textViewtag.setText(i+1+"");
        }

        @Override
        public int getItemCount() {
            return mDatas.size();
        }
        class  VH extends RecyclerView.ViewHolder{
            TextView  textViewcWhName,textViewcPosName,textViewcInvCode,
                    textViewcInvStd,textViewcBatch,textViewiQuantity,textViewtag;
            public VH(@NonNull View itemView) {
                super(itemView);
                textViewcWhName=itemView.findViewById(R.id.tv_cWhName);
                textViewcPosName=itemView.findViewById(R.id.tv_cPosName);
                textViewcInvCode=itemView.findViewById(R.id.tv_cInvCode);
                textViewcInvStd=itemView.findViewById(R.id.tv_cInvStd);
                textViewcBatch=itemView.findViewById(R.id.tv_cBatch);
                textViewiQuantity=itemView.findViewById(R.id.tv_iQuantity);
                textViewtag=itemView.findViewById(R.id.tv_tag);



            }
        }
    }


}
