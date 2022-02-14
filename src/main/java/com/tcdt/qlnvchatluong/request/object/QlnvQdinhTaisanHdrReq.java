package com.tcdt.qlnvchatluong.request.object;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tcdt.qlnvchatluong.util.Contains;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Data
public class QlnvQdinhTaisanHdrReq {
	@ApiModelProperty(notes = "Bắt buộc set đối với update")
	private Long id;
	@NotNull(message = "Không được để trống")
	@Size(max = 50, message = "Số quyết định không được vượt quá 50 ký tự")
	String soQdinh;
	@NotNull(message = "Không được để trống")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Contains.FORMAT_DATE_STR)
	Date ngayQdinh;
	@NotNull(message = "Không được để trống")
	@Size(max = 250, message = "Tiêu đề không được vượt quá 250 ký tự")
	String tieuDe;
	@NotNull(message = "Không được để trống")
	@Size(max = 50, message = "Nơi ban hành không được vượt quá 50 ký tự")
	String noiBhanh;
	@NotNull(message = "Không được để trống")
	@Size(max = 500, message = "Trích yếu không được vượt quá 500 ký tự")
	String trichYeu;
	@Size(max = 250, message = "Filename không được vượt quá 250 ký tự")
	String fileName;

	private List<QlnvQdinhTaisanDtlReq> detail;
}
