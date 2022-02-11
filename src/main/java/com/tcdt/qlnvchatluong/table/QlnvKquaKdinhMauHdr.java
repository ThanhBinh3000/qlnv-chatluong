package com.tcdt.qlnvchatluong.table;

import java.io.Serializable;
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
@Table(name = "QLNV_KQUA_KDINH_MAU_HDR")
@Data
@NamedEntityGraph(name = "QLNV_KQUA_KDINH_MAU_HDR.children", attributeNodes = @NamedAttributeNode("children"))
public class QlnvKquaKdinhMauHdr implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "QLNV_KQUA_KDINH_MAU_HDR_SEQ")
	@SequenceGenerator(sequenceName = "QLNV_KQUA_KDINH_MAU_HDR_SEQ", allocationSize = 1, name = "QLNV_KQUA_KDINH_MAU_HDR_SEQ")
	private Long id;
	
	String soBban;
	String maDvi;
	String maKho;
	Date ngayKdinh;
	String maHhoa;
	String tenHhoa;
	String diaChi;
	String dviKdinh;
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
	String trangThai;
	String maNgan;
	String maLo;
	Date ngayNhanMau;
	Date ngayTraKqua;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@Fetch(value = FetchMode.SUBSELECT)
	@JoinColumn(name = "id_hdr")
	private List<QlnvKquaKdinhMauDtl> children = new ArrayList<>();

	public void setChildren(List<QlnvKquaKdinhMauDtl> children) {
		this.children.clear();
		for (QlnvKquaKdinhMauDtl child : children) {
			child.setParent(this);
		}
		this.children.addAll(children);
	}

	public void addChild(QlnvKquaKdinhMauDtl child) {
		child.setParent(this);
		this.children.add(child);
	}
}
