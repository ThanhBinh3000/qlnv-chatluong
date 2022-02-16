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
public class QlnvBbanXnhanKtclHdrReq {
	@ApiModelProperty(notes = "Bắt buộc set đối với update")
	private Long id;
	@NotNull(message = "Không được để trống")
	@Size(max = 50, message = "Số biên bản không được vượt quá 50 ký tự")
	String soBban;
	@NotNull(message = "Không được để trống")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Contains.FORMAT_DATE_STR)
	Date ngayLap;
	@Size(max = 250, message = "Tên người đại diện đơn vị nhận hàng không được vượt quá 250 ký tự")
	String nguoiNhanDdien;
	@Size(max = 250, message = "Chức vụ người đại diện không được vượt quá 250 ký tự")
	String cvuNhanDdien;
	@Size(max = 250, message = "Tên người đại diện đơn vị cung cấp không được vượt quá 50 ký tự")
	String nguoiCcapDdien;
	@Size(max = 250, message = "Chức vụ người đại diện không được vượt quá 50 ký tự")
	String cvuCcapDdien;
	@Size(max = 500, message = "Căn cứ không được vượt quá 500 ký tự")
	String canCu;
	@Size(max = 250, message = "Địa điểm kiểm tra không được vượt quá 250 ký tự")
	String diaDiem;
	@NotNull(message = "Không được để trống")
	@Size(max = 2, message = "Mã hàng hóa kiểm tra không được vượt quá 2 ký tự")
	String maHhoa;
	@NotNull(message = "Không được để trống")
	@Size(max = 250, message = "Tên hàng hóa kiểm tra không được vượt quá 250 ký tự")
	String tenHhoa;
	@NotNull(message = "Không được để trống")
	@Size(max = 50, message = "Số hợp đồng không được vượt quá 50 ký tự")
	String soHdong;
	@NotNull(message = "Không được để trống")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Contains.FORMAT_DATE_STR)
	Date ngayHdong;
	@Size(max = 250, message = "Kết luận sau khi kiểm tra không được vượt quá 250 ký tự")
	String ketLuan;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Contains.FORMAT_DATE_STR)
	Date ngaySua;
	@Size(max = 250, message = "Điểm kho lưu trữ hàng không được vượt quá 250 ký tự")
	String tenDiemkho;
	@Size(max = 50, message = "Ngăn để hàng không được vượt quá 50 ký tự")
	String tenNgan;
	@Size(max = 50, message = "Lô để hàng không được vượt quá 50 ký tự")
	String tenLo;

	private List<QlnvBbanXnhanKtclDtlReq> detail;
}
