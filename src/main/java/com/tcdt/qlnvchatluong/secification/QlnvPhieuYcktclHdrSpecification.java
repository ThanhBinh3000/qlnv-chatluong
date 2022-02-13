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

import com.tcdt.qlnvchatluong.request.search.QlnvPhieuYcktclSearchReq;
import com.tcdt.qlnvchatluong.table.QlnvPhieuYcktclHdr;

public class QlnvPhieuYcktclHdrSpecification {
	public static Specification<QlnvPhieuYcktclHdr> buildSearchQuery(final @Valid QlnvPhieuYcktclSearchReq objReq) {
		return new Specification<QlnvPhieuYcktclHdr>() {
			private static final long serialVersionUID = 5629977346976896006L;

			@Override
			public Predicate toPredicate(Root<QlnvPhieuYcktclHdr> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				Predicate predicate = builder.conjunction();
				if (ObjectUtils.isEmpty(objReq))
					return predicate;

				String soPhieu = objReq.getSoPhieu();
				String maDvi = objReq.getMaDvi();
				String maHhoa = objReq.getMaHhoa();
				String maNgan = objReq.getMaNgan();
				String maLo = objReq.getMaLo();
				Date tuNgayKtra = objReq.getTuNgayKtra();
				Date denNgayKtra = objReq.getDenNgayKtra();

				root.fetch("children", JoinType.LEFT);

				if (StringUtils.isNotEmpty(soPhieu))
					predicate.getExpressions()
							.add(builder.like(builder.lower(root.get("soPhieu")), "%" + soPhieu.toLowerCase() + "%"));

				if (StringUtils.isNotBlank(maDvi))
					predicate.getExpressions().add(builder.and(builder.equal(root.get("maDvi"), maDvi)));

				if (StringUtils.isNotBlank(maHhoa))
					predicate.getExpressions().add(builder.and(builder.equal(root.get("maHhoa"), maHhoa)));

				if (StringUtils.isNotBlank(maNgan))
					predicate.getExpressions().add(builder.and(builder.equal(root.get("maNgan"), maNgan)));

				if (StringUtils.isNotBlank(maLo))
					predicate.getExpressions().add(builder.and(builder.equal(root.get("maLo"), maLo)));
				
				if (ObjectUtils.isNotEmpty(tuNgayKtra) && ObjectUtils.isNotEmpty(denNgayKtra)) {
					predicate.getExpressions()
							.add(builder.and(builder.greaterThanOrEqualTo(root.get("ngayKtra"), tuNgayKtra)));
					predicate.getExpressions().add(builder.and(
							builder.lessThan(root.get("ngayKtra"), new DateTime(denNgayKtra).plusDays(1).toDate())));
				}

				return predicate;
			}
		};
	}
}
