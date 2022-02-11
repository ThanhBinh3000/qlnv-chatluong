package com.tcdt.qlnvchatluong.request.search;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Past;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tcdt.qlnvchatluong.request.BaseRequest;
import com.tcdt.qlnvchatluong.util.Contains;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QlnvHsktSearchReq extends BaseRequest {

	@ApiModelProperty(example = "QD1234")
	String soHso;

	@ApiModelProperty(example = "HD123")
	String soHdong;

	String maHhoa;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Contains.FORMAT_DATE_STR)
	@Past
	@Temporal(TemporalType.DATE)
	Date tuNgayLap;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Contains.FORMAT_DATE_STR)
	@Past
	@Temporal(TemporalType.DATE)
	Date denNgayLap;

}