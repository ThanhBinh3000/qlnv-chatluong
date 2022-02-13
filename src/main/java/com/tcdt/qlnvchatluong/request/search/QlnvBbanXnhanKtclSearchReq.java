package com.tcdt.qlnvchatluong.request.search;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tcdt.qlnvchatluong.request.BaseRequest;
import com.tcdt.qlnvchatluong.util.Contains;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Past;
import java.util.Date;

@Getter
@Setter
public class QlnvBbanXnhanKtclSearchReq extends BaseRequest {
	String soBban;
	String maDvi;
	String maHhoa;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Contains.FORMAT_DATE_STR)
	@Past
	@Temporal(TemporalType.DATE)
	Date ngayKtraTu;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Contains.FORMAT_DATE_STR)
	@Past
	@Temporal(TemporalType.DATE)
	Date ngayKtraDen;
	String soHdong;
}
