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

public abstract class ItemDetailListBinding extends ViewDataBinding {
  @NonNull
  public final LinearLayout lInput;

  @NonNull
  public final TextView tvCBatch;

  @NonNull
  public final TextView tvCInvName;

  @NonNull
  public final TextView tvCInvStd;

  @NonNull
  public final TextView tvCompleted;

  @NonNull
  public final TextView tvCposition;

  @NonNull
  public final TextView tvDetails;

  @NonNull
  public final TextView tvIQuantity;

  @NonNull
  public final TextView tvIncomplete;

  @NonNull
  public final TextView tvTag;

  protected ItemDetailListBinding(DataBindingComponent _bindingComponent, View _root,
      int _localFieldCount, LinearLayout lInput, TextView tvCBatch, TextView tvCInvName,
      TextView tvCInvStd, TextView tvCompleted, TextView tvCposition, TextView tvDetails,
      TextView tvIQuantity, TextView tvIncomplete, TextView tvTag) {
    super(_bindingComponent, _root, _localFieldCount);
    this.lInput = lInput;
    this.tvCBatch = tvCBatch;
    this.tvCInvName = tvCInvName;
    this.tvCInvStd = tvCInvStd;
    this.tvCompleted = tvCompleted;
    this.tvCposition = tvCposition;
    this.tvDetails = tvDetails;
    this.tvIQuantity = tvIQuantity;
    this.tvIncomplete = tvIncomplete;
    this.tvTag = tvTag;
  }

  @NonNull
  public static ItemDetailListBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  @NonNull
  public static ItemDetailListBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable DataBindingComponent component) {
    return DataBindingUtil.<ItemDetailListBinding>inflate(inflater, com.example.storescontrol.R.layout.item_detail_list, root, attachToRoot, component);
  }

  @NonNull
  public static ItemDetailListBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  @NonNull
  public static ItemDetailListBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable DataBindingComponent component) {
    return DataBindingUtil.<ItemDetailListBinding>inflate(inflater, com.example.storescontrol.R.layout.item_detail_list, null, false, component);
  }

  public static ItemDetailListBinding bind(@NonNull View view) {
    return bind(view, DataBindingUtil.getDefaultComponent());
  }

  public static ItemDetailListBinding bind(@NonNull View view,
      @Nullable DataBindingComponent component) {
    return (ItemDetailListBinding)bind(component, view, com.example.storescontrol.R.layout.item_detail_list);
  }
}
