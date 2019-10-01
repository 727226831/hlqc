package com.example.storescontrol.view;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.storescontrol.R;
import com.example.storescontrol.Url.Request;
import com.example.storescontrol.Url.Untils;
import com.example.storescontrol.bean.TROutBywhcodeBean;
import com.example.storescontrol.view.task.TaskActivity;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 *2调拨入库
 * 3材料出库
 * 5调拨出库
 */
public class ProductionListActivity extends BaseActivity {
    RecyclerView recyclerView;
    private FunctionAdapter functionAdapter;
    TROutBywhcodeBean trOutBywhcodeBean=new TROutBywhcodeBean();
    private String menuname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v=getLayoutInflater().inflate(R.layout.activity_production_list,null,false);
        setContentView(v);
        menuname=getIntent().getStringExtra("menuname");
        Untils.initTitle(menuname,this);
       // Untils.initTitle("待审批",this);
        menuname="生产入库";
        recyclerView=findViewById(R.id.rv_list);


    }

    @Override
    protected void onStart() {
        super.onStart();
        getData();
    }

    private void getData() {

        JSONObject jsonObject=new JSONObject();
        try {
            if(menuname.equals("调拨入库")){
                jsonObject.put("methodname","getTRInBywhcode");
            }else   if(menuname.equals("调拨出库")){
                jsonObject.put("methodname","getTROutBywhcode");
            }else if(menuname.equals("材料出库")){
                jsonObject.put("methodname","getMaterialOutBywhcode");
            }else if(menuname.equals("销售出库")){
                jsonObject.put("methodname","getDispatchBywhcode");
            }else if(menuname.equals("生产入库")){
                jsonObject.put("methodname","getProductStoreInBywhcode");
            }
            jsonObject.put("usercode",usercode);
            jsonObject.put("cwhcode","");
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
                        String result=response.body().string();
//                        result="{\n" +
//                                "\t\"Resultcode\": \"200\",\n" +
//                                "\t\"ResultMessage\": \"产成品入库单据取得成功\",\n" +
//                                "\t\"data\": [{\n" +
//                                "\t\t\"cTRCode\": \"QDMM201908085004\",\n" +
//                                "\t\t\"cmocode\": \"MO20190808007\",\n" +
//                                "\t\t\"ID\": \"1000004709\",\n" +
//                                "\t\t\"cCode\": \"KS20190927013\",\n" +
//                                "\t\t\"dDate\": \" 2019-08-08\",\n" +
//                                "\t\t\"cWhCode\": \"08\",\n" +
//                                "\t\t\"cWhName\": \"泉州市博源电子科技有限公司\"\n" +
//                                "\t}]\n" +
//                                "}";
                        trOutBywhcodeBean=new Gson().fromJson(result,TROutBywhcodeBean.class);
                        functionAdapter=new FunctionAdapter(trOutBywhcodeBean.getData());
                        recyclerView.setLayoutManager(new LinearLayoutManager(ProductionListActivity.this));
                        recyclerView.addItemDecoration(new DividerItemDecoration(ProductionListActivity.this,DividerItemDecoration.VERTICAL));
                        recyclerView.setAdapter(functionAdapter);



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
            View v=getLayoutInflater().inflate(R.layout.item_production_list,viewGroup,false);
            return new FunctionAdapter.VH(v);


        }

        private List<TROutBywhcodeBean.Data> mDatas;
        public FunctionAdapter(List<TROutBywhcodeBean.Data> data) {
            this.mDatas = data;
        }

        @Override
        public void onBindViewHolder(@NonNull  FunctionAdapter.VH vh,final int i) {
            vh.textViewcwhname.setText(mDatas.get(i).getCWhName());
            vh.textViewdate.setText(mDatas.get(i).getdDate());
            vh.textViewcTRCode.setText(mDatas.get(i).getCTRCode());

            vh.textViewtag.setText(i+1+"");


            vh.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(ProductionListActivity.this,DetailListActivity.class);
                     intent.putExtra("id",mDatas.get(i).getID());
                     intent.putExtra("ccode",mDatas.get(i).getCCode());
                     intent.putExtra("menuname",getIntent().getStringExtra("menuname"));
                     intent.putExtra("ddate",mDatas.get(i).getdDate());
                    startActivity(intent);
//                    startActivity(new Intent(ProductionListActivity.this, TaskActivity.class));

                }
            });



        }

        @Override
        public int getItemCount() {
            return mDatas.size();
        }
        class  VH extends RecyclerView.ViewHolder{
            TextView textViewcTRCode,textViewdate,textViewcwhname,textViewtag;
            LinearLayout linearLayout;
            public VH(@NonNull View itemView) {
                super(itemView);
                linearLayout=itemView.findViewById(R.id.l_input);
                textViewcTRCode=itemView.findViewById(R.id.tv_cTRCode);
                textViewdate=itemView.findViewById(R.id.tv_date);
                textViewcwhname=itemView.findViewById(R.id.tv_cWhName);
                textViewtag=itemView.findViewById(R.id.tv_number);



            }
        }
    }


}
