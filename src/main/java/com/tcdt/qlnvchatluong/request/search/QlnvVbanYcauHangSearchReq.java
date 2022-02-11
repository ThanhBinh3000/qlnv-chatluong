package com.tcdt.qlnvchatluong.request.search;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tcdt.qlnvchatluong.request.BaseRequest;
import com.tcdt.qlnvchatluong.util.Contains;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QlnvVbanYcauHangSearchReq extends BaseRequest {

	@ApiModelProperty(example = "SYC001")
	String soYcau;

	@ApiModelProperty(example = "0")
	String maDvi;
	
	String soHdong;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Contains.FORMAT_DATE_STR)
	@Temporal(TemporalType.DATE)
	Date tuNgayYcau;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Contains.FORMAT_DATE_STR)
	@Temporal(TemporalType.DATE)
	Date denNgayYcau;

}