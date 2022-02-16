package com.tcdt.qlnvchatluong.table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "QLNV_PBO_TAISAN_DTL")
@Data
public class QlnvPboTaisanDtl implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "QLNV_PBO_TAISAN_DTL_SEQ")
    @SequenceGenerator(sequenceName = "QLNV_PBO_TAISAN_DTL_SEQ", allocationSize = 1, name = "QLNV_PBO_TAISAN_DTL_SEQ")
    private Long id;

    String maVtu;
    String tenVtu;
    Date tuNgayHdung;
    Date denNgayHdung;
    BigDecimal soLuong;
    String dviTinh;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_hdr")
    private QlnvPboTaisanHdr parent;
}
