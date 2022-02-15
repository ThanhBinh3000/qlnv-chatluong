package com.tcdt.qlnvchatluong.controller;

import com.tcdt.qlnvchatluong.enums.EnumResponse;
import com.tcdt.qlnvchatluong.repository.QlnvTtinGtriBhiemHdrRepository;
import com.tcdt.qlnvchatluong.request.IdSearchReq;
import com.tcdt.qlnvchatluong.request.object.QlnvTtinGtriBhiemDtlReq;
import com.tcdt.qlnvchatluong.request.object.QlnvTtinGtriBhiemHdrReq;
import com.tcdt.qlnvchatluong.request.search.QlnvTtinGtriBhiemSearchReq;
import com.tcdt.qlnvchatluong.response.BaseResponse;
import com.tcdt.qlnvchatluong.secification.QlnvTtinGtriBhiemHdrSpecification;
import com.tcdt.qlnvchatluong.table.QlnvTtinGtriBhiemDtl;
import com.tcdt.qlnvchatluong.table.QlnvTtinGtriBhiemHdr;
import com.tcdt.qlnvchatluong.util.Contains;
import com.tcdt.qlnvchatluong.util.ObjectMapperUtils;
import com.tcdt.qlnvchatluong.util.PaginationSet;
import com.tcdt.qlnvchatluong.util.PathContains;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = PathContains.QL_GIA_TRI_BAO_HIEM)
@Api(tags = "Quản lý thông tin về giá trị bảo hiểm")
public class QlnvTtinGtriBhiemHdrController extends BaseController {
    @Autowired
    private QlnvTtinGtriBhiemHdrRepository qlnvTtinGtriBhiemHdrRepository;

