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
        Matrix A = new Matrix(8, 8,
                new float[] { 5.18840f, 4.84434f, 4.85977f, 5.05376f, 2.27624f, 3.43838f, 8.58283f, 8.33752f, 8.33230f,
                        8.37879f, 4.04831f, 7.99872f, 7.32274f, 2.97995f, 1.38691f, 2.43107f, 7.37580f, 7.52522f,
                        5.92529f, 8.28866f, 0.20732f, 6.64388f, 6.40664f, 7.65851f, 1.66377f, 1.44300f, 7.49860f,
                        0.71681f, 3.21772f, 4.71378f, 8.52711f, 8.18821f, 7.82654f, 4.94234f, 6.31525f, 5.38138f,
                        1.62162f, 7.34643f, 7.22490f, 0.75253f, 2.69273f, 1.53451f, 6.95564f, 4.13109f, 4.05067f,
                        2.28222f, 0.41884f, 3.15392f, 2.68120f, 8.61489f, 5.57958f, 0.75122f, 6.99483f, 5.92925f,
                        2.43982f, 8.95496f, 2.17109f, 8.95178f, 0.86485f, 3.30052f, 7.13957f, 4.91286f, 5.28243f,
                        6.26037f });

        A.display();
        MatrixDecomposition md = new MatrixDecomposition(A);

        Matrix[] decompositions = md.LU();
        System.out.println("U: ");
        decompositions[1].display();
        System.out.println("L: ");
        decompositions[0].display();

    }
}
