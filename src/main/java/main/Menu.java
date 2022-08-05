package main;

import model.Manufacturer;
import model.Souvenir;
import repository.ManufacturesRepository;
import repository.Repository;
import repository.SouvenirsRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

/**
 * Class to demonstrate abilities of the program
 */
public class Menu {
    private final ManufacturesRepository manufacturesRepository = ManufacturesRepository.getInstance();

    private final SouvenirsRepository souvenirsRepository = SouvenirsRepository.getInstance();

    private final Scanner scanner = new Scanner(System.in);

    private final String DATE_PATTERN = "dd-MM-yyyy";

    private void printMainOptions() {
        System.out.println("1) Add, modify, remove or list manufactures, souvenirs");
        System.out.println("2) Print information about souvenirs of the concrete manufacturer");
        System.out.println("3) Print information about souvenirs from the concrete country");
        System.out.println("4) Print information about all manufactures and all souvenirs they produce");
        System.out.println("5) Print information about manufacturers by souvenir name and year");
        System.out.println("6) Print information about souvenirs made by years");
        System.out.println("7) Delete manufacturer and souvenirs");
        System.out.println("8) Save all to disk");
        System.out.println("9) Load all from disk");
        System.out.println("0) Exit");
    }

    /**
     * Method to enter the main manu, demonstrates abilities of the program
     */
    public void mainMenu() {
        boolean exit = false;
        while (!exit) {
            printMainOptions();
            int option = getOption(9);

            switch (option) {
                case 1 -> menuCRUD();
                case 2 -> printSouvenirsOfManufacturer();
                case 3 -> printSouvenirsFromCountry();
                case 4 -> printAll();
                case 5 -> printManufacturerBySouvenir();
                case 6 -> printSouvenirsByYears();
                case 7 -> removeManufacturerAndSouvenirs();
                case 8 -> Repository.saveAll();
                case 9 -> Repository.loadAll();

                case 0 -> exit = true;
            }
        }
    }


    private void printCRUDOptions() {
        System.out.println("1) Add manufacturer");
        System.out.println("2) List all manufacturers");
        System.out.println("3) Remove manufacturers by name");
        System.out.println("4) Modify manufacturer");
        System.out.println("5) Add souvenir");
        System.out.println("6) List all souvenirs");
        System.out.println("7) Remove souvenirs by name");
        System.out.println("8) Modify souvenir");
        System.out.println("0) Exit");
    }

    private void menuCRUD() {
        boolean exit = false;
        while (!exit) {
            printCRUDOptions();
            int option = getOption(8);
            switch (option) {
                case 1 -> addManufacturer();
                case 2 -> printManufacturers();
                case 3 -> removeManufactures();
                case 4 -> modifyManufacturer();
                case 5 -> addSouvenir();
                case 6 -> printSouvenirs();
                case 7 -> removeSouvenirs();
                case 8 -> modifySouvenir();
                case 0 -> exit = true;
            }
        }
    }

    /**
     * Methods to print souvenirs and manufacturers
     */
    private void printSouvenirsByYears() {
        souvenirsRepository.printSouvenirsByYears();
    }

    private void printManufacturerBySouvenir() {
        System.out.println("Please enter name of souvenir:");
        String name = scanner.nextLine();
        System.out.println("Please enter year:");
        int year = Integer.parseInt(scanner.nextLine());

        manufacturesRepository.printManufacturerBySouvenir(name, year);
    }

    private void printAll() {
        System.out.println(manufacturesRepository);
    }

    private void printSouvenirsFromCountry() {
        System.out.println("Please enter country name");
        String country = scanner.nextLine();
        souvenirsRepository.printSouvenirsFromCountry(country);
    }

    private void printSouvenirsOfManufacturer() {
        Manufacturer manufacturer = chooseManufacturer();
        manufacturesRepository.printSouvenirsOfManufacturer(manufacturer);
    }


    private void printShortenManufacturersInfo() {
        System.out.println(manufacturesRepository.listWithIndexes());
    }

    private void printSouvenirs() {
        System.out.println(souvenirsRepository);
    }

    private void printManufacturers() {
        System.out.println(manufacturesRepository.listWithIndexes());
    }

    /**
     * Methods to remove souvenirs and manufacturers
     */

    private void removeManufacturerAndSouvenirs() {
        Manufacturer manufacturer = chooseManufacturer();
        manufacturesRepository.deleteManufacturerAndSouvenirs(manufacturer);
    }

