package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class Info {

    String URL = "jdbc:postgresql://localhost:5432/postgres";
    String user = "postgres";
    String password = "1";
    Connection connection;


    {
        try {
            connection = DriverManager.getConnection(URL, user, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void foreign_Key() {
        ArrayList<String> tables = table_name();
        System.out.println("Quyidagilardan birini tanlang:");
        int l2 = 1;
        for (String str : tables) {
            System.out.println(l2++ + "." + str);
        }
        Scanner son = new Scanner(System.in);
        int choose = son.nextInt();
        String table_name1 = tables.get(choose - 1);
        tables.remove(table_name1);
        ArrayList<String> colums = column_name(table_name1);
        System.out.println("Yana tanlang:");
        l2 = 1;
        for (String str : colums) {
            System.out.println(l2++ + "." + str);
        }
        choose = son.nextInt();
        String column_name1 = colums.get(choose - 1);
        System.out.println("Ulamoqchi bo'lgan boshqa tableni tanlang:");
        l2 = 1;
        for (String str : tables) {
            System.out.println(l2++ + "." + str);
        }
        choose = son.nextInt();
        String table_name2 = tables.get(choose - 1);
        ArrayList<String> colums2 = column_name(table_name2);
        System.out.println("Ulamoqchi bo'lgan tablening columni tanlang:");
        l2 = 1;
        for (String str : colums2) {
            System.out.println(l2++ + "." + str);
        }
        choose = son.nextInt();
        String column2 = colums2.get(choose - 1);
        try {
            Statement statement = connection.createStatement();
            String query = "ALTER TABLE IF EXISTS public." + table_name1 + "\n" + "    ADD CONSTRAINT fk_" + table_name1 + " FOREIGN KEY (" + column_name1 + ")\n" + "    REFERENCES public." + table_name2 + " (" + column2 + ") MATCH SIMPLE\n" + "    ON UPDATE NO ACTION\n" + "    ON DELETE NO ACTION\n" + "    NOT VALID;";
            statement.execute(query);
            statement.close();
        } catch (SQLException e) {
            System.out.println("error");
        }

    }

    public ArrayList<String> column_name(String table_name) {
        ArrayList<String> columnlist = new ArrayList<>();
        try {

            Statement statement = connection.createStatement();
            String query = "SELECT\n" + "    column_name,\n" + "    data_type\n" + "FROM\n" + "    information_schema.columns\n" + "WHERE\n" + "    table_name = '" + table_name + "';";
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                columnlist.add(rs.getString(1) + "(" + rs.getString(2) + ")");
            }
            rs.close();
            statement.close();
        } catch (SQLException e) {
            System.out.println("error");
        }
        return columnlist;
    }

    public void deleteElement(String table_name, int index) {
        String turi, qiymati;
        System.out.println("O'chirish uchun quyidagilardan birini tanlang:");
        int c = 1;
        for (String str : column_name(table_name)) {
            System.out.println(c++ + "." + str);
        }
        Scanner scanner = new Scanner(System.in);
        int choose = scanner.nextInt();
        turi = column_name(table_name).get(choose - 1);
        String tur = "";
        for (int i = 0; i < turi.length(); i++) {
            if (turi.charAt(i) != '(') {
                tur += turi.charAt(i);
            } else break;
        }
        System.out.println("Qiymat kiriting:");
        scanner = new Scanner(System.in);
        System.out.print(turi + " -> ");
        qiymati = scanner.nextLine();
        try {
            Statement statement = connection.createStatement();
            String query = "delete from " + table_name + " where " + tur + " = '" + qiymati + "'";
            statement.executeUpdate(query);
            int navbat = 1;


            statement.close();

            System.out.println("deleted successful\n");

        } catch (SQLException e) {
            System.out.println("error");
            System.out.println();
        }
    }

    public void addElement(String table_name, int index) {
        String turi, qiymati;
        ArrayList<String> qiymarlar = new ArrayList<>();
        System.out.println("Yangi element qo'shish uchun quyidagilarni to'ldiring:");
        int c = 1;
        Scanner scanner = new Scanner(System.in);
        for (String str : column_name(table_name)) {
            System.out.print(c++ + "." + str + " -> ");
            String s = scanner.nextLine();
            if (s.isEmpty()) {
                s = " ";
            }
            qiymarlar.add(s);
        }
        scanner = new Scanner(System.in);


        try {
            Statement statement = connection.createStatement();
            String query = "insert into " + table_name + "(";
            int a1 = -1;
            for (String str : column_name(table_name)) {
                a1++;
                if (qiymarlar.get(a1) == " ") continue;
                String tur = "";
                for (int i = 0; i < str.length(); i++) {
                    if (str.charAt(i) != '(') {
                        tur += str.charAt(i);
                    } else break;
                }
                query += tur + ",";
            }
            query = query.substring(0, query.length() - 1);

            query += ") values ( ";
            int c1 = 0;
            for (String str : qiymarlar) {
                if (qiymarlar.get(c1++) == " ") continue;

                query += "'" + str + "',";
            }
            query = query.substring(0, query.length() - 1);
            query += ");";

            statement.executeUpdate(query);


            statement.close();

            System.out.println("added successful\n");

        } catch (SQLException e) {
            System.out.println("error");
            System.out.println();
        }
    }

    public void unique(String table_name, int index) {
        String turi, qiymati;
        System.out.println("Unique qilish uchun quyidagilardan birini tanlang:");
        int c = 1;
        for (String str : column_name(table_name)) {
            System.out.println(c++ + "." + str);
        }
        Scanner scanner = new Scanner(System.in);
        int choose = scanner.nextInt();
        turi = column_name(table_name).get(choose - 1);
        String tur = "";
        for (int i = 0; i < turi.length(); i++) {
            if (turi.charAt(i) != '(') {
                tur += turi.charAt(i);
            } else break;
        }
        try {
            Statement statement = connection.createStatement();
            String query = "ALTER TABLE IF EXISTS public." + table_name + "\n" + "    ADD CONSTRAINT " + tur + "_unique UNIQUE (" + tur + ");";
            statement.executeUpdate(query);
            int navbat = 1;


            statement.close();

            System.out.println("unique successful\n");

        } catch (SQLException e) {
            System.out.println("error");
        }
    }

    public void updateOne(String table_name, int index) {
        String turi, qiymati;
        System.out.println("update qilish uchun quyidagilardan birini tanlang:");
        int c = 1;
        for (String str : column_name(table_name)) {
            System.out.println(c++ + "." + str);
        }
        Scanner scanner = new Scanner(System.in);
        int choose = scanner.nextInt();
        turi = column_name(table_name).get(choose - 1);
        String tur = "";
        for (int i = 0; i < turi.length(); i++) {
            if (turi.charAt(i) != '(') {
                tur += turi.charAt(i);
            } else break;
        }
        System.out.println("Qiymat kiriting:");
        scanner = new Scanner(System.in);
        System.out.print(turi + " -> ");
        qiymati = scanner.nextLine();
        ArrayList<String> qiymarlar = new ArrayList<>();
        System.out.println("update qilish uchun quyidagilardan istalganini to'ldiring:");
        int c2 = 1;
        scanner = new Scanner(System.in);
        for (String str : column_name(table_name)) {
            System.out.print(c2++ + "." + str + " -> ");
            String s = scanner.nextLine();
            if (s.isEmpty()) {
                s = " ";
            }
            qiymarlar.add(s);
        }
        try {
            Statement statement = connection.createStatement();
            String query = "UPDATE " + table_name + " set ";

            ArrayList<String> strs = column_name(table_name);
            int l1 = -1;
            for (String s : qiymarlar) {
                l1++;
                if (s == " ") continue;
                String tur1 = "";
                for (int i = 0; i < strs.get(l1).length(); i++) {
                    if (strs.get(l1).charAt(i) != '(') {
                        tur1 += strs.get(l1).charAt(i);
                    } else break;
                }
                query += tur1 + " = '" + s + "',";

            }
            query = query.substring(0, query.length() - 1);
            query += " where " + tur + " = '" + qiymati + "';";
            statement.executeUpdate(query);
            System.out.println("update successful\n");
            statement.close();
        } catch (SQLException e) {
            System.out.println("error");
        }
    }

    public void selectOne(String table_name, int index) {
        String turi, qiymati;
        System.out.println("Qidirish uchun quyidagilardan birini tanlang:");
        int c = 1;
        for (String str : column_name(table_name)) {
            System.out.println(c++ + "." + str);
        }
        Scanner scanner = new Scanner(System.in);
        int choose = scanner.nextInt();
        turi = column_name(table_name).get(choose - 1);
        String tur = "";
        for (int i = 0; i < turi.length(); i++) {
            if (turi.charAt(i) != '(') {
                tur += turi.charAt(i);
            } else break;
        }
        System.out.println("Qiymat kiriting:");
        scanner = new Scanner(System.in);
        System.out.print(turi + " -> ");
        qiymati = scanner.nextLine();
        try {
            Statement statement = connection.createStatement();
            String query = "select * from " + table_name + " where " + tur + " = '" + qiymati + "'";
            ResultSet rs = statement.executeQuery(query);
            int navbat = 1;
            if (!rs.next()) {
                System.out.println("hech narsa topilmadi");
                return;
            }
            rs = statement.executeQuery(query);
            for (String str : column_name(table_name)) {
                System.out.print(str + "          ");
            }
            System.out.println();
            while (rs.next()) {
                int a = index;
                while (a != 0) {
                    System.out.print(rs.getString(navbat++) + "                           ");
                    a--;
                }
                navbat = 1;
                System.out.println();
            }
            rs.close();
            statement.close();

            System.out.println("selected all successful\n");

        } catch (SQLException e) {
            System.out.println("error");
        }
    }

    public void selectAll(String table_name, int index) {
        try {
            Statement statement = connection.createStatement();
            String query = "select * from " + table_name;
            ResultSet rs = statement.executeQuery(query);
            int navbat = 1;
            for (String str : column_name(table_name)) {
                System.out.print(str + "          ");
            }
            System.out.println();
            while (rs.next()) {
                int a = index;
                while (a != 0) {
                    System.out.print(rs.getString(navbat++) + "                           ");
                    a--;
                }
                navbat = 1;
                System.out.println();
            }
            rs.close();
            statement.close();

            System.out.println("selected all successful\n");

        } catch (SQLException e) {
            System.exit(0);
        }
    }

    public void createTable(String newTable, String column) {
        try {
            Statement statement = connection.createStatement();
            String query = "CREATE TABLE public." + newTable + "( " + column + " )";
//            for(String name : namelist){
//                query += name;
//            }
//            query += ");";
            statement.executeUpdate(query);
            statement.close();
            connection.close();
            System.out.println("create table");
        } catch (SQLException e) {
            System.exit(0);
        }

    }

    public void deleteTable(String table_name) {
        try {
            Statement statement = connection.createStatement();
            String query = "drop table " + table_name;
            statement.executeUpdate(query);
            statement.close();
            connection.close();
            System.out.println("table deleted");
        } catch (SQLException e) {
            System.exit(0);
        }
    }

    public ArrayList<Integer> column_number() {
        ArrayList<Integer> counts = new ArrayList<>();
        {
            try {
                Statement statement = connection.createStatement();
                String query = "select table_name, count(*) as column_count \n" + "from information_schema.\"columns\"\n" + "where table_schema = 'public' \n" + "GROUP by table_name order by column_count desc;";
                ResultSet rs = statement.executeQuery(query);
                while (rs.next()) {
                    counts.add(rs.getInt(2));
                }
                rs.close();
                statement.close();
                connection.close();
            } catch (SQLException e) {
                System.exit(0);
            }
        }
        return counts;
    }

    public ArrayList<String> table_name() {
        ArrayList<String> tables = new ArrayList<>();
        {
            try {
                Statement statement = connection.createStatement();
                String query = "select table_name, count(*) as column_count \n" + "from information_schema.\"columns\"\n" + "where table_schema = 'public' \n" + "GROUP by table_name order by column_count desc;";
                ResultSet rs = statement.executeQuery(query);
                while (rs.next()) {
                    tables.add(rs.getString(1));
                }
                rs.close();
                statement.close();
                connection.close();
            } catch (SQLException e) {
                System.exit(0);
            }
        }
        return tables;
    }


}

