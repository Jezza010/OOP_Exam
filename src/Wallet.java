import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Wallet implements Serializable {
    private static final long serialVersionUID = 1L;

    private double totalIncome;
    private double totalExpenses;
    private Map<String, Double> incomeByCategory;
    private Map<String, Double> expensesByCategory;
    private Map<String, Category> categories;

    public Wallet() {
        this.totalIncome = 0;
        this.totalExpenses = 0;
        this.incomeByCategory = new HashMap<>();
        this.expensesByCategory = new HashMap<>();
        this.categories = new HashMap<>();
    }

    public void addIncome(String category, double amount) {
        totalIncome += amount;
        incomeByCategory.put(category, incomeByCategory.getOrDefault(category, 0.0) + amount);
    }

    public void addExpense(String category, double amount) {
        totalExpenses += amount;
        expensesByCategory.put(category, expensesByCategory.getOrDefault(category, 0.0) + amount);
    }

    public void addCategory(String name, double budget) {
        categories.put(name, new Category(name, budget));
    }

    public double getTotalIncome() {
        return totalIncome;
    }

    public double getTotalExpenses() {
        return totalExpenses;
    }

    public Map<String, Double> getIncomeByCategory() {
        return incomeByCategory;
    }

    public Map<String, Double> getExpensesByCategory() {
        return expensesByCategory;
    }

    public Map<String, Category> getCategories() {
        return categories;
    }
}
