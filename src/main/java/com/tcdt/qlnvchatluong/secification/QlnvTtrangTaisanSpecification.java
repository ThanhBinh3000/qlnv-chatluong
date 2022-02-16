package com.tcdt.qlnvchatluong.secification;

import com.tcdt.qlnvchatluong.request.search.QlnvTtrangTaisanSearchReq;
import com.tcdt.qlnvchatluong.table.QlnvTtrangTaisan;
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

public class QlnvTtrangTaisanSpecification {
	public static Specification<QlnvTtrangTaisan> buildSearchQuery(final @Valid QlnvTtrangTaisanSearchReq objReq) {
		return new Specification<QlnvTtrangTaisan>() {

			private static final long serialVersionUID = 7261679149468731988L;

			@Override
			public Predicate toPredicate(Root<QlnvTtrangTaisan> root, CriteriaQuery<?> query,
										 CriteriaBuilder builder) {
				Predicate predicate = builder.conjunction();
				if (ObjectUtils.isEmpty(objReq))
					return predicate;

				String soBban = objReq.getSoBban();
				String tenTsan = objReq.getTenTsan();
				String tinhTrang = objReq.getTinhTrang();
				Date ngayPboTu = objReq.getNgayPboTu();
				Date ngayPboDen = objReq.getNgauPboDen();


				if (StringUtils.isNotEmpty(soBban))
					predicate.getExpressions()
							.add(builder.like(builder.lower(root.get("soBban")), "%" + soBban.toLowerCase() + "%"));

				if (StringUtils.isNotEmpty(tenTsan))
					predicate.getExpressions()
							.add(builder.like(builder.lower(root.get("tenTsan")), tenTsan));

				if (StringUtils.isNotEmpty(tinhTrang))
					predicate.getExpressions()
							.add(builder.like(builder.lower(root.get("tinhTrang")), tinhTrang));

				if (ObjectUtils.isNotEmpty(ngayPboTu) && ObjectUtils.isNotEmpty(ngayPboDen)) {
					predicate.getExpressions()
							.add(builder.and(builder.greaterThanOrEqualTo(root.get("ngayPbo"), new DateTime(ngayPboTu).plusDays(1).toDate())));
					predicate.getExpressions().add(builder.and(builder.lessThan(root.get("ngayPbo"), new DateTime(ngayPboDen).plusDays(1).toDate())));
				} else if (ObjectUtils.isNotEmpty(ngayPboTu)) {
					predicate.getExpressions()
							.add(builder.and(builder.greaterThanOrEqualTo(root.get("ngayPbo"), new DateTime(ngayPboTu).plusDays(1).toDate())));
				} else if (ObjectUtils.isNotEmpty(ngayPboDen)) {
					predicate.getExpressions().add(builder.and(builder.lessThan(root.get("ngayPbo"), new DateTime(ngayPboDen).plusDays(1).toDate())));
				}

				return predicate;
			}
		};
	}
}
