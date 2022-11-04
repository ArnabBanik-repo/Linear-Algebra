public class EquationSolver {
    private Matrix A;
    private Matrix B;

    EquationSolver(Matrix A, Matrix B) {
        this.A = A;
        this.B = B;
    }

    public Matrix solve() {
        return Matrix.mul(A.inverse(), B);
    }

    public static void main(String[] args) {
        Matrix A = new Matrix(3, 3, new float[] { 2, 3, 4, 3, 2, 1, 1, 2, 1 });
        Matrix B = new Matrix(3, 1, new float[] { 1, 2, 3 });
        EquationSolver es = new EquationSolver(A, B);
        es.solve().display();
    }
}