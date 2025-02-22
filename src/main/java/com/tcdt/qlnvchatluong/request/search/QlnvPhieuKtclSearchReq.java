package com.tcdt.qlnvchatluong.request.search;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tcdt.qlnvchatluong.request.BaseRequest;
import com.tcdt.qlnvchatluong.util.Contains;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QlnvPhieuKtclSearchReq extends BaseRequest {

	@ApiModelProperty(example = "QD1234")
	String soPhieu;

	@ApiModelProperty(example = "0")
	String maDvi;
	String maHhoa;
	String maNgan;
	String maLo;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Contains.FORMAT_DATE_STR)
	@Past
	@Temporal(TemporalType.DATE)
	Date tuNgayKtra;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Contains.FORMAT_DATE_STR)
	@Past
	@Temporal(TemporalType.DATE)
	Date denNgayKtra;
	
	@NotNull(message = "Không được để trống")
	@Size(max = 02, message = "Loại biên bản không được vượt quá 02 ký tự")
	String loaiBban;

}