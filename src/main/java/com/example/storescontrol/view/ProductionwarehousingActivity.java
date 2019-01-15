package com.example.storescontrol.view;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;

import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.example.storescontrol.R;
import com.example.storescontrol.Url.Request;
import com.example.storescontrol.Url.Untils;
import com.example.storescontrol.bean.ArrivalHeadBean;
import com.example.storescontrol.bean.DetailsBean;
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
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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
    DetailsBean detailsBean=new DetailsBean();
    private  String ccode; //单号
    String string1,string2;
    List<String> list;
    int tag=-1;//0仓库 1料号
    Gson gson=new Gson();
    private  String old="1";
    private  String cwhcode,cposition;
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
    double iQuantitytotal=-1;
    double overplus=-1;

    int MY_PERMISSIONS_REQUEST_CALL_PHONE=300;
    int  MY_PERMISSIONS_REQUEST_CALL_PHONE2=400;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding=DataBindingUtil.setContentView(this,R.layout.activity_productionwarehousing);

        Untils.initTitle(getIntent().getStringExtra("menuname"),this);
        sharedPreferences=getSharedPreferences("sp",MODE_PRIVATE);
        if(getIntent().getStringExtra("menuname").equals("生产入库")){
            binding.lCvenabbname.setVisibility(View.GONE);
            binding.lBatch.setVisibility(View.VISIBLE);
            binding.bSearch.setVisibility(View.VISIBLE);
            binding.tvTotal.setVisibility(View.VISIBLE);
        }else  if(getIntent().getStringExtra("menuname").equals("采购入库")){
            binding.lCvenabbname.setVisibility(View.VISIBLE);
            binding.lBatch.setVisibility(View.VISIBLE);
            binding.bSearch.setVisibility(View.VISIBLE);
            binding.tvTotal.setVisibility(View.VISIBLE);
        }else {
            binding.lCvenabbname.setVisibility(View.VISIBLE);
            binding.lBatch.setVisibility(View.GONE);
            binding.bSearch.setVisibility(View.GONE);
            binding.tvTotal.setVisibility(View.GONE);

            detailsBean=new Gson().fromJson(sharedPreferences.getString("detailsBean",""),DetailsBean.class);
            isCheck=true;
            if(getIntent().getStringExtra("menuname").equals("材料出库")||
                    getIntent().getStringExtra("menuname").equals("调拨出库")){
                isPrint=true;
            }
        }

        if(isCheck){
            binding.etBatch.requestFocus();
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
        binding.etBatch.setOnKeyListener(onKeyListener);
        binding.ibUpload.setOnClickListener(onClickListener);
        binding.ivCwhcode.setOnClickListener(onClickListener);
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
                Uri uri = null;
                if (data != null && data.getData() != null) {
                    uri = data.getData();
                }
                if (uri == null) {
                    if (photoUri != null) {
                        uri = photoUri;
                        uploadBatchPicture(file.getAbsolutePath());
                    }
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
                            if(code.contains("$")){
                                Toast.makeText(ProductionwarehousingActivity.this,"类型错误",Toast.LENGTH_LONG).show();
                            }else {
                                binding.etCwhcode.setText(code);
                                getCwhcode();
                            }
                            break;
                        case 1://存货编码

                            if(code.contains("$")){
                                binding.etBatch.setText(code);
                                parseCode(code);
                            }else {
                                Toast.makeText(ProductionwarehousingActivity.this,"类型错误",Toast.LENGTH_LONG).show();
                            }
                            break;
                    }

                }
            } else {
                super.onActivityResult(requestCode, resultCode, data);
            }


        }

    /**
     * parse code
     * @param code
     */
    private void parseCode(String code){
        stringScan=code;
        if(code.isEmpty()){
            return;
        }

        String  numbers=code.replace("$",",");
        list = Arrays.asList(numbers.split(","));


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
        Log.i("json object",obj);

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
                            ccode =list.get(4);
                            if(ccode !=null) {
                                getArrivalHeadBycode(ccode);
                            }
                        }else {

                            Toast.makeText(ProductionwarehousingActivity.this,"未找到数据",Toast.LENGTH_SHORT).show();
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
        Log.i("json object",obj);
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
                            arrivalHeadBean.setMaterial(list.get(4));   //料号
                            arrivalHeadBean.setCbatch(list.get(1));  //批号
                            arrivalHeadBean.setIquantity(list.get(2));
                            arrivalHeadBean.setIrowno(list.get(5));
                            arrivalHeadBean.setImageid(imageid);

                            arrivalHeadBean.setCwhcode(cwhcode);
                            if(file!=null) {
                                arrivalHeadBean.setFile(file.getAbsolutePath());
                            }
                            binding.setBean(arrivalHeadBean);
                            binding.tvNumber.setText(arrivalHeadBean.getIquantity()+arrivalHeadBean.getCComUnitName());
                            old=arrivalHeadBean.getIquantity();

                                submit();

                        }else {
                            if(!isCheck) {
                                Toast.makeText(ProductionwarehousingActivity.this, "未找到数据", Toast.LENGTH_SHORT).show();
                            }else {
                                arrivalHeadBean.setMaterial(list.get(4));   //料号
                                arrivalHeadBean.setCbatch(list.get(1));  //批号
                                arrivalHeadBean.setIquantity(list.get(2));
                                arrivalHeadBean.setIrowno(list.get(5));
                                binding.setBean(arrivalHeadBean);
                                binding.tvNumber.setText(arrivalHeadBean.getIquantity()+arrivalHeadBean.getCComUnitName());
                                submit();

                            }
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
                        getCwhcode();
                        break;
                    case R.id.et_batch:
                        parseCode(binding.etBatch.getText().toString());
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
                    startActivity(new Intent(ProductionwarehousingActivity.this,PutListActivity.class));
                    break;
                case R.id.b_submit:

                    submit();
                    checkData();

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

    private void checkData() {
        if(isCheck){
            if(list==null) {
                return;
            }

            for (int i = 0; i < detailsBean.getData().size(); i++) {
                if (list.get(0).equals(detailsBean.getData().get(i).getCInvCode()) &&
                        list.get(1).equals(detailsBean.getData().get(i).getCBatch())
                        )
                {
                    isBatch = true;
                    isCInvCode = true;

                    iQuantitytotal = Double.parseDouble(detailsBean.getData().get(i).getIncomplete());

                    double dIncomplete=Double.parseDouble(detailsBean.getData().get(i).getIncomplete());
                    double dCompleted=Double.parseDouble(detailsBean.getData().get(i).getCompleted());



                    if(dIncomplete<Double.parseDouble(arrivalHeadBean.getIquantity())){
                        overplus=Double.parseDouble(arrivalHeadBean.getIquantity())-dIncomplete;
                        detailsBean.getData().get(i).setCompleted(detailsBean.getData().get(i).getIQuantity());
                        detailsBean.getData().get(i).setIncomplete("0");
                        Toast.makeText(ProductionwarehousingActivity.this, "数量超过上限", Toast.LENGTH_LONG).show();
                        if(!isPrint) {
                            return;
                        }
                    }else {
                        dIncomplete=dIncomplete-Double.parseDouble(arrivalHeadBean.getIquantity());
                        dCompleted=dCompleted+Double.parseDouble(arrivalHeadBean.getIquantity());
                        detailsBean.getData().get(i).setCompleted(dCompleted+"");
                        detailsBean.getData().get(i).setIncomplete(dIncomplete+"");

                    }
                    sharedPreferences.edit().putString("detailsBean",new Gson().toJson(detailsBean)).commit();

                }

            }


            if (isPrint) {

                if (!isCInvCode && isBatch ) {
                    Toast.makeText(ProductionwarehousingActivity.this, "料号/批号错误！", Toast.LENGTH_LONG).show();
                    return;
                }
                if(overplus!=-1){
                    Intent intent = new Intent(ProductionwarehousingActivity.this, PrintActivity.class);
                    intent.putExtra("code", stringScan);
                    intent.putExtra("overplus", overplus);
                    intent.putExtra("cvenabbname", arrivalHeadBean.getCvenabbname());
                    intent.putExtra("ddate", arrivalHeadBean.getDdate());
                    startActivity(intent);
                }

            }
        }
    }



    @Override
    protected void onStart() {
        super.onStart();
        arrivalHeadBean=null;
        binding.setBean(arrivalHeadBean);
        binding.tvNumber.setText("");
        getCount();
    }
    //获取已加入清单量
    private void getCount() {
        ArrayList<ArrivalHeadBean> arrivalHeadBeans = new ArrayList<>();
        String stringarrivalHeadBeans = sharedPreferences.getString("putlist", "");
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
            public void onFailure(retrofit2.Call<ResponseBody> call, Throwable t) {

            } });
    }

    /**
     * 制造入库列表
     */
    private void setList() {
        ArrayList<ArrivalHeadBean> arrivalHeadBeans = new ArrayList<>();
        String stringarrivalHeadBeans;
        if(isCheck){
            stringarrivalHeadBeans = sharedPreferences.getString("checklist", "");
        }else {
            stringarrivalHeadBeans= sharedPreferences.getString("putlist", "");
        }


        if (!stringarrivalHeadBeans.equals("")) {
            Gson gson = new Gson();
            JsonArray arry = new JsonParser().parse(stringarrivalHeadBeans).getAsJsonArray();
            for (JsonElement jsonElement : arry) {
                arrivalHeadBeans.add(gson.fromJson(jsonElement, ArrivalHeadBean.class));
            }
        }
        if(!isCheck) {
            if (arrivalHeadBean.getCwhcode() == null) {
                Toast.makeText(ProductionwarehousingActivity.this, "仓库不能为空", Toast.LENGTH_LONG).show();
                return;
            }
        }
        if(stringScan==null){
            return;
        }
        String stringscandata;
        if(isCheck){
            stringscandata=sharedPreferences.getString("checkscan","");
        }else {
            stringscandata=sharedPreferences.getString("putscan","");
        }

        if(stringscandata.contains(stringScan) ){
            Toast.makeText(ProductionwarehousingActivity.this, "此二维码数据已添加", Toast.LENGTH_LONG).show();
            binding.etBatch.setText("");
            return;
        }



            binding.tvTotal.setText("总计："+arrivalHeadBeans.size()+"条");
            //update sharedPreferences->putlist item
            arrivalHeadBean.setCposition(cposition);
            arrivalHeadBean.setIquantity(binding.tvNumber.getText().toString().
                    replace(arrivalHeadBean.getCComUnitName(),""));
            arrivalHeadBeans.add(arrivalHeadBean);
            String strings = new Gson().toJson(arrivalHeadBeans);
            if(isCheck){
                sharedPreferences.edit().putString("checklist", strings).commit();
            }else {
                sharedPreferences.edit().putString("putlist", strings).commit();
            }


            //update sharedPreferences->scan item
            List<String> listscan=new ArrayList<>();
            if(stringscandata.equals("")){
                listscan.add(stringScan);
            }else {
                listscan=new ArrayList<>(Arrays.asList(stringscandata));
                listscan.add(stringScan);
            }

            if(isCheck){
                sharedPreferences.edit().putString("checkscan",listscan.toString()).commit();

            }else {
                sharedPreferences.edit().putString("putscan",listscan.toString()).commit();

            }
            getCount();
            initBatch();
            checkData();



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


    private void changeIquantity(int times) {
        if(arrivalHeadBean!=null){
            double i=Double.parseDouble(old);
            binding.tvNumber.setText(times*i+arrivalHeadBean.getCComUnitName());

        }
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
            public void onResponse(retrofit2.Call<ResponseBody> call, Response<ResponseBody> response) {
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
            public void onFailure(retrofit2.Call<ResponseBody> call, Throwable t) {
                dialog.dismiss();
                if(t.getMessage().equals("timeout")){
                    Toast.makeText(ProductionwarehousingActivity.this,"连接超时",Toast.LENGTH_LONG).show();
                }

            } });
    }


}
