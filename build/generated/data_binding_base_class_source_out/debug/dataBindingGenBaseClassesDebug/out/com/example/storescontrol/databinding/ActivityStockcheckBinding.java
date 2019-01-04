package com.example.storescontrol.databinding;

import android.databinding.Bindable;
import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.storescontrol.bean.ArrivalHeadBean;

public abstract class ActivityStockcheckBinding extends ViewDataBinding {
  @NonNull
  public final Button bSearch;

  @NonNull
  public final EditText etBatch;

  @NonNull
  public final EditText etInventory;

  @NonNull
  public final EditText etPosition;

  @NonNull
  public final EditText etWarehouse;

  @NonNull
  public final LinearLayout lInput;

  @NonNull
  public final RecyclerView rvList;

  @NonNull
  public final TextView tvTitle0;

  @NonNull
  public final TextView tvTitle1;

  @Bindable
  protected ArrivalHeadBean mBean;

  protected ActivityStockcheckBinding(DataBindingComponent _bindingComponent, View _root,
      int _localFieldCount, Button bSearch, EditText etBatch, EditText etInventory,
      EditText etPosition, EditText etWarehouse, LinearLayout lInput, RecyclerView rvList,
      TextView tvTitle0, TextView tvTitle1) {
    super(_bindingComponent, _root, _localFieldCount);
    this.bSearch = bSearch;
    this.etBatch = etBatch;
    this.etInventory = etInventory;
    this.etPosition = etPosition;
    this.etWarehouse = etWarehouse;
    this.lInput = lInput;
    this.rvList = rvList;
    this.tvTitle0 = tvTitle0;
    this.tvTitle1 = tvTitle1;
  }

  public abstract void setBean(@Nullable ArrivalHeadBean bean);

  @Nullable
  public ArrivalHeadBean getBean() {
    return mBean;
  }

  @NonNull
  public static ActivityStockcheckBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  @NonNull
  public static ActivityStockcheckBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable DataBindingComponent component) {
    return DataBindingUtil.<ActivityStockcheckBinding>inflate(inflater, com.example.storescontrol.R.layout.activity_stockcheck, root, attachToRoot, component);
  }

  @NonNull
  public static ActivityStockcheckBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  @NonNull
  public static ActivityStockcheckBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable DataBindingComponent component) {
    return DataBindingUtil.<ActivityStockcheckBinding>inflate(inflater, com.example.storescontrol.R.layout.activity_stockcheck, null, false, component);
  }

  public static ActivityStockcheckBinding bind(@NonNull View view) {
    return bind(view, DataBindingUtil.getDefaultComponent());
  }

  public static ActivityStockcheckBinding bind(@NonNull View view,
      @Nullable DataBindingComponent component) {
    return (ActivityStockcheckBinding)bind(component, view, com.example.storescontrol.R.layout.activity_stockcheck);
  }
}
