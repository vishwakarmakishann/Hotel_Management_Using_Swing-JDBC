package hotel_management;
public class Session {
    private static int userid;
    private static String username;
    private static String role;

    public static void setSession(int id, String user, String r) {
        userid = id;
        username = user;
        role = r;
    }

    public static String getUsername() {
        return username;
    }

    public static String getRole() {
        return role;
    }

    public static void clearSession() {
        userid = 0;
        username = null;
        role = null;
    }
}
