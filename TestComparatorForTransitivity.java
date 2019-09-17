import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

/**
 * See this blog entry yo!
 *
 * @author srasul
 *
 * @param 
 */
public class TestComparatorForTransitivity {
        private List listOfThings;
        private Comparator comparator;

        public TestComparatorForTransitivity(Collection thingsToSort, Comparator comparator) {
                if (thingsToSort.size() < 3) {
                        throw new IllegalArgumentException("Collection 'thingsToSort' must have atleast 3 items: " +
                                        thingsToSort + ". It has only " + thingsToSort.size() + " iteam");
                }
                if (comparator == null) {
                        throw new IllegalArgumentException("Comparator cannot be null yo!");
                }

                listOfThings = new ArrayList(thingsToSort);
                this.comparator = comparator;
        }

        public void doCheck() {
                for (int count = 0; count < listOfThings.size(); count++) {
                        for (int i = 1; i < listOfThings.size(); i++) {
                                for (int j = 2; j < listOfThings.size(); j++) {
                                        if ((count != i) && (i != j) && (count != j)) {
                                                checkComparator(listOfThings, comparator, i, j, count);
                                        }

                                }
                        }
                }

        }

        private void checkComparator(List listOfThings, Comparator comparator, int pointer1, int pointer2, int pointer3) {
                T oA = listOfThings.get(pointer1);
                T oB = listOfThings.get(pointer2);
                T oC = listOfThings.get(pointer3);
                int compareAwithB = comparator.compare(oA, oB);
                int compareBwithC = comparator.compare(oB, oC);
                int compareAwithC = comparator.compare(oA, oC);

                if (compareAwithB < 0) {
                        if (compareBwithC < 0) {
                                if (compareAwithC >= 0) {
                                        System.out.println("Got ya! #1");
                                        System.out.println("compare A < B: " + compareAwithB);
                                        System.out.println("compare B < C: " + compareBwithC);
                                        System.out.println("compare A < C: " + compareAwithC);
                                        System.out.println("A: " + oA);
                                        System.out.println("B: " + oB);
                                        System.out.println("C: " + oC);
                                        System.out.println("");
                                }
                        }
                }

                if (compareAwithB > 0) {
                        if (compareBwithC > 0) {
                                if (compareAwithC <= 0) {
                                        System.out.println("Got ya! #2");
                                        System.out.println("compare A > B: " + compareAwithB);
                                        System.out.println("compare B > C: " + compareBwithC);
                                        System.out.println("compare A > C: " + compareAwithC);
                                        System.out.println("A: " + oA);
                                        System.out.println("B: " + oB);
                                        System.out.println("C: " + oC);
                                        System.out.println("");
                                }
                        }
                }
        }
}
