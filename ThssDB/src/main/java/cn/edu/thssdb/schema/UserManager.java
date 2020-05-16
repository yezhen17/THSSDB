package cn.edu.thssdb.schema;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/***************
 * [class] 用户管理器
 * [note] 单例模式（Holder）
 ***************/
public class UserManager {
    ArrayList<User> users;                          // 用户数据
    HashMap<Long, User> onlineUsers;                // 在线用户数据
    private static ReentrantReadWriteLock lock;     // 可重入读写锁

    /**
     * [method] 构造方法
     */
    public UserManager() {
        // 读入 TODO
    }

    /**
     * [method] 存储用户数据
     */
    public void saveUserData() {
        // TODO
    }

    /**
     * [method] 加载用户数据
     */
    public void loadUserData() {
        // TODO
    }

    /**
     * [method] 用户状态
     * @param sessionId {long} Session ID
     * @return {User.Permission} 用权限表示的登录状态
     */
    public User.Permission isOnline (long sessionId) {
        // TODO
        return User.Permission.NONE;
    }

    /**
     * [method] 用户注册
     * @return {boolean} 是否注册成功（已存在该用户则注册失败）
     */
    public boolean logon (String username, String password, User.Permission permission, String database) {
        // TODO
        User user = new User(username, password, permission, database);
        return true;
    }

    /**
     * [method] 用户登录
     * @param username {String} 用户名
     * @param password {String} 密码
     * @return {long} 登录成功则返回 sessionId；否则返回 -1
     */
    public long login(String username, String password) {
        // TODO
        return -1;
    }

    /**
     * [method] 用户注销
     * @param sessionId {long} Session ID
     * @return {boolean} 是否注销成功
     */
    public boolean logout(long sessionId) {
        // TODO
        return false;
    }

    /**
     * [method] 生成随机SessionId
     * @return {long} 随机生成的SessionId
     */
    public static long getRandomSessionId() {
        return 0;
    }

    /**
     * [method] 获取用户管理器实例
     * @return 用户管理器实例
     */
    public static UserManager getInstance() {
        return UserManager.UserManagerHolder.INSTANCE;
    }

    /**
     * [class] Holder类
     */
    private static class UserManagerHolder {
        private static final UserManager INSTANCE = new UserManager();
    }
}
