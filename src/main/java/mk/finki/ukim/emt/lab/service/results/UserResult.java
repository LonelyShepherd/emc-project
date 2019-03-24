package mk.finki.ukim.emt.lab.service.results;

public class UserResult {
    private boolean succeded;
    private String message;

    private UserResult(String message) {
        this.message = message;
    }

    private UserResult(boolean succeded, String message) {
        this.succeded = succeded;
        this.message = message;
    }

    public boolean isSuccessful() {
        return succeded;
    }

    public String getMessage() {
        return message;
    }

    public static UserResult success(String message) {
        return new UserResult(true, message);
    }

    public static UserResult failed(String message) {
        return new UserResult(message);
    }
}
