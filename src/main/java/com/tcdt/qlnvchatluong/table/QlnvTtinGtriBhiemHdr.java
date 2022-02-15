package com.tcdt.qlnvchatluong.table;

import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "QLNV_TTIN_GTRI_BHIEM_HDR")
@Data
@NamedEntityGraph(name = "QLNV_TTIN_GTRI_BHIEM_HDR.children", attributeNodes = @NamedAttributeNode("children"))
public class QlnvTtinGtriBhiemHdr implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "QLNV_TTIN_GTRI_BHIEM_HDR_SEQ")
    @SequenceGenerator(sequenceName = "QLNV_TTIN_GTRI_BHIEM_HDR_SEQ", allocationSize = 1, name = "QLNV_TTIN_GTRI_BHIEM_HDR_SEQ")
    private Long id;

    String soHdong;
    String maDviYcau;
    String noiDung;
    Date ngayHdong;
    String tenDviBhiem;
    Date ngayTao;
    String nguoiTao;
    Date ngaySua;
    String nguoiSua;
    Date ngayHetHluc;
    String trangThai;
    Date ngayHluc;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(value = FetchMode.SUBSELECT)
    @JoinColumn(name = "id_hdr")
    private List<QlnvTtinGtriBhiemDtl> children = new ArrayList<>();

    public void setChildren(List<QlnvTtinGtriBhiemDtl> children) {
        this.children.clear();
        for (QlnvTtinGtriBhiemDtl child : children) {
            child.setParent(this);
        }
        this.children.addAll(children);
    }

    public void addChild(QlnvTtinGtriBhiemDtl child) {
        child.setParent(this);
        this.children.add(child);
    }
}
