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
import org.springframework.data.jpa.domain.Specification;

import com.tcdt.qlnvchatluong.request.IdSearchReq;
import com.tcdt.qlnvchatluong.request.search.QlnvBbanCbiKhoSearchReq;
import com.tcdt.qlnvchatluong.table.QlnvBbanCbiKhoHdr;
import com.tcdt.qlnvchatluong.table.QlnvBbanNtkklHdr;

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
					predicate.getExpressions().add(builder.and(builder.lessThan(root.get("ngayLap"), denNgayLap)));
				} else if (ObjectUtils.isNotEmpty(tuNgayLap)) {
					predicate.getExpressions()
							.add(builder.and(builder.greaterThanOrEqualTo(root.get("ngayLap"), tuNgayLap)));
				} else if (ObjectUtils.isNotEmpty(denNgayLap)) {
					predicate.getExpressions().add(builder.and(builder.lessThan(root.get("ngayLap"), denNgayLap)));
				}

				return predicate;
			}
		};
	}

	public static Specification<QlnvBbanNtkklHdr> buildFindByIdQuery(final @Valid IdSearchReq objReq) {
		return new Specification<QlnvBbanNtkklHdr>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = -1609160279354911278L;

			@SuppressWarnings("unchecked")
			@Override
			public Predicate toPredicate(Root<QlnvBbanNtkklHdr> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
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
