public class MatrixException extends RuntimeException {
    String message;

    MatrixException() {
        message = "Some error occurred!";
    }

    MatrixException(String msg) {
        message = msg;
    }

    public String toString() {
        return message;
    }
}
