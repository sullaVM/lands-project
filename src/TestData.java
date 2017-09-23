public class TestData {
    String name;
    int rating;
    String email;
    String date;

    public TestData(String line) {
        String[] wordList = line.split(",");
        name = wordList[0];
        String temp = wordList[1];
        rating = Integer.parseInt(temp);
        email = wordList[2];
        date = wordList[3];
    }

    public String toString() {
        return (name + ", " + rating + ", " + email + ", " + date);
    }

}
