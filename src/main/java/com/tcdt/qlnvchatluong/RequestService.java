package com.tcdt.qlnvchatluong;

import javax.servlet.http.HttpServletRequest;

public interface RequestService {

	String getClientIp(HttpServletRequest request);

}