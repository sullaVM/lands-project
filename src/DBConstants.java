public class DBConstants {

    public static final String REMOTE_URL_COMPLETE = "jdbc:mysql://cs1013.nguyenhi.eu:993/PUBLIC";
    public static final String REMOTE_URL_STANDARD = "jdbc:mysql://cs1013.nguyenhi.eu:993/prog_project";
    public static final String LOCAL_URL_STANDARD = "jdbc:mysql://localhost:3306/prog_project";
    public static final String LOCAL_URL_COMPLETE = "jdbc:mysql://localhost:3306/PUBLIC";
    public static final String EMBEDDED_URL = "jdbc:sqlite:data/prog_project.db";
    public static final String DB_USER = "hieu";
    public static final String DB_PASS = "cs1013-1617-18";
    public static final String FIFTY_K_TABLE = "50kEntries";
    public static final String COMPLETE_TABLE = "COMPLETEDATA";
    public static final String[] YEARLY_TABLE = {"data2010", "data2011", "data2012", "data2013", "data2014",
                                                    "data2015", "data2016"};
    public static final String[] DATA_COLUMNS = {"price", "dateofsale", "postcode", "propertytype", "oldnew", "numname",
                                                    "street", "locality", "town", "district", "county"};
    public static final String[] SORT_ORDERS = {"", "a", "d"};
    public static final int QUERY_BATCH_SIZE = 1000;

}
