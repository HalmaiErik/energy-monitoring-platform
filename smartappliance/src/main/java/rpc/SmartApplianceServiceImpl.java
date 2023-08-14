package rpc;

import com.googlecode.jsonrpc4j.JsonRpcHttpClient;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class SmartApplianceServiceImpl implements SmartApplianceService {

    private static final String RPC_URL = "https://energy-platform-backend-erik.herokuapp.com/rpc/smart-app";

    private final JsonRpcHttpClient rpcHttpClient;

    public SmartApplianceServiceImpl() throws MalformedURLException {
        rpcHttpClient = new JsonRpcHttpClient(new URL(RPC_URL));
    }

    @Override
    public List<String> getDevicesOfClient(String username) {
        try {
            return  rpcHttpClient.invoke("getDevicesOfClient", new Object[] {username}, List.class);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return null;
        }
    }

    @Override
    public Map<String, Double> getUserHourlyConsumptionOverDays(String username, int days) {
        try {
            return rpcHttpClient.invoke("getUserHourlyConsumptionOverDays", new Object[] {username, days}, Map.class);
        } catch (Throwable t) {
            t.printStackTrace();
            return null;
        }
    }

    @Override
    public Map<String, Double> getBaseline(String username) {
        try {
            return rpcHttpClient.invoke("getBaseline", new Object[] {username}, Map.class);
        } catch (Throwable t) {
            t.printStackTrace();
            return null;
        }
    }

    @Override
    public Map<String, Map<String, Double>> getBestStartTime(String username, int hourDuration) {
        try {
            return rpcHttpClient.invoke("getBestStartTime", new Object[] {username, hourDuration}, Map.class);
        } catch (Throwable t) {
            t.printStackTrace();
            return null;
        }
    }
}
