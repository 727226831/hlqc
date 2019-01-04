package com.example.storescontrol.databinding;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public abstract class ItemInputBinding extends ViewDataBinding {
  @NonNull
  public final ImageView ivPic;

  @NonNull
  public final LinearLayout lInput;

  @NonNull
  public final TextView tvBatch;

  @NonNull
  public final TextView tvCcode;

  @NonNull
  public final TextView tvCount;

  @NonNull
  public final TextView tvCposcode;

  @NonNull
  public final TextView tvLine;

  @NonNull
  public final TextView tvMaterial;

  @NonNull
  public final TextView tvNumber;

  protected ItemInputBinding(DataBindingComponent _bindingComponent, View _root,
      int _localFieldCount, ImageView ivPic, LinearLayout lInput, TextView tvBatch,
      TextView tvCcode, TextView tvCount, TextView tvCposcode, TextView tvLine, TextView tvMaterial,
      TextView tvNumber) {
    super(_bindingComponent, _root, _localFieldCount);
    this.ivPic = ivPic;
    this.lInput = lInput;
    this.tvBatch = tvBatch;
    this.tvCcode = tvCcode;
    this.tvCount = tvCount;
    this.tvCposcode = tvCposcode;
    this.tvLine = tvLine;
    this.tvMaterial = tvMaterial;
    this.tvNumber = tvNumber;
  }

  @NonNull
  public static ItemInputBinding inflate(@NonNull LayoutInflater inflater, @Nullable ViewGroup root,
      boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  @NonNull
  public static ItemInputBinding inflate(@NonNull LayoutInflater inflater, @Nullable ViewGroup root,
      boolean attachToRoot, @Nullable DataBindingComponent component) {
    return DataBindingUtil.<ItemInputBinding>inflate(inflater, com.example.storescontrol.R.layout.item_input, root, attachToRoot, component);
  }

  @NonNull
  public static ItemInputBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  @NonNull
  public static ItemInputBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable DataBindingComponent component) {
    return DataBindingUtil.<ItemInputBinding>inflate(inflater, com.example.storescontrol.R.layout.item_input, null, false, component);
  }

  public static ItemInputBinding bind(@NonNull View view) {
    return bind(view, DataBindingUtil.getDefaultComponent());
  }

  public static ItemInputBinding bind(@NonNull View view,
      @Nullable DataBindingComponent component) {
    return (ItemInputBinding)bind(component, view, com.example.storescontrol.R.layout.item_input);
  }
}
