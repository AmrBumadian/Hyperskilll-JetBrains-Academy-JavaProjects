package solver;

import java.io.*;

import static java.lang.Math.abs;

public class Main {
    public static void main(String[] args) throws IOException {

        FileReader fileReader = new FileReader(args[1]);
        File outputFile = new File(args[3]);
        PrintWriter printWriter = new PrintWriter(outputFile);

        //System.out.print("Enter the Square Matrix dimension: ");
        char[] input = new char[10000];
        fileReader.read(input);
        fileReader.close();
        String s = new String(input);
        String[] val = s.split("[\\s]");

        for (int i=0;i<val.length;++i) {
            System.out.print(val[i] + " ");
        }
        System.out.println();

        int variablesNum = Integer.parseInt(val[0], 10);
        int equationsNum = Integer.parseInt(val[1], 10);

        int idx = 2;

        var matrix = new ComplexNumber[equationsNum][variablesNum + 1];
        //System.out.println("Enter the Matrix row wise: ");

        for (int i = 0; i < equationsNum; ++i) {
            for (int j = 0; j < variablesNum + 1; ++j) {
                matrix[i][j] = new ComplexNumber(val[idx]);
                ++idx;
            }
        }

        printMatrix(matrix, equationsNum, variablesNum + 1);

        GaussElimination(matrix, equationsNum, variablesNum + 1);

        printMatrix(matrix, equationsNum, variablesNum + 1);

        var solution = new ComplexNumber[variablesNum];
        double answer = backSub(matrix, equationsNum, variablesNum, solution);
        if (Double.isNaN(answer)) {
            System.out.println("No solutions");
            printWriter.write("No solutions");
        } else if (Double.isInfinite(answer)) {
            System.out.println("Infinitely many solutions");
            printWriter.write("Infinitely many solutions");

        } else {
            //System.out.print("The solution is: (");
            for (int i = 0; i < variablesNum; ++i) {
                //System.out.println("*" + solution[i].getNumber());
                //System.out.print(", ");
                printWriter.write(solution[i].getNumber() + "\n");
            }
            //System.out.println(")");
        }
        printWriter.close();
    }

    static int GaussElimination(ComplexNumber mat[][], int equationsNum, int variablesNum) {
        for (int k = 0; k < equationsNum; ++k) {
            int iMax = k;
            ComplexNumber vMax = mat[iMax][k];

            for (int i = k + 1; i < equationsNum; ++i) {
                if (mat[i][k].compareTo(vMax) == 1) {
                    vMax = mat[i][k];
                    iMax = i;
                }
            }
            if (mat[k][iMax].isZero())
                return k;

            if (iMax != k)
                swapRow(mat, k, iMax, variablesNum);

            for (int i = k + 1; i < equationsNum; ++i) {
                ComplexNumber f = mat[i][k].divide(mat[k][k]);

                if (f.isZero()) {
                    continue;
                }
                for (int j = k + 1; j < variablesNum; ++j) {
                    mat[i][j] = mat[i][j].subtract(mat[k][j].multiply(f));
                }
                //System.out.printf("%.1f * R%d + R%d -> R%d\n", f, k + 1, i + 1, i + 1);
                mat[i][k].setNumber("0");
            }
        }
        return 1;
    }

    static void swapRow(ComplexNumber mat[][], int i, int j, int columns) {
        //System.out.printf("R%d -> R%d\n", i + 1, j + 1);
        for (int k = 0; k < columns; ++k) {
            ComplexNumber temp = mat[i][k];
            mat[i][k] = mat[j][k];
            mat[j][k] = temp;
        }
    }

    static void printMatrix(ComplexNumber mat[][], int rows, int columns) {

        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < columns; ++j) {
                System.out.print("         " + mat[i][j].getNumber());
            }
            System.out.println();
        }
        System.out.println();
    }

    static double backSub(ComplexNumber mat[][], int equationsNum, int variablesNum, ComplexNumber[] answer) {
        int ans = 0;
        int rank = equationsNum;
        for (int i = 0; i < equationsNum; ++i) {
            int count = 0;
            for (int j = 0; j < variablesNum + 1; ++j) {
                if (mat[i][j].isZero()) {
                    ++count;
                }
            }
            if (count == variablesNum + 1) {
                ans = 1;
                --rank;
            } else if (count == variablesNum && !mat[i][variablesNum].isZero()) {
                ans = -1;
            }
        }
        if (ans == -1) {
            return Double.NaN;
        } else if (rank < variablesNum) {
            return Double.POSITIVE_INFINITY;
        } else {
            for (int i = variablesNum - 1; i >= 0; --i) {
                answer[i] = mat[i][variablesNum];
                for (int j = i + 1; j < variablesNum; ++j) {
                    answer[i] = answer[i].subtract(mat[i][j].multiply(answer[j]));
                }
                answer[i] = answer[i].divide(mat[i][i]);
            }
        }
        return 1;
    }
}