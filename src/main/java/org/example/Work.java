package org.example;

import java.util.ArrayList;
import java.util.Scanner;

public class Work {
    private Info info = new Info();
    private Scanner str = new Scanner(System.in);
    private Scanner son = new Scanner(System.in);
    private String table_name;
    private String column_count;
    private ArrayList<String> table_names;
    private ArrayList<Integer> table_counts;

    public void main_menu() {
        System.out.println("1.tables list              2.table created");
        System.out.println("3.table deleted            4.exit");
//        System.out.println("5.exit");
        int choose = son.nextInt();
        int c, index;
        switch (choose) {
            case 1:
                info = new Info();
                table_names = info.table_name();
                System.out.println("Kirish uchun quyidagilardan birini tanlang:");
                c = 1;
                for (String name : table_names) {
                    System.out.println(c++ + "." + name);
                }
                System.out.println(c + ".bosh menuga qaytish");
                index = son.nextInt();
                if (index >= c) main_menu();
                table_name = table_names.get(index - 1);
                table_menu(table_name, index - 1);
                break;

            case 2:
                create();
                break;
            case 3:
                info = new Info();
                table_names = info.table_name();
                System.out.println("Delete qilish uchun quyidagilardan birini tanlang:");
                c = 1;
                for (String name : table_names) {
                    System.out.println(c++ + "." + name);
                }
                System.out.println(c + ".bosh menuga qaytish");
                index = son.nextInt();
                if (index >= c) main_menu();
                table_name = table_names.get(index - 1);
                deleted(table_name);
                break;
//            case 4:
//                info = new Info();
//                info.foreign_Key();
//                break;
            case 4:
                return;
            default:
                return;
        }
        return;
    }

    private void table_menu(String table_name, int index) {
        System.out.println("0.exit               1.add user        2.delete user");
        System.out.println("3.select all         4.select one      5.update");
        System.out.println("6.unique qilish      7.bosh menu");
        int i = son.nextInt();
        info = new Info();
        switch (i) {
            case 0:
                return;
            case 1:
                info = new Info();
                info.addElement(table_name, index);
                tanla2(table_name, index);
                break;
            case 2:
                info = new Info();
                info.deleteElement(table_name, index);
                tanla2(table_name, index);
                break;
            case 3:
                table_counts = info.column_number();
                info = new Info();
                info.selectAll(table_name, table_counts.get(index));
                tanla2(table_name, index);
                break;
            case 4:
                selectOne(table_name, index);

                break;
            case 5:
                info = new Info();
                info.updateOne(table_name, index);
                tanla2(table_name, index);
                break;
            case 6:
                info = new Info();
                info.unique(table_name, index);
                tanla2(table_name, index);
                break;
            case 7:
                main_menu();
                break;
        }
        return;
    }

    private void selectOne(String table_name, int index) {

        table_counts = info.column_number();
        info = new Info();
        info.selectOne(table_name, table_counts.get(index));
        tanla2(table_name, index);
    }

    private void create() {
        System.out.println("Yangi table nomini kiriting:");
        String newTable = str.nextLine();
        info = new Info();
        table_names = info.table_name();
        if (!table_names.contains(newTable)) {
            System.out.println("Ushbu tablega o'zgartiruvchi kiritasizmi");
            System.out.println("1.ha                   2.yo'q");
            String column;
            info = new Info();
            if (son.nextInt() == 1) {
                System.out.println();
                System.out.println("id bigserial primary key, name character varying");
                System.out.println();
                System.out.println("O'zgaruvchilarni tepada ko'rsatilgandek kiriting:");
                column = str.nextLine();
                info.createTable(newTable, column);
            } else {
                column = "id bigserial primary key";
                info.createTable(newTable, column);
            }
            tanla();
        } else {
            System.out.println("Bunday table mavjud boshqa kiriting:");
            create();
        }
    }

    private void deleted(String table_name) {
        info = new Info();
        info.deleteTable(table_name);
        tanla();
    }

    private void tanla2(String table_name, int index) {
        System.out.println("1.table menu        2.bosh menu");
        System.out.println("3.exit");
        int i = son.nextInt();
        if (i == 1) {
            table_menu(table_name, index);
        } else if (i == 2) {
            main_menu();
        } else {
            System.out.println("dastur tugallandi");
            return;
        }
    }

    private void tanla() {
        System.out.println("1.Bosh menu        2.exit");
        if (son.nextInt() == 1) {
            main_menu();
        } else {
            System.out.println("dastur tugallandi");
            return;
        }
    }
}
