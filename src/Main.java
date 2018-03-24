import org.mindrot.jbcrypt.BCrypt;

public class Main {

    public static void main(String[] args) {
        //register("jacob", "jacob@brunson.me", "ilovecs3354");
        //register("sally", "sally@gmail.com", "12345");
        //register("XxCoolKidxX", "coolkid04@yahoo.com", "p@ssw0rd");
        //register("donglover", "Donald.Glover@gmail.com", "redbone");
        //register("realDonaldTrump", "donald.trump@us.gov", "FAKENEWS");

        login("jacob", "ilovecs3354");
    }

    public static void register(String username, String email, String password) {
        DatabaseManager db = DatabaseManager.getInstance();

        System.out.println("Instantiating user");

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);

        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        user.setPassword(hashedPassword);

        System.out.println("Saving user to database");

        db.save(user);
    }

    public static boolean login(String username, String password) {
        DatabaseManager db = DatabaseManager.getInstance();

        User user = db.findUser(username);

        if (user != null) {
            if (BCrypt.checkpw(password, user.getPassword())) {
                System.out.println("Login successful!");
                return true;
            } else {
                System.out.println("Incorrect password");
            }
        } else {
            System.out.println("Username not found");
        }

        return false;
    }
}