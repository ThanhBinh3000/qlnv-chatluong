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
import com.tcdt.qlnvchatluong.repository.QlnvQdinhTaisanHdrRepository;
import com.tcdt.qlnvchatluong.request.IdSearchReq;
import com.tcdt.qlnvchatluong.request.object.QlnvQdinhTaisanDtlReq;
import com.tcdt.qlnvchatluong.request.object.QlnvQdinhTaisanHdrReq;
import com.tcdt.qlnvchatluong.request.object.StatusReq;
import com.tcdt.qlnvchatluong.request.search.QlnvQdinhTaisanSearchReq;
import com.tcdt.qlnvchatluong.response.BaseResponse;
import com.tcdt.qlnvchatluong.secification.QlnvQdinhTaisanHdrSpecification;
import com.tcdt.qlnvchatluong.table.QlnvQdinhTaisanDtl;
import com.tcdt.qlnvchatluong.table.QlnvQdinhTaisanHdr;
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
@RequestMapping(value = PathContains.QL_CHAT_LUONG_HANG + PathContains.QDINH_MMOC_TBI)
@Api(tags = "Quản lý máy móc, thiết bị/Quản lý quyết định")
public class QlnvQdinhTsanHdrController extends BaseController {
	@Autowired
	private QlnvQdinhTaisanHdrRepository qlnvQdinhTaisanHdrRepository;

