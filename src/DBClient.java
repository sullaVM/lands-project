import java.util.List;

public class DBClient {

    public static void main(String[] args) throws Exception {
        String url = DBConstants.EMBEDDED_URL;

        // Format: DBQuery(<database_url>, <database_table>);
        DBQuery queryInstance = new DBQuery(url);

        // data must be one of the Strings from DATA_COLUMNS.
        String data = DBConstants.DATA_COLUMNS[0];

        /* Sort order must be one of the Strings from SORT_ORDERS.
         * [0] = no sort preference
         * [1] = sort by ascending
         * [2] = sort by descending
         */
        String order = DBConstants.SORT_ORDERS[1];

        /* WHERE is a clause for SQL querying, similar to an if statement, eg.
         * Java:
         *      if (price < 20000) {
         *          ...
         *      }
         *
         * SQL:
         *      WHERE price < '20000'
         *
         * NOTE: Clause condition must be enclosed in single quotes (')
         *
         * Tutorial on WHERE clauses: https://www.tutorialspoint.com/mysql/mysql-where-clause.htm
         *
         * WHERE condition is optional, leave empty String if WHERE is not needed.
         */
        String where = "where price < '100000'";

        // limit is the number of entries you want the query to return, zero for no limit.
        int limit = 0;

        // Query nanosecond timer start.
        long startingTime = System.nanoTime();

        /* Database return data is stored in a List<String>
         *
         * Standard queries may be sent to the database using the function below.
         */
         //List<String> dataList = queryInstance.fetchColumn(DBConstants.YEARLY_TABLE[0], data, where, data, order, limit);

        /*
         * Custom queries may be sent to the database using the function below.
         * NOTE: The FULL custom query must be typed out.
         * NOTE: Table must be the same as DBQuery specified table, if required table is different to DBQuery table,
         *          a new instance of DBQuery must be instantiated.
         */
//        List<String> dataList = queryInstance.fetchCustomQuery("select count(*) from data2010 where price > '100000' AND price < 300000 AND town = 'YORK'" +
//                "union select count(*) from data2011 where price < '300000' " +
//                "union select count(*) from data2012 where price < '400000' " +
//                "union select count(*) from data2013 where price < '500000' " +
//                "union select count(*) from data2014 where price < '600000' " +
//                "union select count(*) from data2015 where price < '700000' " +
//                "union select count(*) from data2016 where price < '800000'");

//        String year = DBConstants.YEARLY_TABLE[3];
//        String place = "LONDON";
//        List<String> dataList = queryInstance.fetchCustomQuery("select count(*) from " + year + " where price < '100000' AND locality = '" +
//                place + "' " +
//                "union select count(*) from " + year + " where price > '100000' AND price < '150000' AND locality = '" +
//                place + "' " +
//                "union select count(*) from " + year + " where price > '150000' AND price < '200000' AND locality = '" +
//                place + "' " +
//                "union select count(*) from " + year + " where price > '200000' AND price < '250000' AND locality = '" +
//                place + "' " +
//                "union select count(*) from " + year + " where price > '250000' AND price < '300000' AND locality = '" +
//                place + "' " +
//                "union select count(*) from " + year + " where price > '300000' AND price < '350000' AND locality = '" +
//                place + "' " +
//                "union select count(*) from " + year + " where price > '350000' AND price < '400000' AND locality = '" +
//                place + "' " +
//                "union select count(*) from " + year + " where price > '400000' AND price < '450000' AND locality = '" +
//                place + "' " +
//                "union select count(*) from " + year + " where price > '450000' AND price < '500000' AND locality = '" +
//                place + "' " +
//                "union select count(*) from " + year + " where price > '500000' AND price < '550000' AND locality = '" +
//                place + "' " +
//                "union select count(*) from " + year + " where price > '550000' AND town = '" + place + "'");
//        List<String> dataList = queryInstance.fetchCustomQuery("select count(*) from data2016 where price < '150000' AND price < '200000' AND town = 'YORK'");

//        for (String dataString : dataList) {
//            System.out.println(dataString);
//        }

        System.out.println(new DBQuery(DBConstants.EMBEDDED_URL).fetchCustomScalar("select count(*) from " +
                "data2016 where price > '350000' AND price < '400000' AND dateofsale = '2016-04-04 00:00'"));

//        for(int i = 0;i<dataList.size();i++)
//        {
//        	if(dataList.get(i).>10000)
//        	{}
//        }
//        Lazy querying NOT fully functional, do not use.
//        ArrayList<List<String>> batchList = queryInstance.fetchCustomLazy("select price from data2010 order by price");

//        List<LandData> land = queryInstance.fetchAllColumns(DBConstants.YEARLY_TABLE[0], DBConstants.DATA_COLUMNS[0]);

        // Query nanosecond timer end.
        long endTime = System.nanoTime() - startingTime;


        // Concurrent fetching
//        new Thread(() -> {
//            List<LandData> land = queryInstance.fetchAllColumns(DBConstants.YEARLY_TABLE[0], DBConstants.DATA_COLUMNS[0], "d", 10);
//            for (LandData properties : land) {
//                System.out.println(properties.toString());
//            }
//            System.out.println("Query completed.");
//        }).start();
        
//        System.out.printf("%nAverage price: £%.2f", queryInstance.fetchAveragePrice(DBConstants.YEARLY_TABLE[0]));
//        System.out.printf("%nHouses sold: %d", queryInstance.fetchHousesSold(DBConstants.YEARLY_TABLE[0]));
//        System.out.printf("%nQuery time: %.2f%n", endTime/Math.pow(10,9));
//        System.out.printf("%nData list size: %d entries", land.size());

        int dataSize = 0;

//        Displaying batch data
//        for (List<String> dataList : batchList) {
//            for (String dataString : dataList) {
//                System.out.println(dataString);
//                dataSize++;
//            }
//        }

//         Uncomment to print out info.
//        for (String dataString : dataList) {
//            System.out.println(dataString);
//            dataSize++;
//        }
//
//        System.out.printf("%nDatabase URL: %s", url);
//        System.out.printf("%nQueried data size: %d entries in %.2f seconds.%n", dataSize, endTime/Math.pow(10, 9));
//        System.out.println(endConcurrentTime / Math.pow(10, 9));

    }
}