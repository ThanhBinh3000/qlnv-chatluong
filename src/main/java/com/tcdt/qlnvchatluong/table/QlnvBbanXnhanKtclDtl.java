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

@Entity
@Table(name = "QLNV_BBAN_XNHAN_KTCL_DTL")
@Data
public class QlnvBbanXnhanKtclDtl {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "QLNV_BBAN_XNHAN_KTCL_DTL_SEQ")
	@SequenceGenerator(sequenceName = "QLNV_BBAN_XNHAN_KTCL_DTL_SEQ", allocationSize = 1, name = "QLNV_BBAN_XNHAN_KTCL_DTL_SEQ")
	private Long id;
	String maCtieu;
	String tenCtieu;
	String kquaHdong;
	String kquaPtich;
	String ketLuan;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_hdr")
	private QlnvBbanXnhanKtclHdr parent;
}
