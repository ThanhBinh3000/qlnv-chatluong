package com.tcdt.qlnvchatluong.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.tcdt.qlnvchatluong.enums.EnumResponse;
import com.tcdt.qlnvchatluong.repository.QlnvHdongTaisanHdrRepository;
import com.tcdt.qlnvchatluong.request.IdSearchReq;
import com.tcdt.qlnvchatluong.request.object.QlnvHdongTaisanDtlReq;
import com.tcdt.qlnvchatluong.request.object.QlnvHdongTaisanHdrReq;
import com.tcdt.qlnvchatluong.request.search.QlnvHdongTaisanSearchReq;
import com.tcdt.qlnvchatluong.response.BaseResponse;
import com.tcdt.qlnvchatluong.secification.QlnvHdongTaisanHdrSpecification;
import com.tcdt.qlnvchatluong.table.QlnvHdongTaisanDtl;
import com.tcdt.qlnvchatluong.table.QlnvHdongTaisanHdr;
import com.tcdt.qlnvchatluong.util.Contains;
import com.tcdt.qlnvchatluong.util.ObjectMapperUtils;
import com.tcdt.qlnvchatluong.util.PaginationSet;
import com.tcdt.qlnvchatluong.util.PathContains;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = PathContains.QL_CHAT_LUONG_HANG + PathContains.HOP_DONG)
@Api(tags = "Quản lý thông tin hợp đồng")
public class QlnvHdongTaisanHdrController extends BaseController {
    @Autowired
    private QlnvHdongTaisanHdrRepository qlnvHdongTaisanHdrRepository;

    @ApiOperation(value = "Tạo mới hợp đồng", response = List.class)
    @PostMapping(value = PathContains.URL_TAO_MOI, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<BaseResponse> insert(@Valid HttpServletRequest request,
                                               @RequestBody QlnvHdongTaisanHdrReq objReq) {
        BaseResponse resp = new BaseResponse();
        try {
            // Add thong tin hdr
            QlnvHdongTaisanHdr dataMap = new ModelMapper().map(objReq, QlnvHdongTaisanHdr.class);
            dataMap.setNgayTao(getDateTimeNow());
            dataMap.setTrangThai(Contains.TAO_MOI);
            dataMap.setNguoiTao(getUserName(request));

            // Add thong tin detail
            List<QlnvHdongTaisanDtlReq> dtlReqList = objReq.getDetail();
            List<QlnvHdongTaisanDtl> dtls = ObjectMapperUtils.mapAll(dtlReqList, QlnvHdongTaisanDtl.class);
            dataMap.setChildren(dtls);

            QlnvHdongTaisanHdr createCheck = qlnvHdongTaisanHdrRepository.save(dataMap);

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

    @ApiOperation(value = "Xoá thông tin hợp đồng", response = List.class, produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = PathContains.URL_XOA, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<BaseResponse> delete(@RequestBody IdSearchReq idSearchReq) {
        BaseResponse resp = new BaseResponse();
        try {
            if (StringUtils.isEmpty(idSearchReq.getId()))
                throw new Exception("Xoá thất bại, không tìm thấy dữ liệu");

            Optional<QlnvHdongTaisanHdr> qOptional = qlnvHdongTaisanHdrRepository.findById(idSearchReq.getId());
            if (!qOptional.isPresent())
                throw new Exception("Không tìm thấy dữ liệu cần xoá");

            qlnvHdongTaisanHdrRepository.delete(qOptional.get());

            resp.setStatusCode(EnumResponse.RESP_SUCC.getValue());
            resp.setMsg(EnumResponse.RESP_SUCC.getDescription());
        } catch (Exception e) {
            resp.setStatusCode(EnumResponse.RESP_FAIL.getValue());
            resp.setMsg(e.getMessage());
            log.error(e.getMessage());
        }

        return ResponseEntity.ok(resp);
    }

    @ApiOperation(value = "Tra cứu hợp đồng", response = List.class)
    @PostMapping(value = PathContains.URL_TRA_CUU, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<BaseResponse> selectAll(@RequestBody QlnvHdongTaisanSearchReq objReq) {
        BaseResponse resp = new BaseResponse();
        try {
            int page = PaginationSet.getPage(objReq.getPaggingReq().getPage());
            int limit = PaginationSet.getLimit(objReq.getPaggingReq().getLimit());
            Pageable pageable = PageRequest.of(page, limit);

            Page<QlnvHdongTaisanHdr> qhKho = qlnvHdongTaisanHdrRepository
                    .findAll(QlnvHdongTaisanHdrSpecification.buildSearchQuery(objReq), pageable);

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

    @ApiOperation(value = "Cập nhật hợp đồng", response = List.class)
    @PostMapping(value = PathContains.URL_CAP_NHAT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResponse> update(@Valid HttpServletRequest request,
                                               @RequestBody QlnvHdongTaisanHdrReq objReq) {
        BaseResponse resp = new BaseResponse();
        try {
            if (StringUtils.isEmpty(objReq.getId()))
                throw new Exception("Sửa thất bại, không tìm thấy dữ liệu");

            Optional<QlnvHdongTaisanHdr> qOptional = qlnvHdongTaisanHdrRepository.findById(Long.valueOf(objReq.getId()));
            if (!qOptional.isPresent())
                throw new Exception("Không tìm thấy dữ liệu cần sửa");

            QlnvHdongTaisanHdr dataDTB = qOptional.get();
            QlnvHdongTaisanHdr dataMap = ObjectMapperUtils.map(objReq, QlnvHdongTaisanHdr.class);

            updateObjectToObject(dataDTB, dataMap);

            dataDTB.setNgaySua(getDateTimeNow());
            dataDTB.setNguoiSua(getUserName(request));

            // Add thong tin detail
            List<QlnvHdongTaisanDtlReq> dtlReqList = objReq.getDetail();
            List<QlnvHdongTaisanDtl> dtls = ObjectMapperUtils.mapAll(dtlReqList, QlnvHdongTaisanDtl.class);
            dataDTB.setChildren(dtls);

            QlnvHdongTaisanHdr createCheck = qlnvHdongTaisanHdrRepository.save(dataDTB);

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

    @ApiOperation(value = "Lấy chi tiết thông tin hợp đồng", response = List.class)
    @GetMapping(value = PathContains.URL_CHI_TIET + "/{ids}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<BaseResponse> detail(
            @ApiParam(value = "ID hợp đồng", example = "1", required = true) @PathVariable("ids") String ids) {
        BaseResponse resp = new BaseResponse();
        try {
            if (StringUtils.isEmpty(ids))
                throw new UnsupportedOperationException("Không tồn tại bản ghi");

            Optional<QlnvHdongTaisanHdr> qOptional = qlnvHdongTaisanHdrRepository
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
