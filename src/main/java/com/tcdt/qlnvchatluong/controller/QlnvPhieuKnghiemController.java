package com.tcdt.qlnvchatluong.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.ObjectUtils;
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
import com.tcdt.qlnvchatluong.repository.QlnvPhieuKnghiemHdrRepository;
import com.tcdt.qlnvchatluong.request.IdSearchReq;
import com.tcdt.qlnvchatluong.request.object.QlnvPhieuKnghiemDtlReq;
import com.tcdt.qlnvchatluong.request.object.QlnvPhieuKnghiemHdrReq;
import com.tcdt.qlnvchatluong.request.object.StatusReq;
import com.tcdt.qlnvchatluong.request.search.QlnvPhieuKnghiemSearchReq;
import com.tcdt.qlnvchatluong.response.BaseResponse;
import com.tcdt.qlnvchatluong.secification.QlnvPhieuKnghiemHdrSpecification;
import com.tcdt.qlnvchatluong.table.QlnvPhieuKnghiemDtl;
import com.tcdt.qlnvchatluong.table.QlnvPhieuKnghiemHdr;
import com.tcdt.qlnvchatluong.table.catalog.QlnvDmDonvi;
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
@RequestMapping(value = PathContains.QL_CHAT_LUONG_HANG + PathContains.PHIEU_KNGHIEM_CLH)
@Api(tags = "Đề xuất kế hoạch xây dựng Kho tàng trung hạn")
public class QlnvPhieuKnghiemController extends BaseController {
	@Autowired
	private QlnvPhieuKnghiemHdrRepository qlnvPhieuKnghiemHdrRepository;

