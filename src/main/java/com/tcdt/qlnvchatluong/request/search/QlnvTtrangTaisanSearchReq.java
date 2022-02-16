package com.tcdt.qlnvchatluong.request.search;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tcdt.qlnvchatluong.request.BaseRequest;
import com.tcdt.qlnvchatluong.util.Contains;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QlnvTtrangTaisanSearchReq extends BaseRequest {
	String soBban;
	String tenTsan;
	String tinhTrang;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Contains.FORMAT_DATE_STR)
	Date ngayPboTu;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Contains.FORMAT_DATE_STR)
	Date ngauPboDen;
}
