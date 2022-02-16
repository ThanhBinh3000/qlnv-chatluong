package com.tcdt.qlnvchatluong.controller;

import java.util.ArrayList;
import java.util.Date;
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
import com.tcdt.qlnvchatluong.repository.QlnvHsktRepository;
import com.tcdt.qlnvchatluong.request.IdSearchReq;
import com.tcdt.qlnvchatluong.request.object.QlnvHsktBbktDtlReq;
import com.tcdt.qlnvchatluong.request.object.QlnvHsktBbktHdrReq;
import com.tcdt.qlnvchatluong.request.object.QlnvHsktReq;
import com.tcdt.qlnvchatluong.request.object.StatusReq;
import com.tcdt.qlnvchatluong.request.search.QlnvHsktSearchReq;
import com.tcdt.qlnvchatluong.response.BaseResponse;
import com.tcdt.qlnvchatluong.secification.QlnvHsktSpecification;
import com.tcdt.qlnvchatluong.table.FileDKemJoinHsktBbktDtl;
import com.tcdt.qlnvchatluong.table.QlnvHskt;
import com.tcdt.qlnvchatluong.table.QlnvHsktBbGiaomau;
import com.tcdt.qlnvchatluong.table.QlnvHsktBbLaymau;
import com.tcdt.qlnvchatluong.table.QlnvHsktBbktDtl;
import com.tcdt.qlnvchatluong.table.QlnvHsktBbktHdr;
import com.tcdt.qlnvchatluong.table.QlnvHsktBbktNquan;
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
@RequestMapping(value = PathContains.QL_CHAT_LUONG_HANG + PathContains.HO_SO_KY_THUAT)
@Api(tags = "Quản lý hồ sơ kỹ thuật của vật tư")
public class QlnvHsktController extends BaseController {
	@Autowired
	private QlnvHsktRepository qlnvHsktRepository;

