//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Scanner;
import java.util.regex.Pattern;

public final class In {
    private Scanner scanner;
    private static final String CHARSET_NAME = "UTF-8";
    private static final Locale LOCALE;
    private static final Pattern WHITESPACE_PATTERN;
    private static final Pattern EMPTY_PATTERN;
    private static final Pattern EVERYTHING_PATTERN;

    public In() {
        this.scanner = new Scanner(new BufferedInputStream(System.in), "UTF-8");
        this.scanner.useLocale(LOCALE);
    }

    public In(Socket var1) {
        try {
            InputStream var2 = var1.getInputStream();
            this.scanner = new Scanner(new BufferedInputStream(var2), "UTF-8");
            this.scanner.useLocale(LOCALE);
        } catch (IOException var3) {
            System.err.println("Could not open " + var1);
        }

    }

    public In(URL var1) {
        try {
            URLConnection var2 = var1.openConnection();
            InputStream var3 = var2.getInputStream();
            this.scanner = new Scanner(new BufferedInputStream(var3), "UTF-8");
            this.scanner.useLocale(LOCALE);
        } catch (IOException var4) {
            System.err.println("Could not open " + var1);
        }

    }

    public In(File var1) {
        try {
            this.scanner = new Scanner(var1, "UTF-8");
            this.scanner.useLocale(LOCALE);
        } catch (IOException var3) {
            System.err.println("Could not open " + var1);
        }

    }

    public In(String var1) {
        try {
            File var2 = new File(var1);
            if(var2.exists()) {
                this.scanner = new Scanner(var2, "UTF-8");
                this.scanner.useLocale(LOCALE);
                return;
            }

            URL var3 = this.getClass().getResource(var1);
            if(var3 == null) {
                var3 = new URL(var1);
            }

            URLConnection var4 = var3.openConnection();
            InputStream var5 = var4.getInputStream();
            this.scanner = new Scanner(new BufferedInputStream(var5), "UTF-8");
            this.scanner.useLocale(LOCALE);
        } catch (IOException var6) {
            System.err.println("Could not open " + var1);
        }

    }

    public In(Scanner var1) {
        this.scanner = var1;
    }

    public boolean exists() {
        return this.scanner != null;
    }

    public boolean isEmpty() {
        return !this.scanner.hasNext();
    }

    public boolean hasNextLine() {
        return this.scanner.hasNextLine();
    }

    public boolean hasNextChar() {
        this.scanner.useDelimiter(EMPTY_PATTERN);
        boolean var1 = this.scanner.hasNext();
        this.scanner.useDelimiter(WHITESPACE_PATTERN);
        return var1;
    }

    public String readLine() {
        String var1;
        try {
            var1 = this.scanner.nextLine();
        } catch (Exception var3) {
            var1 = null;
        }

        return var1;
    }

    public char readChar() {
        this.scanner.useDelimiter(EMPTY_PATTERN);
        String var1 = this.scanner.next();

        assert var1.length() == 1 : "Internal (Std)In.readChar() error! Please contact the authors.";

        this.scanner.useDelimiter(WHITESPACE_PATTERN);
        return var1.charAt(0);
    }

    public String readAll() {
        if(!this.scanner.hasNextLine()) {
            return "";
        } else {
            String var1 = this.scanner.useDelimiter(EVERYTHING_PATTERN).next();
            this.scanner.useDelimiter(WHITESPACE_PATTERN);
            return var1;
        }
    }

    public String readString() {
        return this.scanner.next();
    }

    public int readInt() {
        return this.scanner.nextInt();
    }

    public double readDouble() {
        return this.scanner.nextDouble();
    }

    public float readFloat() {
        return this.scanner.nextFloat();
    }

    public long readLong() {
        return this.scanner.nextLong();
    }

    public short readShort() {
        return this.scanner.nextShort();
    }

    public byte readByte() {
        return this.scanner.nextByte();
    }

    public boolean readBoolean() {
        String var1 = this.readString();
        if(var1.equalsIgnoreCase("true")) {
            return true;
        } else if(var1.equalsIgnoreCase("false")) {
            return false;
        } else if(var1.equals("1")) {
            return true;
        } else if(var1.equals("0")) {
            return false;
        } else {
            throw new InputMismatchException();
        }
    }

    public String[] readAllStrings() {
        String[] var1 = WHITESPACE_PATTERN.split(this.readAll());
        if(var1.length != 0 && var1[0].length() <= 0) {
            String[] var2 = new String[var1.length - 1];

            for(int var3 = 0; var3 < var1.length - 1; ++var3) {
                var2[var3] = var1[var3 + 1];
            }

            return var2;
        } else {
            return var1;
        }
    }

