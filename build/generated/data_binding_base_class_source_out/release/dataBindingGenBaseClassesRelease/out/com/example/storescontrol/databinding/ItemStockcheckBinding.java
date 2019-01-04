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

public abstract class ItemStockcheckBinding extends ViewDataBinding {
  @NonNull
  public final LinearLayout lInput;

  @NonNull
  public final TextView tvCBatch;

  @NonNull
  public final TextView tvCInvCode;

  @NonNull
  public final TextView tvCInvName;

  @NonNull
  public final TextView tvCInvStd;

  @NonNull
  public final TextView tvCPosName;

  @NonNull
  public final TextView tvCWhName;

  @NonNull
  public final TextView tvIQuantity;

  @NonNull
  public final TextView tvTag;

  protected ItemStockcheckBinding(DataBindingComponent _bindingComponent, View _root,
      int _localFieldCount, LinearLayout lInput, TextView tvCBatch, TextView tvCInvCode,
      TextView tvCInvName, TextView tvCInvStd, TextView tvCPosName, TextView tvCWhName,
      TextView tvIQuantity, TextView tvTag) {
    super(_bindingComponent, _root, _localFieldCount);
    this.lInput = lInput;
    this.tvCBatch = tvCBatch;
    this.tvCInvCode = tvCInvCode;
    this.tvCInvName = tvCInvName;
    this.tvCInvStd = tvCInvStd;
    this.tvCPosName = tvCPosName;
    this.tvCWhName = tvCWhName;
    this.tvIQuantity = tvIQuantity;
    this.tvTag = tvTag;
  }

  @NonNull
  public static ItemStockcheckBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  @NonNull
  public static ItemStockcheckBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable DataBindingComponent component) {
    return DataBindingUtil.<ItemStockcheckBinding>inflate(inflater, com.example.storescontrol.R.layout.item_stockcheck, root, attachToRoot, component);
  }

  @NonNull
  public static ItemStockcheckBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  @NonNull
  public static ItemStockcheckBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable DataBindingComponent component) {
    return DataBindingUtil.<ItemStockcheckBinding>inflate(inflater, com.example.storescontrol.R.layout.item_stockcheck, null, false, component);
  }

  public static ItemStockcheckBinding bind(@NonNull View view) {
    return bind(view, DataBindingUtil.getDefaultComponent());
  }

  public static ItemStockcheckBinding bind(@NonNull View view,
      @Nullable DataBindingComponent component) {
    return (ItemStockcheckBinding)bind(component, view, com.example.storescontrol.R.layout.item_stockcheck);
  }
}
