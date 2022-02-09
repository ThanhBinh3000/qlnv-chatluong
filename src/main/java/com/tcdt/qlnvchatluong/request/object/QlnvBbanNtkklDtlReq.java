package com.tcdt.qlnvchatluong.request.object;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QlnvBbanNtkklDtlReq {
	@ApiModelProperty(notes = "Bắt buộc set đối với update")
	private Long id;

	@NotNull(message = "Không được để trống")
	@Size(max = 250, message = "Nội dung không được vượt quá 250 ký tự")
	String noiDung;

	@NotNull(message = "Không được để trống")
	@Size(max = 50, message = "Đơn vị tính không được vượt quá 50 ký tự")
	String dviTinh;

	BigDecimal sluongTnam;
	BigDecimal dgiaTnam;
	BigDecimal ttienTnam;
	BigDecimal sluongQtoan;
	BigDecimal ttienQtoan;
	BigDecimal tongTien;

}
