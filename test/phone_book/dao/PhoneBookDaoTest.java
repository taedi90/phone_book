package phone_book.dao;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import phone_book.dao.impl.PhoneBookDaoImpl;
import phone_book.dto.Phone;

import java.sql.SQLException;
import java.util.ArrayList;

public class PhoneBookDaoTest {

    private PhoneBookDao dao;

    @Before
    public void setUp() throws Exception {
        dao = PhoneBookDaoImpl.getInstance();
    }

    @After
    public void tearDown() throws Exception {
        System.out.println();
        dao = null;
    }

    @Test
    public void add() {
        System.out.println("add");
        Phone p = new Phone("신규자", "092348293", "대구 남구시");
        int res = 0;
        try {
            res = dao.add(p);
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        Assert.assertNotEquals(0,res);
    }

    @Test
    public void isContain() {
        System.out.println("isContain");
        Phone p = dao.isContain(1);
        Assert.assertNotNull(p);
        System.out.println("No 1 is : " + p);
    }

    @Test
    public void del() {
        System.out.println("delete");
        int res = dao.del(6);
        Assert.assertNotEquals(0,res);
    }

    @Test
    public void searchNumber() {
        System.out.println("searchNumber");
        ArrayList<Phone> list = dao.searchNumber("서울");
        Assert.assertNotEquals(0, list.size());
        list.stream().forEach(System.out::println);
    }

    @Test
    public void showAllList() {
        System.out.println("showAllList");
        ArrayList<Phone> list = dao.showAllList();
        Assert.assertNotEquals(0, list.size());
        list.stream().forEach(System.out::println);
    }

    @Test
    public void update() {
        System.out.println("update");
        Phone p = new Phone(1, "바꾼자", "0987654321", "우주 남구시");
        int res = dao.update(p);
        Assert.assertNotEquals(0,res);
    }

    @Test
    public void reAlign() {
        System.out.println("reAlign");
        int res = dao.reAlign();
        Assert.assertNotNull(res);
        System.out.println(res);
    }

}