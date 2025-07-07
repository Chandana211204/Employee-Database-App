import java.sql.*;

public class EmployeeDatabaseApp {

    static final String URL = "jdbc:mysql://localhost:3306/employee_db";
    static final String USER = "root";  // change if needed
    static final String PASS = "password";  // change your password

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS)) {
            System.out.println("Connected to database.");

            // Add a new employee
            addEmployee(conn, "Alice", "Manager", 70000);
            
            // View all employees
            viewEmployees(conn);

            // Update employee
            updateEmployeeSalary(conn, 1, 80000);

            // Delete employee
            deleteEmployee(conn, 1);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static void addEmployee(Connection conn, String name, String designation, double salary) throws SQLException {
        String sql = "INSERT INTO employees (name, designation, salary) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setString(2, designation);
            ps.setDouble(3, salary);
            ps.executeUpdate();
            System.out.println("Employee added.");
        }
    }

    static void viewEmployees(Connection conn) throws SQLException {
        String sql = "SELECT * FROM employees";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                System.out.printf("ID: %d, Name: %s, Designation: %s, Salary: %.2f%n",
                        rs.getInt("id"), rs.getString("name"),
                        rs.getString("designation"), rs.getDouble("salary"));
            }
        }
    }

    static void updateEmployeeSalary(Connection conn, int id, double newSalary) throws SQLException {
        String sql = "UPDATE employees SET salary = ? WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, newSalary);
            ps.setInt(2, id);
            int rows = ps.executeUpdate();
            System.out.println("Updated rows: " + rows);
        }
    }

    static void deleteEmployee(Connection conn, int id) throws SQLException {
        String sql = "DELETE FROM employees WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            int rows = ps.executeUpdate();
            System.out.println("Deleted rows: " + rows);
        }
    }
}