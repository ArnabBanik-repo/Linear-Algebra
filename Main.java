public class Main {
    public static void main(String[] args) {
        Matrix a = new Matrix(4, 4, new float[] { 1, 3, 3, 4, 7, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16 });
        a.display();
        System.out.println("Determinant: " + a.determinant());

    }
}