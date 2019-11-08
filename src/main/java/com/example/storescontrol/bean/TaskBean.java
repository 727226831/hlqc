package com.example.storescontrol.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class TaskBean implements Parcelable {


        private String Resultcode;

    public String getResultcode() {
        return Resultcode;
    }

    public void setResultcode(String resultcode) {
        Resultcode = resultcode;
    }

    public String getResultMessage() {
        return ResultMessage;
    }

    public void setResultMessage(String resultMessage) {
        ResultMessage = resultMessage;
    }

    private String ResultMessage;
        private List<Data> data;


        public void setData(List<Data> data) {
            this.data = data;
        }
        public List<Data> getData() {
            return data;
        }

    public static class Data implements Parcelable {

        private String S_QuotationID;
        private String R_RecordCompany;
        private String S_InvName;
        private String S_InvVersion;
        private String S_Verifyer;
        private String S_State;
        private String S_VerifyDate;
        private String P_AuditStatus;
        private String S_ApplicationArea;
        private String S_Region;
        private String S_ApplicationProject;
        private String S_InvType;
        private String S_ProjectName;
        private String S_UseQty;
        private String S_Qty;
        private String p_id;

        public String getM_MSN() {
            return M_MSN;
        }

        public void setM_MSN(String m_MSN) {
            M_MSN = m_MSN;
        }

        private String M_MSN;

        public String getP_id() {
            return p_id;
        }

        public void setP_id(String p_id) {
            this.p_id = p_id;
        }

        public String getP_RowNo() {
            return P_RowNo;
        }

        public void setP_RowNo(String p_RowNo) {
            P_RowNo = p_RowNo;
        }

        private String P_RowNo;

        public String getS_Id() {
            return S_Id;
        }

        public void setS_Id(String s_Id) {
            S_Id = s_Id;
        }

        private String S_Id;

        public String getS_ApplicationArea() {
            return S_ApplicationArea;
        }

        public void setS_ApplicationArea(String s_ApplicationArea) {
            S_ApplicationArea = s_ApplicationArea;
        }

        public String getS_Region() {
            return S_Region;
        }

        public void setS_Region(String s_Region) {
            S_Region = s_Region;
        }

        public String getS_ApplicationProject() {
            return S_ApplicationProject;
        }

        public void setS_ApplicationProject(String s_ApplicationProject) {
            S_ApplicationProject = s_ApplicationProject;
        }

        public String getS_InvType() {
            return S_InvType;
        }

        public void setS_InvType(String s_InvType) {
            S_InvType = s_InvType;
        }

        public String getS_ProjectName() {
            return S_ProjectName;
        }

        public void setS_ProjectName(String s_ProjectName) {
            S_ProjectName = s_ProjectName;
        }

        public String getS_UseQty() {
            return S_UseQty;
        }

        public void setS_UseQty(String s_UseQty) {
            S_UseQty = s_UseQty;
        }

        public String getS_Qty() {
            return S_Qty;
        }

        public void setS_Qty(String s_Qty) {
            S_Qty = s_Qty;
        }

        public String getS_Price() {
            return S_Price;
        }

        public void setS_Price(String s_Price) {
            S_Price = s_Price;
        }

        public String getS_Sum() {
            return S_Sum;
        }

        public void setS_Sum(String s_Sum) {
            S_Sum = s_Sum;
        }

        public String getS_Package() {
            return S_Package;
        }

        public void setS_Package(String s_Package) {
            S_Package = s_Package;
        }

        public String getS_Address() {
            return S_Address;
        }

        public void setS_Address(String s_Address) {
            S_Address = s_Address;
        }

        private String S_Price;
        private String S_Sum;
        private String S_Package;
        private  String S_Address;

        public String getP_AuditStatus() {
            return P_AuditStatus;
        }

        public void setP_AuditStatus(String p_AuditStatus) {
            P_AuditStatus = p_AuditStatus;
        }

        public String getP_AuditMemo() {
            return P_AuditMemo;
        }

        public void setP_AuditMemo(String p_AuditMemo) {
            P_AuditMemo = p_AuditMemo;
        }



        private String P_AuditMemo;


        public String getS_RegisterDate() {
            return S_RegisterDate;
        }

        public void setS_RegisterDate(String s_RegisterDate) {
            S_RegisterDate = s_RegisterDate;
        }

        private String S_RegisterDate;
        public void setS_QuotationID(String S_QuotationID) {
            this.S_QuotationID = S_QuotationID;
        }
        public String getS_QuotationID() {
            return S_QuotationID;
        }

        public void setR_RecordCompany(String R_RecordCompany) {
            this.R_RecordCompany = R_RecordCompany;
        }
        public String getR_RecordCompany() {
            return R_RecordCompany;
        }

        public void setS_InvName(String S_InvName) {
            this.S_InvName = S_InvName;
        }
        public String getS_InvName() {
            return S_InvName;
        }

        public void setS_InvVersion(String S_InvVersion) {
            this.S_InvVersion = S_InvVersion;
        }
        public String getS_InvVersion() {
            return S_InvVersion;
        }

        public void setS_Verifyer(String S_Verifyer) {
            this.S_Verifyer = S_Verifyer;
        }
        public String getS_Verifyer() {
            return S_Verifyer;
        }

        public void setS_State(String S_State) {
            this.S_State = S_State;
        }
        public String getS_State() {
            return S_State;
        }

        public void setS_VerifyDate(String S_VerifyDate) {
            this.S_VerifyDate = S_VerifyDate;
        }
        public String getS_VerifyDate() {
            return S_VerifyDate;
        }


        public Data() {
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.S_QuotationID);
            dest.writeString(this.R_RecordCompany);
            dest.writeString(this.S_InvName);
            dest.writeString(this.S_InvVersion);
            dest.writeString(this.S_Verifyer);
            dest.writeString(this.S_State);
            dest.writeString(this.S_VerifyDate);
            dest.writeString(this.P_AuditStatus);
            dest.writeString(this.S_ApplicationArea);
            dest.writeString(this.S_Region);
            dest.writeString(this.S_ApplicationProject);
            dest.writeString(this.S_InvType);
            dest.writeString(this.S_ProjectName);
            dest.writeString(this.S_UseQty);
            dest.writeString(this.S_Qty);
            dest.writeString(this.p_id);
            dest.writeString(this.M_MSN);
            dest.writeString(this.P_RowNo);
            dest.writeString(this.S_Id);
            dest.writeString(this.S_Price);
            dest.writeString(this.S_Sum);
            dest.writeString(this.S_Package);
            dest.writeString(this.S_Address);
            dest.writeString(this.P_AuditMemo);
            dest.writeString(this.S_RegisterDate);
        }

        protected Data(Parcel in) {
            this.S_QuotationID = in.readString();
            this.R_RecordCompany = in.readString();
            this.S_InvName = in.readString();
            this.S_InvVersion = in.readString();
            this.S_Verifyer = in.readString();
            this.S_State = in.readString();
            this.S_VerifyDate = in.readString();
            this.P_AuditStatus = in.readString();
            this.S_ApplicationArea = in.readString();
            this.S_Region = in.readString();
            this.S_ApplicationProject = in.readString();
            this.S_InvType = in.readString();
            this.S_ProjectName = in.readString();
            this.S_UseQty = in.readString();
            this.S_Qty = in.readString();
            this.p_id = in.readString();
            this.M_MSN = in.readString();
            this.P_RowNo = in.readString();
            this.S_Id = in.readString();
            this.S_Price = in.readString();
            this.S_Sum = in.readString();
            this.S_Package = in.readString();
            this.S_Address = in.readString();
            this.P_AuditMemo = in.readString();
            this.S_RegisterDate = in.readString();
        }

        public static final Creator<Data> CREATOR = new Creator<Data>() {
            @Override
            public Data createFromParcel(Parcel source) {
                return new Data(source);
            }

            @Override
            public Data[] newArray(int size) {
                return new Data[size];
            }
        };
    }

    public TaskBean() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.Resultcode);
        dest.writeString(this.ResultMessage);
        dest.writeTypedList(this.data);
    }

    protected TaskBean(Parcel in) {
        this.Resultcode = in.readString();
        this.ResultMessage = in.readString();
        this.data = in.createTypedArrayList(Data.CREATOR);
    }

    public static final Creator<TaskBean> CREATOR = new Creator<TaskBean>() {
        @Override
        public TaskBean createFromParcel(Parcel source) {
            return new TaskBean(source);
        }

        @Override
        public TaskBean[] newArray(int size) {
            return new TaskBean[size];
        }
    };
}
