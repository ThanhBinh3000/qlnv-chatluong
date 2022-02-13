package com.tcdt.qlnvchatluong.request.search;

import com.tcdt.qlnvchatluong.request.BaseRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QlnvDinhmucHaodoiHangSearchReq extends BaseRequest {
	String maHhoa;
	String maDvi;
	String trangThai;
}