    @ApiOperation(value = "Tạo mới thông tin về giá trị bảo hiểm", response = List.class)
    @PostMapping(value = PathContains.URL_TAO_MOI, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<BaseResponse> insert(@Valid HttpServletRequest request,
                                               @RequestBody QlnvTtinGtriBhiemHdrReq objReq) {
        BaseResponse resp = new BaseResponse();
        try {
            // Add thong tin hdr
            QlnvTtinGtriBhiemHdr dataMap = new ModelMapper().map(objReq, QlnvTtinGtriBhiemHdr.class);
            dataMap.setNgayTao(getDateTimeNow());
            dataMap.setTrangThai(Contains.TAO_MOI);
            dataMap.setNguoiTao(getUserName(request));

            // Add thong tin detail
            List<QlnvTtinGtriBhiemDtlReq> dtlReqList = objReq.getDetail();
            List<QlnvTtinGtriBhiemDtl> dtls = ObjectMapperUtils.mapAll(dtlReqList, QlnvTtinGtriBhiemDtl.class);
            dataMap.setChildren(dtls);

            QlnvTtinGtriBhiemHdr createCheck = qlnvTtinGtriBhiemHdrRepository.save(dataMap);

            resp.setData(createCheck);
            resp.setStatusCode(EnumResponse.RESP_SUCC.getValue());
            resp.setMsg(EnumResponse.RESP_SUCC.getDescription());
        } catch (Exception e) {
            resp.setStatusCode(EnumResponse.RESP_FAIL.getValue());
            resp.setMsg(e.getMessage());
            log.error(e.getMessage());
        }
        return ResponseEntity.ok(resp);
    }

    @ApiOperation(value = "Xoá thông tin về giá trị bảo hiểm", response = List.class, produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = PathContains.URL_XOA, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<BaseResponse> delete(@RequestBody IdSearchReq idSearchReq) {
        BaseResponse resp = new BaseResponse();
        try {
            if (StringUtils.isEmpty(idSearchReq.getId()))
                throw new Exception("Xoá thất bại, không tìm thấy dữ liệu");

            Optional<QlnvTtinGtriBhiemHdr> qOptional = qlnvTtinGtriBhiemHdrRepository.findById(idSearchReq.getId());
            if (!qOptional.isPresent())
                throw new Exception("Không tìm thấy dữ liệu cần xoá");

            qlnvTtinGtriBhiemHdrRepository.delete(qOptional.get());

            resp.setStatusCode(EnumResponse.RESP_SUCC.getValue());
            resp.setMsg(EnumResponse.RESP_SUCC.getDescription());
        } catch (Exception e) {
            resp.setStatusCode(EnumResponse.RESP_FAIL.getValue());
            resp.setMsg(e.getMessage());
            log.error(e.getMessage());
        }

        return ResponseEntity.ok(resp);
    }

    @ApiOperation(value = "Tra cứu thông tin về giá trị bảo hiểm", response = List.class)
    @PostMapping(value = PathContains.URL_TRA_CUU, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<BaseResponse> selectAll(@RequestBody QlnvTtinGtriBhiemSearchReq objReq) {
        BaseResponse resp = new BaseResponse();
        try {
            int page = PaginationSet.getPage(objReq.getPaggingReq().getPage());
            int limit = PaginationSet.getLimit(objReq.getPaggingReq().getLimit());
            Pageable pageable = PageRequest.of(page, limit);

            Page<QlnvTtinGtriBhiemHdr> qhKho = qlnvTtinGtriBhiemHdrRepository
                    .findAll(QlnvTtinGtriBhiemHdrSpecification.buildSearchQuery(objReq), pageable);

            resp.setStatusCode(EnumResponse.RESP_SUCC.getValue());
            resp.setMsg(EnumResponse.RESP_SUCC.getDescription());
            resp.setData(qhKho);
        } catch (Exception e) {
            resp.setStatusCode(EnumResponse.RESP_FAIL.getValue());
            resp.setMsg(e.getMessage());
            log.error(e.getMessage());
        }

        return ResponseEntity.ok(resp);
    }

    @ApiOperation(value = "Cập nhật thông tin về giá trị bảo hiểm", response = List.class)
    @PostMapping(value = PathContains.URL_CAP_NHAT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResponse> update(@Valid HttpServletRequest request,
                                               @RequestBody QlnvTtinGtriBhiemHdrReq objReq) {
        BaseResponse resp = new BaseResponse();
        try {
            if (StringUtils.isEmpty(objReq.getId()))
                throw new Exception("Sửa thất bại, không tìm thấy dữ liệu");

            Optional<QlnvTtinGtriBhiemHdr> qOptional = qlnvTtinGtriBhiemHdrRepository.findById(Long.valueOf(objReq.getId()));
            if (!qOptional.isPresent())
                throw new Exception("Không tìm thấy dữ liệu cần sửa");

            QlnvTtinGtriBhiemHdr dataDTB = qOptional.get();
            QlnvTtinGtriBhiemHdr dataMap = ObjectMapperUtils.map(objReq, QlnvTtinGtriBhiemHdr.class);

            updateObjectToObject(dataDTB, dataMap);
            dataDTB.setNgaySua(getDateTimeNow());
            dataDTB.setNguoiSua(getUserName(request));

            // Add thong tin detail
            List<QlnvTtinGtriBhiemDtlReq> dtlReqList = objReq.getDetail();
            List<QlnvTtinGtriBhiemDtl> dtls = ObjectMapperUtils.mapAll(dtlReqList, QlnvTtinGtriBhiemDtl.class);
            dataDTB.setChildren(dtls);

            QlnvTtinGtriBhiemHdr createCheck = qlnvTtinGtriBhiemHdrRepository.save(dataDTB);

            resp.setData(createCheck);
            resp.setStatusCode(EnumResponse.RESP_SUCC.getValue());
            resp.setMsg(EnumResponse.RESP_SUCC.getDescription());
        } catch (Exception e) {
            resp.setStatusCode(EnumResponse.RESP_FAIL.getValue());
            resp.setMsg(e.getMessage());
            log.error(e.getMessage());
        }
        return ResponseEntity.ok(resp);
    }

    @ApiOperation(value = "Lấy chi tiết thông tin về giá trị bảo hiểm", response = List.class)
    @GetMapping(value = PathContains.URL_CHI_TIET + "/{ids}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<BaseResponse> detail(
            @ApiParam(value = "ID thông tin về giá trị bảo hiểm", example = "1", required = true) @PathVariable("ids") String ids) {
        BaseResponse resp = new BaseResponse();
        try {
            if (StringUtils.isEmpty(ids))
                throw new UnsupportedOperationException("Không tồn tại bản ghi");

            Optional<QlnvTtinGtriBhiemHdr> qOptional = qlnvTtinGtriBhiemHdrRepository
                    .findById(Long.valueOf(Long.parseLong(ids)));
            if (!qOptional.isPresent())
                throw new UnsupportedOperationException("Không tồn tại bản ghi");

            resp.setData(qOptional);
            resp.setStatusCode(EnumResponse.RESP_SUCC.getValue());
            resp.setMsg(EnumResponse.RESP_SUCC.getDescription());
        } catch (Exception e) {
            resp.setStatusCode(EnumResponse.RESP_FAIL.getValue());
            resp.setMsg(e.getMessage());
            log.error(e.getMessage());
        }
        return ResponseEntity.ok(resp);
    }
}
