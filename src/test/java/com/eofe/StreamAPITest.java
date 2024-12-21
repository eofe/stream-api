package com.eofe;


import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import static org.junit.jupiter.api.Assertions.*;

public class StreamAPITest {

    MockData mockData = new MockData();
    @Test
    void nothing(){
        var numbers = new HashSet<>();
        var set = new HashSet<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
        IntStream.rangeClosed(1, 10).forEach(numbers::add);
        assertEquals(set, numbers);
    }

    //IntStream iterate
    @Test
    void intStreamIterate(){
        IntStream.iterate(0, operand -> operand + 1)
                .filter(operand -> operand % 2 == 0)
                .limit(100)
                .forEach(System.out::println);
    }

    @Test
    void txWithMinAmount(){
        var tx  = mockData.getTransactions().stream()
                .min(Comparator.comparing(Transaction::getAmount))
                .get();
        assertTrue(tx.getAmount() > 0);
    }


    @Test
    void txWithMaxAmount(){
        var tx  = mockData.getTransactions().stream()
                .max(Comparator.comparing(Transaction::getAmount))
                .get();
        assertTrue(tx.getAmount() > 100);
    }

    @Test
    void distinct(){
        var integers = List.of(10, 10, 100, 20);
        var expected = List.of(10, 100, 20);

        var distinctedList = integers.stream().distinct().collect(Collectors.toList());
        assertEquals(expected,
                distinctedList);
    }

    @Test
    void distinctWithSet(){
        var integers = List.of(10, 10, 100, 20, 659, 155, 155);
        var distinctWithSet = integers.stream().collect(Collectors.toSet());

        assertEquals(Set.of(659, 100, 20, 10, 155), distinctWithSet);
    }

    @Test
    void filter(){
        var transactions = mockData.getTransactions();

        Predicate<Transaction> txPredicate = transaction -> transaction.getTimestamp().isBefore(LocalDateTime.of(2022, 1, 1, 0, 0));

        // Filter takes a predicate that evaluates to true or false
        List<Transaction> filteredTransactions = transactions.stream()
                .filter(txPredicate)
                .toList();
        filteredTransactions.forEach(e -> assertTrue(e.getTimestamp().isBefore(LocalDateTime.of(2022, 1, 1, 0, 0))));
    }

    @Test
    void map(){
        var transactions = mockData.getTransactions();
        var mappedList = transactions.stream()
                .map(TransactionDTO::map)
                .collect(Collectors.toList());
        assertNotNull(mappedList);
    }

    @Test
    void avg(){
        var transactions = mockData.getTransactions();
        var averageTxAmount = transactions.stream()
                .mapToDouble(Transaction::getAmount)
                .average()
                .orElse(0);

        System.out.println(averageTxAmount);

    }

    @Test
    void findAny(){
        Integer[] numbers = {1,100,6,98,45,154,9,62,3,94,5664,98,54,11};

        //Returns an Optional describing any element from the filtered stream.
        //findAny() can return any matching element, which might be faster for parallel streams but does not guarantee the order.
        var any = Arrays.stream(numbers)
                .filter(e -> e>99)
                .findAny()
                .orElse(-1);
        System.out.println(any);
        assertNotNull(any);
    }

    @Test
    void findFirst(){
        Integer[] numbers = {1,100,6,98,45,154,9,62,3,94,5664,98,54,11};
        //findFirst() guarantees to return the first element in the stream that matches the condition.
        var first = Arrays.stream(numbers)
                .filter(e -> e>99)
                .findFirst()
                .orElse(-1);
        System.out.println(first);
    }

    @Test
    void count(){
        var result = mockData.getTransactions().stream()
                .filter(transaction -> transaction.getTimestamp().isBefore(LocalDateTime.of(2022, 1, 1, 0, 0)))
                .count();
        System.out.println(result);
    }

    @Test
    void min(){
        var transactions = mockData.getTransactions();
        var minTxAmount = transactions.stream()
                .filter(tx -> tx.getAmount() > 500)
                .mapToDouble(Transaction::getAmount)
                .min()
                .orElse(-1);
        System.out.println(minTxAmount);
    }

    @Test
    void max(){
        var transactions = mockData.getTransactions();
        var maxTxAmount = transactions.stream()
                .filter(tx -> tx.getAmount() > 500)
                .mapToDouble(Transaction::getAmount)
                .max()
                .orElse(-1);
        System.out.println(maxTxAmount);
    }

    @Test
    void average(){
        var transactions = mockData.getTransactions();
        var avgTxAmount = transactions.stream().filter(tx -> tx.getAmount() > 500)
                .mapToDouble(Transaction::getAmount)
                .average()
                .orElse(-1);
        System.out.println(avgTxAmount);
    }

    @Test
    void txSum(){
        var transactions = mockData.getTransactions();
        var sum = transactions.stream().mapToDouble(Transaction::getAmount).sum();
        BigDecimal bd = BigDecimal.valueOf(sum);
        System.out.println(bd);
    }

    @Test
    void summaryStatistics(){
        var transactions = mockData.getTransactions();
        var summaryStatistics = transactions.stream()
                .mapToDouble(Transaction::getAmount)
                .summaryStatistics();
        System.out.println(summaryStatistics);
    }

    @Test
    void groupBy(){
        var transactions = mockData.getTransactions();
        var groupedTransactions = transactions.stream()
                .collect(Collectors.groupingBy(Transaction::getAmount));
        groupedTransactions.forEach((k, v) -> System.out.println(k + ": " + v));
    }

    @Test
    void reduce(){
        Integer[] numbers = {1,100,6,98,45,154,9,62,3,94,5664,98,54,11, 9965,566,585,12,5,5,5,5,5,9,5448};
        var reduced = Arrays.stream(numbers).reduce(0, Integer::sum);
        System.out.println(reduced);
    }

    @Test
    void withoutFlatMap(){
        List<List<String>> nestedList = List.of(
                List.of("Mariam", "Alex", "Ismail"),
                List.of("John", "Alesha", "Andre"),
                List.of("Susy", "Ali")
        );

        System.out.println(nestedList);

        List<String> result = new ArrayList<>();

        nestedList.forEach(result::addAll);

        System.out.println(result);
    }

    @Test
    void withFlatMap(){
        List<List<String>> nestedList = List.of(
                List.of("Mariam", "Alex", "Ismail"),
                List.of("John", "Alesha", "Andre"),
                List.of("Susy", "Ali")
        );

        System.out.println(nestedList);

        var flatMap = nestedList.stream().flatMap(List::stream).toList();

        System.out.println(flatMap);
    }

    @Test
    void joiningStringsWithoutStream(){
        var strings = List.of("Mariam", "Alex", "Ismail");
        String result = String.join(", ", strings);
        System.out.println(result);
    }

    @Test
    void joiningStringsWithStreamApi(){
        var strings = List.of("Mariam", "Alex", "Ismail");
        var joined = strings.stream()
                .map(String::toUpperCase)
                .collect(Collectors.joining(", "));
        System.out.println(joined);
    }
}
