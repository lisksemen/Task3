package repository;

import model.Souvenir;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * This class is a singleton repository. It contains a list of all souvenirs and methods to proceed them
 */
public class SouvenirsRepository implements Serializable {
    static final long serialVersionUID = 11111111L;
    private static final String FILENAME = "souvenirs.dat";

    private static SouvenirsRepository instance;
    private final List<Souvenir> list = new ArrayList<>();

    public static SouvenirsRepository getInstance() {
        if (instance == null)
            instance = new SouvenirsRepository();
        return instance;
    }

    private SouvenirsRepository() {
    }

    public void removeByName(String name) {
        list.removeIf(s -> Objects.equals(name, s.getName()));
        ManufacturesRepository.getInstance()
                .forEach(manufacturer ->
                        manufacturer.getSouvenirList().removeIf(s -> Objects.equals(name, s.getName())
                        )
                );
    }

    public int size() {
        return list.size();
    }

    public Stream<Souvenir> stream() {
        return list.stream();
    }

    public void removeIf(Predicate<? super Souvenir> filter) {
        list.removeIf(filter);
    }

    public void add(Souvenir souvenir) {
        list.add(souvenir);
    }

    public Souvenir get(int index) {
        return list.get(index);
    }

    public void printSouvenirsByYears() {
        list.stream()
                .collect(Collectors.groupingBy(souvenir -> souvenir.getReleaseDate().getYear()))
                .forEach(
                        (year, list) -> {
                            System.out.println(year + ":");
                            list.forEach(System.out::println);
                            System.out.println();
                        }
                );
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        list.forEach(souvenir -> result.append(souvenir).append("\n"));

        return result + "";
    }

    public void printSouvenirsFromCountry(String country) {
        list.stream()
                .filter(s -> s.getManufacturer().getCountry().equals(country))
                .forEach(System.out::println);
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
            instance.list
                    .addAll(((SouvenirsRepository) inputStream.readObject()).list);
            inputStream.close();

        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public String shortenToString() {
        StringBuilder result = new StringBuilder();
        AtomicInteger i = new AtomicInteger(1);

        list.forEach(souvenir ->
                result.append(i.getAndIncrement())
                        .append(") ")
                        .append(souvenir)
                        .append("\n")
        );

        return result + "";
    }
}
