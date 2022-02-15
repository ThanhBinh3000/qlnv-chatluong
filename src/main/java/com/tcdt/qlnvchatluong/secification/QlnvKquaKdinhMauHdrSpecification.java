package com.tcdt.qlnvchatluong.secification;

import java.util.Date;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.validation.Valid;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.data.jpa.domain.Specification;

import com.tcdt.qlnvchatluong.request.search.QlnvKquaKdinhMauSearchReq;
import com.tcdt.qlnvchatluong.table.QlnvKquaKdinhMauHdr;

public class QlnvKquaKdinhMauHdrSpecification {
	public static Specification<QlnvKquaKdinhMauHdr> buildSearchQuery(final @Valid QlnvKquaKdinhMauSearchReq objReq) {
		return new Specification<QlnvKquaKdinhMauHdr>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = -2236511165531131472L;

			@Override
			public Predicate toPredicate(Root<QlnvKquaKdinhMauHdr> root, CriteriaQuery<?> query,
					CriteriaBuilder builder) {
				Predicate predicate = builder.conjunction();
				if (ObjectUtils.isEmpty(objReq))
					return predicate;

				String soBban = objReq.getSoBban();
				String maDvi = objReq.getMaDvi();
				String maHhoa = objReq.getMaHhoa();
				Date tuNgayKdinh = objReq.getTuNgayKdinh();
				Date denNgayKdinh = objReq.getDenNgayKdinh();

				root.fetch("children", JoinType.LEFT);

				if (StringUtils.isNotEmpty(soBban))
					predicate.getExpressions()
							.add(builder.like(builder.lower(root.get("soBban")), "%" + soBban.toLowerCase() + "%"));

				if (StringUtils.isNotBlank(maDvi))
					predicate.getExpressions().add(builder.and(builder.equal(root.get("maDvi"), maDvi)));
				
				if (StringUtils.isNotBlank(maHhoa))
					predicate.getExpressions().add(builder.and(builder.equal(root.get("maHhoa"), maHhoa)));


				if (ObjectUtils.isNotEmpty(tuNgayKdinh) && ObjectUtils.isNotEmpty(denNgayKdinh)) {
					predicate.getExpressions()
							.add(builder.and(builder.greaterThanOrEqualTo(root.get("ngayKdinh"), tuNgayKdinh)));
					predicate.getExpressions().add(builder.and(builder.lessThan(root.get("ngayKdinh"), new DateTime(denNgayKdinh).plusDays(1).toDate())));
				} else if (ObjectUtils.isNotEmpty(tuNgayKdinh)) {
					predicate.getExpressions()
							.add(builder.and(builder.greaterThanOrEqualTo(root.get("ngayKdinh"), tuNgayKdinh)));
				} else if (ObjectUtils.isNotEmpty(denNgayKdinh)) {
					predicate.getExpressions().add(builder.and(builder.lessThan(root.get("ngayKdinh"), new DateTime(denNgayKdinh).plusDays(1).toDate())));
				}

				return predicate;
			}
		};
	}
}
