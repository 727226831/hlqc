package com.example.storescontrol.bean;

import java.util.List;

public class DispatchdetailsBean {


        private String Resultcode;
        private String ResultMessage;
        private List<Data> data;
        public void setResultcode(String Resultcode) {
            this.Resultcode = Resultcode;
        }
        public String getResultcode() {
            return Resultcode;
        }

        public void setResultMessage(String ResultMessage) {
            this.ResultMessage = ResultMessage;
        }
        public String getResultMessage() {
            return ResultMessage;
        }

        public void setData(List<Data> data) {
            this.data = data;
        }
        public List<Data> getData() {
            return data;
        }

    public class Data {

        private long dlid;
        private String cinvcode;
        private String cinvname;
        private String cinvstd;
        private String cbatch;
        private String iquantity;
        private String irowno;
        private int bWhPos;
        public void setDlid(long dlid) {
            this.dlid = dlid;
        }
        public long getDlid() {
            return dlid;
        }

        public void setCinvcode(String cinvcode) {
            this.cinvcode = cinvcode;
        }
        public String getCinvcode() {
            return cinvcode;
        }

        public void setCinvname(String cinvname) {
            this.cinvname = cinvname;
        }
        public String getCinvname() {
            return cinvname;
        }

        public void setCinvstd(String cinvstd) {
            this.cinvstd = cinvstd;
        }
        public String getCinvstd() {
            return cinvstd;
        }

        public void setCbatch(String cbatch) {
            this.cbatch = cbatch;
        }
        public String getCbatch() {
            return cbatch;
        }

        public void setIquantity(String iquantity) {
            this.iquantity = iquantity;
        }
        public String getIquantity() {
            return iquantity;
        }

        public void setIrowno(String irowno) {
            this.irowno = irowno;
        }
        public String getIrowno() {
            return irowno;
        }

        public void setBWhPos(int bWhPos) {
            this.bWhPos = bWhPos;
        }
        public int getBWhPos() {
            return bWhPos;
        }

    }

}
