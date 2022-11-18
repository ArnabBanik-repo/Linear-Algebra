public class MatrixDecomposition {
    private Matrix A;

    MatrixDecomposition() {
        A = new Matrix(2, 2, new float[] { 2, 3, 5, 4 });
    }

    MatrixDecomposition(Matrix A) {
        this.A = A;
    }

    public Matrix[] LU() {
        int temp = Math.min(A.getCols() - 1, A.getRows() - 1);
        for (int i = 1; i < temp; i++)
            if (Matrix.subMatrix(A, 0, i, 0, i, -1).determinant() == 0)
                new MatrixException("The LU decomposition doesn't exist");

        Matrix L = new Matrix(A.getRows(), A.getRows(), true);
        Matrix U = new Matrix(A);
        for (int i = 0; i < U.getRows(); i++) {
            int pivot = -1;
            for (int j = 0; j < U.getCols(); j++) {
                if (U.getArray()[i][j] != 0) {
                    pivot = j;
                    break;
                }
            }
            if (pivot == -1)
                continue;
            temp = Math.max(U.getCols(), L.getCols());
            for (int j = i + 1; j < U.getRows(); j++) {
                float p = U.getArray()[j][pivot];
                for (int k = i; k < temp; k++) {
                    if (k < U.getCols()) {
                        U.getArray()[j][k] -= U.getArray()[i][k] * p / U.getArray()[i][pivot];
                        if (k == i)
                            L.getArray()[j][k] = p / U.getArray()[i][pivot];
                    }
                }
            }

        }

        return new Matrix[] { L, U };
    }

    public static void main(String[] args) {
        Matrix A = new Matrix(3, 3, new float[] { 1, 2, 3, 4, 5, 6, 1, 4, 9 });
        A.display();
        MatrixDecomposition md = new MatrixDecomposition(A);

        Matrix[] decompositions = md.LU();
        System.out.println("U: ");
        decompositions[1].display();
        System.out.println("L: ");
        decompositions[0].display();

    }
}
