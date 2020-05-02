package cn.edu.thssdb.exception;

/***************
 * [Exception] 重复数据库
 ***************/
public class DuplicateDatabaseException extends RuntimeException {
    @Override
    public String getMessage() {
        return "Exception: creation caused duplicated database!";
    }
}
