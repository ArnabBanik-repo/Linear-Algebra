public class MatrixDecomposition {
    private Matrix A;

    MatrixDecomposition() {
        A = new Matrix(2, 2, new float[] { 2, 3, 5, 4 });
    }

    MatrixDecomposition(Matrix A) {
        this.A = A;
    }

    public Matrix[] LU() {
        Matrix L = new Matrix(A.getRows(), A.getRows());
        Matrix U = new Matrix(A.getRows(), A.getCols());

        return new Matrix[] { L, U };
    }
}
