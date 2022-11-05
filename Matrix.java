public class Matrix {
    private float[][] a;
    private int rows, cols;

    Matrix() {
        rows = 3;
        cols = 3;
        a = new float[rows][cols];
    }

    Matrix(int n) {
        rows = n;
        cols = n;
        a = new float[rows][cols];
    }

    Matrix(int n, boolean identity) {
        rows = n;
        cols = n;
        a = new float[rows][cols];
        for (int i = 0; i < rows; i++)
            a[i][i] = 1;
    }

    Matrix(int n, int m) {
        rows = n;
        cols = m;
        a = new float[rows][cols];
    }

    Matrix(int n, int m, boolean identity) {
        rows = n;
        cols = m;
        a = new float[rows][cols];
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                if (i == j)
                    a[i][j] = 1;
    }

    Matrix(int rows, int cols, float[] values) {
        if (rows * cols != values.length)
            throw new MatrixException("Dimension and number of values do not match!");

        this.rows = rows;
        this.cols = cols;
        this.a = new float[rows][cols];
        int k = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++)
                a[i][j] = values[k++];
        }
    }

    Matrix(float[][] a) {
        rows = a.length;
        cols = a[0].length;
        this.a = new float[rows][cols];
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                this.a[i][j] = a[i][j];
    }

    Matrix(Matrix A) {
        rows = A.rows;
        cols = A.cols;
        this.a = new float[rows][cols];
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                this.a[i][j] = A.a[i][j];
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public float[][] getArray() {
        return a;
    }

    public static Matrix add(Matrix matrix1, Matrix matrix2) {
        if (matrix1.rows != matrix2.rows || matrix1.cols != matrix2.cols)
            throw new MatrixException("Incorrect matrix dimensions!");

        Matrix result = new Matrix(matrix1.a);
        for (int i = 0; i < matrix1.rows; i++)
            for (int j = 0; j < matrix1.cols; j++)
                result.a[i][j] += matrix2.a[i][j];
        return result;
    }

    public static Matrix sub(Matrix matrix1, Matrix matrix2) {
        if (matrix1.rows != matrix2.rows || matrix1.cols != matrix2.cols)
            throw new MatrixException("Incorrect matrix dimensions!");

        Matrix result = new Matrix(matrix1.a);
        for (int i = 0; i < matrix1.rows; i++)
            for (int j = 0; j < matrix1.cols; j++)
                result.a[i][j] -= matrix2.a[i][j];
        return result;
    }

    public static Matrix mul(Matrix matrix1, Matrix matrix2) {
        if (matrix1.cols != matrix2.rows)
            throw new MatrixException("Multiplication not possible!");

        Matrix result = new Matrix(matrix1.rows, matrix2.cols);
        for (int i = 0; i < matrix2.cols; i++) {
            for (int j = 0; j < matrix1.rows; j++) {

                for (int k = 0; k < matrix1.cols; k++)
                    result.a[j][i] += matrix1.a[j][k] * matrix2.a[k][i];
            }
        }
        return result;
    }

    public static float[] melt(Matrix matrix) {
        int k = 0;
        float[] result = new float[matrix.rows * matrix.cols];
        for (int i = 0; i < matrix.rows; i++)
            for (int j = 0; j < matrix.cols; j++)
                result[k++] = matrix.a[i][j];
        return result;
    }

    public static Matrix reshape(Matrix matrix, int rows, int cols) {
        if (matrix.rows * matrix.cols != rows * cols)
            throw new MatrixException("Reshape not possible!");

        int k = 0;
        Matrix result = new Matrix(rows, cols);
        float[] values = melt(matrix);
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                result.a[i][j] = values[k++];
        return result;
    }

    public static boolean search(Matrix matrix, float n) {
        for (int i = 0; i < matrix.rows; i++)
            for (int j = 0; j < matrix.cols; j++)
                if (matrix.a[i][j] == n)
                    return true;
        return false;
    }

    public void populate(float[] values) {
        if (rows * cols != values.length)
            throw new MatrixException("Dimensions and number of values do not match!");

        int k = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++)
                a[i][j] = values[k++];
        }
    }

    public void display() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (a[i][j] < 0)
                    System.out.printf(" %.2f", a[i][j]);
                else
                    System.out.printf("  %.2f", a[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }

    public Matrix transpose() {
        Matrix transpose = new Matrix(cols, rows);
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                transpose.a[j][i] = a[i][j];
        return transpose;
    }

    public Matrix rref() {
        Matrix reduced = new Matrix(a);
        int last = rows - 1;
        for (int i = 0; i < rows; i++) {
            int pivot = -1;
            for (int j = 0; j < cols; j++) {
                if (reduced.a[i][j] != 0) {
                    pivot = j;
                    break;
                }
            }
            if (pivot == -1) {
                if (last < i)
                    continue;
                float[] t = reduced.a[i];
                reduced.a[i] = reduced.a[last];
                reduced.a[last] = t;
                last--;
                i--;
                continue;
            }
            for (int j = 0; j < cols; j++) {
                reduced.a[i][j] /= reduced.a[i][pivot];
            }
            for (int j = 0; j < rows; j++) {
                if (j == i)
                    continue;
                for (int k = 0; k < cols; k++) {
                    reduced.a[j][k] -= reduced.a[i][k] * reduced.a[j][pivot];
                }
            }
        }
        return reduced;
    }

    public int rank() {
        int rank = 0;
        Matrix temp = this.inverse();
        for (int i = 0; i < temp.rows; i++) {
            for (int j = 0; j < temp.cols; j++) {
                if (temp.a[i][j] != 0) {
                    rank++;
                    break;
                }
            }
        }
        return rank;
    }

    public Matrix inverse() {
        Matrix copy = new Matrix(a);
        Matrix inv = new Matrix(rows, cols, true);

        for (int i = 0; i < rows; i++) {
            int pivot = -1;
            for (int j = 0; j < cols; j++)
                if (copy.a[i][j] != 0) {
                    pivot = j;
                    break;
                }
            if (pivot == -1)
                throw new MatrixException("Inverse doesn't exist!");

            for (int j = 0; j < cols; j++) {
                inv.a[i][j] /= copy.a[i][pivot];
                copy.a[i][j] /= copy.a[i][pivot];
            }

            for (int j = 0; j < rows; j++) {
                if (j == i)
                    continue;
                for (int k = 0; k < cols; k++) {
                    inv.a[j][k] -= inv.a[i][k] * copy.a[j][pivot];
                    copy.a[j][k] -= copy.a[i][k] * copy.a[j][pivot];
                }
            }
        }
        return inv;
    }

    public float[] rowSums() {
        float[] result = new float[rows];
        int k = 0;
        for (int i = 0; i < rows; i++) {
            int sum = 0;
            for (int j = 0; j < cols; j++)
                sum += a[i][j];
            result[k++] = sum;
        }
        return result;
    }

    public float[] colSums() {
        float[] result = new float[cols];
        int k = 0;
        for (int i = 0; i < cols; i++) {
            int sum = 0;
            for (int j = 0; j < rows; j++)
                sum += a[i][j];
            result[k++] = sum;
        }
        return result;
    }

    public float trace() {
        float t = 0;
        for (int i = 0; i < rows; i++)
            if (i < cols)
                t += a[i][i];
        return t;
    }

    public Matrix lowerTriangle() {
        Matrix reduced = new Matrix(a);
        int last = rows - 1;
        for (int i = 0; i < rows - 1; i++) {
            int pivot = -1;
            for (int j = 0; j < cols; j++) {
                if (reduced.a[i][j] != 0) {
                    pivot = j;
                    break;
                }
            }
            if (pivot == -1) {
                if (last < i)
                    continue;
                float[] t = reduced.a[i];
                reduced.a[i] = reduced.a[last];
                reduced.a[last] = t;
                last--;
                i--;
                continue;
            }
            for (int j = 0; j < cols; j++) {
                reduced.a[i][j] /= reduced.a[i][pivot];
            }
            for (int j = i + 1; j < rows; j++) {
                for (int k = 0; k < cols; k++) {
                    reduced.a[j][k] -= reduced.a[i][k] * reduced.a[j][pivot];
                }
            }
        }
        return reduced;
    }

    public static Matrix subMatrix(Matrix matrix, int startRow, int endRow, int startCol, int endCol, int avoidCol) {
        Matrix subMatrix = avoidCol == -1 ? new Matrix(endRow - startRow + 1, endCol - startCol + 1)
                : new Matrix(endRow - startRow + 1, endCol - startCol);
        for (int i = startRow; i <= endRow; i++) {
            int k = 0;
            for (int j = startCol; j <= endCol; j++) {
                if (j == avoidCol) {
                    k = 1;
                    continue;
                }
                if (avoidCol == -1)
                    subMatrix.a[i - startRow][j - startCol] = matrix.a[i][j];
                else
                    subMatrix.a[i - startRow][j - startCol - k] = matrix.a[i][j];
            }
        }
        return subMatrix;
    }

    public static Matrix subMatrix(Matrix matrix, int avoidRow, int avoidCol) {
        Matrix subMatrix = new Matrix(matrix.rows - 1, matrix.cols - 1);
        int r = 0;
        for (int i = 0; i < matrix.rows; i++) {
            if (i == avoidRow) {
                r = 1;
                continue;
            }
            int c = 0;
            for (int j = 0; j < matrix.cols; j++) {
                if (j == avoidCol) {
                    c = 1;
                    continue;
                }
                subMatrix.a[i - r][j - c] = matrix.a[i][j];
            }
        }
        return subMatrix;
    }

    public float determinant() {
        if (rows != cols)
            throw new MatrixException("Determinant doesn't exist");

        return temp(this);
    }

    private float temp(Matrix test) {
        if (test.rows == 2)
            return test.a[0][0] * test.a[1][1] - test.a[1][0] * test.a[0][1];
        int x = 1;
        float det = 0.0f;
        for (int i = 0; i < test.cols; i++) {
            det += x * test.a[0][i] * temp(subMatrix(test, 0, i));
            x *= -1;
        }

        return det;
    }
}
