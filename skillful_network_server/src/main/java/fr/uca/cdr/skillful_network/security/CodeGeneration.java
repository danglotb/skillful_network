package fr.uca.cdr.skillful_network.security;

import java.util.Random;
import java.util.function.Function;

public class CodeGeneration {

	private static int BEGIN = 48;

	private static final int END = 122;

	private static final Random RANDOM = new Random();

	public static final Function<Integer, String> generate = length -> RANDOM.ints(BEGIN, END + 1)
			.filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
			.limit(length)
			.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
			.toString();
}
