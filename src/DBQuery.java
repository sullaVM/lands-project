import org.sql2o.Connection;
import org.sql2o.ResultSetIterable;
import org.sql2o.Sql2o;

import java.util.ArrayList;
import java.util.List;

public class DBQuery {

    private static Sql2o sql;

    public DBQuery(String url) {
        sql = new Sql2o(url, DBConstants.DB_USER, DBConstants.DB_PASS);
    }

    public List<String> fetchColumn(String table, String dataColumn, String where, String orderColumn, String order, int limit) {
        String query = constructQuery(table, dataColumn, where, orderColumn, order, limit);

        try (Connection connection = sql.open()) {
            return connection.createQuery(query).executeAndFetch(String.class);
        }
    }

    public List<String> fetchCustomQuery(String query) {
        try (Connection connection = sql.open()) {
            return connection.createQuery(query).executeAndFetch(String.class);
        }
    }

    public ArrayList<List<String>> fetchColumnLazy(String table, String dataCol, String where, String orderCol,
                                                   String order, int limit) {
        String query = constructQuery(table, dataCol, where, orderCol, order, limit);
        return createLazyConnection(query);
    }

    public ArrayList<List<String>> fetchCustomLazy(String query) {
        return createLazyConnection(query);
    }

    public List<LandData> fetchAllColumns(String table, String orderColumn, String order, int limit) {
        String query = "SELECT * FROM " + table + " ORDER BY " + orderColumn;

        if (order.equals("d")) {
            query += " DESC";
        }

        if (limit > 0) {
            query += " limit " + limit;
        }

        try (Connection connection = sql.open()) {
            return connection.createQuery(query).executeAndFetch(LandData.class);
        }
    }

    public List<LandData> fetchCustomAllColumns(String query) {
        try (Connection connection = sql.open()) {
            return connection.createQuery(query).executeAndFetch(LandData.class);
        }
    }

    public List<Integer> fetchAveragePrice() {
        String query = "SELECT AVG(price) from data2010 UNION " +
                "SELECT AVG(price) from data2011 UNION SELECT AVG(price) from data2012 UNION " +
                "SELECT AVG(price) from data2013 UNION SELECT AVG(price) from data2014 UNION " +
                "SELECT AVG(price) from data2015 UNION SELECT AVG(price) from data2016";

        try (Connection connection = sql.open()) {
            return connection.createQuery(query).executeScalarList(Integer.class);
        }
    }

    public List<Integer> fetchHousesSold() {
        String query = "SELECT COUNT(price) from data2010 UNION " +
                "SELECT COUNT(price) from data2011 UNION SELECT COUNT(price) from data2012 UNION " +
                "SELECT COUNT(price) from data2013 UNION SELECT COUNT(price) from data2014 UNION " +
                "SELECT COUNT(price) from data2015 UNION SELECT COUNT(price) from data2016";

        try (Connection connection = sql.open()) {
            return connection.createQuery(query).executeScalarList(Integer.class);
        }
    }

    public Integer fetchCustomScalar(String query) {
        try (Connection connection = sql.open()) {
            return connection.createQuery(query).executeScalar(Integer.class);
        }
    }

    public List<Integer> fetchCustomScalarList(String query) {
        try (Connection connection = sql.open()) {
            return connection.createQuery(query).executeScalarList(Integer.class);
        }
    }

    private String constructQuery(String table, String dataColumn, String where, String orderColumn,
                                  String order, int limit) {
        String query = "SELECT";

        switch (dataColumn) {
            case "price":
                query += " " + DBConstants.DATA_COLUMNS[0];
                break;
            case "dateofsale":
                query += " " + DBConstants.DATA_COLUMNS[1];
                break;
            case "postcode":
                query += " " + DBConstants.DATA_COLUMNS[2];
                break;
            case "propertytype":
                query += " " + DBConstants.DATA_COLUMNS[3];
                break;
            case "oldnew":
                query += " " + DBConstants.DATA_COLUMNS[4];
                break;
            case "numname":
                query += " " + DBConstants.DATA_COLUMNS[5];
                break;
            case "street":
                query += " " + DBConstants.DATA_COLUMNS[6];
                break;
            case "locality":
                query += " " + DBConstants.DATA_COLUMNS[7];
                break;
            case "town":
                query += " " + DBConstants.DATA_COLUMNS[8];
                break;
            case "district":
                query += " " + DBConstants.DATA_COLUMNS[9];
                break;
            case "county":
                query += " " + DBConstants.DATA_COLUMNS[10];
                break;
            default:
                throw new IllegalArgumentException("Invalid data column.");
        }

        query += " FROM " + table + " " + where;

        if (order.equals(DBConstants.SORT_ORDERS[1]))
            query += " ORDER BY " + orderColumn;
        else if (order.equals(DBConstants.SORT_ORDERS[2]))
            query += " ORDER BY " + orderColumn + " DESC";

        if (limit > 0) {
            query += " LIMIT " + limit;
        }

        return query;
    }

    private ArrayList<List<String>> createLazyConnection(String query) {
        ArrayList<List<String>> batchList = new ArrayList<>();
        List<String> dataList = new ArrayList<>();

        try (Connection connection = sql.open()) {
            try (ResultSetIterable<String> resultList = connection.createQuery(query).executeAndFetchLazy(String.class)) {
                for (String data : resultList) {
                    if (dataList.size() == DBConstants.QUERY_BATCH_SIZE) {
                        batchList.add(dataList);
                        dataList.clear();
                    }
                    dataList.add(data);
                }
            }
        }

        return batchList;
    }

}
