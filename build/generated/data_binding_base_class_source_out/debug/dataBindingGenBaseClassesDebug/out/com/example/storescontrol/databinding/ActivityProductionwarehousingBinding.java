package com.example.storescontrol.databinding;

import android.databinding.Bindable;
import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.storescontrol.bean.ArrivalHeadBean;

public abstract class ActivityProductionwarehousingBinding extends ViewDataBinding {
  @NonNull
  public final Button bSearch;

  @NonNull
  public final Button bSubmit;

  @NonNull
  public final EditText etBatch;

  @NonNull
  public final EditText etCwhcode;

  @NonNull
  public final EditText etTimes;

  @NonNull
  public final ImageButton ibUpload;

  @NonNull
  public final ImageView ivAdd;

  @NonNull
  public final ImageView ivBatch;

  @NonNull
  public final ImageView ivCwhcode;

  @NonNull
  public final ImageView ivMinus;

  @NonNull
  public final LinearLayout lBatch;

  @NonNull
  public final LinearLayout lCvenabbname;

  @NonNull
  public final TextView tvCInvName;

  @NonNull
  public final TextView tvCInvStd;

  @NonNull
  public final TextView tvCcode;

  @NonNull
  public final TextView tvCinvcode;

  @NonNull
  public final TextView tvCount;

  @NonNull
  public final TextView tvCvenabbname;

  @NonNull
  public final TextView tvNumber;

  @NonNull
  public final TextView tvTitle1;

  @NonNull
  public final TextView tvTitle2;

  @NonNull
  public final TextView tvTotal;

  @Bindable
  protected ArrivalHeadBean mBean;

  protected ActivityProductionwarehousingBinding(DataBindingComponent _bindingComponent, View _root,
      int _localFieldCount, Button bSearch, Button bSubmit, EditText etBatch, EditText etCwhcode,
      EditText etTimes, ImageButton ibUpload, ImageView ivAdd, ImageView ivBatch,
      ImageView ivCwhcode, ImageView ivMinus, LinearLayout lBatch, LinearLayout lCvenabbname,
      TextView tvCInvName, TextView tvCInvStd, TextView tvCcode, TextView tvCinvcode,
      TextView tvCount, TextView tvCvenabbname, TextView tvNumber, TextView tvTitle1,
      TextView tvTitle2, TextView tvTotal) {
    super(_bindingComponent, _root, _localFieldCount);
    this.bSearch = bSearch;
    this.bSubmit = bSubmit;
    this.etBatch = etBatch;
    this.etCwhcode = etCwhcode;
    this.etTimes = etTimes;
    this.ibUpload = ibUpload;
    this.ivAdd = ivAdd;
    this.ivBatch = ivBatch;
    this.ivCwhcode = ivCwhcode;
    this.ivMinus = ivMinus;
    this.lBatch = lBatch;
    this.lCvenabbname = lCvenabbname;
    this.tvCInvName = tvCInvName;
    this.tvCInvStd = tvCInvStd;
    this.tvCcode = tvCcode;
    this.tvCinvcode = tvCinvcode;
    this.tvCount = tvCount;
    this.tvCvenabbname = tvCvenabbname;
    this.tvNumber = tvNumber;
    this.tvTitle1 = tvTitle1;
    this.tvTitle2 = tvTitle2;
    this.tvTotal = tvTotal;
  }

  public abstract void setBean(@Nullable ArrivalHeadBean bean);

  @Nullable
  public ArrivalHeadBean getBean() {
    return mBean;
  }

  @NonNull
  public static ActivityProductionwarehousingBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  @NonNull
  public static ActivityProductionwarehousingBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable DataBindingComponent component) {
    return DataBindingUtil.<ActivityProductionwarehousingBinding>inflate(inflater, com.example.storescontrol.R.layout.activity_productionwarehousing, root, attachToRoot, component);
  }

  @NonNull
  public static ActivityProductionwarehousingBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  @NonNull
  public static ActivityProductionwarehousingBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable DataBindingComponent component) {
    return DataBindingUtil.<ActivityProductionwarehousingBinding>inflate(inflater, com.example.storescontrol.R.layout.activity_productionwarehousing, null, false, component);
  }

  public static ActivityProductionwarehousingBinding bind(@NonNull View view) {
    return bind(view, DataBindingUtil.getDefaultComponent());
  }

  public static ActivityProductionwarehousingBinding bind(@NonNull View view,
      @Nullable DataBindingComponent component) {
    return (ActivityProductionwarehousingBinding)bind(component, view, com.example.storescontrol.R.layout.activity_productionwarehousing);
  }
}
