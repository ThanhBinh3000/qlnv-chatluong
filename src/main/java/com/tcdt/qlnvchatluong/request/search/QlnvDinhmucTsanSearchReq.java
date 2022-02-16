package com.tcdt.qlnvchatluong.request.search;

import com.tcdt.qlnvchatluong.request.BaseRequest;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QlnvDinhmucTsanSearchReq extends BaseRequest {

	@ApiModelProperty(example = "2022")
	String namDmuc;

	String capDvi;

	String maTsan;

}