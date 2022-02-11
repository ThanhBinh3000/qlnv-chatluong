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
@Table(name = "QLNV_HSKT_BB_GIAOMAU")
@Data
public class QlnvHsktBbGiaomau implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "QLNV_HSKT_BB_GIAOMAU_SEQ")
	@SequenceGenerator(sequenceName = "QLNV_HSKT_BB_GIAOMAU_SEQ", allocationSize = 1, name = "QLNV_HSKT_BB_GIAOMAU_SEQ")
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
	String ctieuKtra;
	String ttrangMau;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_HSKT")
	@JsonBackReference
	private QlnvHskt parent;

}
