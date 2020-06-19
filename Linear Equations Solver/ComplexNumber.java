package solver;

import static java.lang.Integer.max;

public class ComplexNumber implements Comparable<ComplexNumber> {
    private double real;
    private double img;

    private ComplexNumber(Double real, Double img) {
        this.real = real;
        this.img = img;
    }

    public ComplexNumber(String number) {
        setNumber(number);
    }

    public void setNumber(String number) {
        number = number.trim();
        System.out.println(number);

        int idx = number.indexOf("i");
        if (idx == -1) { // pure real
            real = Double.parseDouble(number);
            img = 0;
        } else { // complex
            int sCount = 0;
            int fIdx = -1;
            int sIdx = -1;
            boolean pImaginary = false;
            for (int i = 0; i < number.length(); ++i) {
                if (number.charAt(i) == '-' || number.charAt(i) == '+') {
                    ++sCount;
                    if (sCount == 1) {
                        fIdx = i;
                    } else if (sCount == 2) {
                        sIdx = i;
                        break;
                    }
                }
            }
            if (sCount == 0 || (sCount == 1 && fIdx == 0)) {
                pImaginary = true;
            }
            if (pImaginary) { // pure Imaginary
                real = 0;
                if (number.equals("i")) {
                    img = 1;
                } else if (number.equals("-i")) {
                    img = -1;
                } else {
                    img = Double.parseDouble(number.substring(0, number.length() - 1));
                }
            } else {
                int split;
                if (sCount == 1) split = fIdx;
                else split = sIdx;

                real = Double.parseDouble(number.substring(0, split));
                if (number.substring(split).equals("+i")) {
                    img = 1;
                } else if (number.substring(split).equals("-i")) {
                    img = -1;
                } else {
                    img = Double.parseDouble(number.substring(split, number.length() - 1));
                }
            }
        }
    }

    public ComplexNumber add(ComplexNumber other) {
        Double newReal = this.real + other.real;
        Double newImg = this.img + other.img;

        return new ComplexNumber(newReal, newImg);
    }

    public ComplexNumber subtract(ComplexNumber other) {
        Double newReal = this.real - other.real;
        Double newImg = this.img - other.img;

        return new ComplexNumber(newReal, newImg);
    }

    public ComplexNumber multiply(ComplexNumber other) {
        Double newReal = (this.real * other.real) - (this.img * other.img);
        Double newImg = (this.real * other.img) + (other.real * this.img);

        return new ComplexNumber(newReal, newImg);
    }

    public ComplexNumber divide(ComplexNumber other) {

        var conjugate = makeConjugate(other);
        var numerator = multiply(conjugate);
        var denominator = other.multiply(conjugate);

        Double newReal = numerator.real / denominator.real;
        Double newImg = numerator.img / denominator.real;

        return new ComplexNumber(newReal, newImg);
    }

    @Override
    public int compareTo(ComplexNumber other) {
        if (getModulus() < other.getModulus()) {
            return -1;
        } else if (getModulus() > other.getModulus()) {
            return 1;
        } else {
            return 0;
        }
    }

    private Double getModulus() {
        return Math.sqrt((real * real) + (img * img));
    }

    public boolean isZero() {
        if (real == 0 && img == 0) {
            return true;
        } else return false;
    }

    public String getNumber() {
        if (real == 0 && img == 0) {
            return "0.00";
        }
        String number = "";
        if (real != 0) {
            number += String.format("%.4f", real);
        }
        if (img != 0) {
            if (img > 0 && real != 0) {
                number += "+";
            }
            if (img == 1) {
                number += "i";
            } else if (img == -1) {
                number += "-i";
            } else {
                number += String.format("%.4fi", img);
            }
        }
        return number;
    }

    private ComplexNumber makeConjugate(ComplexNumber number) {
        return new ComplexNumber(number.real, -1*number.img);
    }
}
