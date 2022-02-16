package com.tcdt.qlnvchatluong.secification;

import java.util.Date;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.validation.Valid;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.data.jpa.domain.Specification;

import com.tcdt.qlnvchatluong.request.IdSearchReq;
import com.tcdt.qlnvchatluong.request.search.QlnvHsktSearchReq;
import com.tcdt.qlnvchatluong.table.QlnvHskt;

public class QlnvHsktSpecification {
	public static Specification<QlnvHskt> buildSearchQuery(final @Valid QlnvHsktSearchReq objReq) {
		return new Specification<QlnvHskt>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<QlnvHskt> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				Predicate predicate = builder.conjunction();
				if (ObjectUtils.isEmpty(objReq))
					return predicate;

				String soHso = objReq.getSoHso();
				String soHdong = objReq.getSoHdong();
				String maHhoa = objReq.getMaHhoa();
				Date tuNgayLap = objReq.getTuNgayLap();
				Date denNgayLap = objReq.getDenNgayLap();

				if (StringUtils.isNotEmpty(soHso))
					predicate.getExpressions()
							.add(builder.like(builder.lower(root.get("soHso")), "%" + soHso.toLowerCase() + "%"));

				if (StringUtils.isNotBlank(soHdong))
					predicate.getExpressions()
							.add(builder.like(builder.lower(root.get("soHdong")), "%" + soHdong.toLowerCase() + "%"));

				if (StringUtils.isNotBlank(maHhoa))
					predicate.getExpressions().add(builder.and(builder.equal(root.get("maHhoa"), maHhoa)));

				if (ObjectUtils.isNotEmpty(tuNgayLap) && ObjectUtils.isNotEmpty(denNgayLap)) {
					predicate.getExpressions()
							.add(builder.and(builder.greaterThanOrEqualTo(root.get("ngayLap"), tuNgayLap)));
					predicate.getExpressions().add(builder
							.and(builder.lessThan(root.get("ngayLap"), new DateTime(denNgayLap).plusDays(1).toDate())));
				}

				return predicate;
			}
		};
	}

	public static Specification<QlnvHskt> buildFindByIdQuery(final @Valid IdSearchReq objReq) {
		return new Specification<QlnvHskt>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = -1609160279354911278L;

			@SuppressWarnings("unchecked")
			@Override
			public Predicate toPredicate(Root<QlnvHskt> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				Predicate predicate = builder.conjunction();
				if (ObjectUtils.isEmpty(objReq))
					return predicate;

				Long id = objReq.getId();
				String maDvi = objReq.getMaDvi();

				Join<Object, Object> fetchParent = (Join<Object, Object>) root.fetch("children", JoinType.LEFT);

				predicate.getExpressions().add(builder.and(builder.equal(root.get("id"), id)));
				if (StringUtils.isNotBlank(maDvi))
					predicate.getExpressions().add(builder.and(builder.equal(fetchParent.get("maDvi"), maDvi)));

				return predicate;
			}
		};
	}
}
