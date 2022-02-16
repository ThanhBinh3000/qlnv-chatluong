package com.tcdt.qlnvchatluong.controller;

import com.tcdt.qlnvchatluong.enums.EnumResponse;
import com.tcdt.qlnvchatluong.repository.QlnvPhieuYcktclHdrRepository;
import com.tcdt.qlnvchatluong.request.IdSearchReq;
import com.tcdt.qlnvchatluong.request.object.QlnvPhieuYcktclDtlReq;
import com.tcdt.qlnvchatluong.request.object.QlnvPhieuYcktclHdrReq;
import com.tcdt.qlnvchatluong.request.search.QlnvPhieuYcktclSearchReq;
import com.tcdt.qlnvchatluong.response.BaseResponse;
import com.tcdt.qlnvchatluong.secification.QlnvPhieuYcktclHdrSpecification;
import com.tcdt.qlnvchatluong.table.QlnvPhieuYcktclDtl;
import com.tcdt.qlnvchatluong.table.QlnvPhieuYcktclHdr;
import com.tcdt.qlnvchatluong.util.Contains;
import com.tcdt.qlnvchatluong.util.ObjectMapperUtils;
import com.tcdt.qlnvchatluong.util.PaginationSet;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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

import com.tcdt.qlnvchatluong.util.PathContains;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = PathContains.QL_CHAT_LUONG_HANG + PathContains.PHIEU_YCAU_KTRA_CLH)
@Api(tags = "Quản lý phiếu kiểm định chất lượng hàng của tổ chức, cá nhân cung cấp dịch vụ kiểm định")
public class QlnvPhieuYcktclController extends BaseController {
    @Autowired
    private QlnvPhieuYcktclHdrRepository qlnvPhieuYcktclHdrRepository;

    @ApiOperation(value = "Tạo mới phiếu kiểm định chất lượng hàng của tổ chức, cá nhân cung cấp dịch vụ kiểm định", response = List.class)
    @PostMapping(value = PathContains.URL_TAO_MOI, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<BaseResponse> insert(@Valid HttpServletRequest request,
                                               @RequestBody QlnvPhieuYcktclHdrReq objReq) {
        BaseResponse resp = new BaseResponse();
        try {
            // Add thong tin hdr
            QlnvPhieuYcktclHdr dataMap = new ModelMapper().map(objReq, QlnvPhieuYcktclHdr.class);
            dataMap.setNgayTao(getDateTimeNow());
            dataMap.setTrangThai(Contains.TAO_MOI);
            dataMap.setNguoiTao(getUserName(request));

            // Add thong tin detail
            List<QlnvPhieuYcktclDtlReq> dtlReqList = objReq.getDetail();
            List<QlnvPhieuYcktclDtl> dtls = ObjectMapperUtils.mapAll(dtlReqList, QlnvPhieuYcktclDtl.class);
            dataMap.setChildren(dtls);

            QlnvPhieuYcktclHdr createCheck = qlnvPhieuYcktclHdrRepository.save(dataMap);

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

    @ApiOperation(value = "Xoá thông tin phiếu kiểm định chất lượng hàng của tổ chức, cá nhân cung cấp dịch vụ kiểm định", response = List.class, produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = PathContains.URL_XOA, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<BaseResponse> delete(@RequestBody IdSearchReq idSearchReq) {
        BaseResponse resp = new BaseResponse();
        try {
            if (StringUtils.isEmpty(idSearchReq.getId()))
                throw new Exception("Xoá thất bại, không tìm thấy dữ liệu");

            Optional<QlnvPhieuYcktclHdr> qOptional = qlnvPhieuYcktclHdrRepository.findById(idSearchReq.getId());
            if (!qOptional.isPresent())
                throw new Exception("Không tìm thấy dữ liệu cần xoá");

            qlnvPhieuYcktclHdrRepository.delete(qOptional.get());

            resp.setStatusCode(EnumResponse.RESP_SUCC.getValue());
            resp.setMsg(EnumResponse.RESP_SUCC.getDescription());
        } catch (Exception e) {
            resp.setStatusCode(EnumResponse.RESP_FAIL.getValue());
            resp.setMsg(e.getMessage());
            log.error(e.getMessage());
        }

        return ResponseEntity.ok(resp);
    }

    @ApiOperation(value = "Tra cứu phiếu kiểm định chất lượng hàng của tổ chức, cá nhân cung cấp dịch vụ kiểm định", response = List.class)
    @PostMapping(value = PathContains.URL_TRA_CUU, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<BaseResponse> selectAll(@RequestBody QlnvPhieuYcktclSearchReq objReq) {
        BaseResponse resp = new BaseResponse();
        try {
            int page = PaginationSet.getPage(objReq.getPaggingReq().getPage());
            int limit = PaginationSet.getLimit(objReq.getPaggingReq().getLimit());
            Pageable pageable = PageRequest.of(page, limit);

            Page<QlnvPhieuYcktclHdr> qhKho = qlnvPhieuYcktclHdrRepository
                    .findAll(QlnvPhieuYcktclHdrSpecification.buildSearchQuery(objReq), pageable);

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

    @ApiOperation(value = "Cập nhật phiếu kiểm định chất lượng hàng của tổ chức, cá nhân cung cấp dịch vụ kiểm định", response = List.class)
    @PostMapping(value = PathContains.URL_CAP_NHAT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResponse> update(@Valid HttpServletRequest request,
                                               @RequestBody QlnvPhieuYcktclHdrReq objReq) {
        BaseResponse resp = new BaseResponse();
        try {
            if (StringUtils.isEmpty(objReq.getId()))
                throw new Exception("Sửa thất bại, không tìm thấy dữ liệu");

            Optional<QlnvPhieuYcktclHdr> qOptional = qlnvPhieuYcktclHdrRepository.findById(Long.valueOf(objReq.getId()));
            if (!qOptional.isPresent())
                throw new Exception("Không tìm thấy dữ liệu cần sửa");

            objReq.setSoPhieu(null);
            QlnvPhieuYcktclHdr dataDTB = qOptional.get();
            QlnvPhieuYcktclHdr dataMap = ObjectMapperUtils.map(objReq, QlnvPhieuYcktclHdr.class);

            updateObjectToObject(dataDTB, dataMap);

            dataDTB.setNgaySua(getDateTimeNow());
            dataDTB.setNguoiSua(getUserName(request));

            // Add thong tin detail
            List<QlnvPhieuYcktclDtlReq> dtlReqList = objReq.getDetail();
            List<QlnvPhieuYcktclDtl> dtls = ObjectMapperUtils.mapAll(dtlReqList, QlnvPhieuYcktclDtl.class);
            dataDTB.setChildren(dtls);

            QlnvPhieuYcktclHdr createCheck = qlnvPhieuYcktclHdrRepository.save(dataDTB);

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

    @ApiOperation(value = "Lấy chi tiết thông tin phiếu kiểm định chất lượng hàng của tổ chức, cá nhân cung cấp dịch vụ kiểm định", response = List.class)
    @GetMapping(value = PathContains.URL_CHI_TIET + "/{ids}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<BaseResponse> detail(
            @ApiParam(value = "ID phiếu kiểm định chất lượng hàng của tổ chức, cá nhân cung cấp dịch vụ kiểm định", example = "1", required = true) @PathVariable("ids") String ids) {
        BaseResponse resp = new BaseResponse();
        try {
            if (StringUtils.isEmpty(ids))
                throw new UnsupportedOperationException("Không tồn tại bản ghi");

            Optional<QlnvPhieuYcktclHdr> qOptional = qlnvPhieuYcktclHdrRepository
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
