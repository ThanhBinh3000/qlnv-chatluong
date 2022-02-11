package com.tcdt.qlnvchatluong.table;

import java.io.Serializable;
import java.util.ArrayList;
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

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;

@Entity
@Table(name = QlnvHsktBbktDtl.TABLE_NAME)
@Data
@NamedEntityGraph(name = "QLNV_HSKT_BBKT_DTL.children", attributeNodes = @NamedAttributeNode("children"))
public class QlnvHsktBbktDtl implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String TABLE_NAME = "QLNV_HSKT_BBKT_DTL";

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "QLNV_HSKT_BBKT_DTL_SEQ")
	@SequenceGenerator(sequenceName = "QLNV_HSKT_BBKT_DTL_SEQ", allocationSize = 1, name = "QLNV_HSKT_BBKT_DTL_SEQ")
	private Long id;

	String tenTlieu;
	String tenFile;
	String loaiTlieu;
	String idFile;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@Fetch(value = FetchMode.SUBSELECT)
	@JoinColumn(name = "dataId")
	@JsonManagedReference
	@Where(clause = "data_type='" + QlnvHsktBbktDtl.TABLE_NAME + "'")
	private List<FileDKemJoinHsktBbktDtl> children = new ArrayList<>();

	public void setChildren(List<FileDKemJoinHsktBbktDtl> children) {
		this.children.clear();
		for (FileDKemJoinHsktBbktDtl child : children) {
			child.setParent(this);
		}
		this.children.addAll(children);
	}

	public void addChild(FileDKemJoinHsktBbktDtl child) {
		child.setParent(this);
		this.children.add(child);
	}

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_hdr")
	private QlnvHsktBbktHdr parent;
}
