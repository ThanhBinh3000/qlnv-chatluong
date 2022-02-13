package com.tcdt.qlnvchatluong.request.object;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tcdt.qlnvchatluong.util.Contains;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Data
public class QlnvPboTaisanHdrReq {

    @ApiModelProperty(notes = "Bắt buộc set đối với update")
    private Long id;

    @Size(max = 50, message = "Số hợp đồng không được vượt quá 50 ký tự")
    String soHdong;

    @Size(max = 50, message = "Mã đơn vị không được vượt quá 50 ký tự")
    String maDvi;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Contains.FORMAT_DATE_STR)
    Date ngayPbo;

    @Size(max = 250, message = "Mã đơn vị không được vượt quá 250 ký tự")
    String tenDvi;

    private List<QlnvPboTaisanDtlReq> detail;
}
