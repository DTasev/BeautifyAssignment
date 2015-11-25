package uk.ac.aber.beautify.filters.histogram;

import java.awt.Dimension;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class ShowHistogram extends javax.swing.JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ShowHistogram(int[] histogramData, String channel) {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for (int i = 0; i < histogramData.length; i++) {
			dataset.setValue(histogramData[i], "RGBVal", "" + i);
		}
		JFreeChart chart = ChartFactory.createBarChart(channel, "Values",
                "Range", dataset, PlotOrientation.VERTICAL, false, true, false);
        ChartPanel cp = new ChartPanel(chart) {
			@Override
			public Dimension getPreferredSize() {
				return new Dimension(320, 240);
			}
		};
		add(cp);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
	}
}
