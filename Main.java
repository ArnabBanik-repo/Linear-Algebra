public class Main {
	public static void main(String[] args) {
        Matrix a = new Matrix(4, 4, new float[] { 1, 3, 3, 4, 7, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16 });

		System.out.println("A: ");
    	a.display();

		MatrixDecomposition md = new MatrixDecomposition(a);
		Matrix[] decompositions = md.LU();
		System.out.println("L: ");
		decompositions[0].display();
		System.out.println("U: ");
		decompositions[1].display();
    }
}