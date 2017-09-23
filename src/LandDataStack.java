import java.util.List;

public class LandDataStack implements Runnable {
    private DBQuery queryObject = new DBQuery(DBConstants.EMBEDDED_URL);
    private static List<LandData> data2010, data2011, data2012, data2013, data2014, data2015, data2016;

    @Override
    public void run() {
        data2010 = queryObject.fetchAllColumns(DBConstants.YEARLY_TABLE[0], DBConstants.DATA_COLUMNS[0], "d", 0);
        data2011 = queryObject.fetchAllColumns(DBConstants.YEARLY_TABLE[1], DBConstants.DATA_COLUMNS[0], "d", 0);
        data2012 = queryObject.fetchAllColumns(DBConstants.YEARLY_TABLE[2], DBConstants.DATA_COLUMNS[0], "d", 0);
        data2013 = queryObject.fetchAllColumns(DBConstants.YEARLY_TABLE[3], DBConstants.DATA_COLUMNS[0], "d", 0);
        data2014 = queryObject.fetchAllColumns(DBConstants.YEARLY_TABLE[4], DBConstants.DATA_COLUMNS[0], "d", 0);
        data2015 = queryObject.fetchAllColumns(DBConstants.YEARLY_TABLE[5], DBConstants.DATA_COLUMNS[0], "d", 0);
        data2016 = queryObject.fetchAllColumns(DBConstants.YEARLY_TABLE[6], DBConstants.DATA_COLUMNS[0], "d", 0);
    }

    public static boolean is2010Complete() {
        return data2010 != null;
    }

    public static boolean is2011Complete() {
        return data2010 != null;
    }

    public static boolean is2012Complete() {
        return data2010 != null;
    }

    public static boolean is2013Complete() {
        return data2010 != null;
    }

    public static boolean is2014Complete() {
        return data2010 != null;
    }

    public static boolean is2015Complete() {
        return data2010 != null;
    }

    public static boolean is2016Complete() {
        return data2010 != null;
    }

    public static List<LandData> getData2010() {
        return data2010;
    }

    public static List<LandData> getData2011() {
        return data2011;
    }

    public static List<LandData> getData2012() {
        return data2012;
    }

    public static List<LandData> getData2013() {
        return data2013;
    }

    public static List<LandData> getData2014() {
        return data2014;
    }

    public static List<LandData> getData2015() {
        return data2015;
    }

    public static List<LandData> getData2016() {
        return data2016;
    }
}
