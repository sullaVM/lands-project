public class LandData {

    private int price;
    private String dateofsale;
    private String postcode;
    private char propertytype;
    private char oldnew;
    private String numname;
    private String street;
    private String locality;
    private String town;
    private String district;
    private String county;

    public int getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = Integer.parseInt(price);
    }

    public String getDateofsale() {
        return dateofsale;
    }

    public void setDateofsale(String dateofsale) {
        this.dateofsale = dateofsale;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public char getPropertytype() {
        return propertytype;
    }

    public void setPropertytype(String propertytype) {
        this.propertytype = propertytype.charAt(0);
    }

    public char getOldnew() {
        return oldnew;
    }

    public void setOldnew(String oldnew) {
        this.oldnew = oldnew.charAt(0);
    }

    public String getNumname() {
        return numname;
    }

    public void setNumname(String numname) {
        this.numname = numname;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String toString() {
        return price + "," + dateofsale + "," + postcode + "," + propertytype + "," + oldnew + "," + numname + "," +
                street + "," + locality + "," + town + "," + district + "," + county;
    }
}
