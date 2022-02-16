package com.tcdt.qlnvchatluong.request.search;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tcdt.qlnvchatluong.request.BaseRequest;
import com.tcdt.qlnvchatluong.util.Contains;
import lombok.Data;

import java.util.Date;

@Data
public class QlnvTtrangTaisanSearchReq extends BaseRequest {
	String soBban;
	String tenTsan;
	String tinhTrang;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Contains.FORMAT_DATE_STR)
	Date ngayPboTu;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Contains.FORMAT_DATE_STR)
	Date ngauPboDen;
}
