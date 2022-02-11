package com.tcdt.qlnvchatluong.request.object;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QlnvKquaKdinhMauDtlReq {

	@ApiModelProperty(notes = "Bắt buộc set đối với update")
	private Long id;

	@NotNull(message = "Không được để trống")
	@Size(max = 50, message = "Mã chỉ tiêu không được vượt quá 50 ký tự")
	String maCtieu;
	
	@NotNull(message = "Không được để trống")
	@Size(max = 250, message = "Tên chỉ tiêu không được vượt quá 250 ký tự")
	String tenCtieu;
	
	@NotNull(message = "Không được để trống")
	@Size(max = 50, message = "Tiêu chuẩn không được vượt quá 50 ký tự")
	String tieuChuan;
	
	@NotNull(message = "Không được để trống")
	@Size(max = 50, message = "Kết quả kiểm tra không được vượt quá 50 ký tự")
	String kquaKtra;
	
	@NotNull(message = "Không được để trống")
	@Size(max = 250, message = "Kết luận không được vượt quá 250 ký tự")
	String ketLuan;
	
	@NotNull(message = "Không được để trống")
	@Size(max = 50, message = "Phương pháp xác định không được vượt quá 50 ký tự")
	String pphapXacDinh;
}
