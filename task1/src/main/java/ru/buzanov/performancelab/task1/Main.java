package ru.buzanov.performancelab.task1;

public class Main {

	public static void main(String[] args) {
		int nb = Integer.parseInt(args[0]);
		String base = args[1];
		String result = itoBase(nb, base);
		System.out.println(result);
	}

	private static String itoBase(int nb, String base) {
		char[] baseSymbols = base.toCharArray();
		StringBuilder invertedResult = new StringBuilder();
		divide(nb, baseSymbols, invertedResult);
		String result = "";
		for (int i = invertedResult.length() - 1; i >= 0; i--) {
			result = result + invertedResult.charAt(i);
		}
		return result;
	}

	public static void divide(int intermediateResult, char[] baseSymbols, StringBuilder result) {
		if (intermediateResult <= baseSymbols.length) {
			result.append(baseSymbols[intermediateResult]);
			return;
		}
		int resultForAppendIndex = intermediateResult % baseSymbols.length;
		result.append(baseSymbols[resultForAppendIndex]);
		intermediateResult = (intermediateResult - (intermediateResult % baseSymbols.length)) / baseSymbols.length;
		divide(intermediateResult, baseSymbols, result);
	}

}
