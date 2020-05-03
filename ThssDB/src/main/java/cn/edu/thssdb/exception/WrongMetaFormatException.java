package cn.edu.thssdb.exception;

/***************
 * [Exception] 元数据格式不正确
 ***************/
public class WrongMetaFormatException extends RuntimeException{
    @Override
    public String getMessage() {
        return "Exception: Meta data format error!";
    }
}

