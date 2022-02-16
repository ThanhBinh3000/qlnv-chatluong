package com.tcdt.qlnvchatluong.secification;

import com.tcdt.qlnvchatluong.request.search.QlnvDinhmucHangSearchReq;
import com.tcdt.qlnvchatluong.table.QlnvDinhmucHang;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.validation.Valid;

public class QlnvDinhmucHangSpecification {
	public static Specification<QlnvDinhmucHang> buildSearchQuery(final @Valid QlnvDinhmucHangSearchReq objReq) {
		return new Specification<QlnvDinhmucHang>() {

			/**
			 *
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<QlnvDinhmucHang> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				Predicate predicate = builder.conjunction();
				if (ObjectUtils.isEmpty(objReq))
					return predicate;

				String namDmuc = objReq.getNamDmuc();
				String loaiDmuc = objReq.getLoaiDmuc();
				String loaiHhoa = objReq.getLoaiHhoa();
				String capDvi = objReq.getCapDvi();
				String trangThai = objReq.getTrangThai();

				if (StringUtils.isNotEmpty(namDmuc))
					predicate.getExpressions()
							.add(builder.like(builder.lower(root.get("namDmuc")), namDmuc));

				if (StringUtils.isNotBlank(loaiDmuc))
					predicate.getExpressions().add(builder.and(builder.equal(root.get("loaiDmuc"), loaiDmuc)));

				if (StringUtils.isNotBlank(loaiHhoa))
					predicate.getExpressions().add(builder.and(builder.equal(root.get("loaiHhoa"), loaiHhoa)));

				if (StringUtils.isNotBlank(capDvi))
					predicate.getExpressions().add(builder.and(builder.equal(root.get("capDvi"), capDvi)));

				if (StringUtils.isNotBlank(trangThai))
					predicate.getExpressions().add(builder.and(builder.equal(root.get("trangThai"), trangThai)));

				return predicate;
			}
		};
	}
}
