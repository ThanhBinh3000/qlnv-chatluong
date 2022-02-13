package com.tcdt.qlnvchatluong.secification;

import com.tcdt.qlnvchatluong.request.search.QlnvPboTaisanSearchReq;
import com.tcdt.qlnvchatluong.table.QlnvPboTaisanDtl;
import com.tcdt.qlnvchatluong.table.QlnvPboTaisanHdr;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.validation.Valid;
import java.util.Date;

public class QlnvPboTaisanHdrSpecification {
    public static Specification<QlnvPboTaisanHdr> buildSearchQuery(final @Valid QlnvPboTaisanSearchReq objReq) {
        return new Specification<QlnvPboTaisanHdr>() {

            private static final long serialVersionUID = -4206264689374419556L;

            @Override
            public Predicate toPredicate(Root<QlnvPboTaisanHdr> root, CriteriaQuery<?> query,
                                         CriteriaBuilder builder) {
                Predicate predicate = builder.conjunction();
                if (ObjectUtils.isEmpty(objReq))
                    return predicate;

                String soHdong = objReq.getSoHdong();
                Date tuNgayPbo = objReq.getTuNgayPbo();
                Date denNgayPbo = objReq.getDenNgayPbo();
                String maDvi = objReq.getMaDvi();
                String maVtu = objReq.getMaVtu();

                root.fetch("children", JoinType.LEFT);
                Join<QlnvPboTaisanHdr, QlnvPboTaisanDtl> joinQuery = root.join("children");

                if (StringUtils.isNotEmpty(soHdong))
                    predicate.getExpressions()
                            .add(builder.like(builder.lower(root.get("soHdong")), "%" + soHdong.toLowerCase() + "%"));

                if (StringUtils.isNotBlank(maDvi))
                    predicate.getExpressions().add(builder.and(builder.equal(root.get("maDvi"), maDvi)));

                if (StringUtils.isNotBlank(maVtu))
                    predicate.getExpressions().add(builder.and(builder.equal(joinQuery.get("maVtu"), maVtu)));

                if (ObjectUtils.isNotEmpty(tuNgayPbo) && ObjectUtils.isNotEmpty(denNgayPbo)) {
                    predicate.getExpressions()
                            .add(builder.and(builder.greaterThanOrEqualTo(root.get("ngayPbo"), tuNgayPbo)));
                    predicate.getExpressions().add(builder.and(builder.lessThan(root.get("ngayPbo"), denNgayPbo)));
                } else if (ObjectUtils.isNotEmpty(tuNgayPbo)) {
                    predicate.getExpressions()
                            .add(builder.and(builder.greaterThanOrEqualTo(root.get("ngayPbo"), tuNgayPbo)));
                } else if (ObjectUtils.isNotEmpty(denNgayPbo)) {
                    predicate.getExpressions().add(builder.and(builder.lessThan(root.get("ngayPbo"), denNgayPbo)));
                }

                return predicate;
            }
        };
    }

}
