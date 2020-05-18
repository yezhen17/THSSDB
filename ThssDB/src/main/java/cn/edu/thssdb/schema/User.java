package cn.edu.thssdb.schema;

/***************
 * [class] 用户
 ***************/
public class User {
    /**
     * [enum] 权限
     */
    public enum Permission {
        NONE,       // 无
        ADMIN,      // 管理员
        USER,       // 用户

    }

    public String username;            // 用户名
    public String password;            // 密码
    public Permission permission;      // 权限
    public String database;            // 数据库

    public User(String username, String password, Permission permission) {
        this.username = username;
        this.password = password;
        this.permission = permission;
        this.database = "";
    }

    public User(String username, String password, Permission permission, String database) {
        this.username = username;
        this.password = password;
        this.permission = permission;
        this.database = database;
    }

    public static Permission stringToPermission (String s) {
        if (s.equals("ADMIN")) return Permission.ADMIN;
        else if (s.equals("USER")) return Permission.USER;
        else return Permission.NONE;
    }

    public static String permissionToString (Permission p) {
        if (p == Permission.ADMIN) return "ADMIN";
        else if (p == Permission.USER) return "USER";
        else return "NONE";
    }

    public String toString(char delimiter) {
        return username + delimiter + password + delimiter + permissionToString(permission) + delimiter + database;
    }
}
