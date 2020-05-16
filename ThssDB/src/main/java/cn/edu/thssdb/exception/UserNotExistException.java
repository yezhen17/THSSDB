package cn.edu.thssdb.exception;

/***************
 * [Exception] 用户不存在
 ***************/
public class UserNotExistException extends RuntimeException{
    @Override
    public String getMessage() {
        return "Exception: user doesn't exist!";
    }
}
