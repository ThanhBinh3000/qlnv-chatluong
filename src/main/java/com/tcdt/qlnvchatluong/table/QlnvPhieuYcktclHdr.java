package com.tcdt.qlnvchatluong.table;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import lombok.Data;

@Entity
@Table(name = "QLNV_PHIEU_YCKTCL_HDR")
@Data
@NamedEntityGraph(name = "QLNV_PHIEU_YCKTCL_HDR.children", attributeNodes = @NamedAttributeNode("children"))
public class QlnvPhieuYcktclHdr implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "QLNV_PHIEU_YCKTCL_HDR_SEQ")
	@SequenceGenerator(sequenceName = "QLNV_PHIEU_YCKTCL_HDR_SEQ", allocationSize = 1, name = "QLNV_PHIEU_YCKTCL_HDR_SEQ")
	private Long id;

	String soPhieu;
	String maDvi;
	String maKho;
	Date ngayKtra;
	String maHhoa;
	String tenHhoa;
	String dviYcau;
	String diaChi;
	String ketLuan;
	Date ngayTao;
	String nguoiTao;
	Date ngaySua;
	String nguoiSua;
	Date ngayGuiDuyet;
	String nguoiGuiDuyet;
	String ldoTuchoi;
	Date ngayPduyet;
	String nguoiPduyet;
	BigDecimal khoiLuong;
	String trangThai;
	String maNgan;
	String maLo;
	String soHdong;
	Integer tgianLuumau;
	Date ngayNhanmau;
	Date ngayTraKqua;
	String ttrangMau;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@Fetch(value = FetchMode.SUBSELECT)
	@JoinColumn(name = "id_hdr")
	private List<QlnvPhieuYcktclDtl> children = new ArrayList<>();

	public void setChildren(List<QlnvPhieuYcktclDtl> children) {
		this.children.clear();
		for (QlnvPhieuYcktclDtl child : children) {
			child.setParent(this);
		}
		this.children.addAll(children);
	}

	public void addChild(QlnvPhieuYcktclDtl child) {
		child.setParent(this);
		this.children.add(child);
	}
}
