package com.tcdt.qlnvchatluong.secification;

import com.tcdt.qlnvchatluong.request.search.QlnvHdongTaisanSearchReq;
import com.tcdt.qlnvchatluong.table.QlnvHdongTaisanHdr;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.validation.Valid;
import java.util.Date;

public class QlnvHdongTaisanHdrSpecification {
        public static Specification<QlnvHdongTaisanHdr> buildSearchQuery(final @Valid QlnvHdongTaisanSearchReq objReq) {
            return new Specification<QlnvHdongTaisanHdr>() {

                private static final long serialVersionUID = 7261679149468731988L;

                @Override
                public Predicate toPredicate(Root<QlnvHdongTaisanHdr> root, CriteriaQuery<?> query,
                                             CriteriaBuilder builder) {
                    Predicate predicate = builder.conjunction();
                    if (ObjectUtils.isEmpty(objReq))
                        return predicate;

                    String soHdong = objReq.getSoHdong();
                    String tenHdong = objReq.getTenHdong();
                    String tenNthau = objReq.getTenNthau();
                    Date tuNgayHdong = objReq.getTuNgayHdong();
                    Date denNgayHdong = objReq.getDenNgayHdong();

                    if (StringUtils.isNotEmpty(soHdong))
                        predicate.getExpressions()
                                .add(builder.like(builder.lower(root.get("soHdong")), "%" + soHdong.toLowerCase() + "%"));

                    if (StringUtils.isNotBlank(tenHdong))
                        predicate.getExpressions().add(builder.and(builder.equal(root.get("tenHdong"), tenHdong)));

                    if (StringUtils.isNotBlank(tenNthau))
                        predicate.getExpressions().add(builder.and(builder.equal(root.get("tenNthau"), tenNthau)));


                    if (ObjectUtils.isNotEmpty(tuNgayHdong) && ObjectUtils.isNotEmpty(denNgayHdong)) {
                        predicate.getExpressions()
                                .add(builder.and(builder.greaterThanOrEqualTo(root.get("ngayHdong"), tuNgayHdong)));
                        predicate.getExpressions().add(builder.and(builder.lessThan(root.get("ngayHdong"), denNgayHdong)));
                    } else if (ObjectUtils.isNotEmpty(tuNgayHdong)) {
                        predicate.getExpressions()
                                .add(builder.and(builder.greaterThanOrEqualTo(root.get("ngayHdong"), tuNgayHdong)));
                    } else if (ObjectUtils.isNotEmpty(denNgayHdong)) {
                        predicate.getExpressions().add(builder.and(builder.lessThan(root.get("ngayHdong"), denNgayHdong)));
                    }

                    return predicate;
                }
            };
        }
}
