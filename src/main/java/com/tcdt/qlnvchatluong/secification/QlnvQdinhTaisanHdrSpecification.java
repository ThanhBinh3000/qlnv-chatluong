package com.tcdt.qlnvchatluong.secification;

import com.tcdt.qlnvchatluong.request.search.QlnvQdinhTaisanSearchReq;
import com.tcdt.qlnvchatluong.table.QlnvQdinhTaisanHdr;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.validation.Valid;
import java.util.Date;

public class QlnvQdinhTaisanHdrSpecification {
	public static Specification<QlnvQdinhTaisanHdr> buildSearchQuery(final @Valid QlnvQdinhTaisanSearchReq objReq) {
		return new Specification<QlnvQdinhTaisanHdr>() {

			private static final long serialVersionUID = 7261679149468731988L;

			@Override
			public Predicate toPredicate(Root<QlnvQdinhTaisanHdr> root, CriteriaQuery<?> query,
										 CriteriaBuilder builder) {
				Predicate predicate = builder.conjunction();
				if (ObjectUtils.isEmpty(objReq))
					return predicate;

				String soQdinh = objReq.getSoQdinh();
				Date ngayBhanhTu = objReq.getNgayBhanhTu();
				Date ngayBhanhDen = objReq.getNgayBhanhDen();


				if (StringUtils.isNotEmpty(soQdinh))
					predicate.getExpressions()
							.add(builder.like(builder.lower(root.get("soQdinh")), "%" + soQdinh.toLowerCase() + "%"));

				if (ObjectUtils.isNotEmpty(ngayBhanhTu) && ObjectUtils.isNotEmpty(ngayBhanhDen)) {
					predicate.getExpressions()
							.add(builder.and(builder.greaterThanOrEqualTo(root.get("ngayQdinh"), new DateTime(ngayBhanhTu).plusDays(1).toDate())));
					predicate.getExpressions().add(builder.and(builder.lessThan(root.get("ngayQdinh"), new DateTime(ngayBhanhDen).plusDays(1).toDate())));
				} else if (ObjectUtils.isNotEmpty(ngayBhanhTu)) {
					predicate.getExpressions()
							.add(builder.and(builder.greaterThanOrEqualTo(root.get("ngayQdinh"), new DateTime(ngayBhanhTu).plusDays(1).toDate())));
				} else if (ObjectUtils.isNotEmpty(ngayBhanhDen)) {
					predicate.getExpressions().add(builder.and(builder.lessThan(root.get("ngayQdinh"), new DateTime(ngayBhanhDen).plusDays(1).toDate())));
				}

				return predicate;
			}
		};
	}
}