	@ApiOperation(value = "Tạo mới phiếu kiểm nghiệm chất lượng hàng", response = List.class)
	@PostMapping(value = PathContains.URL_TAO_MOI, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<BaseResponse> insert(@Valid HttpServletRequest request,
			@RequestBody QlnvPhieuKnghiemHdrReq objReq) {
		BaseResponse resp = new BaseResponse();
		try {
			// Add thong tin hdr
			QlnvPhieuKnghiemHdr dataMap = new ModelMapper().map(objReq, QlnvPhieuKnghiemHdr.class);
			dataMap.setNgayTao(getDateTimeNow());
			dataMap.setTrangThai(Contains.TAO_MOI);
			dataMap.setNguoiTao(getUserName(request));

			// Add thong tin detail
			List<QlnvPhieuKnghiemDtlReq> dtlReqList = objReq.getDetail();
			List<QlnvPhieuKnghiemDtl> dtls = ObjectMapperUtils.mapAll(dtlReqList, QlnvPhieuKnghiemDtl.class);
			dataMap.setChildren(dtls);

			QlnvPhieuKnghiemHdr createCheck = qlnvPhieuKnghiemHdrRepository.save(dataMap);

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

	@ApiOperation(value = "Xoá thông tin phiếu kiểm nghiệm chất lượng hàng", response = List.class, produces = MediaType.APPLICATION_JSON_VALUE)
	@PostMapping(value = PathContains.URL_XOA, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<BaseResponse> delete(@RequestBody IdSearchReq idSearchReq) {
		BaseResponse resp = new BaseResponse();
		try {
			if (StringUtils.isEmpty(idSearchReq.getId()))
				throw new Exception("Xoá thất bại, không tìm thấy dữ liệu");

			Optional<QlnvPhieuKnghiemHdr> qOptional = qlnvPhieuKnghiemHdrRepository.findById(idSearchReq.getId());
			if (!qOptional.isPresent())
				throw new Exception("Không tìm thấy dữ liệu cần xoá");

			qlnvPhieuKnghiemHdrRepository.delete(qOptional.get());

			resp.setStatusCode(EnumResponse.RESP_SUCC.getValue());
			resp.setMsg(EnumResponse.RESP_SUCC.getDescription());
		} catch (Exception e) {
			resp.setStatusCode(EnumResponse.RESP_FAIL.getValue());
			resp.setMsg(e.getMessage());
			log.error(e.getMessage());
		}

		return ResponseEntity.ok(resp);
	}

	@ApiOperation(value = "Tra cứu phiếu kiểm nghiệm chất lượng hàng", response = List.class)
	@PostMapping(value = PathContains.URL_TRA_CUU, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<BaseResponse> selectAll(@RequestBody QlnvPhieuKnghiemSearchReq objReq) {
		BaseResponse resp = new BaseResponse();
		try {
			int page = PaginationSet.getPage(objReq.getPaggingReq().getPage());
			int limit = PaginationSet.getLimit(objReq.getPaggingReq().getLimit());
			Pageable pageable = PageRequest.of(page, limit);

			Page<QlnvPhieuKnghiemHdr> qhKho = qlnvPhieuKnghiemHdrRepository
					.findAll(QlnvPhieuKnghiemHdrSpecification.buildSearchQuery(objReq), pageable);

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

	@ApiOperation(value = "Cập nhật phiếu kiểm nghiệm chất lượng hàng", response = List.class)
	@PostMapping(value = PathContains.URL_CAP_NHAT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponse> update(@Valid HttpServletRequest request,
			@RequestBody QlnvPhieuKnghiemHdrReq objReq) {
		BaseResponse resp = new BaseResponse();
		try {
			if (StringUtils.isEmpty(objReq.getId()))
				throw new Exception("Sửa thất bại, không tìm thấy dữ liệu");

			Optional<QlnvPhieuKnghiemHdr> qOptional = qlnvPhieuKnghiemHdrRepository
					.findById(Long.valueOf(objReq.getId()));
			if (!qOptional.isPresent())
				throw new Exception("Không tìm thấy dữ liệu cần sửa");

			objReq.setSoPhieu(null);
			QlnvPhieuKnghiemHdr dataDTB = qOptional.get();
			QlnvPhieuKnghiemHdr dataMap = ObjectMapperUtils.map(objReq, QlnvPhieuKnghiemHdr.class);

			updateObjectToObject(dataDTB, dataMap);

			dataDTB.setNgaySua(getDateTimeNow());
			dataDTB.setNguoiSua(getUserName(request));
			if (ObjectUtils.isNotEmpty(objReq.getNgayLap()))
				dataMap.setNgayLap(getDateTimeNow());

			// Add thong tin detail
			List<QlnvPhieuKnghiemDtlReq> dtlReqList = objReq.getDetail();
			List<QlnvPhieuKnghiemDtl> dtls = ObjectMapperUtils.mapAll(dtlReqList, QlnvPhieuKnghiemDtl.class);
			dataDTB.setChildren(dtls);

			QlnvPhieuKnghiemHdr createCheck = qlnvPhieuKnghiemHdrRepository.save(dataDTB);

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

	@ApiOperation(value = "Lấy chi tiết thông tin phiếu kiểm nghiệm chất lượng hàng", response = List.class)
	@GetMapping(value = PathContains.URL_CHI_TIET + "/{ids}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<BaseResponse> detail(
			@ApiParam(value = "ID phiếu kiểm nghiệm chất lượng hàng", example = "1", required = true) @PathVariable("ids") String ids) {
		BaseResponse resp = new BaseResponse();
		try {
			if (StringUtils.isEmpty(ids))
				throw new UnsupportedOperationException("Không tồn tại bản ghi");

			Optional<QlnvPhieuKnghiemHdr> qOptional = qlnvPhieuKnghiemHdrRepository
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

	@ApiOperation(value = "Trình duyệt-01/Duyệt-02/Từ chối-03 phiếu kiểm nghiệm chất lượng hàng", response = List.class)
	@PostMapping(value = PathContains.URL_PHE_DUYET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponse> updateStatus(HttpServletRequest req, @Valid @RequestBody StatusReq stReq) {
		BaseResponse resp = new BaseResponse();
		try {
			if (StringUtils.isEmpty(stReq.getId()))
				throw new Exception("Không tìm thấy dữ liệu");

			Optional<QlnvPhieuKnghiemHdr> qHoach = qlnvPhieuKnghiemHdrRepository.findById(Long.valueOf(stReq.getId()));
			if (!qHoach.isPresent())
				throw new Exception("Không tìm thấy dữ liệu");

			// Lay thong tin don vi quan ly
			QlnvDmDonvi objDvi = getDvi(req);
			if (!objDvi.getCapDvi().equals(Contains.CAP_CHI_CUC))
				throw new UnsupportedOperationException("Người sử dụng không phải cấp chi cục để phê duyệt");

			String status = stReq.getTrangThai() + qHoach.get().getTrangThai();
			switch (status) {
			case Contains.CHO_DUYET + Contains.TAO_MOI:
				qHoach.get().setNguoiGuiDuyet(getUserName(req));
				qHoach.get().setNgayGuiDuyet(getDateTimeNow());
				break;
			case Contains.TU_CHOI + Contains.CHO_DUYET:
				qHoach.get().setKtvPduyet(getUserName(req));
				qHoach.get().setNgayKtvPduyet(getDateTimeNow());
				qHoach.get().setLdoTuchoi(stReq.getLyDo());
				break;
			case Contains.TU_CHOI + Contains.KTV_DUYET:
				qHoach.get().setNguoiPduyet(getUserName(req));
				qHoach.get().setNgayPduyet(getDateTimeNow());
				qHoach.get().setLdoTuchoi(stReq.getLyDo());
				break;
			case Contains.KTV_DUYET + Contains.CHO_DUYET:
				qHoach.get().setKtvPduyet(getUserName(req));
				qHoach.get().setNgayKtvPduyet(getDateTimeNow());
				break;
			case Contains.DUYET + Contains.KTT_DUYET:
				qHoach.get().setNguoiPduyet(getUserName(req));
				qHoach.get().setNgayPduyet(getDateTimeNow());
				break;
			default:
				throw new Exception("Phê duyệt không thành công");
			}

			qHoach.get().setTrangThai(stReq.getTrangThai());
			qlnvPhieuKnghiemHdrRepository.save(qHoach.get());

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