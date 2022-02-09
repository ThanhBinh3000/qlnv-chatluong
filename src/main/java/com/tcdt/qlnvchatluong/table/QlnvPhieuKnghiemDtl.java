package com.tcdt.qlnvchatluong.table;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Table(name = "QLNV_PHIEU_KNGHIEM_DTL")
@Data
public class QlnvPhieuKnghiemDtl implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "QLNV_PHIEU_KNGHIEM_DTL_SEQ")
	@SequenceGenerator(sequenceName = "QLNV_PHIEU_KNGHIEM_DTL_SEQ", allocationSize = 1, name = "QLNV_PHIEU_KNGHIEM_DTL_SEQ")
	private Long id;

	String maCtieu;
	String tenCtieu;
	String pphapXdinh;
	String dviTinh;
	String kquaKnghiem;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_hdr")
	private QlnvPhieuKnghiemHdr parent;

}
