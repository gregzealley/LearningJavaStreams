package com.gregzealley;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Pattern;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {
        //examplesOfStreams();
        //simpleMapReduceStreamExample();
        examplesOfMatchers();
        examplesOfFind();
        examplesOfReduce();
    }

    private static void examplesOfReduce() {

        List<Person> persons = createListOfPeople();

        //int sumOfAges = persons
        //        .stream()
        //        .reduce(0, (p1, p2) -> p1.getAge() + p2.getAge());
    }

    private static void examplesOfMatchers() {
        //These are short-circuit terminal operations

        List<Person> persons = createListOfPeople();

        boolean result = persons
                .stream()
                .anyMatch(p -> p.getAge() > 20);   //any element match condition
        //      .allMatch(p -> p.getAge() > 20);   //all elements must match condition
        //      .noneMatch(p -> p.getAge() > 20);  //no elements must match condition

        System.out.println("Result is: " + (String.valueOf(result)));
    }

    private static void examplesOfFind() {
        //might have nothing to return if stream is empty or if no values that match
        //will return an Optional that can be empty

        List<Person> persons = createListOfPeople();

        Optional<Person> opt = persons
                .stream()
                .filter(p -> p.getAge() > 20)
        //      .findFirst();                   //assumes stream is ordered
                .findAny();                     //will return any matching
    }

    private static void simpleMapReduceStreamExample() {
        List<Person> persons = createListOfPeople();

        persons.stream()
                .map(p -> p.getAge())
                .peek(System.out::println)     // peek is used to output results for logging purposes, not for production
                .filter(age -> age > 20)       // changes the type of the stream to Integer.. hence it will output the age
                .forEach(System.out::println); // a terminal operation - causes the processing of the stream

        System.out.println("\n");

        persons.stream()
                .filter(p -> p.getAge() > 20)
                .forEach(System.out::println); // with no map now prints out the Person object ID
    }

    private static void examplesOfStreams() {
        // an empty Stream
        Stream.empty();

        //a singelton Stream
        Stream.of("one");

        //a Stream with several elements
        Stream.of("one", "two", "three");

        //a constant Stream - call supplier that will always return one.. a constant stream of "one"s
        // can use .limit to set the number required
        Stream.generate(() -> "one");

        //a growing Stream - starts with the parameter, then adds a + onto the next one.. so +, then ++, then +++ forever
        Stream.iterate("+", s -> s + "+");

        //a random Stream - returns a Stream contained of random integer values
        ThreadLocalRandom.current().ints();

        //a Stream on the letters of a String - returns 'h', 'e', 'l', 'l', 'o'
        IntStream stream = "hello".chars();

        //a Stream on a regular expression - will take the input and split on the RegEx and return a Stream
        String book = "Here is a string of words that can be considered to be like a book";
        Stream<String> words = Pattern
                .compile("[^\\p{javaLetter}]")
                .splitAsStream(book);

        //a Stream on the lines of a text file
        Path path = Paths.get("/tmp/mytextfile.txt");
        try {
            Stream<String> linesFromFile = Files.lines(path);
            linesFromFile.forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //StreamBuilder pattern
        Stream.Builder<String> builder = Stream.builder();

        builder.add("one").add("two").add("three");  // .add() can be chained as it returns a value
        builder.accept("four"); // .accept() cannot be chained as it does not return a value

        Stream<String> streamFromBuilder = builder.build(); //now call build method

        streamFromBuilder.forEach(System.out::println);
    }

    private static List<Person> createListOfPeople() {
        List<Person> persons = new LinkedList<>();
        Person persona = new Person("Bob", 30);
        Person personb = new Person("Jim", 19);
        Person personc = new Person("Carol", 60);

        persons.add(persona);
        persons.add(personb);
        persons.add(personc);

        return persons;
    }
}
