package com.example.storescontrol.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.storescontrol.R;
import com.example.storescontrol.Url.Request;
import com.example.storescontrol.bean.ArrivalHeadBean;
import com.example.storescontrol.Url.iUrl;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PutListActivity extends BaseActivity {
    RecyclerView recyclerView;
    private  FunctionAdapter functionAdapter;
    Button buttonsubmit;
    private ImageView imageViewreturn;
    TextView textViewtitle,textViewtotal;
    ArrayList<ArrivalHeadBean> arrivalHeadBeans=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_put_list);
        recyclerView=findViewById(R.id.rv_list);
        textViewtitle=findViewById(R.id.tv_title);
        buttonsubmit=findViewById(R.id.b_submit);
        textViewtotal=findViewById(R.id.tv_total);
        textViewtitle.setText("入库列表");

        imageViewreturn=findViewById(R.id.iv_return);
        imageViewreturn.setVisibility(View.VISIBLE);
        imageViewreturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        SharedPreferences sharedPreferences = getSharedPreferences("sp", Context.MODE_PRIVATE);
        String data= sharedPreferences.getString("putlist","");
        if (!data.equals("")){

            try {
                Gson gson = new Gson();
                JsonArray arry = new JsonParser().parse(data).getAsJsonArray();
                for (JsonElement jsonElement : arry) {
                    arrivalHeadBeans.add(gson.fromJson(jsonElement, ArrivalHeadBean.class));
                }
                Log.i("arrivalHeadBeans",arrivalHeadBeans.size()+"");
                textViewtotal.setText("总计:"+arrivalHeadBeans.size()+"条");
            } catch (Exception e) {
                e.printStackTrace();
            }

        }




        functionAdapter=new PutListActivity.FunctionAdapter(arrivalHeadBeans);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(functionAdapter);
        buttonsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                putData();

            }
        });

    }

    private void putData() {
        dialog.show();
        JSONObject jsonObject=new JSONObject();
        try {

            jsonObject.put("methodname","CreatePuStoreIn");
            jsonObject.put("usercode",usercode);
            jsonObject.put("acccode",acccode);
            JSONArray jsonArray=new JSONArray();
            for (int i = 0; i <arrivalHeadBeans.size() ; i++) {
                JSONObject object=new JSONObject();
                object.put("cinvcode",arrivalHeadBeans.get(i).getcInvCode());
                object.put("cbatch",arrivalHeadBeans.get(i).getCbatch());
                object.put("ccode",arrivalHeadBeans.get(i).getCcode());
                object.put("cwhcode",arrivalHeadBeans.get(i).getCwhcode());
                object.put("imageid",arrivalHeadBeans.get(i).getImageid());
                object.put("iquantity",arrivalHeadBeans.get(i).getIquantity().replace(arrivalHeadBeans.get(i).getcComUnitName(),""));
                object.put("irowno",arrivalHeadBeans.get(i).getIrowno());
                object.put("cposition",arrivalHeadBeans.get(i).getCposition());
                jsonArray.put(object);
            }
            jsonObject.put("datatetails",jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final String obj=jsonObject.toString();
        Log.i("json object",obj);
        Retrofit retrofit=new Retrofit.Builder().baseUrl(Request.URL).build();
        RequestBody body=RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),obj);

        iUrl login = retrofit.create(iUrl.class);
        retrofit2.Call<ResponseBody> data = login.getMessage(body);
        data.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(retrofit2.Call<ResponseBody> call, Response<ResponseBody> response) {

                try {
                    dialog.dismiss();
                    if(response.code()==200) {
                       JSONObject object=new JSONObject(response.body().string());
                       if(object.getString("Resultcode").equals("200")){

                           arrivalHeadBeans.clear();
                           functionAdapter.notifyDataSetChanged();
                           SharedPreferences sharedPreferences = getSharedPreferences("sp", Context.MODE_PRIVATE);
                           sharedPreferences.edit().putString("putlist","").commit();
                           sharedPreferences.edit().putString("putscan","").commit();


                       }
                       Toast.makeText(PutListActivity.this,object.getString("ResultMessage"),Toast.LENGTH_LONG).show();
                       textViewtotal.setText("");
                       finish();




                    }else if(response.code()==500){
                        Toast.makeText(PutListActivity.this,"数据错误，请检查",Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(retrofit2.Call<ResponseBody> call, Throwable t) {

            } });
    }

    class FunctionAdapter extends RecyclerView.Adapter<PutListActivity.FunctionAdapter.VH>{

        @NonNull
        @Override
        public PutListActivity.FunctionAdapter.VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View v=getLayoutInflater().inflate(R.layout.item_input,viewGroup,false);
            return new PutListActivity.FunctionAdapter.VH(v);


        }

        private List<ArrivalHeadBean> mDatas;
        public FunctionAdapter(List<ArrivalHeadBean> data) {
            this.mDatas = data;
        }

        @Override
        public void onBindViewHolder(@NonNull  PutListActivity.FunctionAdapter.VH vh,final int i) {
            vh.textViewnumber.setText(i+1+"");
            vh.textViewccode.setText(arrivalHeadBeans.get(i).getCcode());
            vh.textViewline.setText(arrivalHeadBeans.get(i).getIrowno());
            vh.textViewcposcode.setText("货位："+arrivalHeadBeans.get(i).getCposition());
            vh.textViewmaterial.setText("料号："+arrivalHeadBeans.get(i).getMaterial());
            vh.textViewbatch.setText("批号："+arrivalHeadBeans.get(i).getCbatch());
            vh.textViewcount.setText("数量："+arrivalHeadBeans.get(i).getIquantity());

            if(arrivalHeadBeans.get(i).getFile()!=null){
                vh.imageViewpic.setTag(i+"");
                Picasso.get().load(new File(arrivalHeadBeans.get(i).getFile())).into(vh.imageViewpic);
            }
            vh.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder=new AlertDialog.Builder(PutListActivity.this);
                    builder.setTitle("提示").setMessage("删除此条数据").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            SharedPreferences sharedPreferences = getSharedPreferences("sp", Context.MODE_PRIVATE);
                            //delete sharedPreferences->scan item
                            String stringscandata=sharedPreferences.getString("putscan","");
                            List<String> listcode = new ArrayList<>(Arrays.asList(stringscandata));
                            for (int j = 0; j <listcode.size() ; j++) {
                                if(listcode.get(j).contains(arrivalHeadBeans.get(i).getIrowno())){
                                    listcode.remove(j);
                                }
                            }
                            sharedPreferences.edit().putString("putscan",listcode.toString()).commit();

                            arrivalHeadBeans.remove(i);
                            functionAdapter.notifyDataSetChanged();
                            textViewtotal.setText("总计:"+arrivalHeadBeans.size()+"条");
                            //delete sharedPreferences->putlist item
                            String strings= new Gson().toJson(arrivalHeadBeans);
                            sharedPreferences.edit().putString("putlist",strings).commit();

                        }
                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).create().show();
                }
            });


        }

        @Override
        public int getItemCount() {
            return mDatas.size();
        }
        class  VH extends RecyclerView.ViewHolder{
            TextView textViewnumber,textViewccode,textViewline,textViewcposcode,textViewmaterial,textViewbatch,textViewcount;
            LinearLayout linearLayout;
            ImageView imageViewpic;
            public VH(@NonNull View itemView) {
                super(itemView);
                linearLayout=itemView.findViewById(R.id.l_input);
                textViewnumber=itemView.findViewById(R.id.tv_number);
                textViewccode=itemView.findViewById(R.id.tv_ccode);
                textViewline=itemView.findViewById(R.id.tv_line);
                textViewcposcode=itemView.findViewById(R.id.tv_cposcode);
                textViewmaterial=itemView.findViewById(R.id.tv_material);
                textViewbatch=itemView.findViewById(R.id.tv_batch);
                textViewcount=itemView.findViewById(R.id.tv_count);
                imageViewpic=itemView.findViewById(R.id.iv_pic);


            }
        }
    }


}
