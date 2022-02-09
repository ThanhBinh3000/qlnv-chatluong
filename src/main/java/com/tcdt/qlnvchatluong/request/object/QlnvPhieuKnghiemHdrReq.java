package com.tcdt.qlnvchatluong.request.object;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tcdt.qlnvchatluong.util.Contains;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QlnvPhieuKnghiemHdrReq {
	@ApiModelProperty(notes = "Bắt buộc set đối với update")
	private Long id;
	@NotNull(message = "Không được để trống")
	@Size(max = 50, message = "Số phiếu không được vượt quá 50 ký tự")
	String soPhieu;

	String maDvi;

	@NotNull(message = "Không được để trống")
	@Size(max = 50, message = "Mã kho không được vượt quá 50 ký tự")
	String maKho;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Contains.FORMAT_DATE_STR)
	@Past
	Date ngayLap;

	@NotNull(message = "Không được để trống")
	@Size(max = 50, message = "Mã hàng hóa không được vượt quá 50 ký tự")
	String maHhoa;

	@NotNull(message = "Không được để trống")
	@Size(max = 250, message = "Tên hàng hóa không được vượt quá 250 ký tự")
	String tenHhoa;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Contains.FORMAT_DATE_STR)
	@Past
	Date ngayLayMau;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Contains.FORMAT_DATE_STR)
	@Past
	Date ngayKnghiem;

	@Size(max = 250, message = "Địa điểm không được vượt quá 250 ký tự")
	String diaDiem;

	@NotNull(message = "Không được để trống")
	@Size(max = 2, message = "Hình thức bảo quản không được vượt quá 2 ký tự")
	String hthucBquan;

	BigDecimal sluongBquan;

	@NotNull(message = "Không được để trống")
	@Size(max = 2, message = "Trạng thái không được vượt quá 2 ký tự")
	String trangThai;

	@NotNull(message = "Không được để trống")
	@Size(max = 50, message = "Mã ngăn không được vượt quá 50 ký tự")
	String maNgan;

	@NotNull(message = "Không được để trống")
	@Size(max = 50, message = "Mã lô không được vượt quá 50 ký tự")
	String maLo;

	@NotNull(message = "Không được để trống")
	@Size(max = 50, message = "Số biên bản kết thúc nhập không được vượt quá 50 ký tự")
	String sobbKthucNhap;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Contains.FORMAT_DATE_STR)
	@Past
	Date ngayNhapDay;

	@Size(max = 250, message = "Lý do từ chối không được vượt quá 250 ký tự")
	String ldoTuchoi;

	private List<QlnvPhieuKnghiemDtlReq> detail;
}
