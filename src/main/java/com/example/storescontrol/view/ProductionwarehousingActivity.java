package com.example.storescontrol.view;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.example.storescontrol.R;
import com.example.storescontrol.Url.Request;
import com.example.storescontrol.Url.Untils;
import com.example.storescontrol.bean.ArrivalHeadBean;
import com.example.storescontrol.bean.DetailsBean;
import com.example.storescontrol.bean.DispatchdetailsBean;
import com.example.storescontrol.databinding.ActivityProductionwarehousingBinding;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 生产/采购 入库
 */
public class ProductionwarehousingActivity extends BaseActivity {
    ActivityProductionwarehousingBinding binding;
    ArrivalHeadBean arrivalHeadBean;
    DispatchdetailsBean dispatchdetailsBean=new DispatchdetailsBean();
    DetailsBean detailsBean=new DetailsBean();
    private  String ccode; //单号
    String string1,string2;
    List<String> list;
    int tag=-1;//0仓库 1料号
    Gson gson=new Gson();
    private  String old="1";
    private  String cwhcode,cposition,updatecposition;
    private  String imageid="";
    Uri photoUri;
    int imageresultcode=100;
    File file;

    private  String stringScan; //扫描到的二维码
    private static final int CAMERA_PERMISSIONS_REQUEST_CODE = 0x03;
    SharedPreferences sharedPreferences;

    boolean isCheck=false;//判断是否是生产/采购出库 之外的
    boolean isPrint=false;//调拨/材料出库 需要打印

    boolean isCInvCode=false;
    boolean isBatch=false;
    double iQuantitytotal;
    double overplus=-1;
    boolean isError=true;
    String sign="$";


    int MY_PERMISSIONS_REQUEST_CALL_PHONE=300;
    int  MY_PERMISSIONS_REQUEST_CALL_PHONE2=400;
    private String cbatch;
    private String cinvcode;
    boolean autoAdd=true;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding=DataBindingUtil.setContentView(this, R.layout.activity_productionwarehousing);

        Untils.initTitle(getIntent().getStringExtra("menuname"),this);

        if(Request.URL.equals(Request.URL_AR)){
            binding.lCboxno.setVisibility(View.VISIBLE);

        }

        sharedPreferences=getSharedPreferences("sp",MODE_PRIVATE);
        if(getIntent().getStringExtra("menuname").equals("生产入库")){
            if(Request.URL.equals(Request.URL_WKF)){
                binding.lBatch.setVisibility(View.GONE);
                binding.bSearch.setVisibility(View.GONE);
                binding.tvTotal.setVisibility(View.GONE);
                binding.rlUpdate.setVisibility(View.GONE);
                detailsBean=getIntent().getParcelableExtra("detailsBean");
                isCheck=true;
                if(!Request.URL.equals(Request.URL_AR)){
                    isPrint=true;
                }

            }else {
                binding.lCvenabbname.setVisibility(View.GONE);
                binding.rlUpdate.setVisibility(View.GONE);
            }
        }else
            if(getIntent().getStringExtra("menuname").equals("采购入库")){
            binding.rlUpdate.setVisibility(View.GONE);
        }else  if(getIntent().getStringExtra("menuname").equals("库存盘点")||
                getIntent().getStringExtra("menuname").equals("产成品入库")){
            binding.lCvenabbname.setVisibility(View.GONE);
            binding.lBatch.setVisibility(View.GONE);
            binding.rlUpdate.setVisibility(View.GONE);
        }else  if(getIntent().getStringExtra("menuname").equals("货位调整")){
            binding.lCvenabbname.setVisibility(View.GONE);
        }else  if(getIntent().getStringExtra("menuname").equals("采购到货")){
            binding.rlCdefine10.setVisibility(View.VISIBLE);
            binding.rlUpdate.setVisibility(View.GONE);
            binding.tvCcodekey.setText("采购订单号：");
            binding.rlCwhcode.setVisibility(View.GONE);

        }
        else {
            binding.lBatch.setVisibility(View.GONE);
            binding.bSearch.setVisibility(View.GONE);
            binding.tvTotal.setVisibility(View.GONE);
            binding.rlUpdate.setVisibility(View.GONE);
            if(getIntent().getStringExtra("menuname").equals("销售出库")){
                dispatchdetailsBean=getIntent().getParcelableExtra("dispatchdetailsBean");

            }else {
                detailsBean=getIntent().getParcelableExtra("detailsBean");
            }


            isCheck=true;
            if(getIntent().getStringExtra("menuname").equals("材料出库")||
                    getIntent().getStringExtra("menuname").equals("调拨出库")){
                if(!Request.URL.equals(Request.URL_AR)){
                    isPrint=true;
                }
            }
        }