	@ApiOperation(value = "Tạo mới hồ sơ kỹ thuật của vật tư", response = List.class)
	@PostMapping(value = PathContains.URL_TAO_MOI, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<BaseResponse> insert(@Valid HttpServletRequest request, @RequestBody QlnvHsktReq objReq) {
		BaseResponse resp = new BaseResponse();
		try {
			// Add thong tin hdr
			QlnvHskt dataMap = new ModelMapper().map(objReq, QlnvHskt.class);
			dataMap.setSoHso(getUUID(Contains.HO_SO));
			dataMap.setNgayTao(getDateTimeNow());
			dataMap.setTrangThai(Contains.TAO_MOI);
			dataMap.setNguoiTao(getUserName(request));

			// Add thong tin detail BB kiem tra HSKT
			if (objReq.getDetail() != null) {
				List<QlnvHsktBbktHdrReq> dtlReqListLv1 = objReq.getDetail();
				List<QlnvHsktBbktHdr> detailListLv1 = new ArrayList<QlnvHsktBbktHdr>();
				for (QlnvHsktBbktHdrReq dtlReqLv1 : dtlReqListLv1) {
					QlnvHsktBbktHdr detailLv1 = ObjectMapperUtils.map(dtlReqLv1, QlnvHsktBbktHdr.class);
					if (dtlReqLv1.getDetail() != null) {
						List<QlnvHsktBbktDtlReq> dtlReqListLv2 = dtlReqLv1.getDetail();
						List<QlnvHsktBbktDtl> detailListLv2 = new ArrayList<QlnvHsktBbktDtl>();
						for (QlnvHsktBbktDtlReq dtlReqLv2 : dtlReqListLv2) {
							QlnvHsktBbktDtl detailLv2 = ObjectMapperUtils.map(dtlReqLv2, QlnvHsktBbktDtl.class);
							List<FileDKemJoinHsktBbktDtl> fileDinhKemList = new ArrayList<FileDKemJoinHsktBbktDtl>();
							if (dtlReqLv2.getFileDinhKems() != null) {
								fileDinhKemList = ObjectMapperUtils.mapAll(dtlReqLv2.getFileDinhKems(),
										FileDKemJoinHsktBbktDtl.class);
								fileDinhKemList.forEach(f -> {
									f.setDataType(QlnvHsktBbktDtl.TABLE_NAME);
									f.setCreateDate(new Date());
								});
							}
							detailLv2.setChildren(fileDinhKemList);
							detailListLv2.add(detailLv2);
						}
						detailLv1.setChildren(detailListLv2);
					}

					detailListLv1.add(detailLv1);
				}
				dataMap.setChildren(detailListLv1);
			}

			// Add thong tin detail BB giao mau
			if (objReq.getDetail1() != null) {
				List<QlnvHsktBbGiaomau> dtls1 = ObjectMapperUtils.mapAll(objReq.getDetail1(), QlnvHsktBbGiaomau.class);
				dataMap.setChildren1(dtls1);
			}

			// Add thong tin detail BB lay mau
			if (objReq.getDetail2() != null) {
				List<QlnvHsktBbLaymau> dtls2 = ObjectMapperUtils.mapAll(objReq.getDetail2(), QlnvHsktBbLaymau.class);
				dataMap.setChildren2(dtls2);
			}

			// Add thong tin detail BB kiem tra ngoai quan
			if (objReq.getDetail3() != null) {
				List<QlnvHsktBbktNquan> dtls3 = ObjectMapperUtils.mapAll(objReq.getDetail3(), QlnvHsktBbktNquan.class);
				dataMap.setChildren3(dtls3);
			}

			QlnvHskt createCheck = qlnvHsktRepository.save(dataMap);

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

	@ApiOperation(value = "Xoá thông tin hồ sơ kỹ thuật của vật tư", response = List.class, produces = MediaType.APPLICATION_JSON_VALUE)
	@PostMapping(value = PathContains.URL_XOA, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<BaseResponse> delete(@RequestBody IdSearchReq idSearchReq) {
		BaseResponse resp = new BaseResponse();
		try {
			if (StringUtils.isEmpty(idSearchReq.getId()))
				throw new Exception("Xoá thất bại, không tìm thấy dữ liệu");

			Optional<QlnvHskt> qOptional = qlnvHsktRepository.findById(idSearchReq.getId());
			if (!qOptional.isPresent())
				throw new Exception("Không tìm thấy dữ liệu cần xoá");

			qlnvHsktRepository.delete(qOptional.get());

			resp.setStatusCode(EnumResponse.RESP_SUCC.getValue());
			resp.setMsg(EnumResponse.RESP_SUCC.getDescription());
		} catch (Exception e) {
			resp.setStatusCode(EnumResponse.RESP_FAIL.getValue());
			resp.setMsg(e.getMessage());
			log.error(e.getMessage());
		}

		return ResponseEntity.ok(resp);
	}

	@ApiOperation(value = "Tra cứu hồ sơ kỹ thuật của vật tư", response = List.class)
	@PostMapping(value = PathContains.URL_TRA_CUU, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<BaseResponse> selectAll(@RequestBody @Valid QlnvHsktSearchReq objReq) {
		BaseResponse resp = new BaseResponse();
		try {
			int page = PaginationSet.getPage(objReq.getPaggingReq().getPage());
			int limit = PaginationSet.getLimit(objReq.getPaggingReq().getLimit());
			Pageable pageable = PageRequest.of(page, limit);

			Page<QlnvHskt> qhKho = qlnvHsktRepository.findAll(QlnvHsktSpecification.buildSearchQuery(objReq), pageable);

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

	@ApiOperation(value = "Cập nhật hồ sơ kỹ thuật của vật tư", response = List.class)
	@PostMapping(value = PathContains.URL_CAP_NHAT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponse> update(@Valid HttpServletRequest request, @RequestBody QlnvHsktReq objReq) {
		BaseResponse resp = new BaseResponse();
		try {
			if (StringUtils.isEmpty(objReq.getId()))
				throw new Exception("Sửa thất bại, không tìm thấy dữ liệu");

			Optional<QlnvHskt> qOptional = qlnvHsktRepository.findById(Long.valueOf(objReq.getId()));
			if (!qOptional.isPresent())
				throw new Exception("Không tìm thấy dữ liệu cần sửa");

			objReq.setSoHso(null);
			QlnvHskt dataDTB = qOptional.get();
			QlnvHskt dataMap = ObjectMapperUtils.map(objReq, QlnvHskt.class);

			updateObjectToObject(dataDTB, dataMap);

			dataDTB.setNgaySua(getDateTimeNow());
			dataDTB.setNguoiSua(getUserName(request));

			// Add thong tin detail BB kiem tra HSKT
			if (objReq.getDetail() != null) {
				List<QlnvHsktBbktHdrReq> dtlReqListLv1 = objReq.getDetail();
				List<QlnvHsktBbktHdr> detailListLv1 = new ArrayList<QlnvHsktBbktHdr>();
				for (QlnvHsktBbktHdrReq dtlReqLv1 : dtlReqListLv1) {
					QlnvHsktBbktHdr detailLv1 = ObjectMapperUtils.map(dtlReqLv1, QlnvHsktBbktHdr.class);
					if (dtlReqLv1.getDetail() != null) {
						List<QlnvHsktBbktDtlReq> dtlReqListLv2 = dtlReqLv1.getDetail();
						List<QlnvHsktBbktDtl> detailListLv2 = new ArrayList<QlnvHsktBbktDtl>();
						for (QlnvHsktBbktDtlReq dtlReqLv2 : dtlReqListLv2) {
							QlnvHsktBbktDtl detailLv2 = ObjectMapperUtils.map(dtlReqLv2, QlnvHsktBbktDtl.class);
							List<FileDKemJoinHsktBbktDtl> fileDinhKemList = new ArrayList<FileDKemJoinHsktBbktDtl>();
							if (dtlReqLv2.getFileDinhKems() != null) {
								fileDinhKemList = ObjectMapperUtils.mapAll(dtlReqLv2.getFileDinhKems(),
										FileDKemJoinHsktBbktDtl.class);
								fileDinhKemList.forEach(f -> {
									f.setDataType(QlnvHsktBbktDtl.TABLE_NAME);
									f.setCreateDate(new Date());
								});
							}
							detailLv2.setChildren(fileDinhKemList);
							detailListLv2.add(detailLv2);
						}
						detailLv1.setChildren(detailListLv2);
					}

					detailListLv1.add(detailLv1);
				}
				dataDTB.setChildren(detailListLv1);
			}

			// Add thong tin detail BB giao mau
			if (objReq.getDetail1() != null) {
				List<QlnvHsktBbGiaomau> dtls1 = ObjectMapperUtils.mapAll(objReq.getDetail1(), QlnvHsktBbGiaomau.class);
				dataDTB.setChildren1(dtls1);
			}

			// Add thong tin detail BB lay mau
			if (objReq.getDetail2() != null) {
				List<QlnvHsktBbLaymau> dtls2 = ObjectMapperUtils.mapAll(objReq.getDetail2(), QlnvHsktBbLaymau.class);
				dataDTB.setChildren2(dtls2);
			}

			// Add thong tin detail BB kiem tra ngoai quan
			if (objReq.getDetail3() != null) {
				List<QlnvHsktBbktNquan> dtls3 = ObjectMapperUtils.mapAll(objReq.getDetail3(), QlnvHsktBbktNquan.class);
				dataDTB.setChildren3(dtls3);
			}

			QlnvHskt createCheck = qlnvHsktRepository.save(dataDTB);

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

	@ApiOperation(value = "Lấy chi tiết thông tin hồ sơ kỹ thuật của vật tư", response = List.class)
	@GetMapping(value = PathContains.URL_CHI_TIET + "/{ids}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<BaseResponse> detail(
			@ApiParam(value = "ID hồ sơ kỹ thuật của vật tư", example = "1", required = true) @PathVariable("ids") String ids) {
		BaseResponse resp = new BaseResponse();
		try {
			if (StringUtils.isEmpty(ids))
				throw new UnsupportedOperationException("Không tồn tại bản ghi");

			Optional<QlnvHskt> qOptional = qlnvHsktRepository.findById(Long.valueOf(Long.parseLong(ids)));
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
	public ResponseEntity<BaseResponse> updateStatus(HttpServletRequest req, @Valid @RequestBody StatusReq stReq) {
		BaseResponse resp = new BaseResponse();
		try {
			if (StringUtils.isEmpty(stReq.getId()))
				throw new Exception("Không tìm thấy dữ liệu");

			Optional<QlnvHskt> qHoach = qlnvHsktRepository.findById(Long.valueOf(stReq.getId()));
			if (!qHoach.isPresent())
				throw new Exception("Không tìm thấy dữ liệu");

			// Lay thong tin don vi quan ly
			QlnvDmDonvi objDvi = getDvi(req);
			if (!objDvi.getCapDvi().equals(Contains.CAP_CUC))
				throw new UnsupportedOperationException("Người sử dụng không phải cấp chi cục để phê duyệt");

			String status = stReq.getTrangThai() + qHoach.get().getTrangThai();
			switch (status) {
			case Contains.CHO_DUYET + Contains.TAO_MOI:
				qHoach.get().setNguoiGuiDuyet(getUserName(req));
				qHoach.get().setNgayGuiDuyet(getDateTimeNow());
				break;
			case Contains.TU_CHOI + Contains.CHO_DUYET:
				qHoach.get().setNguoiPduyet(getUserName(req));
				qHoach.get().setNgayPduyet(getDateTimeNow());
				qHoach.get().setLdoTuchoi(stReq.getLyDo());
				break;
			case Contains.DUYET + Contains.CHO_DUYET:
				qHoach.get().setNguoiPduyet(getUserName(req));
				qHoach.get().setNgayPduyet(getDateTimeNow());
				break;
			default:
				throw new Exception("Phê duyệt không thành công");
			}

			qHoach.get().setTrangThai(stReq.getTrangThai());
			qlnvHsktRepository.save(qHoach.get());

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