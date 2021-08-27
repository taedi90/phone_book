package phone_book;

import phone_book.dao.PhoneBookDao;
import phone_book.dao.impl.PhoneBookDaoImpl;
import phone_book.dto.Phone;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class PhoneBookMain {
    static PhoneBookDao PB = PhoneBookDaoImpl.getInstance(); // 전화번호부
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        while (true) {
            int input = indexMenu(); // 시작 메뉴

            // 메뉴 선택
            switch (input) {
                case 1:
                    fun01_new();
                    break;
                case 2:
                    fun02_update();
                    break;
                case 3:
                    fun03_del();
                    break;
                case 4:
                    fun04_find();
                    break;
                case 5:
                    fun05_show();
                    break;
                case 6:
                    fun06_reAlign();
                    break;
                case 7:
                    fun07_quit();
                    break;
                default:
                    break;
            }
        }

    }

    private static void fun01_new() {

        System.out.println("=========================");
        System.out.println("1. 신규 번호 입력");
        System.out.println("=========================");

        System.out.print("이름을 입력하세요 : ");
        String name = sc.next();

        System.out.print("전화번호를 입력하세요 : ");
        sc.nextLine(); // 버퍼 제거
        String phone = sc.next().replaceAll("[^0-9]", "");
        if (phone.equals("")) {
            phone = null;
        }

        System.out.print("주소를 입력하세요 : ");
        sc.nextLine(); // 버퍼 제거
        String address = sc.nextLine();

        Phone p = new Phone(name, phone, address);
        int res = 0;
        try {
            res = PB.add(p);
            System.out.println("저장 완료!");
        } catch (SQLException e) {
            System.out.println("Mysql - " + e.getMessage());
            System.out.println("저장 실패!");
        } finally {
            System.out.println("=========================");
            System.out.println();
        }


    }

    private static void fun02_update() {
        System.out.println("=========================");
        System.out.println("2. 등록 번호 수정");
        System.out.println("=========================");

        System.out.print("수정할 번호를 입력하세요 : ");

        int delNo = sc.nextInt();
        Phone findPhone = PB.isContain(delNo);
        if (findPhone == null) {
            System.out.println("오류 - 단축 번호를 다시 확인하고 시도하세요");
            System.out.println("=========================");
            System.out.println();
            return;
        }


        System.out.println(findPhone);
        System.out.printf("수정하시겠습니까?(Y/n)");
        String yN = sc.next();

        Phone p = null;
        if (yN.contains("y") || yN.contains("Y") || yN.contains("ㅛ")) {
            System.out.print("이름을 입력하세요 : ");
            String name = sc.next();

            System.out.print("전화번호를 입력하세요 : ");
            sc.nextLine(); // 버퍼 제거
            String phone = sc.next().replaceAll("[^0-9]", "");

            System.out.print("주소를 입력하세요 : ");
            sc.nextLine(); // 버퍼 제거
            String address = sc.nextLine();

            p = new Phone(findPhone.getNo(), name, phone, address);
        }
        int res = 0;
        if (p != null) {
            res = PB.update(p);
        }

        if (res == 1) {
            System.out.println("수정 완료");
        } else {
            System.out.println("수정 취소");
        }


        System.out.println("=========================");
        System.out.println();
        return;

    }

    private static void fun03_del() {
        System.out.println("=========================");
        System.out.println("3. 등록 번호 삭제");
        System.out.println("=========================");

        System.out.print("삭제할 단축 번호를 입력하세요 : ");
        int delNo = sc.nextInt();
        Phone findPhone = PB.isContain(delNo);
        if (findPhone == null) {
            System.out.println("오류 - 단축 번호를 다시 확인하고 시도하세요");
            System.out.println("=========================");
            System.out.println();
            return;
        }

        System.out.println(findPhone);

        System.out.printf("삭제하면 복구할 수 없습니다. 계속하시겠습니까?(Y/n)");
        String yN = sc.next();

        if (yN.contains("y") || yN.contains("Y") || yN.contains("ㅛ")) {
            PB.del(delNo);
            System.out.println("삭제완료");
            System.out.println("=========================");
            System.out.println();
            return;
        }
        System.out.println("취소");
        System.out.println("=========================");
        System.out.println();

    }

    private static void fun04_find() {
        System.out.println("=========================");
        System.out.println("4. 번호 검색 서비스");
        System.out.println("=========================");

        System.out.print("찾으실 이름, 번호, 주소 일부를 입력해주세요 : ");
        String findWhat = sc.next();
        ArrayList<Phone> resArr = PB.searchNumber(findWhat);
        for (Phone p : resArr) {
            System.out.println(p);
        }

        System.out.println("=========================");
        System.out.println();

    }

    private static void fun05_show() {
        System.out.println("=========================");
        System.out.println("5. 전체 리스트 확인");
        System.out.println("=========================");


        ArrayList<Phone> resArr = PB.showAllList();
        for (Phone p : resArr) {
            System.out.println(p);
        }
        System.out.println();

        System.out.println("=========================");
        System.out.println();

    }

    private static void fun06_reAlign() {
        System.out.printf("단축 번호가 재정렬됩니다. 계속하시겠습니까?(Y/n)");
        String yN = sc.next();

        if (yN.contains("y") || yN.contains("Y") || yN.contains("ㅛ")) {
            PB.reAlign();
            System.out.println("정렬 완료!");
            System.out.println("=========================");
            System.out.println();
            return;
        }
        System.out.println("취소");
        System.out.println("=========================");
        System.out.println();
    }

    private static void fun07_quit() {
        System.out.println("프로그램을 종료합니다.");
        sc.close();
        System.exit(0);
    }

    private static int getNum(int stt, int end) {

        int input = 0;
        try {

            input = sc.nextInt();

        } catch (InputMismatchException e) {
            System.out.println("숫자만 이용할 수 있습니다.");
            sc.next();
            return 0;
        }

        if (input < stt || input > end) {
            System.out.printf("%,d에서 %,d만 입력 가능합니다.%n", stt, end);
            return 0;
        }

        return input;

    }

    private static int indexMenu() {

        System.out.println("======== 전화번호부 ========");
        System.out.println("1. 신규 번호 입력");
        System.out.println("2. 등록 번호 수정");
        System.out.println("3. 등록 번호 삭제");
        System.out.println("4. 번호 검색 서비스");
        System.out.println("5. 전체 리스트 확인");
        System.out.println("6. 단축 번호 재정렬");
        System.out.println("7. 종료");
        System.out.println("=========================");

        int input = 0;
        while (input == 0) {
            System.out.print("번호를 선택해주세요 : ");
            input = getNum(1, 7);
        }

        System.out.println();
        return input;

    }
}
