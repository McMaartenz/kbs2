import javax.naming.AuthenticationException;
import javax.swing.JOptionPane;

public class Main {
    public static void main(String[] args) {
        Database db = new Database();
        try {
            db.ConnectToDatabase();
        }
        catch (AuthenticationException ae) {
            JOptionPane.showMessageDialog(null, "Authentication fault", "Could not connect to database", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Display display = new Display(db);
        db.disconnectFromDatabase();
    }
}