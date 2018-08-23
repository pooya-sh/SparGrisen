package se.spargrisen.spargrisen.repository;

public class SpargrisenRepositoryExeption extends RuntimeException {

    public SpargrisenRepositoryExeption() {
    }

    public SpargrisenRepositoryExeption(String message) {
        super(message);
    }

    public SpargrisenRepositoryExeption(String message, Throwable cause) {
        super(message, cause);
    }

    public SpargrisenRepositoryExeption(Throwable cause) {
        super(cause);
    }

    public SpargrisenRepositoryExeption(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

