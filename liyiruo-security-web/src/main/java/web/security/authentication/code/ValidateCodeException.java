package web.security.authentication.code;

/**
 * 异常处理类
 * @author liyiruo
 */
public class ValidateCodeException extends Throwable {
    public ValidateCodeException(String msg,Throwable t) {
        super(msg,t);
    }
    public ValidateCodeException(String msg) {
        super(msg);
    }
}
