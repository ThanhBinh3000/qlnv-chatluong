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
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "QLNV_HDONG_TAISAN_HDR")
@Data
@NamedEntityGraph(name = "QLNV_HDONG_TAISAN_HDR.children", attributeNodes = @NamedAttributeNode("children"))
public class QlnvHdongTaisanHdr implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "QLNV_HDONG_TAISAN_HDR_SEQ")
    @SequenceGenerator(sequenceName = "QLNV_HDONG_TAISAN_HDR_SEQ", allocationSize = 1, name = "QLNV_HDONG_TAISAN_HDR_SEQ")
    private Long id;

    String tenHdong;
    String maDvi;
    String soDthoaiDtu;
    Date ngayHdong;
    String tenNhaDtu;
    String ddienDtu;
    String tenDvi;
    String diaChiDtu;
    String soTkhoanNthau;
    Date ngayTao;
    String nguoiTao;
    Date ngaySua;
    String nguoiSua;
    Date ngayGuiDuyet;
    String nguoiGuiDuyet;
    String ldoTuchoi;
    Date ngayPduyet;
    String nguoiPduyet;
    String cvuDtu;
    String trangThai;
    String soTkhoanDtu;
    String soFaxDtu;
    String soHdong;
    String tenNthau;
    String diaChiNthau;
    String soDthoaiNthau;
    String loaiTsan;
    String soFaxNthau;
    String ddienNthau;
    String cvuNthau;
    BigDecimal gtriHdKthue;
    BigDecimal gtriHdCothue;
    String fileName;
    String nganHangDtu;
    String nganHangNthau;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(value = FetchMode.SUBSELECT)
    @JoinColumn(name = "id_hdr")
    private List<QlnvHdongTaisanDtl> children = new ArrayList<>();

    public void setChildren(List<QlnvHdongTaisanDtl> children) {
        this.children.clear();
        for (QlnvHdongTaisanDtl child : children) {
            child.setParent(this);
        }
        this.children.addAll(children);
    }

    public void addChild(QlnvHdongTaisanDtl child) {
        child.setParent(this);
        this.children.add(child);
    }
}
