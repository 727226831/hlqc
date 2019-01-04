package com.example.storescontrol.databinding;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public abstract class ItemProductionListBinding extends ViewDataBinding {
  @NonNull
  public final LinearLayout lInput;

  @NonNull
  public final TextView tvBatch;

  @NonNull
  public final TextView tvCTRCode;

  @NonNull
  public final TextView tvCWhName;

  @NonNull
  public final TextView tvCposcode;

  @NonNull
  public final TextView tvDate;

  @NonNull
  public final TextView tvNumber;

  protected ItemProductionListBinding(DataBindingComponent _bindingComponent, View _root,
      int _localFieldCount, LinearLayout lInput, TextView tvBatch, TextView tvCTRCode,
      TextView tvCWhName, TextView tvCposcode, TextView tvDate, TextView tvNumber) {
    super(_bindingComponent, _root, _localFieldCount);
    this.lInput = lInput;
    this.tvBatch = tvBatch;
    this.tvCTRCode = tvCTRCode;
    this.tvCWhName = tvCWhName;
    this.tvCposcode = tvCposcode;
    this.tvDate = tvDate;
    this.tvNumber = tvNumber;
  }

  @NonNull
  public static ItemProductionListBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  @NonNull
  public static ItemProductionListBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable DataBindingComponent component) {
    return DataBindingUtil.<ItemProductionListBinding>inflate(inflater, com.example.storescontrol.R.layout.item_production_list, root, attachToRoot, component);
  }

  @NonNull
  public static ItemProductionListBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  @NonNull
  public static ItemProductionListBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable DataBindingComponent component) {
    return DataBindingUtil.<ItemProductionListBinding>inflate(inflater, com.example.storescontrol.R.layout.item_production_list, null, false, component);
  }

  public static ItemProductionListBinding bind(@NonNull View view) {
    return bind(view, DataBindingUtil.getDefaultComponent());
  }

  public static ItemProductionListBinding bind(@NonNull View view,
      @Nullable DataBindingComponent component) {
    return (ItemProductionListBinding)bind(component, view, com.example.storescontrol.R.layout.item_production_list);
  }
}
