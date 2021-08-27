package phone_book.dao;

import phone_book.dto.Phone;

import java.sql.SQLException;
import java.util.ArrayList;

public interface PhoneBookDao {
	int add(Phone p) throws SQLException;

	Phone isContain(int no);

	int del(int no);

	int update(Phone p);

	ArrayList<Phone> searchNumber(String str);

	ArrayList<Phone> showAllList();

	int reAlign();
}
