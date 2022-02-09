package com.tcdt.qlnvchatluong.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tcdt.qlnvchatluong.enums.EnumResponse;
import com.tcdt.qlnvchatluong.repository.QlnvBbanNtkklHdrRepository;
import com.tcdt.qlnvchatluong.request.IdSearchReq;
import com.tcdt.qlnvchatluong.request.TableInWord;
import com.tcdt.qlnvchatluong.request.object.QlnvBbanNtkklDtlReq;
import com.tcdt.qlnvchatluong.request.object.QlnvBbanNtkklHdrReq;
import com.tcdt.qlnvchatluong.request.object.StatusReq;
import com.tcdt.qlnvchatluong.request.search.QlnvBbanNtkklSearchReq;
import com.tcdt.qlnvchatluong.response.BaseResponse;
import com.tcdt.qlnvchatluong.secification.QlnvBbanNtkklHdrSpecification;
import com.tcdt.qlnvchatluong.table.QlnvBbanNtkklDtl;
import com.tcdt.qlnvchatluong.table.QlnvBbanNtkklHdr;
import com.tcdt.qlnvchatluong.table.catalog.QlnvDmDonvi;
import com.tcdt.qlnvchatluong.util.Contains;
import com.tcdt.qlnvchatluong.util.Doc4jUtils;
import com.tcdt.qlnvchatluong.util.Maps;
import com.tcdt.qlnvchatluong.util.MoneyConvert;
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
@RequestMapping(value = PathContains.QL_CHAT_LUONG_HANG + PathContains.BB_NGHIEMTHU_KLOT)
@Api(tags = "Biên bản nghiệm thu kê lót, bảo quản ban đầu nhập lương thực DTQG")
public class QlnvBbanNtkklController extends BaseController {
	@Autowired
	private QlnvBbanNtkklHdrRepository qlnvBbanNtkklHdrRepository;

