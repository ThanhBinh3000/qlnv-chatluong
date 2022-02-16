package com.tcdt.qlnvchatluong.request.object;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tcdt.qlnvchatluong.util.Contains;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class QlnvDinhmucHaodoiHangReq {
	@ApiModelProperty(notes = "Bắt buộc set đối với update")
	private Long id;
	@Size(max = 4, message = "Mã định mức không được vượt quá 4 ký tự")
	String maDmuc;
	@Size(max = 50, message = "Cục DTNN KV không được vượt quá 50 ký tự")
	String maDvi;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Contains.FORMAT_DATE_STR)
	Date ngayHluc;
	@Size(max = 2, message = "Mã hàng DTQG không được vượt quá 2 ký tự")
	String maHhoa;
	@Size(max = 250, message = "Tên hàng DTQG không được vượt quá 250 ký tự")
	String tenHhoa;
	BigDecimal tgianBquan;
	@Size(max = 50, message = "Điều kiện bảo quản không được vượt quá 50 ký tự")
	String dkienBquan;
	BigDecimal gtriDmuc;
}
