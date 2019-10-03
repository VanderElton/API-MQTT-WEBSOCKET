package com.example.websocket.settings;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "quartz")
@PropertySource(value = { "classpath:application.yml" })
public class QuartzSettings {

	private String instanceId;
	private String threadPoolClass;
	private String threadCount;
	private String threadPriority;

	public QuartzSettings() {
		super();
	}

	public String getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	public String getThreadPoolClass() {
		return threadPoolClass;
	}

	public void setThreadPoolClass(String threadPoolClass) {
		this.threadPoolClass = threadPoolClass;
	}

	public String getThreadCount() {
		return threadCount;
	}

	public void setThreadCount(String threadCount) {
		this.threadCount = threadCount;
	}

	public String getThreadPriority() {
		return threadPriority;
	}

	public void setThreadPriority(String threadPriority) {
		this.threadPriority = threadPriority;
	}

}
