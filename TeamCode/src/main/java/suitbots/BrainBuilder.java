package suitbots;

import android.os.Environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class BrainBuilder {
    public static final File ROOT_FOLDER = Environment.getExternalStorageDirectory();
    public static final File FIRST_FOLDER = new File(ROOT_FOLDER + "/FIRST/");

    private static double[] readDoubleArray(final String s) {
        final String[] ss = readRow(s);
        final double[] ret = new double[ss.length];
        for (int i = 0; i < ss.length; ++i) {
            ret[i] = Double.parseDouble(ss[i]);
        }
        return ret;
    }

    private static double[][] readDoubleArrayArray(final String s) {
        final String[][] raw = readRows(s);
        final double[][] ret = new double[raw.length][];
        for (int i = 0; i < raw.length; ++i) {
            final String[] row = raw[i];
            ret[i] = new double[row.length];
            for(int j = 0; j < row.length; ++j) {
                ret[i][j] = Double.parseDouble(row[j]);
            }
        }
        return ret;
    }

    public static int[] readIntArray(final String s) {
        final String[] ss = readRow(s);
        final int[] ret = new int[ss.length];
        for (int i = 0; i < ss.length; ++i) {
            ret[i] = Integer.parseInt(ss[i]);
        }
        return ret;
    }

    public static String[][] readRows(final String s) {
        final String[] ss = s.split("\\}\\s*,\\s*\\{");
        final String[][] ret = new String[ss.length][];
        for (int i = 0; i < ss.length; ++i) {
            ret[i] = readRow(ss[i]);
        }
        return ret;
    }

    public static String[] readRow(final String s) {
        final String[] ss = s.replaceAll("[\\{\\}\\s]", "").split(",");
        return ss;
    }

    public static Brain makeBrain() {
        final File fp = new File(FIRST_FOLDER, "brain.dat");
        try {
            final BufferedReader br = new BufferedReader(new FileReader(fp));
            final int nClasses = Integer.parseInt(br.readLine().trim());
            final int nRows = Integer.parseInt(br.readLine().trim());
            final String kernel = br.readLine().trim();
            final double gamma = Double.parseDouble(br.readLine().trim());
            final double coef0 = Double.parseDouble(br.readLine().trim());
            final double degree = Double.parseDouble(br.readLine().trim());
            final double[][] vectors = readDoubleArrayArray(br.readLine().trim());
            final double[][] coeffs = readDoubleArrayArray(br.readLine().trim());
            final double[] intercepts = readDoubleArray(br.readLine().trim());
            final int[] weights = readIntArray(br.readLine().trim());
            return new Brain(nClasses, nRows, vectors, coeffs, intercepts, weights,
                    kernel, gamma, coef0, degree);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
