package cn.edu.thssdb.exception;

/***************
 * [Exception] 错误密码
 ***************/
public class WrongPasswordException extends RuntimeException{
    @Override
    public String getMessage() {
        return "Exception: Wrong password!";
    }
}