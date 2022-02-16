package com.tcdt.qlnvchatluong.secification;

import java.util.Date;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.validation.Valid;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.data.jpa.domain.Specification;

import com.tcdt.qlnvchatluong.request.search.QlnvKquaBhanhSearchReq;
import com.tcdt.qlnvchatluong.table.QlnvKquaBhanh;

public class QlnvKquaBhanhSpecification {
	public static Specification<QlnvKquaBhanh> buildSearchQuery(final @Valid QlnvKquaBhanhSearchReq objReq) {
		return new Specification<QlnvKquaBhanh>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 3839263637650259711L;

			@Override
			public Predicate toPredicate(Root<QlnvKquaBhanh> root, CriteriaQuery<?> query,
					CriteriaBuilder builder) {
				Predicate predicate = builder.conjunction();
				if (ObjectUtils.isEmpty(objReq))
					return predicate;

				String soBban = objReq.getSoBban();
				String maDvi = objReq.getMaDvi();
				String maHhoa = objReq.getMaHhoa();
				Date tuNgayBhanh = objReq.getTuNgayBhanh();
				Date denNgayBhanh = objReq.getDenNgayBhanh();

				if (StringUtils.isNotEmpty(soBban))
					predicate.getExpressions()
							.add(builder.like(builder.lower(root.get("soBban")), "%" + soBban.toLowerCase() + "%"));

				if (StringUtils.isNotBlank(maDvi))
					predicate.getExpressions().add(builder.and(builder.equal(root.get("maDvi"), maDvi)));
				
				if (StringUtils.isNotBlank(maHhoa))
					predicate.getExpressions().add(builder.and(builder.equal(root.get("maHhoa"), maHhoa)));


				if (ObjectUtils.isNotEmpty(tuNgayBhanh) && ObjectUtils.isNotEmpty(denNgayBhanh)) {
					predicate.getExpressions()
							.add(builder.and(builder.greaterThanOrEqualTo(root.get("ngayBhanh"), tuNgayBhanh)));
					predicate.getExpressions().add(builder.and(builder.lessThan(root.get("ngayBhanh"), new DateTime(denNgayBhanh).plusDays(1).toDate())));
				} else if (ObjectUtils.isNotEmpty(tuNgayBhanh)) {
					predicate.getExpressions()
							.add(builder.and(builder.greaterThanOrEqualTo(root.get("ngayBhanh"), tuNgayBhanh)));
				} else if (ObjectUtils.isNotEmpty(denNgayBhanh)) {
					predicate.getExpressions().add(builder.and(builder.lessThan(root.get("ngayBhanh"), new DateTime(denNgayBhanh).plusDays(1).toDate())));
				}

				return predicate;
			}
		};
	}
}
