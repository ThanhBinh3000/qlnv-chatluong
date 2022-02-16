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
@Table(name = "QLNV_TTRANG_TAISAN")
@Data
public class QlnvTtrangTaisan implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "QLNV_QDINH_TAISAN_HDR_SEQ")
	@SequenceGenerator(sequenceName = "QLNV_QDINH_TAISAN_HDR_SEQ", allocationSize = 1, name = "QLNV_QDINH_TAISAN_HDR_SEQ")
	private Long id;
	String maDvi;
	Date ngayPbo;
	String tenDvi;
	Date ngayTao;
	String nguoiTao;
	Date ngaySua;
	String nguoiSua;
	String soBban;
	String loaiTsan;
	String tenTsan;
	BigDecimal soLuong;
	BigDecimal sluongSdung;
	String tinhTrang;
	String ghiChu;
}
