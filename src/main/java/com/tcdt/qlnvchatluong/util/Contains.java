package com.tcdt.qlnvchatluong.util;

import java.util.Map;

public class Contains {
	public static final String FORMAT_DATE_STR = "dd/MM/yyyy";
	public static final String FORMAT_DATE_TIME_STR = "dd/MM/yyyy HH:mm:ss";

	// Trang thai response
	public static final int RESP_SUCC = 0;
	public static final int RESP_FAIL = 1;

	public static final String TYPE_USER_BACKEND = "BE";
	public static final String TYPE_USER_FRONTEND = "FE";

	// Trang thai phe duyet
	public static final String TAO_MOI = "00";
	public static final String CHO_DUYET = "01";
	public static final String DUYET = "02";
	public static final String TU_CHOI = "03";
	public static final String HUY = "04";
	public static final String TONG_HOP = "05";
	public static final String CCUC_DUYET = "06";
	public static final String CUC_DUYET = "07";
	public static final String TCUC_DUYET = "08";
	public static final String TK_DUYET = "09";// Trang thai trung gian, thu kho phe duyet
	public static final String KTV_DUYET = "10";// Trang thai trung gian, ky thuat vien phe duyet
	public static final String KTT_DUYET = "11";// Trang thai trung gian, ke toan truong phe duyet

	// Loai nhap xuat
	public static final String LOAI_NHAP = "N";
	public static final String LOAI_XUAT = "X";

	// Cap don vi
	public static final String CAP_TONG_CUC = "1";
	public static final String CAP_CUC = "2";
	public static final String CAP_CHI_CUC = "3";

	// Dinh nghia code gen key
	public static final String QUYET_DINH = "QD";
	public static final String QUYET_DINH_DC = "DC";
	public static final String HOP_DONG = "HD";
	public static final String PHIEU_NX = "PH";
	public static final String BANG_KE = "BK";
	public static final String BIEN_BAN = "BB";
	public static final String HO_SO = "HS";

	// Loai bien ban kiem tra chat luong
	public static final String BB_KTCL_HANG = "00";
	public static final String BB_KTCL_VAT_TU = "01";
	public static final String BB_KTCL_HU_HONG = "02";

	// Hop Dong: Loai tai san
	public static final String HD_CONG_CU_DUNG_CU = "00";

	// Tinh trang tai san
	public static final String SU_DUNG_DUOC = "Sử dụng được";
	public static final String DA_HONG = "Đã hỏng";

	// Trang thai dinh muc
	public static final String TT_DMUC_APDUNG = "00";// ap dung
	public static final String TT_DMUC_DUNG = "01";// dung ap dung

	// Loai dinh muc
	public static final String DINHMUC_NHAP = "00";
	public static final String DINHMUC_XUAT = "01";
	public static final String DINHMUC_BQUAN = "02";

	public static final Map<String, String> mpLoaiDmuc;
	static {
		mpLoaiDmuc = Maps.<String, String>buildMap().put(Contains.DINHMUC_NHAP, "Nhập")
				.put(Contains.DINHMUC_XUAT, "Xuất").put(Contains.DINHMUC_BQUAN, "Bảo quản").get();
	}

	public static String getLoaiDmuc(String key) {
		return Contains.mpLoaiDmuc.get(key);
	}
}
