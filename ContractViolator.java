import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ContractViolator {

    static List<Double> getRandomNumbers() {
        List<Double> numbers = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            numbers.add(Math.random());
        }
        return numbers;
    }

    static class Sorter implements Runnable {

        private List<Double> numbers;

        Sorter(List<Double> numbers) {
            this.numbers = numbers;
        }

        @Override
        public void run() {
            Collections.sort(this.numbers);
        }

    }

    public static void main(String[] args) {
        List<Double> numbers = getRandomNumbers();
        for (int i = 0; i < 10; i++) {
            new Thread(new Sorter(numbers)).start();
        }
    }

}
