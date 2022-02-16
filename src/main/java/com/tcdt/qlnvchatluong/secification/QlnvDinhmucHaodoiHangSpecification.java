package com.tcdt.qlnvchatluong.secification;

import com.tcdt.qlnvchatluong.request.search.QlnvDinhmucHaodoiHangSearchReq;
import com.tcdt.qlnvchatluong.table.QlnvDinhmucHaodoiHang;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.validation.Valid;

public class QlnvDinhmucHaodoiHangSpecification {
	public static Specification<QlnvDinhmucHaodoiHang> buildSearchQuery(final @Valid QlnvDinhmucHaodoiHangSearchReq objReq) {
		return new Specification<QlnvDinhmucHaodoiHang>() {

			/**
			 *
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<QlnvDinhmucHaodoiHang> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				Predicate predicate = builder.conjunction();
				if (ObjectUtils.isEmpty(objReq))
					return predicate;

				String maHhoa = objReq.getMaHhoa();
				String maDvi = objReq.getMaDvi();
				String trangThai = objReq.getTrangThai();

				if (StringUtils.isNotEmpty(maHhoa))
					predicate.getExpressions()
							.add(builder.like(builder.lower(root.get("maHhoa")), maHhoa));

				if (StringUtils.isNotBlank(maDvi))
					predicate.getExpressions().add(builder.and(builder.equal(root.get("maDvi"), maDvi)));

				if (StringUtils.isNotBlank(trangThai))
					predicate.getExpressions().add(builder.and(builder.equal(root.get("trangThai"), trangThai)));

				return predicate;
			}
		};
	}
}
