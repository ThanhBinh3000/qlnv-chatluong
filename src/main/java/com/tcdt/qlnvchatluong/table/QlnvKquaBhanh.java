package com.tcdt.qlnvchatluong.table;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "QLNV_KQUA_BHANH")
@Data
public class QlnvKquaBhanh implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "QLNV_KQUA_BHANH_SEQ")
	@SequenceGenerator(sequenceName = "QLNV_KQUA_BHANH_SEQ", allocationSize = 1, name = "QLNV_KQUA_BHANH_SEQ")
	private Long id;
	
	String soBban;
	String maDvi;
	String maKho;
	Date ngayBhanh;
	String maHhoa;
	String tenHhoa;
	String diaChi;
	String dviBhanh;
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
	Date ngayNhanMau;
	Date ngayTraKqua;
	
}
