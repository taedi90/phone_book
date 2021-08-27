package phone_book.dao.impl;

import phone_book.dao.PhoneBookDao;
import phone_book.dto.Phone;
import phone_book.util.JdbcUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;

public class PhoneBookDaoImpl implements PhoneBookDao {

    // singleton
    private static final PhoneBookDaoImpl instance = new PhoneBookDaoImpl();

    private PhoneBookDaoImpl() {
    }

    public static PhoneBookDaoImpl getInstance() {
        return instance;
    }


    int no = 1;
    HashSet<Phone> phoneBook = new HashSet<>();

    @Override
    public int add(Phone p) throws SQLException {
        String sql = "insert into phonebook(name, phone, address) values (?, ?, ?)";

        // 예외처리 미루기, Close 구문 수정해야 함
//        try (Connection con = JdbcUtil.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
//            pstmt.setString(1, p.getName());
//            pstmt.setString(2, p.getPhone());
//            pstmt.setString(3, p.getAddress());
//            //System.out.println("pstmt >>  " + pstmt);
//
//            return pstmt.executeUpdate();
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
        Connection con = JdbcUtil.getConnection();
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1, p.getName());
        pstmt.setString(2, p.getPhone());
        pstmt.setString(3, p.getAddress());
        //System.out.println("pstmt >>  " + pstmt);

        int res = pstmt.executeUpdate();
        con.close();
        pstmt.close();

        return res;

}

    @Override
    public Phone isContain(int no) {
        String sql = "select no, name, phone, address from phonebook where no = ?";
        Phone p = null;

        try (Connection con = JdbcUtil.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, no);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return getPhone(rs);
                }
            } catch (SQLException e) {
                System.out.println("Mysql - " + e.getMessage());
            }

        } catch (SQLException e) {
            System.out.println("Mysql - " + e.getMessage());
        }
        return p;
    }

    @Override
    public int del(int no) {

        String sql = "delete from phonebook where no = ?";
        try (Connection con = JdbcUtil.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, no);

            return pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Mysql - " + e.getMessage());
        }
        return 0;

    }

    @Override
    public int update(Phone p) {
        String sql = "update phonebook set name = ?, phone = ?, address = ? where no = ?";

        try (Connection con = JdbcUtil.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, p.getName());
            pstmt.setString(2, p.getPhone());
            pstmt.setString(3, p.getAddress());
            pstmt.setInt(4, p.getNo());

            return pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Mysql - " + e.getMessage());
        }
        return 0;
    }

    @Override
    public ArrayList<Phone> searchNumber(String str) {
        String sql = "select no, name, phone, address from phonebook where name like ? or phone like ? or address like ?";
        ArrayList<Phone> list = new ArrayList<>();
        str = String.format("%%%s%%", str);

        try (Connection con = JdbcUtil.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, str);
            pstmt.setString(2, str);
            pstmt.setString(3, str);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    do {
                        list.add(getPhone(rs));
                    } while (rs.next());
                }
            } catch (SQLException e) {
                System.out.println("Mysql - " + e.getMessage());
            }

        } catch (SQLException e) {
            System.out.println("Mysql - " + e.getMessage());
        }
        return list;

    }

    @Override
    public ArrayList<Phone> showAllList() {

        String sql = "select no, name, phone, address from phonebook";
        ArrayList<Phone> list = new ArrayList<>();

        try (Connection con = JdbcUtil.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {

                do {
                    list.add(getPhone(rs));
                } while (rs.next());

            }

        } catch (SQLException e) {
            System.out.println("Mysql - " + e.getMessage());
        }

        return list;

    }

    @Override
    public int reAlign() {
        String sql3 = "ALTER TABLE phonebook AUTO_INCREMENT=?";
        String sql1 = "SET @COUNT = 0";
        String sql2 = "UPDATE phonebook SET no = @COUNT:=@COUNT+1";

        try (Connection con = JdbcUtil.getConnection();
             PreparedStatement pstmt3 = con.prepareStatement(sql3);
             PreparedStatement pstmt1 = con.prepareStatement(sql1);
             PreparedStatement pstmt2 = con.prepareStatement(sql2)) {


            pstmt1.executeUpdate();
            int res = pstmt2.executeUpdate();
            pstmt3.setInt(1, res + 1);
            pstmt3.execute();
            return res;


        } catch (SQLException e) {
            System.out.println("Mysql - " + e.getMessage());
        }
        return 0;
    }

    // ResultSet으로 Phone 인스턴스 생성
    private Phone getPhone(ResultSet rs) throws SQLException {
        int no = rs.getInt("no");
        String address = rs.getString("address");
        String phone = rs.getString("phone");
        String name = rs.getString("name");
        return new Phone(no, name, phone, address);
    }

}
