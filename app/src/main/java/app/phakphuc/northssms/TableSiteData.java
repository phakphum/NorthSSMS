package app.phakphuc.northssms;

/**
 * Created by Phakphum on 25-Sep-16.
 */
public class TableSiteData {
    public int _id;
    public String chain = "";
    public String port = "";
    public String site = "";
    public String province = "";
    public String mc = "";
    public String brand = "";
    public String gateway = "";
    public String ip = "";

    //Contructor
    public TableSiteData(int _id,String chain,String port,String site,String province,
                         String mc,String brand,String gateway,String ip) {
        this._id=_id;
        this.chain = chain;
        this.port = port;
        this.site = site;
        this.province = province;
        this.mc = mc;
        this.brand = brand;
        this.gateway = gateway;
        this.ip = ip;
    }

    public TableSiteData() {

    }

    public String getChain() { return chain; }
    public String getPort() {return port; }
    public String getSite() { return site; }
    public String getProvince() {return province; }
    public String getMC() { return mc; }
    public String getBrand() {return brand; }
    public String getGateway() { return gateway; }
    public String getIP() {return ip; }
}
