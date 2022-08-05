package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Manufacturer implements Serializable {
    static final long serialVersionUID = 1234567890L;
    private final List<Souvenir> souvenirList = new ArrayList<>();

    private String name;

    private String country;

    public static Manufacturer createManufacturer (String name, String country) {
        if (name == null || country == null)
            throw new IllegalArgumentException("Name and country can not be null");

        return new Manufacturer(name, country);
    }

    private Manufacturer(String name, String country) {
        this.name = name;
        this.country = country;
    }

    public List<Souvenir> getSouvenirList() {
        return souvenirList;
    }

    public void addSouvenir(Souvenir souvenir) {
        souvenirList.add(souvenir);
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("Manufacturer " + name + "\nCountry " + country + "\n");
        souvenirList.forEach(s -> result.append(s).append("\n"));

        return result + "";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Manufacturer that = (Manufacturer) o;
        return souvenirList.equals(that.souvenirList) && name.equals(that.name) && country.equals(that.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(souvenirList, name, country);
    }
}
