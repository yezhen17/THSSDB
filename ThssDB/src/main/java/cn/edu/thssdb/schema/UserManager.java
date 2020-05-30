package cn.edu.thssdb.schema;

import cn.edu.thssdb.exception.*;
import cn.edu.thssdb.utils.Global;

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
    HashMap<Long, UserService> onlineUsers;         // 在线用户数据
    private static ReentrantReadWriteLock lock;     // 可重入读写锁
    private static Random random = new Random();    // 随机数发生器
    private Meta meta; // 用户元数据


    /**
     * [method] 构造方法
     */
    public UserManager() {
        users = new ArrayList<>();
        onlineUsers = new HashMap<>();
        lock = new ReentrantReadWriteLock();
        try {
            meta = new Meta(Global.DATA_ROOT_FOLDER, "user.data", true);
            loadUserData();
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    /**
     * [method] 存储用户数据
     */
    public void saveUserData() throws CustomIOException {
        // TODO 决定何时存储
        // TODO 存储外部数据至 users
        ArrayList<String> meta_data = new ArrayList<>();
        for (User user : users) {
            meta_data.add(user.toString(','));
        }
        this.meta.writeToFile(meta_data);
    }

    /**
     * [method] 加载用户数据
     */
    public void loadUserData() throws MetaFileNotFoundException, CustomIOException {
        // TODO 加载外部数据至 users
        ArrayList<String []> meta_data = this.meta.readFromFile();
        try {
            for (String [] row: meta_data) {
                users.add(new User(row[0], row[1], User.stringToPermission(row[2]), row[3]));
            }
        } catch (Exception e) {
            throw new WrongMetaFormatException();
        }
    }

    /**
     * [method] 获取用户
     * @param username {String} 用户名
     * @return {User} 用户对象（无则返回 null）
     */
    public User getUser(String username) {
        try {
            lock.readLock().lock();
            for (User u : users) {
                if (u.username.equals(username)) { return u;  }
            }
            return null;
        } finally {
            lock.readLock().unlock();
        }
    }

    /**
     * [method] 用户服务
     * @param sessionId {long} Session ID
     * @return {UserService} 用户服务，无则返回 null
     */
    public UserService getUserService(long sessionId) {
        try {
            lock.readLock().lock();
            return onlineUsers.getOrDefault(sessionId, null);
        } finally {
            lock.readLock().unlock();
        }
    }

    /**
     * [method] 用户权限
     * @param sessionId {long} Session ID
     * @return {User.Permission} 用户权限（同时用于检查登录状态）
     */
    public User.Permission getPermission(long sessionId) {
        try {
            lock.readLock().lock();
            if (onlineUsers.containsKey(sessionId))
                return onlineUsers.get(sessionId).user.permission;
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
    public void logon(long sessionId, String username, String password, User.Permission permission) {
        // 权限判断
        if (getPermission(sessionId) != User.Permission.ADMIN) {
            throw new PermissionDeniedException();
        }
        // 判断是否已存在该用户
        if (getUser(username) != null) {
            throw new DuplicateUserException();
        }
        // 成功注册
        User user = new User(username, password, permission);
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
     * @exception UserNotExistException 用户不存在
     * @exception WrongPasswordException 错误密码
     */
    public long login(String username, String password) {
        // ROOT 用户检测
        if (username.equals(Global.ROOT_USERNAME) && password.equals(Global.ROOT_PASSWORD)) {
            User user = new User(Global.ROOT_USERNAME, Global.ROOT_PASSWORD, User.Permission.ADMIN);
            long sessionId = getRandomSessionId();
            try {
                lock.writeLock().lock();
                onlineUsers.put(sessionId, new UserService(user));
            } finally {
                lock.writeLock().unlock();
            }
            return sessionId;
        }
        // 检索用户
        User user = getUser(username);
        if (user == null) {
            throw new UserNotExistException();
        }
        // 匹配密码
        if (!password.equals(user.password)) {
            throw new WrongPasswordException();
        }
        // 登录成功 记录在线用户数据
        long sessionId = getRandomSessionId();
        try {
            lock.writeLock().lock();
            onlineUsers.put(sessionId, new UserService(user));
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
            onlineUsers.get(sessionId).disconnect();
            onlineUsers.remove(sessionId);
        } catch (Exception e) {
            return false;
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
