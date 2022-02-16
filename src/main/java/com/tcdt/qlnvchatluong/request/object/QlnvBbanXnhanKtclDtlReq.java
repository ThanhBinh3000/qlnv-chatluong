package com.tcdt.qlnvchatluong.request.object;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class QlnvBbanXnhanKtclDtlReq {
	@ApiModelProperty(notes = "Bắt buộc set đối với update")
	private Long id;
	@NotNull(message = "Không được để trống")
	@Size(max = 250, message = "Nội dung không được vượt quá 250 ký tự")
	String maCtieu;
	@NotNull(message = "Không được để trống")
	@Size(max = 250, message = "Nội dung không được vượt quá 250 ký tự")
	String tenCtieu;
	@NotNull(message = "Không được để trống")
	@Size(max = 250, message = "Nội dung không được vượt quá 250 ký tự")
	String kquaHdong;
	@NotNull(message = "Không được để trống")
	@Size(max = 250, message = "Nội dung không được vượt quá 250 ký tự")
	String kquaPtich;
	@NotNull(message = "Không được để trống")
	@Size(max = 250, message = "Nội dung không được vượt quá 250 ký tự")
	String ketLuan;
}
