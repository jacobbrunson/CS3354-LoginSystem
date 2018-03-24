import java.sql.*;

public class DatabaseManager {

    private Connection connection;


    private static DatabaseManager instance;

    public static DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }

        return instance;
    }

    private DatabaseManager() {
        String url = "jdbc:mysql://localhost:3306/cs3354";
        String username = "root";
        String password = "root";

        System.out.println("Connecting database...");
        try {
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Database connected!");
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }

    public void save(User user) {
        try {
            PreparedStatement s = connection.prepareStatement("INSERT INTO users (username, email, password) VALUES(?, ?, ?)");
            s.setString(1, user.getUsername());
            s.setString(2, user.getEmail());
            s.setString(3, user.getPassword());
            s.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User findUser(String username) {
        try {
            PreparedStatement s = connection.prepareStatement("SELECT * FROM users WHERE username = ?");
            s.setString(1, username);
            ResultSet results = s.executeQuery();

            if (results.first()) {
                User user = new User();
                user.setId(results.getInt("id"));
                user.setUsername(results.getString("username"));
                user.setEmail(results.getString("email"));
                user.setPassword(results.getString("password"));

                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