    private void removeSouvenirs() {
        System.out.println("Please enter name of souvenirs to remove");
        String name = scanner.nextLine();

        souvenirsRepository.removeByName(name);
        System.out.println("Removed successfully");
    }

    private void removeManufactures() {
        System.out.println("Please enter name of manufacture to delete:");
        String name = scanner.nextLine();

        manufacturesRepository.removeByName(name);
        System.out.println("Successfully removed");
    }

    /**
     * Methods to add souvenirs and manufacturers
     */
    private void addSouvenir() {
        System.out.println("Please enter name:");
        String name = scanner.nextLine();

        Manufacturer manufacturer = chooseManufacturer();

        System.out.println("Please enter price");
        double price = Double.parseDouble(scanner.nextLine());

        System.out.println("Please enter date in format " + DATE_PATTERN);
        LocalDate date = LocalDate.parse(scanner.nextLine(), DateTimeFormatter.ofPattern(DATE_PATTERN));

        Souvenir souvenir = Souvenir.createSouvenir(name, manufacturer, date, price);

        souvenirsRepository.add(souvenir);
        manufacturer.addSouvenir(souvenir);
        System.out.println("Added successfully");

    }

    private void addManufacturer() {
        System.out.println("Please enter name:");
        String name = scanner.nextLine();

        System.out.println("Please enter country:");
        String country = scanner.nextLine();

        manufacturesRepository.add(Manufacturer.createManufacturer(name, country));
        System.out.println("Manufacturer " + name + " successfully created");
    }

    /**
     * Methods to get users choice
     */
    private int getOption(int to) {
        System.out.println("Please choose option (" + 0 + "-" + to + ")");
        int option = Integer.parseInt(scanner.nextLine());
        while (option < 0 || option > to) {
            System.out.println("Invalid input, please try again");
            option = Integer.parseInt(scanner.nextLine());
        }
        return option;
    }

    private Souvenir chooseSouvenir() {
        System.out.println("Please enter index of souvenir:");
        System.out.println(souvenirsRepository.shortenToString());

        return souvenirsRepository.get(Integer.parseInt(scanner.nextLine()) - 1);
    }

    private Manufacturer chooseManufacturer() {
        System.out.println("Please choose manufacturer by index");
        printShortenManufacturersInfo();

        int input = Integer.parseInt(scanner.nextLine());
        return manufacturesRepository.get(input - 1);
    }

    /**
     * Methods to modify manufacturers and souvenirs
     */
    private void modifyManufacturer() {
        Manufacturer manufacturer = chooseManufacturer();

        System.out.println("Please enter name to change (blank for not changing)");
        String name = scanner.nextLine();
        if (name.equals(""))
            name = manufacturer.getName();

        System.out.println("Please enter country to change (blank for not changing)");
        String country = scanner.nextLine();
        if (country.equals(""))
            country = manufacturer.getCountry();

        manufacturer.setName(name);
        manufacturer.setCountry(country);
        System.out.println("Modified successfully");
    }

    private void modifySouvenir() {
        if (souvenirsRepository.size() == 0) {
            System.out.println("No souvenirs to modify");
            return;
        }
        Souvenir souvenir = chooseSouvenir();

        System.out.println("Please enter name (blank for not changing)");
        String name = scanner.nextLine();
        if (name.isBlank())
            name = souvenir.getName();

        System.out.println("Do you want to change manufacturer (y/n)");
        Manufacturer manufacturer = souvenir.getManufacturer();
        if (scanner.nextLine().equalsIgnoreCase("y"))
            manufacturer = chooseManufacturer();

        System.out.println("Please enter date in following format " + DATE_PATTERN + " (blank for not changing):");
        String input = scanner.nextLine();
        if (input.isBlank())
            input = souvenir.getReleaseDate() + "";
        LocalDate date = LocalDate.parse(input, DateTimeFormatter.ofPattern(DATE_PATTERN));

        System.out.println("Please enter price (-1 fro not changing)");
        double price = Double.parseDouble(scanner.nextLine());
        if (price == -1)
            price = souvenir.getPrice();

        souvenir.setName(name);
        souvenir.setManufacturer(manufacturer);
        souvenir.setReleaseDate(date);
        souvenir.setPrice(price);
        System.out.println("Modified successfully");
    }
}
