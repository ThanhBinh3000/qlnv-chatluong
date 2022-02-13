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
public class QlnvDinhmucHangReq {
	@ApiModelProperty(notes = "Bắt buộc set đối với update")
	private Long id;
	@NotNull(message = "Không được để trống")
	@Size(max = 4, message = "Định mức năm không được vượt quá 4 ký tự")
	String namDmuc;
	@NotNull(message = "Không được để trống")
	@Size(max = 50, message = "Loại hàng DTQG không được vượt quá 50 ký tự")
	String loaiHhoa;
	@NotNull(message = "Không được để trống")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Contains.FORMAT_DATE_STR)
	Date ngayHluc;
	@NotNull(message = "Không được để trống")
	@Size(max = 50, message = "Số biên bản không được vượt quá 50 ký tự")
	String lhinhBquan;
	@NotNull(message = "Không được để trống")
	@Size(max = 20, message = "Cấp đơn vị nhận phí không được vượt quá 20 ký tự")
	String capDvi;
	@NotNull(message = "Không được để trống")
	@Size(max = 250, message = "Định mức nghiệp vụ chuyên môn  không được vượt quá 250 ký tự")
	String dmucNvu;
	@NotNull(message = "Không được để trống")
	@Size(max = 250, message = "Định mức thông tin cá nhân không được vượt quá 250 ký tự")
	String dmucTtin;
	@NotNull(message = "Không được để trống")
	BigDecimal dmucTcuc;
	@NotNull(message = "Không được để trống")
	@Size(max = 50, message = "ĐVT định mức không được vượt quá 50 ký tự")
	String dvtDmuc;
	@NotNull(message = "Không được để trống")
	@Size(max = 50, message = "ĐVT hàng không được vượt quá 50 ký tự")
	String dvtHang;
	@NotNull(message = "Không được để trống")
	@Size(max = 50, message = "Loại định mức không được vượt quá 50 ký tự")
	String loaiDmuc;

	String maDvi;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Contains.FORMAT_DATE_STR)
	Date ngayTao;
	String nguoiTao;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Contains.FORMAT_DATE_STR)
	Date ngaySua;
	String nguoiSua;
	String trangThai;
}
