package com.tcdt.qlnvchatluong.request.object;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tcdt.qlnvchatluong.util.Contains;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QlnvVbanYcauHangHdrReq {
	@ApiModelProperty(notes = "Bắt buộc set đối với update")
	private Long id;
	
	@NotNull(message = "Không được để trống")
	@Size(max = 50, message = "Số yêu cầu không được vượt quá 50 ký tự")
	String soYcau;
	
	@NotNull(message = "Không được để trống")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Contains.FORMAT_DATE_STR)
	Date ngayYcau;
	
	@NotNull(message = "Không được để trống")
	@Size(max = 50, message = "Mã đơn vị không được vượt quá 50 ký tự")
	String maDvi;
	
	@Size(max = 50, message = "Người đại diện không được vượt quá 50 ký tự")
	String nguoiDdien;
	
	@Size(max = 250, message = "Chức vụ đại diện không được vượt quá 250 ký tự")
	String cvuDdien;
	
	@NotNull(message = "Không được để trống")
	@Size(max = 50, message = "Số hợp đồng không được vượt quá 50 ký tự")
	String soHdong;
	
	private List<QlnvVbanYcauHangDtlReq> detail;
}
