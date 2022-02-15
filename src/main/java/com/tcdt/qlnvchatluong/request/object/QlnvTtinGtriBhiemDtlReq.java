package com.tcdt.qlnvchatluong.request.object;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Data
public class QlnvTtinGtriBhiemDtlReq {
    @ApiModelProperty(notes = "Bắt buộc set đối với update")
    private Long id;

    @NotNull(message = "Không được để trống")
    @Size(max = 50, message = "Mã vật tư không được vượt quá 50 ký tự")
    String maVtu;

    @NotNull(message = "Không được để trống")
    @Size(max = 50, message = "Tên vật tư không được vượt quá 50 ký tự")
    String tenVtu;

    @NotNull(message = "Không được để trống")
    BigDecimal giaTriBhiem;
}
