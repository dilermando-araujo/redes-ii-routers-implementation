package app;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class NetService {
	
	public String getAddressNetByIpAsString(String ip, int mask) {
		return this.parseBinaryIpToString(getFirstAndLastIp(ip, mask)[0]);
	}
	
	public long[] getFirstAndLastIp(String ip, int mask) {
		long maskBinary = getBinaryFromMask(mask);
		
		return getFirstAndLastIpByIpAndBinaryMask(ip, maskBinary, 0, 2);
	}
	
	public long[] getFirstAndLastIpByIpAndBinaryMask(String ip, long maskBinary, long subnetBinary, int quantityBitSubnet) {
		long ipBinary = getBinaryFromIp(ip);
		
		long firstIp = (ipBinary & maskBinary) | subnetBinary;
		long lastIp = firstIp + ((~((int) maskBinary)) >> quantityBitSubnet);
			
		return new long[] {firstIp, lastIp};
	}
	
	public Long getBinaryFromIp(String ip) {
		Long binaryIp = 0L;
		for (String ipPart : ip.split("\\.")) {
			binaryIp = (binaryIp << 8) | Long.parseLong(ipPart);
		}
		
		return binaryIp;
	}
	
	public Long getBinaryFromMask(int mask) {
		return (long) ~((~0b0) << mask) << (32 - mask);
	}
	
	public String parseBinaryIpToString(Long ipBinary) {
		StringBuilder sb = new StringBuilder();
		
		sb.append((ipBinary >> 24) & 255);
		sb.append(".");
		sb.append((ipBinary >> 16) & 255);
		sb.append(".");
		sb.append((ipBinary >> 8) & 255);
		sb.append(".");
		sb.append(ipBinary & 255);
		
		return sb.toString();
	}
	
	public void printBinaryIp(Long ipBinary) {
		System.out.println(parseBinaryIpToString(ipBinary));
	}
	
	public List<Long[]> listSubnetPools(String ip, Integer mask, Integer quantityMinSubnets) {
		List<Long[]> pools = new ArrayList<>();
		
		long maskBinary = getBinaryFromMask(mask);
	
		Integer bitToIncrement = LogHelper.log2(quantityMinSubnets);
	
		int lastBinary = ~(~0b0 << bitToIncrement);
		for (int i = 0; i <= lastBinary; i++) {
			int subnetMask = i << (32 - 24 - bitToIncrement);
		
			long[] pool = getFirstAndLastIpByIpAndBinaryMask(ip, maskBinary, subnetMask, bitToIncrement);
			pools.add(new Long[] {pool[0], pool[1]});
		}
		
		return pools;
	}
	
	public boolean checkIpInNet(String ip, String addressNet, Integer mask) {
		long[] ips = getFirstAndLastIp(ip, mask);
		long ipBinary = getBinaryFromIp(addressNet);
	
		if (ips[0] == ipBinary) return true;
		return false;
	}
	
	public Optional<NetAddress> getAddressNetInListByIp(List<NetAddress> netAddresses, String ip) {
		netAddresses.sort(Comparator.comparing(NetAddress::getMask).reversed());
		
		boolean in = false;
		for (NetAddress netAddress : netAddresses) {
			in = checkIpInNet(ip, netAddress.getAddress(), netAddress.getMask());
			
			if (in) {
				return Optional.of(netAddress);
			}
		}
		return Optional.empty();
	}
	
}
