package com.tcdt.qlnvchatluong.request.object;

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
public class QlnvPhieuKtclHdrReq {
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
	Date ngayKtra;

	@NotNull(message = "Không được để trống")
	@Size(max = 50, message = "Mã hàng hóa không được vượt quá 50 ký tự")
	String maHhoa;

	@NotNull(message = "Không được để trống")
	@Size(max = 250, message = "Tên hàng hóa không được vượt quá 250 ký tự")
	String tenHhoa;

	@NotNull(message = "Không được để trống")
	@Size(max = 250, message = "Người bán hàng không được vượt quá 250 ký tự")
	String nguoiBan;

	@Size(max = 250, message = "Địa chỉ không được vượt quá 250 ký tự")
	String diaChi;

	@Size(max = 250, message = "Kết luận không được vượt quá 250 ký tự")
	String ketLuan;

	Long khoiLuong;

	@NotNull(message = "Không được để trống")
	@Size(max = 50, message = "Số hợp đồng không được vượt quá 50 ký tự")
	String soHdong;

	Long lanSuaChua;
	Long lanKtra;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Contains.FORMAT_DATE_STR)
	@Past
	Date ngayHhanBhanh;

	@NotNull(message = "Không được để trống")
	@Size(max = 2, message = "Trạng thái không được vượt quá 2 ký tự")
	String trangThai;

	@NotNull(message = "Không được để trống")
	@Size(max = 50, message = "Mã ngăn không được vượt quá 50 ký tự")
	String maNgan;

	@NotNull(message = "Không được để trống")
	@Size(max = 50, message = "Mã lô không được vượt quá 50 ký tự")
	String maLo;

	@Size(max = 250, message = "Lý do từ chối không được vượt quá 250 ký tự")
	String ldoTuchoi;

	private List<QlnvPhieuKtclDtlReq> detail;
}
