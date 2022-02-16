package com.tcdt.qlnvchatluong.request.search;

import com.tcdt.qlnvchatluong.request.BaseRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QlnvDinhmucHangSearchReq extends BaseRequest {
	String namDmuc;
	String loaiDmuc;
	String loaiHhoa;
	String capDvi;
	String trangThai;
}
