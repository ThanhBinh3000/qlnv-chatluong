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
public class QlnvKquaKdinhMauSearchReq extends BaseRequest {
	@ApiModelProperty(example = "SBB001")
	String soBban;

	@ApiModelProperty(example = "0")
	String maDvi;
	
	@ApiModelProperty(example = "MH")
	String maHhoa;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Contains.FORMAT_DATE_STR)
	@Temporal(TemporalType.DATE)
	Date tuNgayKdinh;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Contains.FORMAT_DATE_STR)
	@Temporal(TemporalType.DATE)
	Date denNgayKdinh;
}
