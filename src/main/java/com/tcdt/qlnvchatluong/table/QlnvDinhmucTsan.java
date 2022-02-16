package com.tcdt.qlnvchatluong.table;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "QLNV_DINHMUC_TSAN")
@Data
public class QlnvDinhmucTsan implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "QLNV_DINHMUC_TSAN_SEQ")
	@SequenceGenerator(sequenceName = "QLNV_DINHMUC_TSAN_SEQ", allocationSize = 1, name = "QLNV_DINHMUC_TSAN_SEQ")
	private Long id;

	String namDmuc;
	String maDvi;
	String capDvi;
	Date ngayHluc;
	String maTsan;
	String tenTsan;
	Date ngayTao;
	String nguoiTao;
	Date ngaySua;
	String nguoiSua;
	String trangThai;
	String ghiChu;
	BigDecimal sluongHang;
	String dvtHang;
	String dvtDmuc;
	BigDecimal dinhMuc;
	BigDecimal donGiaTda;
	String loaiDmuc;

}
