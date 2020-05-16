package cn.edu.thssdb.schema;

import cn.edu.thssdb.exception.DatabaseNotExistException;
import cn.edu.thssdb.exception.DuplicateUserException;
import cn.edu.thssdb.exception.PermissionDeniedException;
import cn.edu.thssdb.exception.WrongUsernameOrPasswordException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/***************
 * [class] 用户管理器
 * [note] 单例模式（Holder）
 ***************/
public class UserManager {
    ArrayList<User> users;                          // 用户数据
    HashMap<Long, User> onlineUsers;                // 在线用户数据
    private static ReentrantReadWriteLock lock;     // 可重入读写锁
    private static Random random = new Random();    // 随机数发生器


    /**
     * [method] 构造方法
     */
    public UserManager() {
        onlineUsers = new HashMap<>();
        loadUserData();
    }

    /**
     * [method] 存储用户数据
     */
    public void saveUserData() {
        // TODO 决定何时存储
        // TODO 存储外部数据至 users
    }

    /**
     * [method] 加载用户数据
     */
    public void loadUserData() {
        // TODO 加载外部数据至 users
    }

    /**
     * [method] 用户状态
     * @param sessionId {long} Session ID
     * @return {User.Permission} 用权限表示的登录状态
     */
    public User.Permission checkOnline (long sessionId) {
        try {
            lock.readLock().lock();
            if (onlineUsers.containsKey(sessionId))
                return onlineUsers.get(sessionId).permission;
            else
                return User.Permission.NONE;
        } finally {
            lock.readLock().unlock();
        }
    }

    /**
     * [method] 用户注册
     * @exception DuplicateUserException 重复用户
     * @exception PermissionDeniedException 权限拒绝
     */
    public void logon (long sessionId, String username, String password, User.Permission permission, String database) {
        // 权限判断
        if (checkOnline(sessionId) != User.Permission.ADMIN) {
            throw new PermissionDeniedException();
        }

        // 判断是否已存在该用户
        try {
            lock.readLock().lock();
            for (User u : users) {
                if (u.username.equals(username)) {
                    throw new DuplicateUserException();
                }
            }
        } finally {
            lock.readLock().unlock();
        }
        // 成功注册
        User user = new User(username, password, permission, database);
        try {
            lock.writeLock().lock();
            users.add(user);
        } finally {
            lock.writeLock().unlock();
        }
    }

    /**
     * [method] 用户登录
     * @param username {String} 用户名
     * @param password {String} 密码
     * @return {long} 是否登录成功  登录成功则返回 sessionId
     * @exception WrongUsernameOrPasswordException 错误用户名或密码
     */
    public long login(String username, String password) {
        // 匹配用户名与密码
        User user = null;
        try {
            lock.readLock().lock();
            for (User u : users) {
                if (u.username.equals(username) && u.password.equals(password)) {
                    user = u;
                    break;
                }
            }
        } finally {
            lock.readLock().unlock();
        }
        if (user == null) {
            throw new WrongUsernameOrPasswordException();
        }
        // 登录成功 记录在线用户数据
        long sessionId = getRandomSessionId();
        try {
            lock.writeLock().lock();
            onlineUsers.put(sessionId, user);
        } finally {
            lock.writeLock().unlock();
        }
        return sessionId;
    }

    /**
     * [method] 用户注销
     * @param sessionId {long} Session ID
     * @return {boolean} 是否注销成功
     */
    public boolean logout(long sessionId) {
        // 判断是否登录
        try {
            lock.readLock().lock();
            if (!onlineUsers.containsKey(sessionId))
                return false;
        } finally {
            lock.readLock().unlock();
        }
        // 注销成功 删除在线用户数据
        try {
            lock.writeLock().lock();
            onlineUsers.remove(sessionId);
        } finally {
            lock.writeLock().unlock();
        }
        return true;
    }

    /**
     * [method] 生成随机SessionId
     * @return {long} 随机生成的SessionId
     */
    public long getRandomSessionId() {
        // 循环生成唯一 Session Id
        while (true) {
            long r = random.nextLong();
            try {
                lock.readLock().lock();
                if (!onlineUsers.containsKey(r))
                    return r;
            } finally {
                lock.readLock().unlock();
            }
        }
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
