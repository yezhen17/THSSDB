package cn.edu.thssdb.exception;


/***************
 * [Exception] 错误用户名或密码
 ***************/
public class WrongUsernameOrPasswordException extends RuntimeException{
    @Override
    public String getMessage() {
        return "Exception: Wrong username or password!";
    }
}