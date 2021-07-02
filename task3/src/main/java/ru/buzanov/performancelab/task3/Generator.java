package ru.buzanov.performancelab.task3;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class Generator {

	public static void main(String[] args) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
		Random random = new Random();
		try (BufferedWriter writer = new BufferedWriter(new FileWriter("log.log"))) {
			writer.write("META DATA:\n" + "200 (объем бочки)\n" + "32 (текущий объем воды в бочке)\n");
			for (int i = 1; i < 15000; i++) {
				String datetime = LocalDateTime.now().format(formatter) + "Z";
				String action = random.nextInt(2) > 0 ? "wanna top up" : "wanna scoop";
				String volume = random.nextInt(100) + "l";
				String result = random.nextInt(2) > 0 ? "успех" : "фейл";
				String line = String.format("%s – [username1] - %s %s (%s)\n", datetime, action, volume, result);
				writer.write(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