    public String[] readAllLines() {
        ArrayList var1 = new ArrayList();

        while(this.hasNextLine()) {
            var1.add(this.readLine());
        }

        return (String[])var1.toArray(new String[0]);
    }

    public int[] readAllInts() {
        String[] var1 = this.readAllStrings();
        int[] var2 = new int[var1.length];

        for(int var3 = 0; var3 < var1.length; ++var3) {
            var2[var3] = Integer.parseInt(var1[var3]);
        }

        return var2;
    }

    public double[] readAllDoubles() {
        String[] var1 = this.readAllStrings();
        double[] var2 = new double[var1.length];

        for(int var3 = 0; var3 < var1.length; ++var3) {
            var2[var3] = Double.parseDouble(var1[var3]);
        }

        return var2;
    }

    public void close() {
        this.scanner.close();
    }

    public static int[] readInts(String var0) {
        return (new In(var0)).readAllInts();
    }

    public static double[] readDoubles(String var0) {
        return (new In(var0)).readAllDoubles();
    }

    public static String[] readStrings(String var0) {
        return (new In(var0)).readAllStrings();
    }

    public static int[] readInts() {
        return (new In()).readAllInts();
    }

    public static double[] readDoubles() {
        return (new In()).readAllDoubles();
    }

    public static String[] readStrings() {
        return (new In()).readAllStrings();
    }

    public static void main(String[] var0) {
        String var2 = "http://introcs.cs.princeton.edu/stdlib/InTest.txt";
        System.out.println("readAll() from URL " + var2);
        System.out.println("---------------------------------------------------------------------------");

        In var1;
        try {
            var1 = new In(var2);
            System.out.println(var1.readAll());
        } catch (Exception var4) {
            System.out.println(var4);
        }

        System.out.println();
        System.out.println("readLine() from URL " + var2);
        System.out.println("---------------------------------------------------------------------------");

        String var3;
        try {
            var1 = new In(var2);

            while(!var1.isEmpty()) {
                var3 = var1.readLine();
                System.out.println(var3);
            }
        } catch (Exception var11) {
            System.out.println(var11);
        }

        System.out.println();
        System.out.println("readString() from URL " + var2);
        System.out.println("---------------------------------------------------------------------------");

        try {
            var1 = new In(var2);

            while(!var1.isEmpty()) {
                var3 = var1.readString();
                System.out.println(var3);
            }
        } catch (Exception var10) {
            System.out.println(var10);
        }

        System.out.println();
        System.out.println("readLine() from current directory");
        System.out.println("---------------------------------------------------------------------------");

        try {
            var1 = new In("./InTest.txt");

            while(!var1.isEmpty()) {
                var3 = var1.readLine();
                System.out.println(var3);
            }
        } catch (Exception var9) {
            System.out.println(var9);
        }

        System.out.println();
        System.out.println("readLine() from relative path");
        System.out.println("---------------------------------------------------------------------------");

        try {
            var1 = new In("../stdlib/InTest.txt");

            while(!var1.isEmpty()) {
                var3 = var1.readLine();
                System.out.println(var3);
            }
        } catch (Exception var8) {
            System.out.println(var8);
        }

        System.out.println();
        System.out.println("readChar() from file");
        System.out.println("---------------------------------------------------------------------------");

        try {
            var1 = new In("InTest.txt");

            while(!var1.isEmpty()) {
                char var12 = var1.readChar();
                System.out.print(var12);
            }
        } catch (Exception var7) {
            System.out.println(var7);
        }

        System.out.println();
        System.out.println();
        System.out.println("readLine() from absolute OS X / Linux path");
        System.out.println("---------------------------------------------------------------------------");
        var1 = new In("/n/fs/introcs/www/java/stdlib/InTest.txt");

        try {
            while(!var1.isEmpty()) {
                var3 = var1.readLine();
                System.out.println(var3);
            }
        } catch (Exception var6) {
            System.out.println(var6);
        }

        System.out.println();
        System.out.println("readLine() from absolute Windows path");
        System.out.println("---------------------------------------------------------------------------");

        try {
            var1 = new In("G:\\www\\introcs\\stdlib\\InTest.txt");

            while(!var1.isEmpty()) {
                var3 = var1.readLine();
                System.out.println(var3);
            }

            System.out.println();
        } catch (Exception var5) {
            System.out.println(var5);
        }

        System.out.println();
    }

    static {
        LOCALE = Locale.US;
        WHITESPACE_PATTERN = Pattern.compile("\\p{javaWhitespace}+");
        EMPTY_PATTERN = Pattern.compile("");
        EVERYTHING_PATTERN = Pattern.compile("\\A");
    }
}
