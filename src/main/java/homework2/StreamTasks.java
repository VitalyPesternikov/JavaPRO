package homework2;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamTasks {
    public static void main(String[] args) {
        System.out.println(getThirdBiggestNumber(5, 2, 10, 9, 4, 3, 10, 1, 13));
        System.out.println(getThirdBiggestUniqueNumber(5, 2, 10, 9, 4, 3, 10, 1, 13));
        System.out.println(getThreeOldestEngineersNames(
                new Employee("Name1", 30, "Инженер"),
                new Employee("Name2", 40, "Инженер"),
                new Employee("Name3", 20, "Инженер"),
                new Employee("Name4", 50, "Инженер"),
                new Employee("Name5", 40, "Архитектор"),
                new Employee("Name6", 70, "Аналитик")
        ));
        System.out.println(getEngineersAverageAge(
                new Employee("Name1", 30, "Инженер"),
                new Employee("Name2", 40, "Инженер"),
                new Employee("Name3", 20, "Инженер"),
                new Employee("Name4", 50, "Инженер"),
                new Employee("Name5", 40, "Архитектор"),
                new Employee("Name6", 70, "Аналитик")
        ));
        System.out.println(getTheLongestWord("1", "12", "1234567", "123", "123", "1234"));
        System.out.println(getFrequencyAnalysisMap("qwe qwe rty wer qwe asd ert rty zcx qweq qwer qe qweq"));
        System.out.println(getSortedWords("322", "123", "12", "21", "123134", "321243", "321"));
        System.out.println(getTheLongestWordFromArray(
                "123 321 123 13123 123",
                "sdfs dxfdsg dsre ertrew fe",
                "werrere cvbgf sfghyt rthyt er",
                "dxffb rtgrtg grtg rgtr sd",
                "sdssdzfddfvd srgrt ewfse egtrg rtg"));
    }

    private static Integer getThirdBiggestNumber(Integer... numbers) {
        return Arrays.stream(numbers)
                .sorted(Comparator.reverseOrder())
                .skip(2)
                .findFirst()
                .orElse(null);
    }

    private static Integer getThirdBiggestUniqueNumber(Integer... numbers) {
        return Arrays.stream(numbers)
                .distinct()
                .sorted(Comparator.reverseOrder())
                .skip(2)
                .findFirst()
                .orElse(null);
    }

    private static List<String> getThreeOldestEngineersNames(Employee... employees) {
        return Arrays.stream(employees)
                .sorted(Comparator.comparing(Employee::getAge, Comparator.reverseOrder()))
                .filter(employee -> "Инженер".equals(employee.getPosition()))
                .limit(3)
                .map(Employee::getName)
                .toList();
    }

    private static Double getEngineersAverageAge(Employee... employees) {
        return Arrays.stream(employees)
                .filter(employee -> "Инженер".equals(employee.getPosition()))
                .mapToInt(Employee::getAge)
                .average()
                .orElse(0);
    }

    private static String getTheLongestWord(String... words) {
        return Arrays.stream(words)
                .max(Comparator.comparing(String::length))
                .orElse(null);
    }

    private static Map<String, Long> getFrequencyAnalysisMap(String text) {
        return Arrays.stream(text.split(" "))
                .collect(
                Collectors.groupingBy(word -> word, Collectors.counting())
        );
    }

    private static List<String> getSortedWords(String... words) {
        return Arrays.stream(words)
                .sorted(Comparator.comparing(String::length)
                        .thenComparing(word -> word))
                .toList();
    }

    private static String getTheLongestWordFromArray(String... texts) {
        return Arrays.stream(texts)
                .flatMap(text -> Stream.of(text.split(" ")))
                .max(Comparator.comparing(String::length))
                .orElse(null);
    }
}
