package cn.edu.thssdb.exception;

/***************
 * [Exception] 权限拒绝
 ***************/
public class PermissionDeniedException extends RuntimeException {
    @Override
    public String getMessage() {
        return "Exception: permission denied!";
    }
}