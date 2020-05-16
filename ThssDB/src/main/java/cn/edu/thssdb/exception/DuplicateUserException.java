package cn.edu.thssdb.exception;


/***************
 * [Exception] 重复用户
 ***************/
public class DuplicateUserException extends RuntimeException {
    @Override
    public String getMessage() {
        return "Exception: logon caused duplicated user!";
    }
}