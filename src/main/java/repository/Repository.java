package repository;

public class Repository {
    public static void saveAll() {
        ManufacturesRepository.getInstance().save();
        SouvenirsRepository.getInstance().save();
    }

    public static void loadAll() {
        ManufacturesRepository.getInstance().load();
        SouvenirsRepository.getInstance().load();
    }
}
