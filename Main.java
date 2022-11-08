public class Main {
    public static void main(String[] args) {
        Matrix a = new Matrix(4, 4, new float[] { 1, 3, 3, 4, 7, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16 });

        System.out.println("A: ");
        a.display();

        Matrix inv = a.inverse();
        System.out.println("Inverse:");
        inv.display();

        System.out.println("A.A-1: ");
        Matrix.mul(a, inv).display();
    }
}