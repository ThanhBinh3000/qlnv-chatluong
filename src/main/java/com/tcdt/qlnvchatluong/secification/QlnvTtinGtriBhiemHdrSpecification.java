package com.tcdt.qlnvchatluong.secification;

import com.tcdt.qlnvchatluong.request.search.QlnvTtinGtriBhiemSearchReq;
import com.tcdt.qlnvchatluong.table.QlnvTtinGtriBhiemHdr;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import javax.validation.Valid;
import java.util.Date;

public class QlnvTtinGtriBhiemHdrSpecification {
	public static Specification<QlnvTtinGtriBhiemHdr> buildSearchQuery(final @Valid QlnvTtinGtriBhiemSearchReq objReq) {
		return new Specification<QlnvTtinGtriBhiemHdr>() {

			private static final long serialVersionUID = -4253195591541593939L;

			@Override
			public Predicate toPredicate(Root<QlnvTtinGtriBhiemHdr> root, CriteriaQuery<?> query,
					CriteriaBuilder builder) {
				Predicate predicate = builder.conjunction();
				if (ObjectUtils.isEmpty(objReq))
					return predicate;

				String soHdong = objReq.getSoHdong();
				String maDviYcau = objReq.getMaDviYcau();
				String tenDviBhiem = objReq.getTenDviBhiem();
				Date tuNgayHdong = objReq.getTuNgayHdong();
				Date denNgayHdong = objReq.getDenNgayHdong();

				root.fetch("children", JoinType.LEFT);

				if (StringUtils.isNotEmpty(soHdong))
					predicate.getExpressions()
							.add(builder.like(builder.lower(root.get("soHdong")), "%" + soHdong.toLowerCase() + "%"));

				if (StringUtils.isNotBlank(maDviYcau))
					predicate.getExpressions().add(builder.and(builder.equal(root.get("maDviYcau"), maDviYcau)));

				if (StringUtils.isNotBlank(tenDviBhiem))
					predicate.getExpressions().add(builder.and(builder.equal(root.get("tenDviBhiem"), tenDviBhiem)));

				if (ObjectUtils.isNotEmpty(tuNgayHdong) && ObjectUtils.isNotEmpty(denNgayHdong)) {
					predicate.getExpressions()
							.add(builder.and(builder.greaterThanOrEqualTo(root.get("ngayHdong"), tuNgayHdong)));
					predicate.getExpressions().add(builder.and(builder.lessThan(root.get("ngayHdong"), new DateTime(denNgayHdong).plusDays(1).toDate())));
				} else if (ObjectUtils.isNotEmpty(tuNgayHdong)) {
					predicate.getExpressions()
							.add(builder.and(builder.greaterThanOrEqualTo(root.get("ngayHdong"), tuNgayHdong)));
				} else if (ObjectUtils.isNotEmpty(denNgayHdong)) {
					predicate.getExpressions().add(builder.and(builder.lessThan(root.get("ngayHdong"), new DateTime(denNgayHdong).plusDays(1).toDate())));
				}

				return predicate;
			}
		};
	}
}
