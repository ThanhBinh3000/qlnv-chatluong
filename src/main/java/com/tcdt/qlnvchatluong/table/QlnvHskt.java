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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

@Entity
@Table(name = "QLNV_HSKT")
@Data
public class QlnvHskt implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "QLNV_HSKT_SEQ")
	@SequenceGenerator(sequenceName = "QLNV_HSKT_SEQ", allocationSize = 1, name = "QLNV_HSKT_SEQ")
	private Long id;

	String soHso;
	String maDvi;
	@Temporal(TemporalType.DATE)
	Date ngayLap;
	String maHhoa;
	String tenHhoa;
	String tenHso;
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
	String soHdong;
	@Temporal(TemporalType.DATE)
	Date ngayKyHdong;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "ID_HSKT")
	private List<QlnvHsktBbktHdr> children = new ArrayList<>();

	public void setChildren(List<QlnvHsktBbktHdr> children) {
		this.children.clear();
		for (QlnvHsktBbktHdr child : children) {
			child.setParent(this);
		}
		this.children.addAll(children);
	}

	public void addChild(QlnvHsktBbktHdr child) {
		child.setParent(this);
		this.children.add(child);
	}

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "ID_HSKT")
	private List<QlnvHsktBbGiaomau> children1 = new ArrayList<>();

	public void setChildren1(List<QlnvHsktBbGiaomau> children1) {
		this.children1.clear();
		for (QlnvHsktBbGiaomau child1 : children1) {
			child1.setParent(this);
		}
		this.children1.addAll(children1);
	}

	public void addChild1(QlnvHsktBbGiaomau child1) {
		child1.setParent(this);
		this.children1.add(child1);
	}

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "ID_HSKT")
	private List<QlnvHsktBbLaymau> children2 = new ArrayList<>();

	public void setChildren2(List<QlnvHsktBbLaymau> children2) {
		this.children2.clear();
		for (QlnvHsktBbLaymau child2 : children2) {
			child2.setParent(this);
		}
		this.children2.addAll(children2);
	}

	public void addChild2(QlnvHsktBbLaymau child2) {
		child2.setParent(this);
		this.children2.add(child2);
	}

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "ID_HSKT")
	private List<QlnvHsktBbktNquan> children3 = new ArrayList<>();

	public void setChildren3(List<QlnvHsktBbktNquan> children3) {
		this.children3.clear();
		for (QlnvHsktBbktNquan child3 : children3) {
			child3.setParent(this);
		}
		this.children3.addAll(children3);
	}

	public void addChild3(QlnvHsktBbktNquan child3) {
		child3.setParent(this);
		this.children3.add(child3);
	}

}
