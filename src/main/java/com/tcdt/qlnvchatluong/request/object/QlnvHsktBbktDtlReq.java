package com.tcdt.qlnvchatluong.request.object;

import java.util.List;

import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QlnvHsktBbktDtlReq {
	@ApiModelProperty(notes = "Bắt buộc set đối với update")
	private Long id;

	@Size(max = 250, message = "Tên hồ sơ tài liệu không được vượt quá 250 ký tự")
	String tenTlieu;

	@Size(max = 250, message = "Tên file đính kèm không được vượt quá 250 ký tự")
	String tenFile;

	@Size(max = 50, message = "Loại tài liệu không được vượt quá 50 ký tự")
	String loaiTlieu;

	List<FileDinhKemReq> fileDinhKems;

}
