package com.tcdt.qlnvchatluong.request.object;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tcdt.qlnvchatluong.util.Contains;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class QlnvTtrangTaisanReq {
	@ApiModelProperty(notes = "Bắt buộc set đối với update")
	private Long id;
	@Size(max = 50, message = "Mã đơn vị không được vượt quá 50 ký tự")
	String maDvi;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Contains.FORMAT_DATE_STR)
	Date ngayPbo;
	@Size(max = 250, message = "Tên đơn vị không được vượt quá 250 ký tự")
	String tenDvi;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Contains.FORMAT_DATE_STR)
	Date ngayTao;
	@Size(max = 50, message = "Người tạo không được vượt quá 50 ký tự")
	String nguoiTao;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Contains.FORMAT_DATE_STR)
	Date ngaySua;
	@Size(max = 50, message = "Người sửa không được vượt quá 50 ký tự")
	String nguoiSua;
	@Size(max = 50, message = "Số biên bản không được vượt quá 50 ký tự")
	String soBban;
	@Size(max = 2, message = "Mã tài sản không được vượt quá 2 ký tự")
	String loaiTsan;
	@Size(max = 250, message = "Tên tài sản không được vượt quá 250 ký tự")
	String tenTsan;
	@NotNull(message = "Không được để trống")
	BigDecimal soLuong;
	@NotNull(message = "Không được để trống")
	BigDecimal sluongSdung;
	@NotNull(message = "Không được để trống")
	@Size(max = 20, message = "Tình trạng không được vượt quá 20 ký tự")
	String tinhTrang;
	@Size(max = 250, message = "Ghi chú không được vượt quá 250 ký tự")
	String ghiChu;
}
