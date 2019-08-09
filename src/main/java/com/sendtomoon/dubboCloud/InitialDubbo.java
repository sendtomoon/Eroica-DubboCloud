package com.sendtomoon.dubboCloud;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.dubbo.common.utils.ConfigUtils;
import org.apache.dubbo.common.utils.StringUtils;
import org.springframework.context.SmartLifecycle;

public class InitialDubbo implements SmartLifecycle {
//	protected Log logger = LogFactory.getLog(this.getClass());

	private boolean isRunning;

	private Properties dubboProperties;

	private String ipAddr;

	private String zookeeper;

	private static final String DEFAULT_CONFIG_PATH = "dubbo.properties";

	protected void initDubboConfigs() {
		if (dubboProperties == null) {
			try {
				this.getDefPro();
			} catch (FileNotFoundException e) {
				System.err.println("can't find any propertie file!");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (StringUtils.isNotEmpty(ipAddr)) {
			dubboProperties.setProperty("dubbo.protocol.host", ipAddr);
		}
		if (StringUtils.isNotEmpty(zookeeper)) {
			dubboProperties.setProperty("dubbo.registry.address", zookeeper);
		} else {
			dubboProperties.setProperty("dubbo.registry.address", "N/A");
		}

		ConfigUtils.setProperties(dubboProperties);
	}

	@Override
	public boolean isRunning() {
		return isRunning;
	}

	@Override
	public void start() {
		if (isRunning)
			return;
		initDubboConfigs();
	}

	@Override
	public void stop() {
		isRunning = false;
	}

	@Override
	public int getPhase() {
		return 0;
	}

	@Override
	public boolean isAutoStartup() {
		return false;
	}

	@Override
	public void stop(Runnable arg0) {
	}

	public Properties getDubboProperties() {
		return dubboProperties;
	}

	public void setDubboProperties(Properties dubboProperties) {
		this.dubboProperties = dubboProperties;
	}

	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}

	private void getDefPro() throws FileNotFoundException, IOException {
		InputStream input = this.getClass().getClassLoader().getResourceAsStream(DEFAULT_CONFIG_PATH);
		dubboProperties = new Properties();
		dubboProperties.load(input);
	}

	public String getIpAddr() {
		return ipAddr;
	}

	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}

	public String getZookeeper() {
		return zookeeper;
	}

	public void setZookeeper(String zookeeper) {
		this.zookeeper = zookeeper;
	}

}
