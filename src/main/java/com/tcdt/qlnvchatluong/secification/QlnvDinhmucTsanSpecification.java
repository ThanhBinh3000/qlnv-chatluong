package com.tcdt.qlnvchatluong.secification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.validation.Valid;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import com.tcdt.qlnvchatluong.request.search.QlnvDinhmucTsanSearchReq;
import com.tcdt.qlnvchatluong.table.QlnvDinhmucTsan;

public class QlnvDinhmucTsanSpecification {
	public static Specification<QlnvDinhmucTsan> buildSearchQuery(final @Valid QlnvDinhmucTsanSearchReq objReq) {
		return new Specification<QlnvDinhmucTsan>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<QlnvDinhmucTsan> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				Predicate predicate = builder.conjunction();
				if (ObjectUtils.isEmpty(objReq))
					return predicate;

				String namDmuc = objReq.getNamDmuc();
				String capDvi = objReq.getCapDvi();
				String maTsan = objReq.getMaTsan();
				String trangThai = objReq.getTrangThai();

				if (StringUtils.isNotBlank(namDmuc))
					predicate.getExpressions().add(builder.and(builder.equal(root.get("namDmuc"), namDmuc)));
				if (StringUtils.isNotBlank(capDvi))
					predicate.getExpressions().add(builder.and(builder.equal(root.get("capDvi"), capDvi)));
				if (StringUtils.isNotBlank(maTsan))
					predicate.getExpressions().add(builder.and(builder.equal(root.get("maTsan"), maTsan)));
				if (StringUtils.isNotBlank(trangThai))
					predicate.getExpressions().add(builder.and(builder.equal(root.get("trangThai"), trangThai)));

				return predicate;
			}
		};
	}

}
