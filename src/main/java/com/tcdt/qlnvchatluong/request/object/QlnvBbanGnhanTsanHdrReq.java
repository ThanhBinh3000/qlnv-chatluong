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
public class QlnvBbanGnhanTsanHdrReq {
    @ApiModelProperty(notes = "Bắt buộc set đối với update")
    private Long id;

    @NotNull(message = "Không được để trống")
    @Size(max = 50, message = "Số biên bản không được vượt quá 50 ký tự")
    String soBban;

    @NotNull(message = "Không được để trống")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Contains.FORMAT_DATE_STR)
    Date ngayLap;

    @NotNull(message = "Không được để trống")
    @Size(max = 50, message = "Mã đơn vị giao không được vượt quá 50 ký tự")
    String maDviGiao;

    @NotNull(message = "Không được để trống")
    @Size(max = 250, message = "Tên đơn vị giao không được vượt quá 250 ký tự")
    String tenDviGiao;

    @Size(max = 250, message = "Đại diện giao không được vượt quá 250 ký tự")
    String ddienGiao;

    @Size(max = 50, message = "Chức vụ giao không được vượt quá 50 ký tự")
    String cvuGiao;

    @NotNull(message = "Không được để trống")
    @Size(max = 02, message = "Loại tài sản không được vượt quá 02 ký tự")
    String loaiTsan;

    @Size(max = 250, message = "Đại diện nhận không được vượt quá 250 ký tự")
    String ddienNhan;

    @Size(max = 250, message = "Chức vụ nhận không được vượt quá 250 ký tự")
    String cvuNhan;

    @NotNull(message = "Không được để trống")
    @Size(max = 50, message = "Mã đơn vị nhận không được vượt quá 50 ký tự")
    String maDviNhan;

    @NotNull(message = "Không được để trống")
    @Size(max = 50, message = "Tên đơn vị nhận không được vượt quá 50 ký tự")
    String tenDviNhan;

    private List<QlnvBbanGnhanTsanDtlReq> detail;
}
