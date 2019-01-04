package com.example.storescontrol.databinding;

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
import android.widget.RelativeLayout;

public abstract class ActivityPrintBinding extends ViewDataBinding {
  @NonNull
  public final Button bPrint;

  @NonNull
  public final EditText etCbatch;

  @NonNull
  public final EditText etCinvcode;

  @NonNull
  public final EditText etCvenabbname;

  @NonNull
  public final EditText etDdate;

  @NonNull
  public final EditText etIquantity;

  @NonNull
  public final ImageView ivCode;

  @NonNull
  public final RelativeLayout rlTag;

  protected ActivityPrintBinding(DataBindingComponent _bindingComponent, View _root,
      int _localFieldCount, Button bPrint, EditText etCbatch, EditText etCinvcode,
      EditText etCvenabbname, EditText etDdate, EditText etIquantity, ImageView ivCode,
      RelativeLayout rlTag) {
    super(_bindingComponent, _root, _localFieldCount);
    this.bPrint = bPrint;
    this.etCbatch = etCbatch;
    this.etCinvcode = etCinvcode;
    this.etCvenabbname = etCvenabbname;
    this.etDdate = etDdate;
    this.etIquantity = etIquantity;
    this.ivCode = ivCode;
    this.rlTag = rlTag;
  }

  @NonNull
  public static ActivityPrintBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  @NonNull
  public static ActivityPrintBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable DataBindingComponent component) {
    return DataBindingUtil.<ActivityPrintBinding>inflate(inflater, com.example.storescontrol.R.layout.activity_print, root, attachToRoot, component);
  }

  @NonNull
  public static ActivityPrintBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  @NonNull
  public static ActivityPrintBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable DataBindingComponent component) {
    return DataBindingUtil.<ActivityPrintBinding>inflate(inflater, com.example.storescontrol.R.layout.activity_print, null, false, component);
  }

  public static ActivityPrintBinding bind(@NonNull View view) {
    return bind(view, DataBindingUtil.getDefaultComponent());
  }

  public static ActivityPrintBinding bind(@NonNull View view,
      @Nullable DataBindingComponent component) {
    return (ActivityPrintBinding)bind(component, view, com.example.storescontrol.R.layout.activity_print);
  }
}
