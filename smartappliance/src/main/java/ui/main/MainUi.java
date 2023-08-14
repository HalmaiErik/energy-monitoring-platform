package ui.main;

import rpc.SmartApplianceService;
import rpc.SmartApplianceServiceImpl;

import javax.swing.*;
import java.awt.*;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

public class MainUi extends JFrame {
    private JPanel panel;
    private JTextField devicesText;
    private JSpinner daysSpinner;
    private JButton applyDaysButton;
    private JPanel consumptionPanel;
    private JPanel baselinePanel;
    private JLabel durationLabel;
    private JButton bestTimeButton;
    private JPanel leftPanel;
    private JPanel rightPanel;
    private JPanel changeDaysPanel;
    private JPanel graphPanel;
    private JPanel devicesPanel;
    private JPanel bestTimePanel;
    private JPanel bestTimeResultsPanel;
    private JSpinner durationSpinner;
    private ConsumptionChart consumptionChart;
    private ConsumptionChart baselineChart;

    private final String username;
    private final SmartApplianceService smartApplianceService;
    private final List<String> devices;
    private Map<String, Double> hourlyConsumption;
    private Map<String, Double> baseline;

    public MainUi(String username) throws MalformedURLException {
        this.username = username;
        smartApplianceService = new SmartApplianceServiceImpl();
        this.devices = smartApplianceService.getDevicesOfClient(username);
        initDefaultUi();
        addListeners();
    }

    private void initDefaultUi() {
        this.setTitle("Smart Appliance");
        this.setContentPane(panel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        setSize(1200, 1200);

        initDevicesText();
        daysSpinner.setValue(7);
        updateHourlyConsumptionChart();
        initBaselineChart();
    }

    private void addListeners() {
        applyDaysButton.addActionListener(e -> updateHourlyConsumptionChart());
        bestTimeButton.addActionListener(e -> displayBestTimeResults());
    }

    private void initDevicesText() {
        devicesText.setText("Your devices: ");
        for (String str : devices) {
            devicesText.setText(devicesText.getText() + str + ", ");
        }
    }

    private void updateHourlyConsumptionChart() {
        int days = (int) daysSpinner.getValue();
        consumptionPanel.removeAll();

        hourlyConsumption = smartApplianceService.getUserHourlyConsumptionOverDays(username, days);
        consumptionChart = new ConsumptionChart(hourlyConsumption, "Energy consumption over 7 days", new Dimension(500, 300));
        consumptionPanel.add(consumptionChart);
        consumptionPanel.updateUI();
        this.pack();
    }

    private void displayBestTimeResults() {
        int duration = (int) durationSpinner.getValue();
        bestTimeResultsPanel.removeAll();

        Map<String, Map<String, Double>> bestTimeResults = smartApplianceService.getBestStartTime(username, duration);
        for (String device : bestTimeResults.keySet()) {
            ConsumptionChart chart = new ConsumptionChart(bestTimeResults.get(device), device + " estimation",
                    new Dimension(500, 300));
            bestTimeResultsPanel.add(chart);
        }
        bestTimeResultsPanel.updateUI();
        this.pack();
    }

    private void initBaselineChart() {
        baseline = smartApplianceService.getBaseline(username);
        baselineChart = new ConsumptionChart(baseline, "Baseline energy consumption", new Dimension(500, 300));
        baselinePanel.add(baselineChart);
        baselinePanel.updateUI();
        this.pack();
    }
}
