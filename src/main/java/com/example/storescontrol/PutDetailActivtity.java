package com.example.storescontrol;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.storescontrol.Url.Request;
import com.example.storescontrol.bean.ArrivalHeadBean;
import com.example.storescontrol.Url.iUrl;
import com.example.storescontrol.databinding.ActivityPutdetailBinding;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PutDetailActivtity extends  BaseActivity {

    TextView  titleTv;
    ArrivalHeadBean arrivalHeadBean;
    private  String dnumber;
    private ImageView imageViewreturn;
    String string1,string2;
    List<String> list;
    int tag=-1;
    ActivityPutdetailBinding binding;
    Gson gson=new Gson();
    private  String old="1";
    private  String cwhcode,cposition;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding=DataBindingUtil.setContentView(this,R.layout.activity_putdetail);

        titleTv=binding.getRoot().findViewById(R.id.tv_title);
        titleTv.setText(getIntent().getStringExtra("menuname"));
        if(getIntent().getStringExtra("menuname").equals("生产入库")){
            binding.lCvenabbname.setVisibility(View.GONE);
        }

        imageViewreturn=binding.getRoot().findViewById(R.id.iv_return);
        imageViewreturn.setVisibility(View.VISIBLE);
        imageViewreturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.ivCwhcode.setOnClickListener(onClickListener);
        binding.ivBatch.setOnClickListener(onClickListener);
        binding.bSubmit.setOnClickListener(onClickListener);
        binding.bSearch.setOnClickListener(onClickListener);
        binding.ivAdd.setOnClickListener(onClickListener);
        binding.ivMinus.setOnClickListener(onClickListener);
        binding.etTimes.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!s.toString().isEmpty()){
                    int times;
                    times = Integer.parseInt(s.toString());
                    if (times < 1) {
                        times=1;
                        binding.etTimes.setText(times+"");
                        Toast.makeText(PutDetailActivtity.this, "此值必须大于1", Toast.LENGTH_LONG).show();

                    }
                    changeIquantity(times);
                }


            }
        });

        binding.etCwhcode.setOnKeyListener(onKeyListener);
        binding.etBatch.setOnKeyListener(onKeyListener);


    }

    View.OnKeyListener onKeyListener=new View.OnKeyListener() {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if(keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                switch (v.getId()) {
                    case R.id.et_cwhcode:
                        if(binding.etCwhcode.getText().toString().contains("仓库")){
                            Toast.makeText(PutDetailActivtity.this,"如需重新查询，请清空该项所有字符",Toast.LENGTH_LONG).show();
                            break;
                        }
                        getCwhcode();
                        break;
                    case R.id.et_batch:
                        getData(binding.etBatch.getText().toString());

                        break;

                }
            }

            return false;
        }
    };


    View.OnClickListener onClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int times;
                 switch (v.getId()){
                     case R.id.iv_cwhcode:
                         tag=0;
                         openScan();
                         break;
                     case R.id.iv_add:
                         times=Integer.parseInt(binding.etTimes.getText().toString());
                         binding.etTimes.setText(times+1+"");
                         changeIquantity(times+1);


                         break;
                     case R.id.iv_minus:
                         times=Integer.parseInt(binding.etTimes.getText().toString());
                         if(times>1) {
                             binding.etTimes.setText(times - 1+"");
                             changeIquantity(times-1);
                         }

                         break;
                     case  R.id.iv_batch:
                         tag=1;
                          openScan();


                         break;
                     case R.id.b_search:
                         startActivity(new Intent(PutDetailActivtity.this,PutListActivity.class));
                         break;
                     case R.id.b_submit:
                         if(binding.etTimes.getText().toString().isEmpty()) {
                             binding.etTimes.setText("1");
                             Toast.makeText(PutDetailActivtity.this, "数量倍数值必须大于1", Toast.LENGTH_LONG).show();
                             changeIquantity(1);
                         }
                         if(arrivalHeadBean!=null) {
                             setList();
                         }

                         //clear view data
                         arrivalHeadBean=null;
                         binding.setBean(arrivalHeadBean);
                         binding.etBatch.setText("");
                         binding.tvNumber.setText("");
                         break;
                 }
        }

        private void openScan() {
            new IntentIntegrator(PutDetailActivtity.this)
                    // 自定义Activity，重点是这行----------------------------
                    .setCaptureActivity(ScanActivity.class)
                    .setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES)// 扫码的类型,可选：一维码，二维码，一/二维码
                    .setPrompt("请对准二维码")// 设置提示语
                    .setCameraId(0)// 选择摄像头,可使用前置或者后置
                    .setBeepEnabled(false)// 是否开启声音,扫完码之后会"哔"的一声
                    .setBarcodeImageEnabled(true)// 扫完码之后生成二维码的图片
                    .initiateScan();// 初始化扫码
        }
    };



    private void getCwhcode() {

        JSONObject jsonObject=new JSONObject();
        try {

            jsonObject.put("methodname","getWhcodeBypocode");
            jsonObject.put("acccode",acccode);
            jsonObject.put("cposition",binding.etCwhcode.getText().toString());
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
                       JSONObject object=new JSONObject(response.body().string());
                       cwhcode=object.getString("cwhcode");
                       if(!cwhcode.isEmpty()){
                           cposition=binding.etCwhcode.getText().toString();
                           binding.etCwhcode.setText(binding.etCwhcode.getText().toString()+"/仓库"+object.getString("cwhcode"));
                       }else {
                           Toast.makeText(PutDetailActivtity.this,"仓库为空",Toast.LENGTH_LONG).show();
                       }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(retrofit2.Call<ResponseBody> call, Throwable t) {

            } });
    }

    /**
     * 制造入库列表
     */
    private void setList() {



        ArrayList<ArrivalHeadBean> arrivalHeadBeans = new ArrayList<>();
        SharedPreferences sharedPreferences = getSharedPreferences("sp", Context.MODE_PRIVATE);
        String data = sharedPreferences.getString("putlist", "");

        if (!data.equals("")) {
            Gson gson = new Gson();
            JsonArray arry = new JsonParser().parse(data).getAsJsonArray();
            for (JsonElement jsonElement : arry) {
                arrivalHeadBeans.add(gson.fromJson(jsonElement, ArrivalHeadBean.class));
            }
        }
        //判断是否此单已添加
        boolean isSelected = false;
        for (int i = 0; i < arrivalHeadBeans.size(); i++) {
            if (arrivalHeadBean.getStamp().equals(arrivalHeadBeans.get(i).getStamp())) {

                isSelected = true;
            }else if (arrivalHeadBean.getCcode().equals(arrivalHeadBeans.get(i).getCcode())) {
                isSelected = true;
            }else if (arrivalHeadBean.getCwhcode().equals(arrivalHeadBeans.get(i).getCwhcode())) {
                isSelected = true;
            }
        }
        if (isSelected == true) {
            Toast.makeText(PutDetailActivtity.this, "时间戳/单号/仓库 不能重复", Toast.LENGTH_LONG).show();
        } else {
            arrivalHeadBean.setCwhcode(cwhcode);
            arrivalHeadBean.setCposition(cposition);

            arrivalHeadBean.setIquantity(binding.tvNumber.getText().toString());
            arrivalHeadBeans.add(arrivalHeadBean);
            String strings = new Gson().toJson(arrivalHeadBeans);
            sharedPreferences.edit().putString("putlist", strings).commit();
        }
    }

    private void changeIquantity(int times) {
        if(arrivalHeadBean!=null){
            int i=Integer.parseInt(old);
            binding.tvNumber.setText(times*i+arrivalHeadBean.getCComUnitName());

        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() != null) {
                String code=result.getContents();
                if(tag==0 && code.length()>5){
                    Toast.makeText(PutDetailActivtity.this,"类型错误",Toast.LENGTH_LONG).show();
                }else {
                    binding.etBatch.setText(code);
                    Log.i("scan",code);
                    getData(code);

            }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void getData(String code){
        if(code.isEmpty()){
            return;
        }

        String  numbers=code.replace("$",",");
        list = Arrays.asList(numbers.split(","));
;
       if(list.size()<4){
           Toast.makeText(PutDetailActivtity.this,"条码类型错误！",Toast.LENGTH_LONG).show();
           return;
       }
        dnumber=list.get(4);
        getInventoryBycode(list.get(0));
        binding.etTimes.setText("1");
    }

    private void getInventoryBycode(String cinvcode) {
        JSONObject jsonObject=new JSONObject();
        try {

            jsonObject.put("methodname","getInventoryBycode");
            jsonObject.put("acccode",acccode);
            jsonObject.put("cinvcode",cinvcode);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String obj=jsonObject.toString();
        Log.i("getInventoryBycode",obj);

        Call<ResponseBody> data =Request.getRequestbody(obj);
        data.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(retrofit2.Call<ResponseBody> call, Response<ResponseBody> response) {

                try {
                    if(response.code()==200) {
                        JSONArray jsonArray=new JSONArray(response.body().string());
                        if(jsonArray.isNull(0)!=true){
                            String data=jsonArray.getJSONObject(0).toString();
                            arrivalHeadBean=gson.fromJson(data,ArrivalHeadBean.class);

                            string1=data.substring(1,data.length()-1)+",";
                            binding.setBean(arrivalHeadBean);
                            if(dnumber!=null) {
                                getArrivalHeadBycode(dnumber);
                            }
                        }else {
                            Toast.makeText(PutDetailActivtity.this,"未找到数据",Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(retrofit2.Call<ResponseBody> call, Throwable t) {

            } });

    }



    private void getArrivalHeadBycode(String s) {

        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("methodname","getArrivalHeadBycode");
            jsonObject.put("acccode",acccode);
            jsonObject.put("ccode",s);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String obj=jsonObject.toString();
        Log.i("getArrivalHeadBycode",obj);
        Call<ResponseBody> data =Request.getRequestbody(obj);
        data.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(retrofit2.Call<ResponseBody> call, Response<ResponseBody> response) {

                try {


                    if(response.code()==200) {
                        JSONArray jsonArray=new JSONArray(response.body().string());
                        if(jsonArray.isNull(0)!=true){

                            String data=jsonArray.getJSONObject(0).toString();
                           string2=data.substring(1,data.length()-1);

                            String string="{"+string1+string2+"}";
                            arrivalHeadBean=gson.fromJson(string,ArrivalHeadBean.class);
                            arrivalHeadBean.setMaterial(list.get(0));
                            arrivalHeadBean.setCbatch(list.get(1));
                            arrivalHeadBean.setIquantity(list.get(2));
                            arrivalHeadBean.setIrowno(list.get(5));
                            arrivalHeadBean.setStamp(list.get(6));
                            Log.i("arrivalHeadBean",new Gson().toJson(arrivalHeadBean));
                            binding.setBean(arrivalHeadBean);
                            binding.tvNumber.setText(arrivalHeadBean.getIquantity()+arrivalHeadBean.getCComUnitName());
                            old=arrivalHeadBean.getIquantity();


                        }else {
                            Toast.makeText(PutDetailActivtity.this,"未找到数据",Toast.LENGTH_SHORT).show();
                        }
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
