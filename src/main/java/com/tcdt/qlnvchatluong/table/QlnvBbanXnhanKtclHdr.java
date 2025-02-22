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
@Table(name = "QLNV_BBAN_XNHAN_KTCL_HDR")
@Data
@NamedEntityGraph(name = "QLNV_BBAN_XNHAN_KTCL_HDR.children", attributeNodes = @NamedAttributeNode("children"))
public class QlnvBbanXnhanKtclHdr implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "QLNV_BBAN_XNHAN_KTCL_HDR_SEQ")
	@SequenceGenerator(sequenceName = "QLNV_BBAN_XNHAN_KTCL_HDR_SEQ", allocationSize = 1, name = "QLNV_BBAN_XNHAN_KTCL_HDR_SEQ")
	private Long id;
	String soBban;
	Date ngayLap;
	String nguoiNhanDdien;
	String cvuNhanDdien;
	String nguoiCcapDdien;
	String cvuCcapDdien;
	String canCu;
	String diaDiem;
	String maHhoa;
	String tenHhoa;
	String soHdong;
	Date ngayHdong;
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
	String tenLo;
	String tenNgan;
	String tenDiemkho;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@Fetch(value = FetchMode.SUBSELECT)
	@JoinColumn(name = "id_hdr")
	private List<QlnvBbanXnhanKtclDtl> children = new ArrayList<>();

	public void setChildren(List<QlnvBbanXnhanKtclDtl> children) {
		this.children.clear();
		for (QlnvBbanXnhanKtclDtl child : children) {
			child.setParent(this);
		}
		this.children.addAll(children);
	}

	public void addChild(QlnvBbanXnhanKtclDtl child) {
		child.setParent(this);
		this.children.add(child);
	}
}
