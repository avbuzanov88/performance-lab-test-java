package ru.buzanov.performancelab.task2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class Main {

	public static void main(String[] args) {
		StringBuilder resultStringBuilder = new StringBuilder();
	    try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(args[0]))))) {
	        String line;
	        while ((line = br.readLine()) != null) {
	            resultStringBuilder.append(line).append("\n");
	        }
	    } catch (Exception e) {
			e.printStackTrace();
			return;
		}
		String request = resultStringBuilder.toString().replaceAll(" ", "");
		
		int sphereParametersStart = request.indexOf("sphere") + "sphere".length() + 2;
		String sphereParameters = (request.substring(sphereParametersStart)).substring(0,
				request.substring(sphereParametersStart).indexOf("}"));

		int centerParametersStart = sphereParameters.indexOf("center") + "center".length() + 2;
		String centerParameters = (sphereParameters.substring(centerParametersStart)).substring(0,
				sphereParameters.substring(centerParametersStart).indexOf("]"));
		String[] centerParts = centerParameters.split(",");
		double sphereCenterX = Double.parseDouble(centerParts[0]);
		double sphereCenterY = Double.parseDouble(centerParts[1]);
		double sphereCenterZ = Double.parseDouble(centerParts[2]);

		int radiusParametersStart = sphereParameters.indexOf("radius") + "radius".length() + 1;
		String radiusParameters = sphereParameters.substring(radiusParametersStart).contains(",")
				? sphereParameters.substring(radiusParametersStart, sphereParameters.indexOf(","))
				: sphereParameters.substring(radiusParametersStart);
		double circleRadius = Double.parseDouble(radiusParameters);
		
		int lineParametersStart = request.indexOf("line") + "line".length() + 2;
		String lineParameters = (request.substring(lineParametersStart)).substring(0,
				request.substring(lineParametersStart).indexOf("}"));
		lineParameters = lineParameters.substring(1, lineParameters.length() - 1);
		
		String linePoint1Parameters = lineParameters.substring(0, lineParameters.indexOf("]"));
		String[] linePoint1Parts = linePoint1Parameters.split(",");
		double linePoint1X = Double.parseDouble(linePoint1Parts[0]);
		double linePoint1Y = Double.parseDouble(linePoint1Parts[1]);
		double linePoint1Z = Double.parseDouble(linePoint1Parts[2]);
		
		String linePoint2Parameters = lineParameters.substring(lineParameters.indexOf("[") + 1);
		String[] linePoint2Parts = linePoint2Parameters.split(",");
		double linePoint2X = Double.parseDouble(linePoint2Parts[0]);
		double linePoint2Y = Double.parseDouble(linePoint2Parts[1]);
		double linePoint2Z = Double.parseDouble(linePoint2Parts[2]);

		double vectorX = linePoint2X - linePoint1X;
		double vectorY = linePoint2Y - linePoint1Y;
		double vectorZ = linePoint2Z - linePoint1Z;

		double a = vectorX * vectorX + vectorY * vectorY + vectorZ * vectorZ;
		double b = 2 * (linePoint1X * vectorX + linePoint1Y * vectorY + linePoint1Z * vectorZ
				- vectorX * sphereCenterX - vectorY * sphereCenterY - vectorZ * sphereCenterZ);
		double c = linePoint1X * linePoint1X - 2 * linePoint1X * sphereCenterX + sphereCenterX * sphereCenterX
				+ linePoint1Y * linePoint1Y - 2 * linePoint1Y * sphereCenterY + sphereCenterY * sphereCenterY
				+ linePoint1Z * linePoint1Z - 2 * linePoint1Z * sphereCenterZ + sphereCenterZ * sphereCenterZ
				- circleRadius * circleRadius;
		double disriminant = b * b - 4 * a * c;

		if (disriminant < 0) {
			System.out.println("Коллизий не найдено");
			return;
		}

		double t1 = (-b - Math.sqrt(disriminant)) / (2.0 * a);
		double x1 = linePoint1X * (1 - t1) + t1 * linePoint2X;
		double y1 = linePoint1Y * (1 - t1) + t1 * linePoint2Y;
		double z1 = linePoint1Z * (1 - t1) + t1 * linePoint2Z;

		if (disriminant == 0) {
			System.out.println(x1 + ";" + y1 + ";" + z1);
			return;
		}

		double t2 = (-b + Math.sqrt(disriminant)) / (2.0 * a);
		double x2 = linePoint1X * (1 - t2) + t2 * linePoint2X;
		double y2 = linePoint1Y * (1 - t2) + t2 * linePoint2Y;
		double z2 = linePoint1Z * (1 - t2) + t2 * linePoint2Z;

		System.out.println(x1 + ";" + y1 + ";" + z1 + "\n" + x2 + ";" + y2 + ";" + z2);
	}

}
