package com.tcdt.qlnvchatluong.request.object;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QlnvPhieuKnghiemDtlReq{
	@ApiModelProperty(notes = "Bắt buộc set đối với update")
	private Long id;
	
	@NotNull(message = "Không được để trống")
	@Size(max = 50, message = "Mã chỉ tiêu không được vượt quá 50 ký tự")
	String maCtieu;
	
	@NotNull(message = "Không được để trống")
	@Size(max = 250, message = "Tên chỉ tiêu không được vượt quá 250 ký tự")
	String tenCtieu;
	
	@NotNull(message = "Không được để trống")
	@Size(max = 250, message = "Phương pháp xác định không được vượt quá 250 ký tự")
	String pphapXdinh;
	
	@NotNull(message = "Không được để trống")
	@Size(max = 50, message = "Đơn vị tính không được vượt quá 50 ký tự")
	String dviTinh;
	
	@NotNull(message = "Không được để trống")
	@Size(max = 250, message = "Kết quả kiểm nghiệm không được vượt quá 250 ký tự")
	String kquaKnghiem;
	
}
