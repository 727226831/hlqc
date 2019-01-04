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
import android.widget.ImageView;
import android.widget.TextView;
import com.example.storescontrol.bean.ArrivalHeadBean;

public abstract class ActivityDispatchdetailsBinding extends ViewDataBinding {
  @NonNull
  public final Button bSearch;

  @NonNull
  public final Button bSubmit;

  @NonNull
  public final TextView etBatch;

  @NonNull
  public final TextView etCwhcode;

  @NonNull
  public final EditText etTimes;

  @NonNull
  public final ImageView ivAdd;

  @NonNull
  public final ImageView ivMinus;

  @NonNull
  public final TextView tvCInvName;

  @NonNull
  public final TextView tvCInvStd;

  @NonNull
  public final TextView tvCinvcode;

  @NonNull
  public final TextView tvCount;

  @NonNull
  public final TextView tvNumber;

  @NonNull
  public final TextView tvTitle1;

  @NonNull
  public final TextView tvTitle2;

  @NonNull
  public final TextView tvTitle3;

  @NonNull
  public final TextView tvValue1;

  @NonNull
  public final TextView tvValue2;

  @NonNull
  public final TextView tvValue3;

  @Bindable
  protected ArrivalHeadBean mBean;

  protected ActivityDispatchdetailsBinding(DataBindingComponent _bindingComponent, View _root,
      int _localFieldCount, Button bSearch, Button bSubmit, TextView etBatch, TextView etCwhcode,
      EditText etTimes, ImageView ivAdd, ImageView ivMinus, TextView tvCInvName, TextView tvCInvStd,
      TextView tvCinvcode, TextView tvCount, TextView tvNumber, TextView tvTitle1,
      TextView tvTitle2, TextView tvTitle3, TextView tvValue1, TextView tvValue2,
      TextView tvValue3) {
    super(_bindingComponent, _root, _localFieldCount);
    this.bSearch = bSearch;
    this.bSubmit = bSubmit;
    this.etBatch = etBatch;
    this.etCwhcode = etCwhcode;
    this.etTimes = etTimes;
    this.ivAdd = ivAdd;
    this.ivMinus = ivMinus;
    this.tvCInvName = tvCInvName;
    this.tvCInvStd = tvCInvStd;
    this.tvCinvcode = tvCinvcode;
    this.tvCount = tvCount;
    this.tvNumber = tvNumber;
    this.tvTitle1 = tvTitle1;
    this.tvTitle2 = tvTitle2;
    this.tvTitle3 = tvTitle3;
    this.tvValue1 = tvValue1;
    this.tvValue2 = tvValue2;
    this.tvValue3 = tvValue3;
  }

  public abstract void setBean(@Nullable ArrivalHeadBean bean);

  @Nullable
  public ArrivalHeadBean getBean() {
    return mBean;
  }

  @NonNull
  public static ActivityDispatchdetailsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  @NonNull
  public static ActivityDispatchdetailsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable DataBindingComponent component) {
    return DataBindingUtil.<ActivityDispatchdetailsBinding>inflate(inflater, com.example.storescontrol.R.layout.activity_dispatchdetails, root, attachToRoot, component);
  }

  @NonNull
  public static ActivityDispatchdetailsBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  @NonNull
  public static ActivityDispatchdetailsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable DataBindingComponent component) {
    return DataBindingUtil.<ActivityDispatchdetailsBinding>inflate(inflater, com.example.storescontrol.R.layout.activity_dispatchdetails, null, false, component);
  }

  public static ActivityDispatchdetailsBinding bind(@NonNull View view) {
    return bind(view, DataBindingUtil.getDefaultComponent());
  }

  public static ActivityDispatchdetailsBinding bind(@NonNull View view,
      @Nullable DataBindingComponent component) {
    return (ActivityDispatchdetailsBinding)bind(component, view, com.example.storescontrol.R.layout.activity_dispatchdetails);
  }
}