	@ApiOperation(value = "Tạo mới biên bản nghiệm thu kê lót", response = List.class)
	@PostMapping(value = PathContains.URL_TAO_MOI, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<BaseResponse> insert(@Valid HttpServletRequest request,
			@RequestBody QlnvBbanNtkklHdrReq objReq) {
		BaseResponse resp = new BaseResponse();
		try {
			// Add thong tin hdr
			QlnvBbanNtkklHdr dataMap = new ModelMapper().map(objReq, QlnvBbanNtkklHdr.class);
			dataMap.setSoBban(getUUID(Contains.BIEN_BAN));
			dataMap.setNgayTao(getDateTimeNow());
			dataMap.setTrangThai(Contains.TAO_MOI);
			dataMap.setNguoiTao(getUserName(request));

			// Add thong tin detail
			List<QlnvBbanNtkklDtlReq> dtlReqList = objReq.getDetail();
			List<QlnvBbanNtkklDtl> dtls = ObjectMapperUtils.mapAll(dtlReqList, QlnvBbanNtkklDtl.class);
			dataMap.setChildren(dtls);

			QlnvBbanNtkklHdr createCheck = qlnvBbanNtkklHdrRepository.save(dataMap);

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

	@ApiOperation(value = "Xoá thông tin biên bản nghiệm thu kê lót", response = List.class, produces = MediaType.APPLICATION_JSON_VALUE)
	@PostMapping(value = PathContains.URL_XOA, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<BaseResponse> delete(@RequestBody IdSearchReq idSearchReq) {
		BaseResponse resp = new BaseResponse();
		try {
			if (StringUtils.isEmpty(idSearchReq.getId()))
				throw new Exception("Xoá thất bại, không tìm thấy dữ liệu");

			Optional<QlnvBbanNtkklHdr> qOptional = qlnvBbanNtkklHdrRepository.findById(idSearchReq.getId());
			if (!qOptional.isPresent())
				throw new Exception("Không tìm thấy dữ liệu cần xoá");

			qlnvBbanNtkklHdrRepository.delete(qOptional.get());

			resp.setStatusCode(EnumResponse.RESP_SUCC.getValue());
			resp.setMsg(EnumResponse.RESP_SUCC.getDescription());
		} catch (Exception e) {
			resp.setStatusCode(EnumResponse.RESP_FAIL.getValue());
			resp.setMsg(e.getMessage());
			log.error(e.getMessage());
		}

		return ResponseEntity.ok(resp);
	}

	@ApiOperation(value = "Tra cứu biên bản nghiệm thu kê lót", response = List.class)
	@PostMapping(value = PathContains.URL_TRA_CUU, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<BaseResponse> selectAll(@RequestBody QlnvBbanNtkklSearchReq objReq) {
		BaseResponse resp = new BaseResponse();
		try {
			int page = PaginationSet.getPage(objReq.getPaggingReq().getPage());
			int limit = PaginationSet.getLimit(objReq.getPaggingReq().getLimit());
			Pageable pageable = PageRequest.of(page, limit);

			Page<QlnvBbanNtkklHdr> qhKho = qlnvBbanNtkklHdrRepository
					.findAll(QlnvBbanNtkklHdrSpecification.buildSearchQuery(objReq), pageable);

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

	@ApiOperation(value = "Cập nhật biên bản nghiệm thu kê lót", response = List.class)
	@PostMapping(value = PathContains.URL_CAP_NHAT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponse> update(@Valid HttpServletRequest request,
			@RequestBody QlnvBbanNtkklHdrReq objReq) {
		BaseResponse resp = new BaseResponse();
		try {
			if (StringUtils.isEmpty(objReq.getId()))
				throw new Exception("Sửa thất bại, không tìm thấy dữ liệu");

			Optional<QlnvBbanNtkklHdr> qOptional = qlnvBbanNtkklHdrRepository.findById(Long.valueOf(objReq.getId()));
			if (!qOptional.isPresent())
				throw new Exception("Không tìm thấy dữ liệu cần sửa");

			objReq.setSoBban(null);
			QlnvBbanNtkklHdr dataDTB = qOptional.get();
			QlnvBbanNtkklHdr dataMap = ObjectMapperUtils.map(objReq, QlnvBbanNtkklHdr.class);

			updateObjectToObject(dataDTB, dataMap);

			dataDTB.setNgaySua(getDateTimeNow());
			dataDTB.setNguoiSua(getUserName(request));

			// Add thong tin detail
			List<QlnvBbanNtkklDtlReq> dtlReqList = objReq.getDetail();
			List<QlnvBbanNtkklDtl> dtls = ObjectMapperUtils.mapAll(dtlReqList, QlnvBbanNtkklDtl.class);
			dataDTB.setChildren(dtls);

			QlnvBbanNtkklHdr createCheck = qlnvBbanNtkklHdrRepository.save(dataDTB);

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

	@ApiOperation(value = "Lấy chi tiết thông tin biên bản nghiệm thu kê lót", response = List.class)
	@GetMapping(value = PathContains.URL_CHI_TIET + "/{ids}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<BaseResponse> detail(
			@ApiParam(value = "ID biên bản nghiệm thu kê lót", example = "1", required = true) @PathVariable("ids") String ids) {
		BaseResponse resp = new BaseResponse();
		try {
			if (StringUtils.isEmpty(ids))
				throw new UnsupportedOperationException("Không tồn tại bản ghi");

			Optional<QlnvBbanNtkklHdr> qOptional = qlnvBbanNtkklHdrRepository
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

	@ApiOperation(value = "Trình duyệt-01/Duyệt-02/Từ chối-03 biên bản nghiệm thu kê lót", response = List.class)
	@PostMapping(value = PathContains.URL_PHE_DUYET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BaseResponse> updateStatus(HttpServletRequest req, @Valid @RequestBody StatusReq stReq) {
		BaseResponse resp = new BaseResponse();
		try {
			if (StringUtils.isEmpty(stReq.getId()))
				throw new Exception("Không tìm thấy dữ liệu");

			Optional<QlnvBbanNtkklHdr> qHoach = qlnvBbanNtkklHdrRepository.findById(Long.valueOf(stReq.getId()));
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
				qHoach.get().setTkPduyet(getUserName(req));
				qHoach.get().setNgayTkPduyet(getDateTimeNow());
				qHoach.get().setLdoTuchoi(stReq.getLyDo());
				break;
			case Contains.TU_CHOI + Contains.TK_DUYET:
				qHoach.get().setKtvPduyet(getUserName(req));
				qHoach.get().setNgayKtvPduyet(getDateTimeNow());
				qHoach.get().setLdoTuchoi(stReq.getLyDo());
				break;
			case Contains.TU_CHOI + Contains.KTV_DUYET:
				qHoach.get().setKttPduyet(getUserName(req));
				qHoach.get().setNgayKttPduyet(getDateTimeNow());
				qHoach.get().setLdoTuchoi(stReq.getLyDo());
				break;
			case Contains.TU_CHOI + Contains.KTT_DUYET:
				qHoach.get().setNguoiPduyet(getUserName(req));
				qHoach.get().setNgayPduyet(getDateTimeNow());
				qHoach.get().setLdoTuchoi(stReq.getLyDo());
				break;
			case Contains.TK_DUYET + Contains.CHO_DUYET:
				qHoach.get().setTkPduyet(getUserName(req));
				qHoach.get().setNgayTkPduyet(getDateTimeNow());
				break;
			case Contains.KTV_DUYET + Contains.TK_DUYET:
				qHoach.get().setKtvPduyet(getUserName(req));
				qHoach.get().setNgayKtvPduyet(getDateTimeNow());
				break;
			case Contains.KTT_DUYET + Contains.KTV_DUYET:
				qHoach.get().setKttPduyet(getUserName(req));
				qHoach.get().setNgayKttPduyet(getDateTimeNow());
				break;
			case Contains.DUYET + Contains.KTT_DUYET:
				qHoach.get().setNguoiPduyet(getUserName(req));
				qHoach.get().setNgayPduyet(getDateTimeNow());
				break;
			default:
				throw new Exception("Phê duyệt không thành công");
			}

			qHoach.get().setTrangThai(stReq.getTrangThai());
			qlnvBbanNtkklHdrRepository.save(qHoach.get());

			resp.setStatusCode(EnumResponse.RESP_SUCC.getValue());
			resp.setMsg(EnumResponse.RESP_SUCC.getDescription());
		} catch (Exception e) {
			resp.setStatusCode(EnumResponse.RESP_FAIL.getValue());
			resp.setMsg(e.getMessage());
			log.error(e.getMessage());
		}
		return ResponseEntity.ok(resp);
	}

	@ApiOperation(value = "In biên bản nghiệm thu kê lót", response = List.class)
	@PostMapping(value = PathContains.URL_KET_XUAT, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public void export(@Valid @RequestBody IdSearchReq searchReq, HttpServletResponse response, HttpServletRequest req)
			throws Exception {
		String template = "/reports/BBAN_NGHIEMTHU_KLOT.docx";
		try {
			if (StringUtils.isEmpty(searchReq.getId()))
				throw new Exception("Không tìm thấy dữ liệu");

			Optional<QlnvBbanNtkklHdr> qPhieu = qlnvBbanNtkklHdrRepository.findById(searchReq.getId());
			if (!qPhieu.isPresent())
				throw new Exception("Không tìm thấy dữ liệu");

			ServletOutputStream dataOutput = response.getOutputStream();
			response.setContentType("application/octet-stream");
			response.addHeader("content-disposition",
					"attachment;filename=BBAN_NGHIEMTHU_KLOT_" + getDateTimeNow() + ".docx");

			// Add parameter to table
			List<QlnvBbanNtkklDtl> detailList = new ArrayList<QlnvBbanNtkklDtl>();
			if (qPhieu.get().getChildren() != null)
				detailList = qPhieu.get().getChildren();

			Map<TableInWord, List<Map<String, Object>>> tableMap = new HashMap<TableInWord, List<Map<String, Object>>>();
			List<Map<String, Object>> lstMapDetail = null;
			BigDecimal soLuong = BigDecimal.ZERO;
			BigDecimal thanhTien = BigDecimal.ZERO;
			BigDecimal soLuong2 = BigDecimal.ZERO;
			BigDecimal thanhTien2 = BigDecimal.ZERO;
			BigDecimal tongTien = BigDecimal.ZERO;
			if (detailList.size() > 0) {
				lstMapDetail = new ArrayList<Map<String, Object>>();
				Map<String, Object> detailMap;
				for (int i = 0; i < detailList.size(); i++) {
					detailMap = Maps.<String, Object>buildMap().put("stt", i + 1)
							.put("noidung", detailList.get(i).getNoiDung())
							.put("dvtinh", detailList.get(i).getDviTinh())
							.put("soluong", detailList.get(i).getSluongTnam())
							.put("dongia", detailList.get(i).getDgiaTnam())
							.put("thanhtien", detailList.get(i).getTtienTnam())
							.put("soluong2", detailList.get(i).getSluongQtoan())
							.put("thanhtien2", detailList.get(i).getTtienQtoan())
							.put("tong", detailList.get(i).getTongTien()).get();
					lstMapDetail.add(detailMap);
					soLuong = Optional.ofNullable(detailList.get(i).getSluongTnam()).orElse(BigDecimal.ZERO)
							.add(soLuong);
					thanhTien = Optional.ofNullable(detailList.get(i).getTtienTnam()).orElse(BigDecimal.ZERO)
							.add(thanhTien);
					soLuong2 = Optional.ofNullable(detailList.get(i).getSluongQtoan()).orElse(BigDecimal.ZERO)
							.add(soLuong2);
					thanhTien2 = Optional.ofNullable(detailList.get(i).getTtienQtoan()).orElse(BigDecimal.ZERO)
							.add(thanhTien2);
					tongTien = Optional.ofNullable(detailList.get(i).getTongTien()).orElse(BigDecimal.ZERO)
							.add(tongTien);
				}
			}
			TableInWord tabWord = new TableInWord();
			tabWord.setTbNum(1);
			tabWord.setTbIndex(3);
			tableMap.put(tabWord, lstMapDetail);

			// Add gia tri bien string
			// TODO: Dang fix tam, sau nay co yeu cau cu the sua sau
			Map<String, String> mappings = Maps.<String, String>buildMap().put("param1", getDvi(req).getTenDvi())
					.put("param2", "").put("param3", qPhieu.get().getSoBban())
					.put("param4", getDateText(qPhieu.get().getNgayLap())).put("param5", "")
					.put("param6", qPhieu.get().getNguoiPduyet()).put("param7", qPhieu.get().getKttPduyet())
					.put("param8", qPhieu.get().getKtvPduyet()).put("param9", qPhieu.get().getTkPduyet())
					.put("param10", qPhieu.get().getTenHhoa())
					.put("param11", qPhieu.get().getMaLo() + "-" + qPhieu.get().getMaKho())
					.put("param12", qPhieu.get().getTkPduyet()).put("param13", qPhieu.get().getLhinhKho())
					.put("param14", qPhieu.get().getTichLuong().toString())
					.put("param15", qPhieu.get().getSluongTnhap().toString())
					.put("param16", qPhieu.get().getPthucBquan()).put("param17", qPhieu.get().getHthucKlot())
					.put("param18", qPhieu.get().getDmucGiao().toString()).put("param19", "")
					.put("tong1", soLuong.toString()).put("tong2", thanhTien.toString())
					.put("tong3", soLuong2.toString()).put("tong4", thanhTien2.toString())
					.put("tong5", tongTien.toString()).put("param20", thanhTien.toString())
					.put("param21", MoneyConvert.doctienBangChu(thanhTien.toPlainString(), ""))
					.put("param22", qPhieu.get().getKetLuan()).get();

			// save the docs
			Doc4jUtils.generateDocMutipleTable(template, mappings, tableMap, dataOutput);
			dataOutput.flush();
			dataOutput.close();
		} catch (Exception e) {
			log.error("In biên bản nghiệm thu kê lót", e);
			final Map<String, Object> body = new HashMap<>();
			body.put("statusCode", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			body.put("msg", e.getMessage());

			response.setContentType(MediaType.APPLICATION_JSON_VALUE);
			response.setCharacterEncoding("UTF-8");

			final ObjectMapper mapper = new ObjectMapper();
			mapper.writeValue(response.getOutputStream(), body);
		}
	}

}