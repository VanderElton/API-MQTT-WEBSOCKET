package com.example.websocket.entity.device;


import com.example.websocket.exception.ApiException;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

public class TokenBuilder {

	public String createToken(Device device) {
		validate(device);
		return DigestUtils.md5Hex(device.getId());
	}

	private void validate(Device device) {
		if (device == null || StringUtils.isBlank(device.getId())) {
			throw new ApiException("Device is required to create token builder.");
		}
	}
}
