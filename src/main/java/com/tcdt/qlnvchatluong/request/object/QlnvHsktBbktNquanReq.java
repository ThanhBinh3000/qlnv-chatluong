package com.tcdt.qlnvchatluong.request.object;

import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tcdt.qlnvchatluong.util.Contains;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QlnvHsktBbktNquanReq {
	@ApiModelProperty(notes = "Bắt buộc set đối với update")
	private Long id;

	@Size(max = 50, message = "Số biên bản không được vượt quá 50 ký tự")
	String soBban;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Contains.FORMAT_DATE_STR)
	@Past
	Date ngayLap;

	@Size(max = 50, message = "Chức vụ người đại diện đơn vị nhận hàng không được vượt quá 50 ký tự")
	String cvuNhanDdien;

	@Size(max = 250, message = "Người đại diện đơn vị cung cấp không được vượt quá 250 ký tự")
	String nguoiCcapDdien;

	@Size(max = 250, message = "Người đại diện đơn vị nhận hàng không được vượt quá 250 ký tự")
	String nguoiNhanDdien;

	@Size(max = 50, message = "Chức vụ người đại diện đơn vị cung cấp không được vượt quá 50 ký tự")
	String cvuCcapDdien;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Contains.FORMAT_DATE_STR)
	@Past
	Date ngayNhap;

	@Size(max = 500, message = "Căn cứ không được vượt quá 500 ký tự")
	String canCu;

	@Size(max = 250, message = "Địa điểm không được vượt quá 250 ký tự")
	String diaDiem;

	BigDecimal sluongNhap;

	@Size(max = 250, message = "Kiểm tra bao bì không được vượt quá 250 ký tự")
	String ttrangBaobi;

	BigDecimal sluongMau;

	@Size(max = 20, message = "Phương pháp lấy mẫu không được vượt quá 20 ký tự")
	String pphapLaymau;

	@Size(max = 50, message = "Ký mã hiệu, seri không được vượt quá 50 ký tự")
	String kyHieu;

	@Size(max = 250, message = "Chỉ tiêu kiểm tra không được vượt quá 250 ký tự")
	String ctieuKtra;

	@Size(max = 20, message = "Phương pháp kiểm tra không được vượt quá 20 ký tự")
	String pphapKtra;

	@Size(max = 250, message = "Vận hành sử dụng không được vượt quá 250 ký tự")
	String ttrangSdung;

	@Size(max = 250, message = "Phụ kiện kèm theo không được vượt quá 250 ký tự")
	String phuKien;

	@Size(max = 250, message = "Kết luận không được vượt quá 250 ký tự")
	String ketLuan;

}
