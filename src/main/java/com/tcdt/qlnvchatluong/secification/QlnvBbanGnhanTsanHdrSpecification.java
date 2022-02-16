package com.tcdt.qlnvchatluong.secification;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tcdt.qlnvchatluong.request.search.QlnvBbanGnhanTsanSearchReq;
import com.tcdt.qlnvchatluong.request.search.QlnvPboTaisanSearchReq;
import com.tcdt.qlnvchatluong.table.QlnvBbanGnhanTsanHdr;
import com.tcdt.qlnvchatluong.table.QlnvPboTaisanDtl;
import com.tcdt.qlnvchatluong.table.QlnvPboTaisanHdr;
import com.tcdt.qlnvchatluong.util.Contains;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import javax.validation.Valid;
import java.util.Date;

public class QlnvBbanGnhanTsanHdrSpecification {
    public static Specification<QlnvBbanGnhanTsanHdr> buildSearchQuery(final @Valid QlnvBbanGnhanTsanSearchReq objReq) {
        return new Specification<QlnvBbanGnhanTsanHdr>() {

            private static final long serialVersionUID = 8358529230545640768L;

            @Override
            public Predicate toPredicate(Root<QlnvBbanGnhanTsanHdr> root, CriteriaQuery<?> query,
                                         CriteriaBuilder builder) {
                Predicate predicate = builder.conjunction();
                if (ObjectUtils.isEmpty(objReq))
                    return predicate;

                String soBban = objReq.getSoBban();
                String maDviGiao = objReq.getMaDviGiao();
                String maDviNhan = objReq.getMaDviNhan();
                Date tuNgayLap = objReq.getTuNgayLap();
                Date denNgayLap = objReq.getDenNgayLap();
                root.fetch("children", JoinType.LEFT);

                if (StringUtils.isNotEmpty(soBban))
                    predicate.getExpressions()
                            .add(builder.like(builder.lower(root.get("soBban")), "%" + soBban.toLowerCase() + "%"));

                if (StringUtils.isNotBlank(maDviGiao))
                    predicate.getExpressions().add(builder.and(builder.equal(root.get("maDviGiao"), maDviGiao)));

                if (StringUtils.isNotBlank(maDviNhan))
                    predicate.getExpressions().add(builder.and(builder.equal(root.get("maDviNhan"), maDviNhan)));


                if (ObjectUtils.isNotEmpty(tuNgayLap) && ObjectUtils.isNotEmpty(denNgayLap)) {
                    predicate.getExpressions()
                            .add(builder.and(builder.greaterThanOrEqualTo(root.get("ngayLap"), tuNgayLap)));
                    predicate.getExpressions().add(builder.and(builder.lessThan(root.get("ngayLap"), new DateTime(denNgayLap).plusDays(1).toDate())));
                } else if (ObjectUtils.isNotEmpty(tuNgayLap)) {
                    predicate.getExpressions()
                            .add(builder.and(builder.greaterThanOrEqualTo(root.get("ngayLap"), tuNgayLap)));
                } else if (ObjectUtils.isNotEmpty(denNgayLap)) {
                    predicate.getExpressions().add(builder.and(builder.lessThan(root.get("ngayLap"), new DateTime(denNgayLap).plusDays(1).toDate())));
                }

                return predicate;
            }
        };
    }

}
