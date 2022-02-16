package com.tcdt.qlnvchatluong.table;

import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "QLNV_BBAN_GNHAN_TSAN_HDR")
@Data
@NamedEntityGraph(name = "QLNV_BBAN_GNHAN_TSAN_HDR.children", attributeNodes = @NamedAttributeNode("children"))
public class QlnvBbanGnhanTsanHdr implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "QLNV_BBAN_GNHAN_TSAN_HDR_SEQ")
    @SequenceGenerator(sequenceName = "QLNV_BBAN_GNHAN_TSAN_HDR_SEQ", allocationSize = 1, name = "QLNV_BBAN_GNHAN_TSAN_HDR_SEQ")
    private Long id;

    String maDviGiao;
    Date ngayLap;
    String ddienGiao;
    String tenDviGiao;
    Date ngayTao;
    String nguoiTao;
    Date ngaySua;
    String nguoiSua;
    Date ngayGuiDuyet;
    String nguoiGuiDuyet;
    String ldoTuchoi;
    Date ngayPduyet;
    String nguoiPduyet;
    String cvuGiao;
    String trangThai;
    String soBban;
    String loaiTsan;
    String ddienNhan;
    String cvuNhan;
    String maDviNhan;
    String tenDviNhan;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(value = FetchMode.SUBSELECT)
    @JoinColumn(name = "id_hdr")
    private List<QlnvBbanGnhanTsanDtl> children = new ArrayList<>();

    public void setChildren(List<QlnvBbanGnhanTsanDtl> children) {
        this.children.clear();
        for (QlnvBbanGnhanTsanDtl child : children) {
            child.setParent(this);
        }
        this.children.addAll(children);
    }

    public void addChild(QlnvBbanGnhanTsanDtl child) {
        child.setParent(this);
        this.children.add(child);
    }
}
