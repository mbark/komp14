/**
 * Find the greatest product of five consecutive digits in the 50-digit
 * number.
 *
 * 73167176531330624919225119674426574742355349194934
 */
class ProjectEulerEightish {
    public static void main(String[] args) {
        int n;
        Aux a;
        a = new Aux();
        n = a.initialize();
        n = a.calculate();
        System.out.println(n);
    }
}

class Aux {
    int[] digits;

    public int calculate() {
        int i;
        int product;
        int answer;

        i = 4;
        answer = 0;
        while (i < 50) {
            product = digits[i - 4] * digits[i - 3] * digits[i - 2]
                    * digits[i - 1] * digits[i];
            if (answer < product) {
                answer = product;
            } else {
            }
            i = i + 1;
        }

        return answer;
    }

    public int initialize() {
        digits = new int[50];

        digits[0] = 7;
        digits[1] = 3;
        digits[2] = 1;
        digits[3] = 6;
        digits[4] = 7;
        digits[5] = 1;
        digits[6] = 7;
        digits[7] = 6;
        digits[8] = 5;
        digits[9] = 3;
        digits[10] = 1;
        digits[11] = 3;
        digits[12] = 3;
        digits[13] = 0;
        digits[14] = 6;
        digits[15] = 2;
        digits[16] = 4;
        digits[17] = 9;
        digits[18] = 1;
        digits[19] = 9;
        digits[20] = 2;
        digits[21] = 2;
        digits[22] = 5;
        digits[23] = 1;
        digits[24] = 1;
        digits[25] = 9;
        digits[26] = 6;
        digits[27] = 7;
        digits[28] = 4;
        digits[29] = 4;
        digits[30] = 2;
        digits[31] = 6;
        digits[32] = 5;
        digits[33] = 7;
        digits[34] = 4;
        digits[35] = 7;
        digits[36] = 4;
        digits[37] = 2;
        digits[38] = 3;
        digits[39] = 5;
        digits[40] = 5;
        digits[41] = 3;
        digits[42] = 4;
        digits[43] = 9;
        digits[44] = 1;
        digits[45] = 9;
        digits[46] = 4;
        digits[47] = 9;
        digits[48] = 3;
        digits[49] = 4;

        // throwaway return value in lack of a void type
        return 0;
    }
}
