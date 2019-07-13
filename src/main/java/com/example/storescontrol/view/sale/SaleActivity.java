package com.example.storescontrol.view.sale;

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
import com.example.storescontrol.bean.CreatesaleoutBean;
import com.example.storescontrol.bean.DispatchdetailsBean;
import com.example.storescontrol.view.BaseActivity;
import com.example.storescontrol.view.DetailListActivity;
import com.example.storescontrol.view.ProductionwarehousingActivity;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SaleActivity extends BaseActivity {
    RecyclerView recyclerView;
    private FunctionAdapter functionAdapter;
    Button buttonSubmit,buttonScan;
    TextView textViewTotal;

    DispatchdetailsBean dispatchdetailsBean=new DispatchdetailsBean();
    List<CreatesaleoutBean> createsaleoutBeanArrayList=new ArrayList<>();
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_list);
        Untils.initTitle(getIntent().getStringExtra("menuname"),this);
        recyclerView=findViewById(R.id.rv_list);
        buttonSubmit=findViewById(R.id.b_submit);
        buttonScan=findViewById(R.id.b_scan);
        textViewTotal=findViewById(R.id.tv_total);

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
               createSaleOut();
            }
        });
        buttonScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SaleActivity.this,ProductionwarehousingActivity.class);
                intent.putExtra("menuname",getIntent().getStringExtra("menuname"));
                Log.i("put-->",new Gson().toJson(dispatchdetailsBean));
                intent.putExtra("dispatchdetailsBean",dispatchdetailsBean);
                startActivity(intent);
            }
        });


        sharedPreferences = getSharedPreferences("sp", Context.MODE_PRIVATE);
        clearCheckdata();
        getDispatchDetailsByccode();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(!sharedPreferences.getString("detailsBean","").equals("")){

            dispatchdetailsBean= new Gson().fromJson(sharedPreferences.getString("detailsBean",""),DispatchdetailsBean.class);
            functionAdapter=new FunctionAdapter(dispatchdetailsBean.getData());
            recyclerView.setLayoutManager(new LinearLayoutManager(SaleActivity.this));
            recyclerView.addItemDecoration(new DividerItemDecoration(SaleActivity.this,DividerItemDecoration.VERTICAL));
            recyclerView.setAdapter(functionAdapter);
            functionAdapter.notifyDataSetChanged();
        }
    }

    private void createSaleOut() {

        for (int i = 0; i <dispatchdetailsBean.getData().size() ; i++) {

            if( Double.parseDouble(dispatchdetailsBean.getData().get(i).getIncomplete())!=0){
                Toast.makeText(SaleActivity.this,"有未扫码条目，请完成后再提交",Toast.LENGTH_LONG).show();
                dialog.dismiss();
                return;
            }

        }
        JSONObject jsonObject=new JSONObject();
        try {

            jsonObject.put("methodname","CreateSaleOut");
            jsonObject.put("usercode",usercode);
            jsonObject.put("acccode",acccode);
            jsonObject.put("ccode",getIntent().getStringExtra("ccode"));
            JSONArray jsonArray=new JSONArray(new Gson().toJson(dispatchdetailsBean.getData()));
            jsonObject.put("datatetails",jsonArray);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        String obj = jsonObject.toString();
        Log.i("json object", obj);

        Call<ResponseBody> data =Request.getRequestbody(obj);
        data.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(retrofit2.Call<ResponseBody> call, Response<ResponseBody> response) {
                dialog.dismiss();
                try {
                    if(response.code()==200) {
                        Toast.makeText(SaleActivity.this,response.body().string(),Toast.LENGTH_LONG).show();
                        clearCheckdata();
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




    private void getDispatchDetailsByccode() {

        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("methodname","getDispatchDetailsByccode");
            jsonObject.put("id",getIntent().getStringExtra("id"));
            jsonObject.put("acccode",acccode);
            jsonObject.put("cwhcode",getIntent().getStringExtra("cwhcode"));
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
                       dispatchdetailsBean=new Gson().fromJson(response.body().string(),DispatchdetailsBean.class);
                        for (int i = 0; i <dispatchdetailsBean.getData().size() ; i++) {
                            if(dispatchdetailsBean.getData().get(i).getCompleted()==null){
                                dispatchdetailsBean.getData().get(i).setCompleted("0");
                                dispatchdetailsBean.getData().get(i).setIncomplete(dispatchdetailsBean.getData().get(i).getIquantity());
                            }
                        }
                        functionAdapter=new FunctionAdapter(dispatchdetailsBean.getData());
                        recyclerView.setLayoutManager(new LinearLayoutManager(SaleActivity.this));
                        recyclerView.addItemDecoration(new DividerItemDecoration(SaleActivity.this,DividerItemDecoration.VERTICAL));
                        recyclerView.setAdapter(functionAdapter);

                        sharedPreferences.edit().putString("detailsBean",new Gson().toJson(dispatchdetailsBean)).commit();
                        textViewTotal.setText("总计："+dispatchdetailsBean.getData().size()+"条");
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

        private List<DispatchdetailsBean.Data> mDatas;
        public FunctionAdapter(List<DispatchdetailsBean.Data> data) {
            this.mDatas = data;

        }

        @Override
        public void onBindViewHolder(@NonNull  FunctionAdapter.VH vh,final int i) {
            vh.textViewDetails.setText(mDatas.get(i).getCinvcode());
            vh.textViewcposition.setText("行号："+mDatas.get(i).getIrowno()+"");
            vh.textViewtag.setText(i+1+"");
            vh.textViewcBatch.setText("批号："+mDatas.get(i).getCbatch());
            vh.textViewcInvName.setText("名称："+mDatas.get(i).getCinvname()+"/"+mDatas.get(i).getCinvstd());
            vh.textViewiQuantity.setText("数量："+mDatas.get(i).getIquantity());
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
            VH(@NonNull View itemView) {
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
