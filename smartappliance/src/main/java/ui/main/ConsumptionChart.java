package ui.main;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class ConsumptionChart extends JPanel {

    private final String title;
    private final Map<String, Double> dataSet;
    private final Dimension dimension;

    public ConsumptionChart(Map<String, Double> dataSet, String title, Dimension dimension) {
        this.dataSet = dataSet;
        this.title = title;
        this.dimension = dimension;
        initUI();
    }

    private void initUI() {
        XYDataset dataset = createDataset();
        JFreeChart chart = createChart(dataset);

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        chartPanel.setBackground(Color.white);
        chartPanel.setPreferredSize(dimension);
        add(chartPanel);
    }

    private XYDataset createDataset() {
        XYSeries series = new XYSeries(title);
        for (Map.Entry<String, Double> entry : dataSet.entrySet()) {
            series.add(Integer.valueOf(entry.getKey()), entry.getValue());
        }

        XYSeriesCollection seriesCollection = new XYSeriesCollection();
        seriesCollection.addSeries(series);

        return seriesCollection;
    }

    private JFreeChart createChart(XYDataset dataset) {
        JFreeChart chart = ChartFactory.createXYLineChart(
                title,
                "Day",
                "Energy Consumption",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        XYPlot plot = chart.getXYPlot();

        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesPaint(0, Color.RED);
        renderer.setSeriesStroke(0, new BasicStroke(2.0f));

        plot.setRenderer(renderer);
        plot.setBackgroundPaint(Color.white);

        plot.setRangeGridlinesVisible(true);
        plot.setRangeGridlinePaint(Color.BLACK);

        plot.setDomainGridlinesVisible(true);
        plot.setDomainGridlinePaint(Color.BLACK);

        chart.getLegend().setFrame(BlockBorder.NONE);

        chart.setTitle(
                new TextTitle(title,
                        new Font("Serif", java.awt.Font.BOLD, 18))
        );

        return chart;
    }
}
