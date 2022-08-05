package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Souvenir implements Serializable {
    static final long serialVersionUID = 1122334455L;

    private String name;

    private Manufacturer manufacturer;

    private LocalDate releaseDate;

    private double price;

    public static Souvenir createSouvenir(String name, Manufacturer manufacturer, LocalDate releaseDate, double price) {
        if (name == null || manufacturer == null || releaseDate == null)
            throw new IllegalArgumentException("Name, manufacturer and date can not be null");
        if (price < 0)
            throw new IllegalArgumentException("Price can not be less than zero");

        return new Souvenir(name, manufacturer, releaseDate, price);
    }

    private Souvenir(String name, Manufacturer manufacturer, LocalDate releaseDate, double price) {
        this.name = name;
        this.manufacturer = manufacturer;
        this.releaseDate = releaseDate;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public double getPrice() {
        return price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Souvenir{" +
                "name='" + name + '\'' +
                ", manufacturer=" + manufacturer.getName() +
                ", releaseDate=" + releaseDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")) +
                ", price=" + price +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Souvenir souvenir = (Souvenir) o;
        return Double.compare(souvenir.price, price) == 0
                && name.equals(souvenir.name)
                && manufacturer.equals(souvenir.manufacturer)
                && releaseDate.equals(souvenir.releaseDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, manufacturer, releaseDate, price);
    }
}
