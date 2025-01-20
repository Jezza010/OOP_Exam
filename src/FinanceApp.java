import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class FinanceApp {
    private Map<String, User> users;
    private User currentUser;

    public FinanceApp() {
        // Загрузка пользователей из файла при старте
        this.users = FileManager.loadUsers();
        if (this.users == null) {
            this.users = new HashMap<>();
        }
        this.currentUser = null;
    }

    public boolean registerUser(String username, String password) {
        if (users.containsKey(username)) {
            System.out.println("Ошибка: Пользователь с таким именем уже существует.");
            return false;
        }
        users.put(username, new User(username, password));
        FileManager.saveUsers(users);  // Сохраняем данные после регистрации
        return true;
    }

    public boolean loginUser(String username, String password) {
        User user = users.get(username);
        if (user != null && user.getPassword().equals(password)) {
            currentUser = user;
            System.out.println("Добро пожаловать, " + username + "!");
            return true;
        } else {
            System.out.println("Ошибка: Неверное имя пользователя или пароль.");
            return false;
        }
    }

    public void addIncome() {
        if (currentUser == null) {
            System.out.println("Вы не авторизованы.");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите категорию дохода:");
        String category = scanner.nextLine();
        System.out.println("Введите сумму дохода:");
        double amount = scanner.nextDouble();
        currentUser.getWallet().addIncome(category, amount);
        System.out.println("Доход добавлен.");
    }

    public void addExpense() {
        if (currentUser == null) {
            System.out.println("Вы не авторизованы.");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите категорию расхода:");
        String category = scanner.nextLine();
        System.out.println("Введите сумму расхода:");
        double amount = scanner.nextDouble();
        currentUser.getWallet().addExpense(category, amount);
        System.out.println("Расход добавлен.");
    }

    public void addCategory() {
        if (currentUser == null) {
            System.out.println("Вы не авторизованы.");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите название категории:");
        String name = scanner.nextLine();
        System.out.println("Введите бюджет для категории:");
        double budget = scanner.nextDouble();
        currentUser.getWallet().addCategory(name, budget);
        System.out.println("Категория с бюджетом добавлена.");
    }

    public void showUserStats() {
        if (currentUser == null) {
            System.out.println("Вы не авторизованы.");
            return;
        }

        System.out.println("Общий доход: " + currentUser.getWallet().getTotalIncome());
        System.out.println("Общие расходы: " + currentUser.getWallet().getTotalExpenses());

        System.out.println("Доходы по категориям:");
        for (Map.Entry<String, Double> entry : currentUser.getWallet().getIncomeByCategory().entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

        System.out.println("Расходы по категориям:");
        for (Map.Entry<String, Double> entry : currentUser.getWallet().getExpensesByCategory().entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

        System.out.println("Бюджеты по категориям:");
        for (Map.Entry<String, Category> entry : currentUser.getWallet().getCategories().entrySet()) {
            Category category = entry.getValue();
            double remainingBudget = category.getBudget() - currentUser.getWallet().getExpensesByCategory().getOrDefault(category.getName(), 0.0);
            System.out.println(category.getName() + ": " + category.getBudget() + ", Оставшийся бюджет: " + remainingBudget);
        }
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            if (currentUser == null) {
                // Пока пользователь не авторизован, показываем только команды для регистрации и входа
                System.out.println("Выберите команду:");
                System.out.println("1. Регистрация");
                System.out.println("2. Вход");
                System.out.println("3. Выход");

                int choice = scanner.nextInt();
                scanner.nextLine();  // consume newline

                switch (choice) {
                    case 1:
                        System.out.println("Введите имя пользователя:");
                        String username = scanner.nextLine();
                        System.out.println("Введите пароль:");
                        String password = scanner.nextLine();
                        if (registerUser(username, password)) {
                            System.out.println("Регистрация успешна.");
                        }
                        break;
                    case 2:
                        System.out.println("Введите имя пользователя:");
                        username = scanner.nextLine();
                        System.out.println("Введите пароль:");
                        password = scanner.nextLine();
                        if (loginUser(username, password)) {
                            System.out.println("Вход успешен.");
                        }
                        break;
                    case 3:
                        System.out.println("Выход...");
                        FileManager.saveUsers(users);  // Сохраняем данные перед выходом
                        return;
                    default:
                        System.out.println("Некорректная команда.");
                }
            } else {
                // После входа показываем основные команды
                System.out.println("Выберите команду:");
                System.out.println("1. Добавить доход");
                System.out.println("2. Добавить расход");
                System.out.println("3. Добавить категорию с бюджетом");
                System.out.println("4. Показать статистику");
                System.out.println("5. Выход");

                int choice = scanner.nextInt();
                scanner.nextLine();  // consume newline

                switch (choice) {
                    case 1:
                        addIncome();
                        break;
                    case 2:
                        addExpense();
                        break;
                    case 3:
                        addCategory();
                        break;
                    case 4:
                        showUserStats();
                        break;
                    case 5:
                        System.out.println("Выход...");
                        FileManager.saveUsers(users);  // Сохраняем данные перед выходом
                        return;
                    default:
                        System.out.println("Некорректная команда.");
                }
            }
        }
    }

    public static void main(String[] args) {
        FinanceApp app = new FinanceApp();
        app.run();
    }
}
