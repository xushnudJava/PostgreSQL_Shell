package imtihon;

import java.util.Scanner;

public class Book {
    Scanner son = new Scanner(System.in);
    public void menu(){
        System.out.println("0.exit               1.add user        2.delete user");
        System.out.println("3.select all         4.select one      5.update");
        int i = son.nextInt();
        JDBC_Book jdbcBook = new JDBC_Book();
        switch (i){
            case 1:

                jdbcBook.addElement();
                break;
            case 2:
                jdbcBook.deleteElement();
                break;

            case 3:
                jdbcBook.selectAll();
                break;
            case 4:
                jdbcBook.selectOne();
                break;
            case 5:
                jdbcBook.updateOne();
                break;
            case 0:
                return;

        }
        menu();
    }

}
