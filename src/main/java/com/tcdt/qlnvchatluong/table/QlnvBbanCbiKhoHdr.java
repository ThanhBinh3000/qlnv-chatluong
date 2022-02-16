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
@Table(name = "QLNV_BBAN_CBI_KHO_HDR")
@Data
@NamedEntityGraph(name = "QLNV_BBAN_CBI_KHO_HDR.children", attributeNodes = @NamedAttributeNode("children"))
public class QlnvBbanCbiKhoHdr implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "QLNV_BBAN_CBI_KHO_HDR_SEQ")
	@SequenceGenerator(sequenceName = "QLNV_BBAN_CBI_KHO_HDR_SEQ", allocationSize = 1, name = "QLNV_BBAN_CBI_KHO_HDR_SEQ")
	private Long id;
	
	String soBban;
	String maDvi;
	String maKho;
	Date ngayLap;
	String maHhoa;
	String tenHhoa;
	String daiDien;
	String chucVu;
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
	String thuKho;
	String pthucBquan;
	Double dmucGiao;
	Date ngayKthuc;
	BigDecimal tongKinhPhi;
	
	String tkPduyet;
	Date ngayTkPduyet;
	String kttPduyet;
	Date ngayKttPduyet;
	String ktvPduyet;
	Date ngayKtvPduyet;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@Fetch(value = FetchMode.SUBSELECT)
	@JoinColumn(name = "id_hdr")
	private List<QlnvBbanCbiKhoDtl> children = new ArrayList<>();

	public void setChildren(List<QlnvBbanCbiKhoDtl> children) {
		this.children.clear();
		for (QlnvBbanCbiKhoDtl child : children) {
			child.setParent(this);
		}
		this.children.addAll(children);
	}

	public void addChild(QlnvBbanCbiKhoDtl child) {
		child.setParent(this);
		this.children.add(child);
	}

}
