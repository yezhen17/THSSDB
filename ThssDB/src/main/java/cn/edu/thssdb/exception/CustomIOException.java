package cn.edu.thssdb.exception;

import java.io.IOException;

/***************
 * [Exception] IO过程出现错误
 ***************/
public class CustomIOException extends IOException {
    @Override
    public String getMessage() {
        return "Exception: An IOError occurred";
    }
}