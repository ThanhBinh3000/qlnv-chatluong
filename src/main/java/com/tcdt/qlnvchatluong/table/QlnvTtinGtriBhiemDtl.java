package com.tcdt.qlnvchatluong.table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "QLNV_TTIN_GTRI_BHIEM_DTL")
@Data
public class QlnvTtinGtriBhiemDtl implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "QLNV_TTIN_GTRI_BHIEM_DTL_SEQ")
    @SequenceGenerator(sequenceName = "QLNV_TTIN_GTRI_BHIEM_DTL_SEQ", allocationSize = 1, name = "QLNV_TTIN_GTRI_BHIEM_DTL_SEQ")
    private Long id;

    String maVtu;
    String tenVtu;
    BigDecimal giaTriBhiem;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_hdr")
    private QlnvTtinGtriBhiemHdr parent;
}
