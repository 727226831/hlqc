package com.example.storescontrol.databinding;
import com.example.storescontrol.R;
import com.example.storescontrol.BR;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ActivityProductionwarehousingBindingImpl extends ActivityProductionwarehousingBinding  {

    @Nullable
    private static final android.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.tv_title1, 7);
        sViewsWithIds.put(R.id.iv_cwhcode, 8);
        sViewsWithIds.put(R.id.et_cwhcode, 9);
        sViewsWithIds.put(R.id.tv_title2, 10);
        sViewsWithIds.put(R.id.iv_batch, 11);
        sViewsWithIds.put(R.id.et_batch, 12);
        sViewsWithIds.put(R.id.tv_count, 13);
        sViewsWithIds.put(R.id.tv_number, 14);
        sViewsWithIds.put(R.id.iv_add, 15);
        sViewsWithIds.put(R.id.et_times, 16);
        sViewsWithIds.put(R.id.iv_minus, 17);
        sViewsWithIds.put(R.id.l_cvenabbname, 18);
        sViewsWithIds.put(R.id.l_batch, 19);
        sViewsWithIds.put(R.id.ib_upload, 20);
        sViewsWithIds.put(R.id.b_submit, 21);
        sViewsWithIds.put(R.id.b_search, 22);
        sViewsWithIds.put(R.id.tv_total, 23);
    }
    // views
    @NonNull
    private final android.widget.ScrollView mboundView0;
    @NonNull
    private final android.widget.LinearLayout mboundView1;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivityProductionwarehousingBindingImpl(@Nullable android.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 24, sIncludes, sViewsWithIds));
    }
    private ActivityProductionwarehousingBindingImpl(android.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.Button) bindings[22]
            , (android.widget.Button) bindings[21]
            , (android.widget.EditText) bindings[12]
            , (android.widget.EditText) bindings[9]
            , (android.widget.EditText) bindings[16]
            , (android.widget.ImageButton) bindings[20]
            , (android.widget.ImageView) bindings[15]
            , (android.widget.ImageView) bindings[11]
            , (android.widget.ImageView) bindings[8]
            , (android.widget.ImageView) bindings[17]
            , (android.widget.LinearLayout) bindings[19]
            , (android.widget.LinearLayout) bindings[18]
            , (android.widget.TextView) bindings[3]
            , (android.widget.TextView) bindings[4]
            , (android.widget.TextView) bindings[5]
            , (android.widget.TextView) bindings[2]
            , (android.widget.TextView) bindings[13]
            , (android.widget.TextView) bindings[6]
            , (android.widget.TextView) bindings[14]
            , (android.widget.TextView) bindings[7]
            , (android.widget.TextView) bindings[10]
            , (android.widget.TextView) bindings[23]
            );
        this.mboundView0 = (android.widget.ScrollView) bindings[0];
        this.mboundView0.setTag(null);
        this.mboundView1 = (android.widget.LinearLayout) bindings[1];
        this.mboundView1.setTag(null);
        this.tvCInvName.setTag(null);
        this.tvCInvStd.setTag(null);
        this.tvCcode.setTag(null);
        this.tvCinvcode.setTag(null);
        this.tvCvenabbname.setTag(null);
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x2L;
        }
        requestRebind();
    }

    @Override
    public boolean hasPendingBindings() {
        synchronized(this) {
            if (mDirtyFlags != 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean setVariable(int variableId, @Nullable Object variable)  {
        boolean variableSet = true;
        if (BR.bean == variableId) {
            setBean((com.example.storescontrol.bean.ArrivalHeadBean) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setBean(@Nullable com.example.storescontrol.bean.ArrivalHeadBean Bean) {
        this.mBean = Bean;
        synchronized(this) {
            mDirtyFlags |= 0x1L;
        }
        notifyPropertyChanged(BR.bean);
        super.requestRebind();
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
        }
        return false;
    }

    @Override
    protected void executeBindings() {
        long dirtyFlags = 0;
        synchronized(this) {
            dirtyFlags = mDirtyFlags;
            mDirtyFlags = 0;
        }
        java.lang.String beanCInvName = null;
        java.lang.String beanCInvStd = null;
        java.lang.String beanCbatch = null;
        java.lang.String beanCcode = null;
        java.lang.String beanCvenabbname = null;
        com.example.storescontrol.bean.ArrivalHeadBean bean = mBean;

        if ((dirtyFlags & 0x3L) != 0) {



                if (bean != null) {
                    // read bean.cInvName
                    beanCInvName = bean.getCInvName();
                    // read bean.cInvStd
                    beanCInvStd = bean.getCInvStd();
                    // read bean.cbatch
                    beanCbatch = bean.getCbatch();
                    // read bean.ccode
                    beanCcode = bean.getCcode();
                    // read bean.cvenabbname
                    beanCvenabbname = bean.getCvenabbname();
                }
        }
        // batch finished
        if ((dirtyFlags & 0x3L) != 0) {
            // api target 1

            android.databinding.adapters.TextViewBindingAdapter.setText(this.tvCInvName, beanCInvName);
            android.databinding.adapters.TextViewBindingAdapter.setText(this.tvCInvStd, beanCInvStd);
            android.databinding.adapters.TextViewBindingAdapter.setText(this.tvCcode, beanCcode);
            android.databinding.adapters.TextViewBindingAdapter.setText(this.tvCinvcode, beanCbatch);
            android.databinding.adapters.TextViewBindingAdapter.setText(this.tvCvenabbname, beanCvenabbname);
        }
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): bean
        flag 1 (0x2L): null
    flag mapping end*/
    //end
}