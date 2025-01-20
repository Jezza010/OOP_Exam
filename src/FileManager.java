import java.io.*;
import java.util.Map;

public class FileManager {

    public static void saveUsers(Map<String, User> users) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("users.dat"))) {
            out.writeObject(users);
        } catch (IOException e) {
            System.out.println("Ошибка при сохранении данных: " + e.getMessage());
        }
    }

    public static Map<String, User> loadUsers() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("users.dat"))) {
            return (Map<String, User>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Ошибка при загрузке данных: " + e.getMessage());
            return null;
        }
    }
}
