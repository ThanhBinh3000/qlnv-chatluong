package com.tcdt.qlnvchatluong.table;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "QLNV_DINHMUC_HANG")
@Data
public class QlnvDinhmucHang {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "QLNV_DINHMUC_HANG_SEQ")
	@SequenceGenerator(sequenceName = "QLNV_DINHMUC_HANG_SEQ", allocationSize = 1, name = "QLNV_DINHMUC_HANG_SEQ")
	private Long id;
	String namDmuc;
	String maDvi;
	String capDvi;
	Date ngayHluc;
	String loaiHhoa;
	String loaiDmuc;
	Date ngayTao;
	String nguoiTao;
	Date ngaySua;
	String nguoiSua;
	String trangThai;
	String lhinhBquan;
	String dmucTtin;
	String dvtHang;
	String dvtDmuc;
	String dmucNvu;
	BigDecimal dmucTcuc;
}
