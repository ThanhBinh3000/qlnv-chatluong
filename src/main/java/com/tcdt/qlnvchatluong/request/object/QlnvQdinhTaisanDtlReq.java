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
public class QlnvQdinhTaisanDtlReq {
	@ApiModelProperty(notes = "Bắt buộc set đối với update")
	private Long id;
	@NotNull(message = "Không được để trống")
	@Size(max = 50, message = "Mã vật tư không được vượt quá 50 ký tự")
	String maVtu;
	@NotNull(message = "Không được để trống")
	@Size(max = 250, message = "Tên vật tư không được vượt quá 250 ký tự")
	String tenVtu;
	@NotNull(message = "Không được để trống")
	@Size(max = 50, message = "Đơn vị tính không được vượt quá 50 ký tự")
	String dviTinh;
	@NotNull(message = "Không được để trống")
	BigDecimal soLuong;
	@NotNull(message = "Không được để trống")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Contains.FORMAT_DATE_STR)
	Date tdiemDapung;
	@NotNull(message = "Không được để trống")
	@Size(max = 250, message = "Tên chỉ tiêu không được vượt quá 250 ký tự")
	String tenCtieu;
}
