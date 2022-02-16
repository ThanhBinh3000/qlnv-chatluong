package com.tcdt.qlnvchatluong.table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "QLNV_HDONG_TAISAN_DTL")
public class QlnvHdongTaisanDtl implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "QLNV_HDONG_TAISAN_DTL_SEQ")
    @SequenceGenerator(sequenceName = "QLNV_HDONG_TAISAN_DTL_SEQ", allocationSize = 1, name = "QLNV_HDONG_TAISAN_DTL_SEQ")
    private Long id;

    String maVtu;
    String tenVtu;
    BigDecimal thanhTien;
    String dviTinh;
    BigDecimal donGia;
    BigDecimal soLuong;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_hdr")
    private QlnvHdongTaisanHdr parent;
}
