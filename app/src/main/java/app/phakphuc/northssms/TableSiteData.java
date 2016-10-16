package app.phakphuc.northssms;

/**
 * Created by Phakphum on 25-Sep-16.
 */
public class TableSiteData {
    public int _id;
    public String site = "";
    public String ip = "";
    public String brand = "";
    public String zone = "";

    //Contructor
    public TableSiteData(int _id,String site,String ip,String brand,String zone) {
        this._id=_id;
        this.site = site;
        this.ip = ip;
        this.brand = brand;
        this.zone = zone;
    }

    public TableSiteData() {

    }

    public String getSite() { return site; }
    public String getIP() {return ip; }
    public String getBrand() { return brand; }
    public String getZone() {return zone; }
}
