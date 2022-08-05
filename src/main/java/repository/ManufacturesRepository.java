package repository;

import model.Manufacturer;
import model.Souvenir;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

/**
 * This class is a singleton repository. It contains a list of all manufactures and methods to proceed them
 */
public class ManufacturesRepository implements Serializable {
    final static long serialVersionUID = 10203040506070L;
    private final List<Manufacturer> list = new ArrayList<>();

    private static ManufacturesRepository instance;

    private final String FILENAME = "manufacturers.dat";

    public static ManufacturesRepository getInstance() {
        if (instance == null)
            instance = new ManufacturesRepository();
        return instance;
    }

    private ManufacturesRepository() {}

    public void add(Manufacturer manufacturer) {
        list.add(manufacturer);
    }

    public Manufacturer get(int index) {
        return list.get(index);
    }

    public void forEach(Consumer<? super Manufacturer> action) {
        list.forEach(action);
    }

    /**
     * Removes all manufacturers with specified name
     * @param name name to search and remove
     */
    public void removeByName(String name) {
        list.removeIf(s -> Objects.equals(s.getName(), name));
    }

    public void deleteManufacturerAndSouvenirs(Manufacturer manufacturer) {
        SouvenirsRepository.getInstance().removeIf(s -> s.getManufacturer().equals(manufacturer));

        list.remove(manufacturer);
    }

    public String listWithIndexes() {
        StringBuilder result = new StringBuilder();
        AtomicInteger i = new AtomicInteger(1);

        list.forEach(m -> result.append(i.getAndIncrement()).append(") Manufacturer ")
                .append(m.getName())
                .append(", country ").append(m.getCountry())
                .append("\n"));

        return result + "";
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("Manufactures count " + list.size() + ":\n");
        AtomicInteger i = new AtomicInteger(1);

        list.forEach(
                manufacturer -> result.append(i.getAndIncrement())
                        .append(") Manufacturer ").append(manufacturer.getName())
                        .append("\n")
                        .append("Country: ").append(manufacturer.getCountry())
                        .append("\n")
                        .append(
                                manufacturer.getSouvenirList().stream()
                                        .map(Souvenir::toString)
                                        .reduce("", (a, b) -> a + "\n" + b)
                        )
                        .append("\n")
        );

        return result + "\n";
    }

    public void printManufacturerBySouvenir(String name, int year) {
        SouvenirsRepository.getInstance().stream()
                .filter(s -> s.getName().equals(name))
                .filter(a -> a.getReleaseDate().getYear() == year)
                .forEach(d -> System.out.println("Manufacturer: " +
                        d.getManufacturer().getName() +
                        "\nCountry: " +
                        d.getManufacturer().getCountry())
                );
    }

    public void printSouvenirsOfManufacturer(Manufacturer manufacturer) {
        manufacturer.getSouvenirList().forEach(System.out::println);
        System.out.println();
    }

    public void save() {
        try {
            var outputStream = new ObjectOutputStream(Files.newOutputStream(Path.of(FILENAME)));
            outputStream.writeObject(getInstance());
            outputStream.close();
            outputStream.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void load() {
        try {
            var inputStream = new ObjectInputStream(new FileInputStream(FILENAME));

            instance.list.clear();
            instance.list.
                    addAll(((ManufacturesRepository) inputStream.readObject()).list);

            inputStream.close();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
