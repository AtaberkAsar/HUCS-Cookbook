import org.knowm.xchart.*;
import org.knowm.xchart.style.Styler;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Drawer {

    public static void draw(String title, Map<String, List<Double>> results, String[] resultSet, String unit)
            throws IOException {

        // Convert x axis to double[]
        double[] xAxis = {500, 1000, 2000, 4000, 8000, 16000, 32000, 64000, 128000, 250000};

        // Sort Results
        XYChart chart = new XYChartBuilder().width(800).height(600).title(title)
                .yAxisTitle("Time in " + unit).xAxisTitle("Input Size").build();

        // Customize Chart
        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNE);
        chart.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line);

        // Add a plot for a sorting algorithm
        chart.addSeries(resultSet[0], xAxis, results.get(resultSet[0]).stream().mapToDouble(d -> d).toArray());
        chart.addSeries(resultSet[1], xAxis, results.get(resultSet[1]).stream().mapToDouble(d -> d).toArray());
        chart.addSeries(resultSet[2], xAxis, results.get(resultSet[2]).stream().mapToDouble(d -> d).toArray());

        // Below line added because of some lines are very close to other line
        // ie. it cannot be seen on the graph since it was right below other line
        chart.getSeriesMap().get(resultSet[1]).setLineWidth(7.5F);

        // Save the chart as PNG
        BitmapEncoder.saveBitmap(chart, title + ".png", BitmapEncoder.BitmapFormat.PNG);

        // Show the chart
        new SwingWrapper(chart).displayChart();
    }
}
