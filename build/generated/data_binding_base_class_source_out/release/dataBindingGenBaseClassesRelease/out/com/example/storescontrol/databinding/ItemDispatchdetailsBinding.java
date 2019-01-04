package com.example.storescontrol.databinding;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

public abstract class ItemDispatchdetailsBinding extends ViewDataBinding {
  @NonNull
  public final CheckBox cbSelect;

  @NonNull
  public final RelativeLayout lInput;

  @NonNull
  public final TextView tvBWhPos;

  @NonNull
  public final TextView tvCBatch;

  @NonNull
  public final TextView tvCinvcode;

  @NonNull
  public final TextView tvCinvname;

  @NonNull
  public final TextView tvCinvstd;

  @NonNull
  public final TextView tvIquantity;

  @NonNull
  public final TextView tvIrowno;

  @NonNull
  public final TextView tvNumber;

  protected ItemDispatchdetailsBinding(DataBindingComponent _bindingComponent, View _root,
      int _localFieldCount, CheckBox cbSelect, RelativeLayout lInput, TextView tvBWhPos,
      TextView tvCBatch, TextView tvCinvcode, TextView tvCinvname, TextView tvCinvstd,
      TextView tvIquantity, TextView tvIrowno, TextView tvNumber) {
    super(_bindingComponent, _root, _localFieldCount);
    this.cbSelect = cbSelect;
    this.lInput = lInput;
    this.tvBWhPos = tvBWhPos;
    this.tvCBatch = tvCBatch;
    this.tvCinvcode = tvCinvcode;
    this.tvCinvname = tvCinvname;
    this.tvCinvstd = tvCinvstd;
    this.tvIquantity = tvIquantity;
    this.tvIrowno = tvIrowno;
    this.tvNumber = tvNumber;
  }

  @NonNull
  public static ItemDispatchdetailsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  @NonNull
  public static ItemDispatchdetailsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable DataBindingComponent component) {
    return DataBindingUtil.<ItemDispatchdetailsBinding>inflate(inflater, com.example.storescontrol.R.layout.item_dispatchdetails, root, attachToRoot, component);
  }

  @NonNull
  public static ItemDispatchdetailsBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  @NonNull
  public static ItemDispatchdetailsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable DataBindingComponent component) {
    return DataBindingUtil.<ItemDispatchdetailsBinding>inflate(inflater, com.example.storescontrol.R.layout.item_dispatchdetails, null, false, component);
  }

  public static ItemDispatchdetailsBinding bind(@NonNull View view) {
    return bind(view, DataBindingUtil.getDefaultComponent());
  }

  public static ItemDispatchdetailsBinding bind(@NonNull View view,
      @Nullable DataBindingComponent component) {
    return (ItemDispatchdetailsBinding)bind(component, view, com.example.storescontrol.R.layout.item_dispatchdetails);
  }
}
