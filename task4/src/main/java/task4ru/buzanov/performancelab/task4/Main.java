package task4ru.buzanov.performancelab.task4;

import java.util.regex.Pattern;

public class Main {

	public static void main(String[] args) {
		String data = args[0];
		String pattern = args[1];
		pattern = pattern.replaceAll("[\\*]+", ".+");
		String result = Pattern.compile(pattern).matcher(data).matches() ? "OK" : "KO";
		System.out.println(result);
	}

}
