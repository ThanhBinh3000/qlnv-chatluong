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

import com.tcdt.qlnvchatluong.request.search.QlnvBbanCbiKhoSearchReq;
import com.tcdt.qlnvchatluong.table.QlnvBbanCbiKhoHdr;

public class QlnvBbanCbiKhoHdrSpecification {
	public static Specification<QlnvBbanCbiKhoHdr> buildSearchQuery(final @Valid QlnvBbanCbiKhoSearchReq objReq) {
		return new Specification<QlnvBbanCbiKhoHdr>() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 5486965109620544482L;

			@Override
			public Predicate toPredicate(Root<QlnvBbanCbiKhoHdr> root, CriteriaQuery<?> query,
					CriteriaBuilder builder) {
				Predicate predicate = builder.conjunction();
				if (ObjectUtils.isEmpty(objReq))
					return predicate;

				String soBban = objReq.getSoBban();
				String maDvi = objReq.getMaDvi();
				String maHhoa = objReq.getMaHhoa();
				String maNgan = objReq.getMaNgan();
				String maLo = objReq.getMaLo();
				Date tuNgayLap = objReq.getTuNgayLap();
				Date denNgayLap = objReq.getDenNgayLap();

				root.fetch("children", JoinType.LEFT);

				if (StringUtils.isNotEmpty(soBban))
					predicate.getExpressions()
							.add(builder.like(builder.lower(root.get("soBban")), "%" + soBban.toLowerCase() + "%"));

				if (StringUtils.isNotBlank(maDvi))
					predicate.getExpressions().add(builder.and(builder.equal(root.get("maDvi"), maDvi)));

				if (StringUtils.isNotBlank(maHhoa))
					predicate.getExpressions().add(builder.and(builder.equal(root.get("maHhoa"), maHhoa)));

				if (StringUtils.isNotBlank(maNgan))
					predicate.getExpressions().add(builder.and(builder.equal(root.get("maNgan"), maNgan)));

				if (StringUtils.isNotBlank(maLo))
					predicate.getExpressions().add(builder.and(builder.equal(root.get("maLo"), maLo)));

				if (ObjectUtils.isNotEmpty(tuNgayLap) && ObjectUtils.isNotEmpty(denNgayLap)) {
					predicate.getExpressions()
							.add(builder.and(builder.greaterThanOrEqualTo(root.get("ngayLap"), tuNgayLap)));
					predicate.getExpressions().add(builder
							.and(builder.lessThan(root.get("ngayLap"), new DateTime(denNgayLap).plusDays(1).toDate())));
				} else if (ObjectUtils.isNotEmpty(tuNgayLap)) {
					predicate.getExpressions()
							.add(builder.and(builder.greaterThanOrEqualTo(root.get("ngayLap"), tuNgayLap)));
				} else if (ObjectUtils.isNotEmpty(denNgayLap)) {
					predicate.getExpressions().add(builder
							.and(builder.lessThan(root.get("ngayLap"), new DateTime(denNgayLap).plusDays(1).toDate())));
				}

				return predicate;
			}
		};
	}
}
