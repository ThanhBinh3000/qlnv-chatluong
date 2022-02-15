package com.tcdt.qlnvchatluong.table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "QLNV_BBAN_GNHAN_TSAN_DTL")
@Data
public class QlnvBbanGnhanTsanDtl implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "QLNV_BBAN_GNHAN_TSAN_DTL_SEQ")
    @SequenceGenerator(sequenceName = "QLNV_BBAN_GNHAN_TSAN_DTL_SEQ", allocationSize = 1, name = "QLNV_BBAN_GNHAN_TSAN_DTL_SEQ")
    private Long id;

    String maVtu;
    String tenVtu;
    BigDecimal soLuong;
    String dviTinh;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_hdr")
    private QlnvBbanGnhanTsanHdr parent;
}
