package com.tcdt.qlnvchatluong.secification;

import com.tcdt.qlnvchatluong.request.search.QlnvBbanXnhanKtclSearchReq;
import com.tcdt.qlnvchatluong.table.QlnvBbanXnhanKtclHdr;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.validation.Valid;
import java.util.Date;

public class QlnvBbanXnhanKtclHdrSpecification {
	public static Specification<QlnvBbanXnhanKtclHdr> buildSearchQuery(final @Valid QlnvBbanXnhanKtclSearchReq objReq) {
		return new Specification<QlnvBbanXnhanKtclHdr>() {

			/**
			 *
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<QlnvBbanXnhanKtclHdr> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				Predicate predicate = builder.conjunction();
				if (ObjectUtils.isEmpty(objReq))
					return predicate;

				String soBban = objReq.getSoBban();
				String maDvi = objReq.getMaDvi();
				String maHhoa = objReq.getMaHhoa();
				Date ngayKtraTu = objReq.getNgayKtraTu();
				Date ngayKtraDen = objReq.getNgayKtraDen();
				String soHdong = objReq.getSoHdong();

				root.fetch("children", JoinType.LEFT);

				if (StringUtils.isNotEmpty(soBban))
					predicate.getExpressions()
							.add(builder.like(builder.lower(root.get("soBban")), "%" + soBban.toLowerCase() + "%"));

				if (StringUtils.isNotBlank(maDvi))
					predicate.getExpressions().add(builder.and(builder.equal(root.get("maDvi"), maDvi)));

				if (StringUtils.isNotBlank(maHhoa))
					predicate.getExpressions().add(builder.and(builder.equal(root.get("maHhoa"), maHhoa)));

				if (StringUtils.isNotBlank(soHdong))
					predicate.getExpressions().add(builder.and(builder.equal(root.get("soHdong"), soHdong)));

				if (ObjectUtils.isNotEmpty(ngayKtraTu) && ObjectUtils.isNotEmpty(ngayKtraDen)) {
					predicate.getExpressions()
							.add(builder.and(builder.greaterThanOrEqualTo(root.get("ngayLap"), ngayKtraTu)));
					predicate.getExpressions().add(builder
							.and(builder.lessThan(root.get("ngayLap"), new DateTime(ngayKtraDen).plusDays(1).toDate())));
				}

				return predicate;
			}
		};
	}
}
