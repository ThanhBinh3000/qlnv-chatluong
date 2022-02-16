package com.tcdt.qlnvchatluong.request.object;

import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tcdt.qlnvchatluong.util.Contains;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QlnvDinhmucTsanReq {
	@ApiModelProperty(notes = "Bắt buộc set đối với update")
	private Long id;

	@NotNull(message = "Không được để trống")
	@Size(max = 4, message = "Định mức năm không được vướt quá 4 ký tự")
	String namDmuc;

	@NotNull(message = "Không được để trống")
	@Size(max = 50, message = "Mã đơn vị không được vướt quá 50 ký tự")
	String maDvi;

	@NotNull(message = "Không được để trống")
	@Size(max = 20, message = "Cấp đơn vị không được vướt quá 20 ký tự")
	String capDvi;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Contains.FORMAT_DATE_STR)
	@Past
	Date ngayHluc;

	@NotNull(message = "Không được để trống")
	@Size(max = 50, message = "Cấp đơn vị không được vướt quá 50 ký tự")
	String maTsan;

	@NotNull(message = "Không được để trống")
	@Size(max = 500, message = "Cấp đơn vị không được vướt quá 500 ký tự")
	String tenTsan;

	@NotNull(message = "Không được để trống")
	@Size(max = 2, message = "Trạng thái không được vượt quá 2 ký tự")
	String trangThai;

	BigDecimal sluongHang;

	@NotNull(message = "Không được để trống")
	@Size(max = 50, message = "Đơn vị tính hàng DTGD không được vượt quá 50 ký tự")
	String dvtHang;

	@NotNull(message = "Không được để trống")
	@Size(max = 50, message = "Đơn vị tính định mức không được vượt quá 50 ký tự")
	String dvtDmuc;

	BigDecimal dinhMuc;
	BigDecimal donGiaTda;
	
	@NotNull(message = "Không được để trống")
	@Size(max = 2, message = "Loại định mức không được vượt quá 2 ký tự")
	String loaiDmuc;

}
