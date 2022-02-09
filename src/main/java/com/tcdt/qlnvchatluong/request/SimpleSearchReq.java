package com.tcdt.qlnvchatluong.request;

import lombok.Data;

@Data
public class SimpleSearchReq {

	String name;
	String code;
	String maDvi;
	PaggingReq paggingReq;
}
