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
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;

@Entity
@Table(name = "QLNV_HSKT_BBKT_HDR")
@Data
@NamedEntityGraph(name = "QLNV_HSKT_BBKT_HDR.children", attributeNodes = @NamedAttributeNode("children"))
public class QlnvHsktBbktHdr implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "QLNV_HSKT_BBKT_HDR_SEQ")
	@SequenceGenerator(sequenceName = "QLNV_HSKT_BBKT_HDR_SEQ", allocationSize = 1, name = "QLNV_HSKT_BBKT_HDR_SEQ")
	private Long id;

	String soBban;
	Date ngayLap;

	String cvuNhanDdien;
	String nguoiCcapDdien;
	String nguoiNhanDdien;
	String cvuCcapDdien;
	@Temporal(TemporalType.DATE)
	Date ngayNhap;
	String canCu;
	String diaDiem;
	BigDecimal sluongNhap;
	String ketLuan;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@Fetch(value = FetchMode.SUBSELECT)
	@JoinColumn(name = "id_hdr")
	private List<QlnvHsktBbktDtl> children = new ArrayList<>();

	public void setChildren(List<QlnvHsktBbktDtl> children) {
		this.children.clear();
		for (QlnvHsktBbktDtl child : children) {
			child.setParent(this);
		}
		this.children.addAll(children);
	}

	public void addChild(QlnvHsktBbktDtl child) {
		child.setParent(this);
		this.children.add(child);
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_HSKT")
	@JsonBackReference
	private QlnvHskt parent;

}