	@ApiOperation(value = "Tạo mới quyết định", response = List.class)
	@PostMapping(value = PathContains.URL_TAO_MOI, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<BaseResponse> insert(@Valid HttpServletRequest request,
			@RequestBody QlnvQdinhTaisanHdrReq objReq) {
		BaseResponse resp = new BaseResponse();
		try {
			// Add thong tin hdr
			QlnvQdinhTaisanHdr dataMap = new ModelMapper().map(objReq, QlnvQdinhTaisanHdr.class);
			dataMap.setNgayTao(getDateTimeNow());
			dataMap.setTrangThai(Contains.TAO_MOI);
			dataMap.setNguoiTao(getUserName(request));

			// Add thong tin detail
			List<QlnvQdinhTaisanDtlReq> dtlReqList = objReq.getDetail();
			List<QlnvQdinhTaisanDtl> dtls = ObjectMapperUtils.mapAll(dtlReqList, QlnvQdinhTaisanDtl.class);
			dataMap.setChildren(dtls);

			QlnvQdinhTaisanHdr createCheck = qlnvQdinhTaisanHdrRepository.save(dataMap);

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

	@ApiOperation(value = "Xoá quyết định", response = List.class, produces = MediaType.APPLICATION_JSON_VALUE)
	@PostMapping(value = PathContains.URL_XOA, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<BaseResponse> delete(@RequestBody IdSearchReq idSearchReq) {
		BaseResponse resp = new BaseResponse();
		try {
			if (StringUtils.isEmpty(idSearchReq.getId()))
				throw new Exception("Xoá thất bại, không tìm thấy dữ liệu");

			Optional<QlnvQdinhTaisanHdr> qOptional = qlnvQdinhTaisanHdrRepository.findById(idSearchReq.getId());
			if (!qOptional.isPresent())
				throw new Exception("Không tìm thấy dữ liệu cần xoá");

			qlnvQdinhTaisanHdrRepository.delete(qOptional.get());

			resp.setStatusCode(EnumResponse.RESP_SUCC.getValue());
			resp.setMsg(EnumResponse.RESP_SUCC.getDescription());
		} catch (Exception e) {
			resp.setStatusCode(EnumResponse.RESP_FAIL.getValue());
			resp.setMsg(e.getMessage());
			log.error(e.getMessage());
		}

		return ResponseEntity.ok(resp);
	}

	@ApiOperation(value = "Tra cứu quyết định", response = List.class)
	@PostMapping(value = PathContains.URL_TRA_CUU, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<BaseResponse> selectAll(@RequestBody QlnvQdinhTaisanSearchReq objReq) {
		BaseResponse resp = new BaseResponse();
		try {
			int page = PaginationSet.getPage(objReq.getPaggingReq().getPage());
			int limit = PaginationSet.getLimit(objReq.getPaggingReq().getLimit());
			Pageable pageable = PageRequest.of(page, limit);

			Page<QlnvQdinhTaisanHdr> qhKho = qlnvQdinhTaisanHdrRepository
					.findAll(QlnvQdinhTaisanHdrSpecification.buildSearchQuery(objReq), pageable);

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

	@ApiOperation(value = "Cập nhật quyết định", response = List.class)
	@PostMapping(value = PathContains.URL_CAP_NHAT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponse> update(@Valid HttpServletRequest request,
			@RequestBody QlnvQdinhTaisanHdrReq objReq) {
		BaseResponse resp = new BaseResponse();
		try {
			if (StringUtils.isEmpty(objReq.getId()))
				throw new Exception("Sửa thất bại, không tìm thấy dữ liệu");

			Optional<QlnvQdinhTaisanHdr> qOptional = qlnvQdinhTaisanHdrRepository
					.findById(Long.valueOf(objReq.getId()));
			if (!qOptional.isPresent())
				throw new Exception("Không tìm thấy dữ liệu cần sửa");

			QlnvQdinhTaisanHdr dataDTB = qOptional.get();
			QlnvQdinhTaisanHdr dataMap = ObjectMapperUtils.map(objReq, QlnvQdinhTaisanHdr.class);

			updateObjectToObject(dataDTB, dataMap);

			dataDTB.setNgaySua(getDateTimeNow());
			dataDTB.setNguoiSua(getUserName(request));

//			 Add thong tin detail
			List<QlnvQdinhTaisanDtlReq> dtlReqList = objReq.getDetail();
			List<QlnvQdinhTaisanDtl> dtls = ObjectMapperUtils.mapAll(dtlReqList, QlnvQdinhTaisanDtl.class);
			dataDTB.setChildren(dtls);

			QlnvQdinhTaisanHdr createCheck = qlnvQdinhTaisanHdrRepository.save(dataDTB);

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

	@ApiOperation(value = "Lấy chi tiết thông tin quyết định", response = List.class)
	@GetMapping(value = PathContains.URL_CHI_TIET + "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<BaseResponse> detail(
			@ApiParam(value = "ID quyết định", example = "1", required = true) @PathVariable("id") String id) {
		BaseResponse resp = new BaseResponse();
		try {
			if (StringUtils.isEmpty(id))
				throw new UnsupportedOperationException("Không tồn tại bản ghi");

			Optional<QlnvQdinhTaisanHdr> qOptional = qlnvQdinhTaisanHdrRepository.findById(Long.parseLong(id));
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

	@ApiOperation(value = "Trình duyệt-01/Duyệt-02/Từ chối-03 hồ sơ kỹ thuật của vật tư", response = List.class)
	@PostMapping(value = PathContains.URL_PHE_DUYET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponse> approve(HttpServletRequest req, @Valid @RequestBody StatusReq stReq) {
		BaseResponse resp = new BaseResponse();
		try {
			if (StringUtils.isEmpty(stReq.getId()))
				throw new Exception("Không tìm thấy dữ liệu");

			Optional<QlnvQdinhTaisanHdr> entity = qlnvQdinhTaisanHdrRepository.findById(stReq.getId());
			if (!entity.isPresent())
				throw new Exception("Không tìm thấy dữ liệu");

			// Lay thong tin don vi quan ly
//			QlnvDmDonvi objDvi = getDvi(req);
//			if (!objDvi.getCapDvi().equals(Contains.CAP_CUC))
//				throw new UnsupportedOperationException("Người sử dụng không phải cấp chi cục để phê duyệt");

			String status = stReq.getTrangThai() + entity.get().getTrangThai();
			switch (status) {
			case Contains.CHO_DUYET + Contains.TAO_MOI:
				entity.get().setNguoiGuiDuyet(getUserName(req));
				entity.get().setNgayGuiDuyet(getDateTimeNow());
				break;
			case Contains.TU_CHOI + Contains.CHO_DUYET:
				entity.get().setNguoiPduyet(getUserName(req));
				entity.get().setNgayPduyet(getDateTimeNow());
				entity.get().setLdoTuchoi(stReq.getLyDo());
				break;
			case Contains.DUYET + Contains.CHO_DUYET:
				entity.get().setNguoiPduyet(getUserName(req));
				entity.get().setNgayPduyet(getDateTimeNow());
				break;
			default:
				throw new Exception("Phê duyệt không thành công");
			}

			entity.get().setTrangThai(stReq.getTrangThai());
			qlnvQdinhTaisanHdrRepository.save(entity.get());

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
