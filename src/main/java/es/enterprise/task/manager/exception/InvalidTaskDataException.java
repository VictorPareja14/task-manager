package es.enterprise.task.manager.exception;

public class InvalidTaskDataException extends RuntimeException {
  public InvalidTaskDataException(String message) {
    super(message);
  }
}
