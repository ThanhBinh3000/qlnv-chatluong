package com.tcdt.qlnvchatluong.table;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "QLNV_DMUC_HAODOI_HANG")
@Data
public class QlnvDinhmucHaodoiHang implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "QLNV_DMUC_HAODOI_HANG_SEQ")
	@SequenceGenerator(sequenceName = "QLNV_DMUC_HAODOI_HANG_SEQ", allocationSize = 1, name = "QLNV_DMUC_HAODOI_HANG_SEQ")
	private Long id;
	String maDmuc;
	String maDvi;
	Date ngayHluc;
	String maHhoa;
	String tenHhoa;
	String nguoiTao;
	Date ngayTao;

	String nguoiSua;
	Date ngaySua;
	BigDecimal tgianBquan;
	String dkienBquan;
	BigDecimal gtriDmuc;
	String trangThai;
}
