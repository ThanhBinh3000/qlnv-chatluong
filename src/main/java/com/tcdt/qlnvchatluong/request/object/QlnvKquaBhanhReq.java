package com.tcdt.qlnvchatluong.request.object;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tcdt.qlnvchatluong.util.Contains;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QlnvKquaBhanhReq {

	@ApiModelProperty(notes = "Bắt buộc set đối với update")
	private Long id;
	
	@NotNull(message = "Không được để trống")
	@Size(max = 50, message = "Số biên bản không được vượt quá 50 ký tự")
	String soBban;
	
	@NotNull(message = "Không được để trống")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Contains.FORMAT_DATE_STR)
	Date ngayBhanh;
	
	@NotNull(message = "Không được để trống")
	@Size(max = 250, message = "Đơn vị bảo hành không được vượt quá 250 ký tự")
	String dviBhanh;
	
	@NotNull(message = "Không được để trống")
	@Size(max = 50, message = "Mã đơn vị không được vượt quá 50 ký tự")
	String maDvi;
	
	@NotNull(message = "Không được để trống")
	@Size(max = 50, message = "Mã hàng hóa không được vượt quá 50 ký tự")
	String maHhoa;
	
	@NotNull(message = "Không được để trống")
	@Size(max = 250, message = "Tên hàng hóa không được vượt quá 250 ký tự")
	String tenHhoa;
	
	@Size(max = 250, message = "Địa chỉ không được vượt quá 250 ký tự")
	String diaChi;
	
	@NotNull(message = "Không được để trống")
	@Size(max = 50, message = "Mã kho không được vượt quá 50 ký tự")
	String maKho;
	
	@NotNull(message = "Không được để trống")
	@Size(max = 50, message = "Mã ngăn không được vượt quá 50 ký tự")
	String maNgan;
	
	@NotNull(message = "Không được để trống")
	@Size(max = 50, message = "Mã Lô không được vượt quá 50 ký tự")
	String maLo;
	
	@NotNull(message = "Không được để trống")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Contains.FORMAT_DATE_STR)
	Date ngayNhanMau;
	
	@NotNull(message = "Không được để trống")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Contains.FORMAT_DATE_STR)
	Date ngayTraKqua;
}
