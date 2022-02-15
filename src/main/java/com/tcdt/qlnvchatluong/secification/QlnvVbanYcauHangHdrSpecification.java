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

import com.tcdt.qlnvchatluong.request.search.QlnvVbanYcauHangSearchReq;
import com.tcdt.qlnvchatluong.table.QlnvVbanYcauHangHdr;

public class QlnvVbanYcauHangHdrSpecification {
	public static Specification<QlnvVbanYcauHangHdr> buildSearchQuery(final @Valid QlnvVbanYcauHangSearchReq objReq) {
		return new Specification<QlnvVbanYcauHangHdr>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = -3205580895878593056L;

			@Override
			public Predicate toPredicate(Root<QlnvVbanYcauHangHdr> root, CriteriaQuery<?> query,
					CriteriaBuilder builder) {
				Predicate predicate = builder.conjunction();
				if (ObjectUtils.isEmpty(objReq))
					return predicate;

				String soYcau = objReq.getSoYcau();
				String maDvi = objReq.getMaDvi();
				String soHdong = objReq.getSoHdong();
				Date tuNgayYcau = objReq.getTuNgayYcau();
				Date denNgayYcau = objReq.getDenNgayYcau();

				root.fetch("children", JoinType.LEFT);

				if (StringUtils.isNotEmpty(soYcau))
					predicate.getExpressions()
							.add(builder.like(builder.lower(root.get("soYcau")), "%" + soYcau.toLowerCase() + "%"));

				if (StringUtils.isNotBlank(maDvi))
					predicate.getExpressions().add(builder.and(builder.equal(root.get("maDvi"), maDvi)));
				
				if (StringUtils.isNotBlank(soHdong))
					predicate.getExpressions().add(builder.and(builder.equal(root.get("soHdong"), soHdong)));


				if (ObjectUtils.isNotEmpty(tuNgayYcau) && ObjectUtils.isNotEmpty(denNgayYcau)) {
					predicate.getExpressions()
							.add(builder.and(builder.greaterThanOrEqualTo(root.get("ngayYcau"), tuNgayYcau)));
					predicate.getExpressions().add(builder.and(builder.lessThan(root.get("ngayYcau"), new DateTime(denNgayYcau).plusDays(1).toDate())));
				} else if (ObjectUtils.isNotEmpty(tuNgayYcau)) {
					predicate.getExpressions()
							.add(builder.and(builder.greaterThanOrEqualTo(root.get("ngayYcau"), tuNgayYcau)));
				} else if (ObjectUtils.isNotEmpty(denNgayYcau)) {
					predicate.getExpressions().add(builder.and(builder.lessThan(root.get("ngayYcau"), new DateTime(denNgayYcau).plusDays(1).toDate())));
				}

				return predicate;
			}
		};
	}
}
