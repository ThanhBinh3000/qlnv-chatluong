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
public class QlnvTtinGtriBhiemHdrReq {
    @ApiModelProperty(notes = "Bắt buộc set đối với update")
    private Long id;

    @NotNull(message = "Không được để trống")
    @Size(max = 50, message = "Số hợp đồng không được vượt quá 50 ký tự")
    String soHdong;

    @NotNull(message = "Không được để trống")
    @Size(max = 50, message = "Mã đơn vị yêu cầu không được vượt quá 50 ký tự")
    String maDviYcau;

    @NotNull(message = "Không được để trống")
    @Size(max = 500, message = "Nội dung không được vượt quá 500 ký tự")
    String noiDung;

    @NotNull(message = "Không được để trống")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Contains.FORMAT_DATE_STR)
    Date ngayHdong;

    @NotNull(message = "Không được để trống")
    @Size(max = 250, message = "Tên đơn vị bảo hiểm không được vượt quá 250 ký tự")
    String tenDviBhiem;

    @NotNull(message = "Không được để trống")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Contains.FORMAT_DATE_STR)
    Date ngayHetHluc;

    @NotNull(message = "Không được để trống")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Contains.FORMAT_DATE_STR)
    Date ngayHluc;

    private List<QlnvTtinGtriBhiemDtlReq> detail;
}
