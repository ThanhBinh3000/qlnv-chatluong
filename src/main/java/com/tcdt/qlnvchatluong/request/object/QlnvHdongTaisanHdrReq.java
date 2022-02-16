package com.tcdt.qlnvchatluong.request.object;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tcdt.qlnvchatluong.util.Contains;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class QlnvHdongTaisanHdrReq {

    @ApiModelProperty(notes = "Bắt buộc set đối với update")
    private Long id;

    @NotNull(message = "Không được để trống")
    @Size(max = 50, message = "Tên hợp đồng không được vượt quá 50 ký tự")
    String tenHdong;

    @NotNull(message = "Không được để trống")
    @Size(max = 50, message = "Số điện thoại đầu tư không được vượt quá 50 ký tự")
    String soDthoaiDtu;

    @NotNull(message = "Không được để trống")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Contains.FORMAT_DATE_STR)
    Date ngayHdong;

    @NotNull(message = "Không được để trống")
    @Size(max = 50, message = "Số hợp đồng không được vượt quá 50 ký tự")
    String soHdong;

    @NotNull(message = "Không được để trống")
    @Size(max = 250, message = "Tên nhà đầu tư không được vượt quá 250 ký tự")
    String tenNhaDtu;

    @NotNull(message = "Không được để trống")
    @Size(max = 250, message = "Đại diện đầu tư không được vượt quá 250 ký tự")
    String ddienDtu;

    @NotNull(message = "Không được để trống")
    @Size(max = 250, message = "Địa chỉ đầu tư không được vượt quá 250 ký tự")
    String diaChiDtu;

    @Size(max = 50, message = "Chức vụ đầu tư không được vượt quá 50 ký tự")
    String cvuDtu;

    @NotNull(message = "Không được để trống")
    @Size(max = 50, message = "Số tài khoản đầu tư không được vượt quá 50 ký tự")
    String soTkhoanDtu;

    @Size(max = 50, message = "Số fax đầu tư không được vượt quá 50 ký tự")
    String soFaxDtu;

    @NotNull(message = "Không được để trống")
    @Size(max = 50, message = "Số tài khoản nhà thầu không được vượt quá 50 ký tự")
    String soTkhoanNthau;

    @NotNull(message = "Không được để trống")
    @Size(max = 250, message = "Tên nhà thầu không được vượt quá 250 ký tự")
    String tenNthau;

    @NotNull(message = "Không được để trống")
    @Size(max = 250, message = "Địa chỉ nhà thầu không được vượt quá 250 ký tự")
    String diaChiNthau;

    @NotNull(message = "Không được để trống")
    @Size(max = 50, message = "Số điện thoại nhà thầu không được vượt quá 50 ký tự")
    String soDthoaiNthau;

    @NotNull(message = "Không được để trống")
    @Size(max = 02, message = "Loại tài sản không được vượt quá 02 ký tự")
    String loaiTsan;

    @Size(max = 50, message = "Số fax nhà thầu không được vượt quá 50 ký tự")
    String soFaxNthau;

    @NotNull(message = "Không được để trống")
    @Size(max = 250, message = "Đại diện nhà thầu không được vượt quá 250 ký tự")
    String ddienNthau;

    @Size(max = 250, message = "Chức vụ nhà thầu không được vượt quá 250 ký tự")
    String cvuNthau;

    @NotNull(message = "Không được để trống")
    BigDecimal gtriHdKthue;

    @NotNull(message = "Không được để trống")
    BigDecimal gtriHdCothue;

    @Size(max = 250, message = "File name không được vượt quá 250 ký tự")
    String fileName;

    @NotNull(message = "Không được để trống")
    @Size(max = 250, message = "Ngân hàng đầu tư không được vượt quá 250 ký tự")
    String nganHangDtu;

    @NotNull(message = "Không được để trống")
    @Size(max = 250, message = "Ngân hàng nhà thầu không được vượt quá 250 ký tự")
    String nganHangNthau;

    private List<QlnvHdongTaisanDtlReq> detail;
}
