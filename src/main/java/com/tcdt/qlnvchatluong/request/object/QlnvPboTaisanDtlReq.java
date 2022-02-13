package com.tcdt.qlnvchatluong.request.object;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tcdt.qlnvchatluong.util.Contains;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class QlnvPboTaisanDtlReq {

    @ApiModelProperty(notes = "Bắt buộc set đối với update")
    private Long id;

    @NotNull(message = "Không được để trống")
    @Size(max = 50, message = "Số hợp đồng không được vượt quá 50 ký tự")
    String maVtu;

    @NotNull(message = "Không được để trống")
    @Size(max = 250, message = "Tên vật tư không được vượt quá 250 ký tự")
    String tenVtu;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Contains.FORMAT_DATE_STR)
    @NotNull(message = "Không được để trống")
    Date tuNgayHdung;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Contains.FORMAT_DATE_STR)
    @NotNull(message = "Không được để trống")
    Date denNgayHdung;

    @NotNull(message = "Không được để trống")
    BigDecimal soLuong;

    @NotNull(message = "Không được để trống")
    @Size(max = 50, message = "Đơn vị tính không được vượt quá 50 ký tự")
    String dviTinh;
}
