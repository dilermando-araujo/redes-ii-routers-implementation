package app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class RouteTable {
	
	private NetService netService = new NetService();
	private Map<NetAddress, RouteTableEntry> routes = new HashMap<>();
	
	public List<NetAddress> getAddressesNet() {
		return new ArrayList<NetAddress>(routes.keySet());
	}
	
	public void addEntry(RouteTableEntry entry) {
		this.routes.put(entry.getAddressNet(), entry);
	}
	
	public Optional<RouteTableEntry> getEntryByIp(String ip) {
		Optional<NetAddress> netAddressOpt = netService.getAddressNetInListByIp(getAddressesNet(), ip);
		if (netAddressOpt.isEmpty()) return Optional.empty();
		
		return Optional.of(routes.get(netAddressOpt.get()));
	}
}
