package mk.finki.ukim.emt.lab.service.results;

public class PasswordResult {
    private boolean succeded;
    private String message;

    private PasswordResult(String message) {
        this.message = message;
    }

    private PasswordResult(boolean succeded, String message) {
        this.succeded = succeded;
        this.message = message;
    }

    public boolean isSuccessful() {
        return succeded;
    }

    public String getMessage() {
        return message;
    }

    public static PasswordResult success(String message) {
        return new PasswordResult(true, message);
    }

    public static PasswordResult failed(String message) {
        return new PasswordResult(message);
    }
}
