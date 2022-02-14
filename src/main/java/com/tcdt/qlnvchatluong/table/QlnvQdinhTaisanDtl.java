package com.tcdt.qlnvchatluong.table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@Table(name = "QLNV_QDINH_TAISAN_DTL")
public class QlnvQdinhTaisanDtl implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "QLNV_QDINH_TAISAN_DTL_SEQ")
	@SequenceGenerator(sequenceName = "QLNV_QDINH_TAISAN_DTL_SEQ", allocationSize = 1, name = "QLNV_QDINH_TAISAN_DTL_SEQ")
	private Long id;
	String maVtu;
	String tenVtu;
	BigDecimal soLuong;
	String dviTinh;
	Date tdiemDapung;
	String tenCtieu;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_hdr")
	private QlnvQdinhTaisanHdr parent;
}
