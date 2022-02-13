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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "QLNV_PBO_TAISAN_HDR")
@Data
@NamedEntityGraph(name = "QLNV_PBO_TAISAN_HDR.children", attributeNodes = @NamedAttributeNode("children"))
public class QlnvPboTaisanHdr implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "QLNV_PBO_TAISAN_HDR_SEQ")
    @SequenceGenerator(sequenceName = "QLNV_PBO_TAISAN_HDR_SEQ", allocationSize = 1, name = "QLNV_PBO_TAISAN_HDR_SEQ")
    private Long id;

    String maDvi;
    Date ngayPbo;
    String tenDvi;
    Date ngayTao;
    String nguoiTao;
    Date ngaySua;
    String nguoiSua;
    String trangThai;
    String soHdong;
    String loaiTsan;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(value = FetchMode.SUBSELECT)
    @JoinColumn(name = "id_hdr")
    private List<QlnvPboTaisanDtl> children = new ArrayList<>();

    public void setChildren(List<QlnvPboTaisanDtl> children) {
        this.children.clear();
        for (QlnvPboTaisanDtl child : children) {
            child.setParent(this);
        }
        this.children.addAll(children);
    }

    public void addChild(QlnvPboTaisanDtl child) {
        child.setParent(this);
        this.children.add(child);
    }
}