        if(isCheck){
            binding.etBatch.requestFocus();
        }
        if(Request.URL.equals(Request.URL_WKF)){
            sign="|";
            if(getIntent().getStringExtra("menuname").equals("调拨入库")||
                    getIntent().getStringExtra("menuname").equals("调拨出库")||
                    getIntent().getStringExtra("menuname").equals("库存盘点")){
               autoAdd=false;
            }
        }
        if(Request.URL.equals(Request.URL_AR)){
            if(getIntent().getStringExtra("menuname").equals("调拨入库")||
                    getIntent().getStringExtra("menuname").equals("调拨出库")||
                    getIntent().getStringExtra("menuname").equals("库存盘点")||
                    getIntent().getStringExtra("menuname").equals("销售出库")||
                    getIntent().getStringExtra("menuname").equals("材料出库")
            ){
                autoAdd=false;
            }
        }




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
                        Toast.makeText(ProductionwarehousingActivity.this, "此值必须大于1", Toast.LENGTH_LONG).show();
                    }
                    changeIquantity(times);
                }
            }
        });
        binding.etCwhcode.setOnKeyListener(onKeyListener);
        binding.etUpdatecwhcode.setOnKeyListener(onKeyListener);
        binding.etBatch.setOnKeyListener(onKeyListener);
        binding.ibUpload.setOnClickListener(onClickListener);
        binding.ivCwhcode.setOnClickListener(onClickListener);
        binding.ivUpdatecwhcode.setOnClickListener(onClickListener);
        binding.ivBatch.setOnClickListener(onClickListener);
        binding.bSubmit.setOnClickListener(onClickListener);
        binding.bSearch.setOnClickListener(onClickListener);
        binding.ivAdd.setOnClickListener(onClickListener);
        binding.ivMinus.setOnClickListener(onClickListener);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        //上传图片
        if (requestCode == imageresultcode) {
            if (photoUri != null) {
                uploadBatchPicture(file.getAbsolutePath());
            }

        }
        //扫码
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() != null) {
                String code=result.getContents();
                Log.i("scan",code);

                switch (tag){
                    case 0://仓位
                        if(code.contains(sign)){
                            Toast.makeText(ProductionwarehousingActivity.this,"类型错误",Toast.LENGTH_LONG).show();
                        }else {
                            binding.etCwhcode.setText(code);
                            getCwhcode(0);
                        }
                        break;
                    case 1://存货编码

                        if(code.contains(sign)){
                            stringScan=code;
                            binding.etBatch.setText(code);

                            if(Request.URL.equals(Request.URL_WKF)){
                                list=Untils.parseCode(code,1);
                                cinvcode=list.get(2);
                                cbatch=list.get(4);
                                getInventoryBycode(list.get(2));
                            }else if(Request.URL.equals(Request.URL_AR)){
                                list=Untils.parseCode(code,0);
                                cinvcode=list.get(0);
                                cbatch=list.get(1);
                                getInventoryBycode(list.get(0));
                            }else {
                                list=Untils.parseCode(code,0);
                                Log.i("list size",list.size()+"");
                                getInventoryBycode(list.get(0));
                            }




                            binding.etTimes.setText("1");

                        }else {
                            Toast.makeText(ProductionwarehousingActivity.this,"类型错误",Toast.LENGTH_LONG).show();
                        }
                        break;
                    case 2:
                        //调入货位
                        if(code.contains(sign)){
                            Toast.makeText(ProductionwarehousingActivity.this,"类型错误",Toast.LENGTH_LONG).show();
                        }else {
                            binding.etUpdatecwhcode.setText(code);
                            getCwhcode(1);

                        }
                        break;

                }

            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }


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
        Log.i("json object",obj);

        Call<ResponseBody> data =Request.getRequestbody(obj);
        data.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                try {
                    if(response.code()==200) {

                        JSONArray jsonArray=new JSONArray(response.body().string());
                        if(jsonArray.isNull(0)!=true){
                            String data=jsonArray.getJSONObject(0).toString();
                            arrivalHeadBean=gson.fromJson(data,ArrivalHeadBean.class);
                            binding.setBean(arrivalHeadBean);

                            if(Request.URL.equals(Request.URL_WKF)){
                                ccode = list.get(2);
                                getArrivalHeadBycode(ccode);
                                if(list.size()>7) {
                                    binding.tvCvenbatch.setText("供应商批次：" + list.get(7));
                                    arrivalHeadBean.setCvenbatch(list.get(7));
                                }
                            }else {
                                if(list.size()>3) {
                                    ccode = list.get(4);

                                    getArrivalHeadBycode(ccode);
                                }


                                if( !Request.URL.equals(Request.URL_AR) && list.size()>7) {
                                    binding.tvCvenbatch.setText("供应商批次：" + list.get(7));
                                    arrivalHeadBean.setCvenbatch(list.get(7));
                                }
                            }


                            string1=new Gson().toJson(arrivalHeadBean).substring(1,new Gson().toJson(arrivalHeadBean).length()-1)+",";

                        }else {

                            Toast.makeText(ProductionwarehousingActivity.this,"未找到数据",Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            } });

    }

    private void getArrivalHeadBycode(String s) {

        JSONObject jsonObject=new JSONObject();
        try {

            jsonObject.put("methodname","getArrivalHeadBycode");
            jsonObject.put("acccode",acccode);
            jsonObject.put("ccode",s);
//            if(Request.URL.equals(Request.URL_AR)) {
//                jsonObject.put("cVouchType", getIntent().getStringExtra("menuname"));
//            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String obj=jsonObject.toString();
        Log.i("json object",obj);
        Call<ResponseBody> data =Request.getRequestbody(obj);
        data.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                try {
                    if(response.code()==200) {
                        JSONArray jsonArray=new JSONArray(response.body().string());
                        if(jsonArray.isNull(0)!=true){

                            String data=jsonArray.getJSONObject(0).toString();
                            string2=data.substring(1,data.length()-1);
                            String string="{"+string1+string2+"}";
                            Log.i("response-->",string);
                            arrivalHeadBean=gson.fromJson(string,ArrivalHeadBean.class);
                            if(Request.URL.equals(Request.URL_AR)){
                                arrivalHeadBean.setMaterial(list.get(0));
                                arrivalHeadBean.setCbatch(list.get(1));  //批号
                                arrivalHeadBean.setIquantity(list.get(2));
                               arrivalHeadBean.setCboxno(list.get(7));
                            }else if(Request.URL.equals(Request.URL_WKF)){
                                arrivalHeadBean.setCbatch(list.get(4));  //批号
                                arrivalHeadBean.setIquantity(list.get(3));
                                arrivalHeadBean.setMaterial(list.get(4));
                            }else {
                                arrivalHeadBean.setCbatch(list.get(1));  //批号
                                arrivalHeadBean.setIquantity(list.get(2));
                                arrivalHeadBean.setMaterial(list.get(4));
                            }
                            arrivalHeadBean.setIrowno(list.get(5));
                            arrivalHeadBean.setImageid(imageid);
                            arrivalHeadBean.setCwhcode(cwhcode);

                            if(file!=null) {
                                arrivalHeadBean.setFile(file.getAbsolutePath());
                            }
                            binding.setBean(arrivalHeadBean);
                            if(arrivalHeadBean.getcComUnitName()!=null) {
                                binding.etCount.setText(arrivalHeadBean.getIquantity() + arrivalHeadBean.getCComUnitName());
                            }else {
                                arrivalHeadBean.getIquantity();
                            }
                            old=arrivalHeadBean.getIquantity();


                        }else {
                            if(getIntent().getStringExtra("menuname").equals("采购入库")) {
                                Toast.makeText(ProductionwarehousingActivity.this, "未找到数据", Toast.LENGTH_SHORT).show();
                            }else {
                                if(Request.URL.equals(Request.URL_WKF)){
                                    arrivalHeadBean.setIquantity(list.get(3));
                                    arrivalHeadBean.setCbatch(list.get(4));
                                }else  if(Request.URL.equals(Request.URL_AR)){
                                    arrivalHeadBean.setMaterial(list.get(0));
                                    arrivalHeadBean.setCbatch(list.get(1));  //批号
                                    arrivalHeadBean.setIquantity(list.get(2));
                                    arrivalHeadBean.setCboxno(list.get(7));
                                    arrivalHeadBean.setCcode(list.get(4));
                                    arrivalHeadBean.setIrowno(list.get(5));
                                }else {
                                    arrivalHeadBean.setMaterial(list.get(0));   //料号
                                    arrivalHeadBean.setCbatch(list.get(1));  //批号
                                    arrivalHeadBean.setIquantity(list.get(2));
                                    arrivalHeadBean.setCcode(list.get(4));
                                    arrivalHeadBean.setIrowno(list.get(5));
                                }
                                if(cwhcode!=null) {
                                    arrivalHeadBean.setCwhcode(cwhcode);
                                }
                                binding.setBean(arrivalHeadBean);
                                binding.etCount.setText(arrivalHeadBean.getIquantity()+arrivalHeadBean.getCComUnitName());



                            }
                        }
                        if(autoAdd){
                            submit();
                        }

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            } });
    }

    View.OnKeyListener onKeyListener=new View.OnKeyListener() {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if(keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                switch (v.getId()) {
                    case R.id.et_cwhcode:
                        if(binding.etCwhcode.getText().toString().contains("仓库")){
                            Toast.makeText(ProductionwarehousingActivity.this,"如需" +
                                    "重新查询，请清空该项所有字符",Toast.LENGTH_LONG).show();
                            break;
                        }
                        getCwhcode(0);
                        break;
                    case R.id.et_batch:
                        stringScan=binding.etBatch.getText().toString();


                        if(Request.URL.equals(Request.URL_WKF)){
                            list=Untils.parseCode( stringScan,1);
                            cinvcode=list.get(2);
                            cbatch=list.get(4);
                            getInventoryBycode(list.get(2));
                        }else if(Request.URL.equals(Request.URL_AR)){
                            list=Untils.parseCode(stringScan,0);
                            cinvcode=list.get(0);
                            cbatch=list.get(1);
                            getInventoryBycode(list.get(0));
                        }else {
                            list=Untils.parseCode( stringScan,0);
                            getInventoryBycode(list.get(0));
                        }

                        binding.etTimes.setText("1");
                        break;
                    case R.id.et_updatecwhcode:
                        if(binding.etUpdatecwhcode.getText().toString().contains("仓库")){
                            Toast.makeText(ProductionwarehousingActivity.this,"如需" +
                                    "重新查询，请清空该项所有字符",Toast.LENGTH_LONG).show();
                            break;
                        }
                        getCwhcode(1);
                        break;
                }
            }

            return false;
        }
    };


    View.OnClickListener onClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int times;//数量倍数
            switch (v.getId()){

                case R.id.iv_cwhcode:
                    tag=0;
                    openScan();
                    break;
                case R.id.iv_updatecwhcode:
                    tag=2;
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
                    Intent intent=new Intent(ProductionwarehousingActivity.this,PutListActivity.class);
                    intent.putExtra("menuname",getIntent().getStringExtra("menuname"));
                    if(binding.rlCdefine10.getVisibility()==View.VISIBLE){
                        intent.putExtra("cdefine10",binding.etCdefine10.getText().toString());
                    }
                    startActivity(intent);
                    break;
                case R.id.b_submit:

                    submit();

                    break;
                case R.id.ib_upload:

                    takePhone();

                    break;
            }
        }




        public void takePhone() {
            if (Build.VERSION.SDK_INT >= 23) {
                int checkCallPhonePermission = ContextCompat.checkSelfPermission(ProductionwarehousingActivity.this,
                        Manifest.permission.CAMERA);
                if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(ProductionwarehousingActivity.this, new String[]{Manifest.permission.CAMERA,}
                            , MY_PERMISSIONS_REQUEST_CALL_PHONE);
                } else if (ContextCompat.checkSelfPermission(ProductionwarehousingActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(ProductionwarehousingActivity.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA},
                            MY_PERMISSIONS_REQUEST_CALL_PHONE2);
                } else {
                    //  takePhoto();
                    autoObtainCameraPermission();

                }

            } else {
                // takePhoto();
                autoObtainCameraPermission();
            }
        }





        private void openScan() {

            new IntentIntegrator(ProductionwarehousingActivity.this)
                    .setCaptureActivity(ScanActivity.class)
                    .setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES)// 扫码的类型,可选：一维码，二维码，一/二维码
                    .setPrompt("请对准二维码")// 设置提示语
                    .setCameraId(0)// 选择摄像头,可使用前置或者后置
                    .setBeepEnabled(false)// 是否开启声音,扫完码之后会"哔"的一声
                    .setBarcodeImageEnabled(true)// 扫完码之后生成二维码的图片
                    .initiateScan();// 初始化扫码

        }
    };

    private void submit() {

        if(binding.etTimes.getText().toString().isEmpty()) {
            binding.etTimes.setText("1");
            Toast.makeText(ProductionwarehousingActivity.this, "数量倍数值必须大于1", Toast.LENGTH_LONG).show();
            changeIquantity(1);
        }

        if(arrivalHeadBean!=null) {
            setList();
        }
    }
    private void changeIquantity(int times) {
        if(arrivalHeadBean!=null){

            BigDecimal i=new BigDecimal(old);
            BigDecimal time=new BigDecimal(times);
            binding.etCount.setText(time.multiply(i)+arrivalHeadBean.getCComUnitName());

        }
    }
    /**
     * 制造入库列表
     */
    private void setList() {

        String stringarrivalHeadBeans="";
        if(isCheck){
            stringarrivalHeadBeans = sharedPreferences.getString("checklist", "");
        }else if(getIntent().getStringExtra("menuname").equals("采购入库")) {

            stringarrivalHeadBeans= sharedPreferences.getString("CreatePuStoreInlist", "");
        }else  if(getIntent().getStringExtra("menuname").equals("库存盘点")) {
            stringarrivalHeadBeans= sharedPreferences.getString("CreateCheckdetailslist", "");
        }else  if(getIntent().getStringExtra("menuname").equals("生产入库")){

            stringarrivalHeadBeans= sharedPreferences.getString("CreateProductStoreInlist", "");
        }else  if(getIntent().getStringExtra("menuname").equals("货位调整")){
            stringarrivalHeadBeans= sharedPreferences.getString("UpdatePositionTRlist", "");
        }else  if(getIntent().getStringExtra("menuname").equals("采购到货")){
            stringarrivalHeadBeans= sharedPreferences.getString("CreatePuArrivalInlist", "");
        }




        ArrayList<ArrivalHeadBean> arrivalHeadBeans = new ArrayList<>();
        if (!stringarrivalHeadBeans.equals("")) {
            Gson gson = new Gson();
            JsonArray arry = new JsonParser().parse(stringarrivalHeadBeans).getAsJsonArray();
            for (JsonElement jsonElement : arry) {
                arrivalHeadBeans.add(gson.fromJson(jsonElement, ArrivalHeadBean.class));
            }
        }

        if(!isCheck) {
            if (arrivalHeadBean.getCwhcode() == null) {
                if(Request.URL!=Request.URL_WKF){
                    if(!getIntent().getStringExtra("menuname").equals("销售出库")){
                        Toast.makeText(ProductionwarehousingActivity.this, "仓库不能为空", Toast.LENGTH_LONG).show();
                        return;
                    }
                }


            }
        }

        if(stringScan==null){
            return;
        }
        String stringscandata="";

        if(isCheck){
            stringscandata=sharedPreferences.getString("checkscan","");
        }else if(getIntent().getStringExtra("menuname").equals("采购入库")) {

            stringscandata=sharedPreferences.getString("CreatePuStoreInscan","");
        }else  if(getIntent().getStringExtra("menuname").equals("库存盘点")) {
            stringscandata=sharedPreferences.getString("CreateCheckdetailsscan","");

        }else  if(getIntent().getStringExtra("menuname").equals("生产入库")){

            stringscandata=sharedPreferences.getString("CreateProductStoreInscan","");
        }else  if(getIntent().getStringExtra("menuname").equals("货位调整")){

            stringscandata=sharedPreferences.getString("UpdatePositionTRscan","");
        }else  if(getIntent().getStringExtra("menuname").equals("采购到货")){
            stringscandata=sharedPreferences.getString("CreatePuArrivalInscan","");
        }else  if(getIntent().getStringExtra("menuname").equals("销售出库")){
            stringscandata=sharedPreferences.getString("CreatePuArrivalInscan","");
        }


            if (stringscandata.contains(stringScan)) {
                Log.i("scan", stringscandata + "/" + stringScan);
                Toast.makeText(ProductionwarehousingActivity.this, "此二维码数据已添加", Toast.LENGTH_LONG).show();
                binding.etBatch.setText("");
                return;
            }






        binding.tvTotal.setText("总计："+arrivalHeadBeans.size()+"条");
        //update sharedPreferences->putlist item
        if(getIntent().getStringExtra("menuname").equals("货位调整")){
            arrivalHeadBean.setInposition(updatecposition);
        }
        arrivalHeadBean.setCposition(cposition);

        if(arrivalHeadBean.getcComUnitName()!=null) {
            arrivalHeadBean.setIquantity(binding.etCount.getText().toString().
                    replace(arrivalHeadBean.getCComUnitName(), ""));
        }

        arrivalHeadBeans.add(arrivalHeadBean);
        String strings = new Gson().toJson(arrivalHeadBeans);
        if(isCheck){
            sharedPreferences.edit().putString("checklist", strings).commit();
        } else if(getIntent().getStringExtra("menuname").equals("采购入库")) {
            sharedPreferences.edit().putString("CreatePuStoreInlist", strings).commit();
        }else  if(getIntent().getStringExtra("menuname").equals("库存盘点")) {
            sharedPreferences.edit().putString("CreateCheckdetailslist", strings).commit();
        }else  if(getIntent().getStringExtra("menuname").equals("生产入库")){
            sharedPreferences.edit().putString("CreateProductStoreInlist", strings).commit();
        }else  if(getIntent().getStringExtra("menuname").equals("货位调整")){
            sharedPreferences.edit().putString("UpdatePositionTRlist", strings).commit();
        }else  if(getIntent().getStringExtra("menuname").equals("采购到货")){
            sharedPreferences.edit().putString("CreatePuArrivalInlist", strings).commit();
        }






        //update sharedPreferences->scan item
        List<String> listscan=new ArrayList<>();
        if(stringscandata.equals("")){
            listscan.add(stringScan);
        }else {
            listscan=new ArrayList<>(Arrays.asList(stringscandata));
            listscan.add(stringScan);
        }


        getCount();

        initBatch();
        if(isCheck){

            checkData();
            if(isOverplus){
                isOverplus=false;

            }else {
                sharedPreferences.edit().putString("checkscan",listscan.toString()).commit();
            }

        }else    if(getIntent().getStringExtra("menuname").equals("采购入库")) {
            sharedPreferences.edit().putString("CreatePuStoreInscan",listscan.toString()).commit();
        }else  if(getIntent().getStringExtra("menuname").equals("库存盘点")) {
            sharedPreferences.edit().putString("CreateCheckdetailsscan",listscan.toString()).commit();
        }else  if(getIntent().getStringExtra("menuname").equals("生产入库")){
            sharedPreferences.edit().putString("CreateProductStoreInscan",listscan.toString()).commit();
        }else  if(getIntent().getStringExtra("menuname").equals("货位调整")){
            sharedPreferences.edit().putString("UpdatePositionTRscan",listscan.toString()).commit();
        }else  if(getIntent().getStringExtra("menuname").equals("采购到货")){
            sharedPreferences.edit().putString("CreatePuArrivalInscan",listscan.toString()).commit();
        }


    }

    /**
     * 初始化数据
     */
    private void initBatch() {
        binding.etBatch.setText("");

        Picasso.get().load(getResources().getResourceName(R.mipmap.ic_defaultpic)).into(binding.ibUpload);
        file=null;
        binding.etBatch.setFocusable(true);
        binding.etBatch.setFocusableInTouchMode(true);
        binding.etBatch.requestFocus();
        imageid="";
    }

    private void checkData() {
        if(isCheck){
            if(list==null) {
                return;
            }
            if(arrivalHeadBean==null){
                return;
            }
            if(getIntent().getStringExtra("menuname").equals("销售出库")) {
                checkSale();
            }else {
                checkList();
            }
            if(isError) {
                isError=true;
                Toast.makeText(ProductionwarehousingActivity.this, "料号/批号错误！", Toast.LENGTH_LONG).show();
            }


            if (isPrint) {

                if(overplus!=-1){
                    Intent intent = new Intent(ProductionwarehousingActivity.this, PrintActivity.class);
                    intent.putExtra("arrivalHeadBean",arrivalHeadBean);
                    intent.putExtra("code", stringScan);
                    intent.putExtra("overplus", overplus);
                    startActivity(intent);
                }

            }
        }
    }

    private void checkSale() {

        for (int i = 0; i < dispatchdetailsBean.getData().size(); i++) {
           if (list.get(0).equals(dispatchdetailsBean.getData().get(i).getCinvcode()) &&
                    list.get(1).equals(dispatchdetailsBean.getData().get(i).getCbatch())) {

               isError=false;
               iQuantitytotal = Double.parseDouble(dispatchdetailsBean.getData().get(i).getIncomplete());
               double dIncomplete = Double.parseDouble(dispatchdetailsBean.getData().get(i).getIncomplete());
               double dCompleted = Double.parseDouble(dispatchdetailsBean.getData().get(i).getCompleted());
               if (arrivalHeadBean.getIquantity() == null) {
                   return;
               }

               //是否超出

               if(dIncomplete<Double.parseDouble(arrivalHeadBean.getIquantity())){
                   isOverplus=true;
                   Toast.makeText(ProductionwarehousingActivity.this, "数量超出"+overplus, Toast.LENGTH_LONG).show();
                   return;
               } else {

                   dIncomplete = dIncomplete-(Double.parseDouble(arrivalHeadBean.getIquantity()));
                   dCompleted = dCompleted+(Double.parseDouble(arrivalHeadBean.getIquantity()));
                   dispatchdetailsBean.getData().get(i).setCompleted(dCompleted + "");
                   dispatchdetailsBean.getData().get(i).setIncomplete(dIncomplete + "");

               }
               dispatchdetailsBean.getData().get(i).setCposition(cposition);
               Log.i("put-->",new Gson().toJson(dispatchdetailsBean));
               sharedPreferences.edit().putString("detailsBean", new Gson().toJson(dispatchdetailsBean)).commit();
            }



        }
    }
   private  Boolean isOverplus=false;
    private void checkList() {
        for (int i = 0; i < detailsBean.getData().size(); i++) {

            if(detailsBean.getData().get(i).getCBatch()==null &&cinvcode.equals(detailsBean.getData().get(i).getCInvCode())) {
                isBatch = true;
                isCInvCode = true;
            }else if (cinvcode.equals(detailsBean.getData().get(i).getCInvCode()) && cbatch.equals(detailsBean.getData().get(i).getCBatch())) {
                isBatch = true;
                isCInvCode = true;
            }

            if (isCInvCode && isBatch ) {

                isError=false;
                isCInvCode=false;
                isBatch=false;
                iQuantitytotal=Double.parseDouble(detailsBean.getData().get(i).getIncomplete());
                Double dIncomplete=Double.parseDouble(detailsBean.getData().get(i).getIncomplete());

                Double dCompleted=Double.parseDouble(detailsBean.getData().get(i).getCompleted());
                if(arrivalHeadBean.getIquantity()==null){
                    return;
                }

                if(dIncomplete<Double.parseDouble(arrivalHeadBean.getIquantity())){
                    overplus=Double.parseDouble(arrivalHeadBean.getIquantity())-(dIncomplete);
                    detailsBean.getData().get(i).setCompleted(detailsBean.getData().get(i).getIQuantity());
                    detailsBean.getData().get(i).setIncomplete("0");
                    Toast.makeText(ProductionwarehousingActivity.this, "数量超过上限", Toast.LENGTH_LONG).show();
                    if(!isPrint) {
                        return;
                    }
                }else {
                    dIncomplete=dIncomplete-(Double.parseDouble(arrivalHeadBean.getIquantity()));
                    dCompleted=dCompleted+(Double.parseDouble(arrivalHeadBean.getIquantity()));
                    detailsBean.getData().get(i).setCompleted(dCompleted+"");
                    detailsBean.getData().get(i).setIncomplete(dIncomplete+"");

                }

                sharedPreferences.edit().putString("detailsBean",new Gson().toJson(detailsBean)).commit();

            }
            return;
        }


    }


    @Override
    protected void onStart() {
        super.onStart();
        arrivalHeadBean=null;
        binding.setBean(arrivalHeadBean);
        binding.etCount.setText("");
        binding.tvCvenbatch.setText("");
        getCount();
    }
    //获取已加入清单量
    private void getCount() {
        ArrayList<ArrivalHeadBean> arrivalHeadBeans = new ArrayList<>();
        String stringarrivalHeadBeans="";
        if(getIntent().getStringExtra("menuname").equals("采购入库")) {
            stringarrivalHeadBeans = sharedPreferences.getString("CreatePuStoreInlist", "");
        }else  if(getIntent().getStringExtra("menuname").equals("库存盘点")) {

            stringarrivalHeadBeans = sharedPreferences.getString("CreateCheckdetailslist", "");
        }else  if(getIntent().getStringExtra("menuname").equals("生产入库")){

            stringarrivalHeadBeans = sharedPreferences.getString("CreateProductStoreInlist", "");
        }else  if(getIntent().getStringExtra("menuname").equals("货位调整")){
            stringarrivalHeadBeans = sharedPreferences.getString("UpdatePositionTRlist", "");
        }else  if(getIntent().getStringExtra("menuname").equals("采购到货")){
            stringarrivalHeadBeans = sharedPreferences.getString("CreatePuArrivalInlist", "");
        }
        if (!stringarrivalHeadBeans.equals("")) {
            Gson gson = new Gson();
            JsonArray arry = new JsonParser().parse(stringarrivalHeadBeans).getAsJsonArray();
            for (JsonElement jsonElement : arry) {
                arrivalHeadBeans.add(gson.fromJson(jsonElement, ArrivalHeadBean.class));
            }
        }
        binding.tvTotal.setText("总计："+arrivalHeadBeans.size()+"条");
    }



    private void autoObtainCameraPermission() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                // ToastUtils.showShort(this, "您已经拒绝过一次");
            }
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE}, CAMERA_PERMISSIONS_REQUEST_CODE);
        } else {//有权限直接调用系统相机拍照
            if (hasSdcard()) {
                String path = Environment.getExternalStorageDirectory() +
                        File.separator + Environment.DIRECTORY_DCIM + File.separator;
                String fileName = Untils.getPhotoFileName() + ".jpg";
                file = new File(path);
                if (!file.exists()) {
                    file.mkdir();
                }

                photoUri = Uri.fromFile(new File(path + fileName));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                    //通过FileProvider创建一个content类型的Uri
                    file=new File(path + fileName);
                photoUri=FileProvider.getUriForFile(ProductionwarehousingActivity.this
                        ,"com.storescontrol.fileprovider",file);
                Intent intentCamera = new Intent();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    intentCamera.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //添加这一句表示对目标应用临时授权该Uri所代表的文件
                }
                intentCamera.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                //将拍照结果保存至photo_file的Uri中，不保留在相册中
                intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(intentCamera, imageresultcode);

            }
        }
    }

    public static boolean hasSdcard() {
        String state = Environment.getExternalStorageState();
        return state.equals(Environment.MEDIA_MOUNTED);
    }

    private void getCwhcode(final  int type) {
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("methodname","getWhcodeBypocode");
            jsonObject.put("acccode",acccode);
            switch (type){
                case 0:
                    jsonObject.put("cposition",binding.etCwhcode.getText().toString());
                    break;
                case 1:
                    jsonObject.put("cposition",binding.etUpdatecwhcode.getText().toString());
                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        String obj=jsonObject.toString();
        Log.i("json object",obj);

        Call<ResponseBody> data =Request.getRequestbody(obj);
        data.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                try {
                    if(response.code()==200) {
                        JSONObject object=new JSONObject(response.body().string());
                        cwhcode=object.getString("cwhcode");
                        if(!cwhcode.isEmpty()){
                            switch (type){
                                case 0:
                                    cposition=binding.etCwhcode.getText().toString();
                                    binding.etCwhcode.setText(binding.etCwhcode.getText().toString()+"/仓库"+object.getString("cwhcode"));
                                    break;
                                case 1:
                                    updatecposition=binding.etUpdatecwhcode.getText().toString();
                                    binding.etUpdatecwhcode.setText(binding.etUpdatecwhcode.getText().toString()+"/仓库"+object.getString("cwhcode"));

                                    break;
                            }
                            if(arrivalHeadBean!=null){
                                arrivalHeadBean.setCwhcode(cwhcode);
                            }

                            binding.etBatch.setFocusable(true);
                            binding.etBatch.setFocusableInTouchMode(true);
                            binding.etBatch.requestFocus();
                        }else {
                            Toast.makeText(ProductionwarehousingActivity.this,"仓库为空",Toast.LENGTH_LONG).show();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            } });
    }






    private void uploadBatchPicture(String path) {
        dialog.show();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("methodname","UploadBatchPicture");
            jsonObject.put("acccode",acccode);
            jsonObject.put("usercode",usercode);
            jsonObject.put("ccode","");
            jsonObject.put("image",Untils.imageToBase64(path));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        String obj=jsonObject.toString();
        Log.i("json object",obj);
        Call<ResponseBody> data =Request.getRequestbody(obj);
        data.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                dialog.dismiss();
                try {

                    if(response.code()==200) {
                        JSONObject jsonObjectresponse=new JSONObject(response.body().string());

                        Toast.makeText(ProductionwarehousingActivity.this,jsonObjectresponse.
                                getString("ResultMessage"),Toast.LENGTH_LONG).show();
                        imageid=jsonObjectresponse.optString("id");

                        Picasso.get().load(file).into(binding.ibUpload);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                dialog.dismiss();
                if(t.getMessage().equals("timeout")){
                    Toast.makeText(ProductionwarehousingActivity.this,"连接超时",Toast.LENGTH_LONG).show();
                }

            } });
    }


}
