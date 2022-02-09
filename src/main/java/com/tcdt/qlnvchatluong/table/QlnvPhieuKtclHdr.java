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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import lombok.Data;

@Entity
@Table(name = "QLNV_PHIEU_KTCL_HDR")
@Data
@NamedEntityGraph(name = "QLNV_PHIEU_KTCL_HDR.children", attributeNodes = @NamedAttributeNode("children"))
public class QlnvPhieuKtclHdr implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "QLNV_PHIEU_KTCL_HDR_SEQ")
	@SequenceGenerator(sequenceName = "QLNV_PHIEU_KTCL_HDR_SEQ", allocationSize = 1, name = "QLNV_PHIEU_KTCL_HDR_SEQ")
	private Long id;

	String soPhieu;
	String maDvi;
	String maKho;
	@Temporal(TemporalType.DATE)
	Date ngayKtra;
	String maHhoa;
	String tenHhoa;
	String nguoiBan;
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
	Long khoiLuong;
	String trangThai;
	String maNgan;
	String maLo;
	String soHdong;
	Long lanSuaChua;
	Long lanKtra;
	Date ngayHhanBhanh;
	String loaiBban;
	String ktvPduyet;
	Date ngayKtvPduyet;
	Date ngayHdong;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@Fetch(value = FetchMode.SUBSELECT)
	@JoinColumn(name = "id_hdr")
	private List<QlnvPhieuKtclDtl> children = new ArrayList<>();

	public void setChildren(List<QlnvPhieuKtclDtl> children) {
		this.children.clear();
		for (QlnvPhieuKtclDtl child : children) {
			child.setParent(this);
		}
		this.children.addAll(children);
	}

	public void addChild(QlnvPhieuKtclDtl child) {
		child.setParent(this);
		this.children.add(child);
	}

}
