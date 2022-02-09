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
import com.tcdt.qlnvchatluong.request.search.QlnvPhieuKnghiemSearchReq;
import com.tcdt.qlnvchatluong.table.QlnvPhieuKnghiemHdr;

public class QlnvPhieuKnghiemHdrSpecification {
	public static Specification<QlnvPhieuKnghiemHdr> buildSearchQuery(
			final @Valid QlnvPhieuKnghiemSearchReq objReq) {
		return new Specification<QlnvPhieuKnghiemHdr>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<QlnvPhieuKnghiemHdr> root, CriteriaQuery<?> query,
					CriteriaBuilder builder) {
				Predicate predicate = builder.conjunction();
				if (ObjectUtils.isEmpty(objReq))
					return predicate;

				String soPhieu = objReq.getSoPhieu();
				String maDvi = objReq.getMaDvi();
				String maHhoa = objReq.getMaHhoa();
				String maNgan = objReq.getMaNgan();
				String maLo = objReq.getMaLo();
				Date tuNgayKnghiem = objReq.getTuNgayKnghiem();
				Date denNgayKnghiem = objReq.getDenNgayKnghiem();

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

				if (ObjectUtils.isNotEmpty(tuNgayKnghiem) && ObjectUtils.isNotEmpty(denNgayKnghiem)) {
					predicate.getExpressions()
							.add(builder.and(builder.greaterThanOrEqualTo(root.get("ngayKnghiem"), tuNgayKnghiem)));
					predicate.getExpressions().add(builder.and(builder.lessThan(root.get("ngayKnghiem"),
							new DateTime(denNgayKnghiem).plusDays(1).toDate())));
				}

				return predicate;
			}
		};
	}

	public static Specification<QlnvPhieuKnghiemHdr> buildFindByIdQuery(final @Valid IdSearchReq objReq) {
		return new Specification<QlnvPhieuKnghiemHdr>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = -1609160279354911278L;

			@SuppressWarnings("unchecked")
			@Override
			public Predicate toPredicate(Root<QlnvPhieuKnghiemHdr> root, CriteriaQuery<?> query,
					CriteriaBuilder builder) {
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
