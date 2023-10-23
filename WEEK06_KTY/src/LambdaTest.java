import java.util.Arrays;

public class LambdaTest {

    public static void main(String[] args) {
        double[] numbers = {12.0, 45.0, 78.0, 23.0, 56.0, 89.0, 34.0};

        // 최대값을 찾는 람다 표현식
        ArrayProcessing maxFinder = (array) -> {
            double max = Double.MIN_VALUE;
            for (double num : array) {
                if (num > max) {
                    max = num;
                }
            }
            return max;
        };

        // 최소값을 찾는 람다 표현식
        ArrayProcessing minFinder = (array) -> {
            double min = Double.MAX_VALUE;
            for (double num : array) {
                if (num < min) {
                    min = num;
                }
            }
            return min;
        };

        // 평균값을 계산하는 람다 표현식
        ArrayProcessing averageCalculator = (array) -> {
            double sum = Arrays.stream(array).sum();
            return sum / array.length;
        };

        double maxNumber = maxFinder.apply(numbers);
        double minNumber = minFinder.apply(numbers);
        double average = averageCalculator.apply(numbers);

        System.out.println("최대값: " + maxNumber);
        System.out.println("최소값: " + minNumber);
        System.out.println("평균값: " + average);
    }
}

