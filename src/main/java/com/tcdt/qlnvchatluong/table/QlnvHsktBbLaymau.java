package com.tcdt.qlnvchatluong.table;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;

@Entity
@Table(name = "QLNV_HSKT_BB_LAYMAU")
@Data
public class QlnvHsktBbLaymau implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "QLNV_HSKT_BB_LAYMAU_SEQ")
	@SequenceGenerator(sequenceName = "QLNV_HSKT_BB_LAYMAU_SEQ", allocationSize = 1, name = "QLNV_HSKT_BB_LAYMAU_SEQ")
	private Long id;

	String soBban;
	Date ngayLap;
	String cvuNhanDdien;
	String nguoiCcapDdien;
	String nguoiNhanDdien;
	String cvuCcapDdien;
	String canCu;
	String diaDiem;
	BigDecimal sluongMau;
	String pphapLaymau;
	String kyHieu;
	String ctieuKtra;
	String ketQua;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_HSKT")
	@JsonBackReference
	private QlnvHskt parent;

}
