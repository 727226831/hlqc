package com.example.storescontrol;

import android.databinding.DataBinderMapper;
import android.databinding.DataBindingComponent;
import android.databinding.ViewDataBinding;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import com.example.storescontrol.databinding.ActivityDispatchdetailsBindingImpl;
import com.example.storescontrol.databinding.ActivityIndexBindingImpl;
import com.example.storescontrol.databinding.ActivityLoginBindingImpl;
import com.example.storescontrol.databinding.ActivityPrintBindingImpl;
import com.example.storescontrol.databinding.ActivityProductionwarehousingBindingImpl;
import com.example.storescontrol.databinding.ActivityPutdetailBindingImpl;
import com.example.storescontrol.databinding.ActivityStockcheckBindingImpl;
import com.example.storescontrol.databinding.ItemDetailListBindingImpl;
import com.example.storescontrol.databinding.ItemDispatchdetailsBindingImpl;
import com.example.storescontrol.databinding.ItemInputBindingImpl;
import com.example.storescontrol.databinding.ItemProductionListBindingImpl;
import com.example.storescontrol.databinding.ItemStockcheckBindingImpl;
import java.lang.IllegalArgumentException;
import java.lang.Integer;
import java.lang.Object;
import java.lang.Override;
import java.lang.RuntimeException;
import java.lang.String;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataBinderMapperImpl extends DataBinderMapper {
  private static final int LAYOUT_ACTIVITYDISPATCHDETAILS = 1;

  private static final int LAYOUT_ACTIVITYINDEX = 2;

  private static final int LAYOUT_ACTIVITYLOGIN = 3;

  private static final int LAYOUT_ACTIVITYPRINT = 4;

  private static final int LAYOUT_ACTIVITYPRODUCTIONWAREHOUSING = 5;

  private static final int LAYOUT_ACTIVITYPUTDETAIL = 6;

  private static final int LAYOUT_ACTIVITYSTOCKCHECK = 7;

  private static final int LAYOUT_ITEMDETAILLIST = 8;

  private static final int LAYOUT_ITEMDISPATCHDETAILS = 9;

  private static final int LAYOUT_ITEMINPUT = 10;

  private static final int LAYOUT_ITEMPRODUCTIONLIST = 11;

  private static final int LAYOUT_ITEMSTOCKCHECK = 12;

  private static final SparseIntArray INTERNAL_LAYOUT_ID_LOOKUP = new SparseIntArray(12);

  static {
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.example.storescontrol.R.layout.activity_dispatchdetails, LAYOUT_ACTIVITYDISPATCHDETAILS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.example.storescontrol.R.layout.activity_index, LAYOUT_ACTIVITYINDEX);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.example.storescontrol.R.layout.activity_login, LAYOUT_ACTIVITYLOGIN);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.example.storescontrol.R.layout.activity_print, LAYOUT_ACTIVITYPRINT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.example.storescontrol.R.layout.activity_productionwarehousing, LAYOUT_ACTIVITYPRODUCTIONWAREHOUSING);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.example.storescontrol.R.layout.activity_putdetail, LAYOUT_ACTIVITYPUTDETAIL);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.example.storescontrol.R.layout.activity_stockcheck, LAYOUT_ACTIVITYSTOCKCHECK);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.example.storescontrol.R.layout.item_detail_list, LAYOUT_ITEMDETAILLIST);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.example.storescontrol.R.layout.item_dispatchdetails, LAYOUT_ITEMDISPATCHDETAILS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.example.storescontrol.R.layout.item_input, LAYOUT_ITEMINPUT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.example.storescontrol.R.layout.item_production_list, LAYOUT_ITEMPRODUCTIONLIST);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.example.storescontrol.R.layout.item_stockcheck, LAYOUT_ITEMSTOCKCHECK);
  }

  @Override
  public ViewDataBinding getDataBinder(DataBindingComponent component, View view, int layoutId) {
    int localizedLayoutId = INTERNAL_LAYOUT_ID_LOOKUP.get(layoutId);
    if(localizedLayoutId > 0) {
      final Object tag = view.getTag();
      if(tag == null) {
        throw new RuntimeException("view must have a tag");
      }
      switch(localizedLayoutId) {
        case  LAYOUT_ACTIVITYDISPATCHDETAILS: {
          if ("layout/activity_dispatchdetails_0".equals(tag)) {
            return new ActivityDispatchdetailsBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_dispatchdetails is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYINDEX: {
          if ("layout/activity_index_0".equals(tag)) {
            return new ActivityIndexBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_index is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYLOGIN: {
          if ("layout/activity_login_0".equals(tag)) {
            return new ActivityLoginBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_login is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYPRINT: {
          if ("layout/activity_print_0".equals(tag)) {
            return new ActivityPrintBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_print is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYPRODUCTIONWAREHOUSING: {
          if ("layout/activity_productionwarehousing_0".equals(tag)) {
            return new ActivityProductionwarehousingBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_productionwarehousing is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYPUTDETAIL: {
          if ("layout/activity_putdetail_0".equals(tag)) {
            return new ActivityPutdetailBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_putdetail is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYSTOCKCHECK: {
          if ("layout/activity_stockcheck_0".equals(tag)) {
            return new ActivityStockcheckBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_stockcheck is invalid. Received: " + tag);
        }
        case  LAYOUT_ITEMDETAILLIST: {
          if ("layout/item_detail_list_0".equals(tag)) {
            return new ItemDetailListBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for item_detail_list is invalid. Received: " + tag);
        }
        case  LAYOUT_ITEMDISPATCHDETAILS: {
          if ("layout/item_dispatchdetails_0".equals(tag)) {
            return new ItemDispatchdetailsBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for item_dispatchdetails is invalid. Received: " + tag);
        }
        case  LAYOUT_ITEMINPUT: {
          if ("layout/item_input_0".equals(tag)) {
            return new ItemInputBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for item_input is invalid. Received: " + tag);
        }
        case  LAYOUT_ITEMPRODUCTIONLIST: {
          if ("layout/item_production_list_0".equals(tag)) {
            return new ItemProductionListBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for item_production_list is invalid. Received: " + tag);
        }
        case  LAYOUT_ITEMSTOCKCHECK: {
          if ("layout/item_stockcheck_0".equals(tag)) {
            return new ItemStockcheckBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for item_stockcheck is invalid. Received: " + tag);
        }
      }
    }
    return null;
  }

  @Override
  public ViewDataBinding getDataBinder(DataBindingComponent component, View[] views, int layoutId) {
    if(views == null || views.length == 0) {
      return null;
    }
    int localizedLayoutId = INTERNAL_LAYOUT_ID_LOOKUP.get(layoutId);
    if(localizedLayoutId > 0) {
      final Object tag = views[0].getTag();
      if(tag == null) {
        throw new RuntimeException("view must have a tag");
      }
      switch(localizedLayoutId) {
      }
    }
    return null;
  }

  @Override
  public int getLayoutId(String tag) {
    if (tag == null) {
      return 0;
    }
    Integer tmpVal = InnerLayoutIdLookup.sKeys.get(tag);
    return tmpVal == null ? 0 : tmpVal;
  }

  @Override
  public String convertBrIdToString(int localId) {
    String tmpVal = InnerBrLookup.sKeys.get(localId);
    return tmpVal;
  }

  @Override
  public List<DataBinderMapper> collectDependencies() {
    ArrayList<DataBinderMapper> result = new ArrayList<DataBinderMapper>(1);
    result.add(new com.android.databinding.library.baseAdapters.DataBinderMapperImpl());
    return result;
  }

  private static class InnerBrLookup {
    static final SparseArray<String> sKeys = new SparseArray<String>(4);

    static {
      sKeys.put(0, "_all");
      sKeys.put(1, "user");
      sKeys.put(2, "bean");
    }
  }

  private static class InnerLayoutIdLookup {
    static final HashMap<String, Integer> sKeys = new HashMap<String, Integer>(12);

    static {
      sKeys.put("layout/activity_dispatchdetails_0", com.example.storescontrol.R.layout.activity_dispatchdetails);
      sKeys.put("layout/activity_index_0", com.example.storescontrol.R.layout.activity_index);
      sKeys.put("layout/activity_login_0", com.example.storescontrol.R.layout.activity_login);
      sKeys.put("layout/activity_print_0", com.example.storescontrol.R.layout.activity_print);
      sKeys.put("layout/activity_productionwarehousing_0", com.example.storescontrol.R.layout.activity_productionwarehousing);
      sKeys.put("layout/activity_putdetail_0", com.example.storescontrol.R.layout.activity_putdetail);
      sKeys.put("layout/activity_stockcheck_0", com.example.storescontrol.R.layout.activity_stockcheck);
      sKeys.put("layout/item_detail_list_0", com.example.storescontrol.R.layout.item_detail_list);
      sKeys.put("layout/item_dispatchdetails_0", com.example.storescontrol.R.layout.item_dispatchdetails);
      sKeys.put("layout/item_input_0", com.example.storescontrol.R.layout.item_input);
      sKeys.put("layout/item_production_list_0", com.example.storescontrol.R.layout.item_production_list);
      sKeys.put("layout/item_stockcheck_0", com.example.storescontrol.R.layout.item_stockcheck);
    }
  }
}
