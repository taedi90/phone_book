package phone_book.dto;

public class Phone implements Comparable<Phone> {
    private int no;
    private String name;
    private String phone;
    private String address;

    public Phone() {
    }

    public Phone(String phone) {
        this.phone = phone;
    }

    public Phone(String name, String phone, String address) {
        this.name = name;
        this.phone = phone;
        this.address = address;

    }

    public Phone(int no, String name, String phone, String address) {
        this.no = no;
        this.name = name;
        this.phone = phone;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    @Override
    public String toString() {
        return String.format("[%04d, %s, %11s, %s]", no, name, phone, address);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((phone == null) ? 0 : phone.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Phone other = (Phone) obj;
        if (phone == null) {
            if (other.phone != null)
                return false;
        } else if (!phone.equals(other.phone))
            return false;
        return true;
    }

    @Override
    public int compareTo(Phone o) {

        return this.name.compareTo(o.name);
    }

    public int getNo() {
        return no;
    }
}