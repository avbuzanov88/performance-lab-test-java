package ru.buzanov.performancelab.task3;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

	public static void main(String[] args) {
		String usage = "ожидается 3 параметра: путь к файлу, "
				+ "дата-время начала поиска в формате 'yyyy-MM-dd'T'HH:mm:ss.SSS' "
				+ "и дата время окончания поиска в формате 'yyyy-MM-dd'T'HH:mm:ss.SSS'\n"
				+ "пример ./performance-lab-test-java/task3/log.log 2021-07-02T01:12:30.278 2021-07-02T01:12:30.300";
		if (args == null || args.length != 3) {
			System.out.println(usage);
			return;
		}
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
		LocalDateTime start;
		LocalDateTime finish;
		try {
			start = LocalDateTime.parse(args[1], formatter);
			finish = LocalDateTime.parse(args[2], formatter);
		} catch (Exception e) {
			System.out.println(usage);
			return;
		}
		int topUpCount = 0;
		long topUpVolumeSuccess = 0;
		long topUpVolumeFail = 0;
		int topUpCountFail = 0;
		int scoopCount = 0;
		long scoopVolumeSuccess = 0;
		long scoopVolumeFail = 0;
		int scoopCountFail = 0;
		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(args[0]))))) {
			String line;
			while ((line = br.readLine()) != null) {
				if (!line.contains("wanna top up") && !line.contains("wanna scoop")) {
					continue;
				}
				String datetimeToString = line.substring(0, line.indexOf("Z"));
				LocalDateTime datetime = LocalDateTime.parse(datetimeToString, formatter);
				if (datetime.isAfter(finish)) {
					break;
				}
				if (datetime.isBefore(start)) {
					continue;
				}
				if (line.contains("wanna top up")) {
					topUpCount++;
					String result = line.substring(line.indexOf("wanna top up") + "wanna top up".length() + 1,
							line.length() - 1);
					long volume = Long.parseLong(result.substring(0, result.indexOf("l")));
					if (result.contains("успех")) {
						topUpVolumeSuccess += volume;
					} else {
						topUpVolumeFail += volume;
						topUpCountFail++;
					}
					continue;
				}
				if (line.contains("wanna scoop")) {
					scoopCount++;
					String result = line.substring(line.indexOf("wanna scoop") + "wanna scoop".length() + 1,
							line.length() - 1);
					long volume = Long.parseLong(result.substring(0, result.indexOf("l")));
					if (result.contains("успех")) {
						scoopVolumeSuccess += volume;
					} else {
						scoopVolumeFail += volume;
						scoopCountFail++;
					}
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println(usage);
			return;
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		double topUpPercentFail = topUpCountFail > 0 ? Double.valueOf(topUpCountFail) / topUpCount * 100 : 0;
		double scoopPercentFail = scoopCountFail > 0 ? Double.valueOf(scoopCountFail) / scoopCount * 100 : 0;
		try (BufferedWriter writer = new BufferedWriter(new FileWriter("result.csv"))) {
			writer.write(
					"topUpCount;topUpPercentFail;topUpVolumeSuccess;topUpVolumeFail;scoopCount;scoopPercentFail;scoopVolumeSuccess;scoopVolumeFail\n");
			String data = Stream.of(topUpCount, topUpPercentFail, topUpVolumeSuccess, topUpVolumeFail, scoopCount,
					scoopPercentFail, scoopVolumeSuccess, scoopVolumeFail).map(String::valueOf)
					.collect(Collectors.joining(";"));
			writer.write(data);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
