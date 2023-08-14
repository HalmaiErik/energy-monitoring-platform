package rpc;

import java.util.List;
import java.util.Map;

public interface SmartApplianceService {

    List<String> getDevicesOfClient(String username);
    Map<String, Double> getUserHourlyConsumptionOverDays(String username, int days);
    Map<String, Double> getBaseline(String username);
    Map<String, Map<String, Double>> getBestStartTime(String username, int hourDuration);
}
