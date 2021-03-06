package com.inossem.template.impl;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;
import com.inossem.core.Common;
import com.inossem.core.Constant;
import com.inossem.core.PrintBaseCopy;
import com.inossem.core.PrintFileUtil;
import com.inossem.core.QRcodeUtil;
import com.inossem.template.CopyTemplate;

public class CWPrintTaskImpl extends PrintBaseCopy implements CopyTemplate {

	private static final byte DOWNTASK = (byte) 34; // 下架

	@SuppressWarnings("unchecked")
	@Override
	public String mdPrint(List<Map> dataList, String type) throws Exception {

		Map<String, Object> header = (Map<String, Object>) dataList.get(0).get("header");
		List<Map<String, Object>> item = (List<Map<String, Object>>) dataList.get(0).get("list");

		// 获取模板
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		String filePath = "";
		if (Byte.parseByte(String.valueOf(header.get("receiptType"))) == DOWNTASK) {
			filePath = request.getServletContext().getRealPath(Constant.TEMPLATE_LABEL_DOWNTASK);
		} else {
			filePath = request.getServletContext().getRealPath(Constant.TEMPLATE_LABEL_PROTRANS);
		}
		// 读取模板
		HSSFWorkbook hssInputExeclFile = new HSSFWorkbook(new FileInputStream(filePath));
		File f = new File(filePath);
		POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(f));
		HSSFWorkbook wb = new HSSFWorkbook(fs);
		// 读取sheet内容
		HSSFSheet sheet = wb.getSheetAt(0);
		// 自动换行
		HSSFCellStyle cellStyle = wb.createCellStyle();
		cellStyle.setWrapText(true);

		String path = this.getOutPutFileTablePath();
		// 输出文件
		String OutXlsxFile = path + Constant.OUTPUTFILE_TYPE_XLS;
		String OutPdfFile = path + Constant.OUTPUTFILE_TYPE_PDF;
		// 二维码路径
		String imgPath = this.getOutPutQRcodeFilePath() + Constant.OUTPUTFILE_TYPE_PNG;

		
		/*if(item.size()<6){
			
		}*/
		// 条形码
		// JbarcodeUtil2.encode(("1313323"), 4200, 900, imgPath);
		// 二维码
		QRcodeUtil.zxingCodeCreate(String.valueOf(header.get("stock_task_code")), 400, 400, imgPath, "png");
		ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
		// 加载图片
		BufferedImage bufferImg = ImageIO.read(new File(imgPath));
		ImageIO.write(bufferImg, "jpg", byteArrayOut);
		HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
		/*
		 * dx1 dy1 起始单元格中的x,y坐标.
		 * 
		 * dx2 dy2 结束单元格中的x,y坐标
		 * 
		 * col1,row1 指定起始的单元格，下标从0开始
		 * 
		 * col2,row2 指定结束的单元格 ，下标从0开始
		 */
		HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 0, 0, (short) 10, 1, (short) 11, 3);
		// 插入图片
		patriarch.createPicture(anchor, wb.addPicture(byteArrayOut.toByteArray(), HSSFWorkbook.PICTURE_TYPE_PNG));

		// 在相应的单元格进行赋值--header
		// 根据类型判断上下架
		if (Byte.parseByte(String.valueOf(header.get("receiptType"))) == DOWNTASK) {

			sheet.getRow(4).getCell(1).setCellValue(String.valueOf("发出工厂：" + header.get("fty_name")));
			sheet.getRow(4).getCell(6).setCellValue(String.valueOf(header.get("driver")));
			sheet.getRow(4).getCell(10).setCellValue(String.valueOf(header.get("stock_task_code")));// 作业单号
			sheet.getRow(5).getCell(1).setCellValue(String.valueOf("发出库房：" + header.get("location_name")));
			sheet.getRow(5).getCell(6).setCellValue(String.valueOf(header.get("vehicle")));
			sheet.getRow(5).getCell(10).setCellValue(String.valueOf(header.get("create_time")));// 创建日期
			sheet.getRow(6).getCell(1).setCellValue("交货单号：" + String.valueOf(header.get("receipt_code")));// 交货单号
			sheet.getRow(6).getCell(6).setCellValue(String.valueOf(header.get("receive_name")));// 客户名称
			sheet.getRow(6).getCell(9).setCellValue("单据类型："+String.valueOf(header.get("receipt_type_name")));// 单据类型
			sheet.getRow(7).getCell(1).setCellValue(String.valueOf("备注说明：" + header.get("headremark")));
		}

		// item
		int rowNum = 10;
		double reqnum = 0;
		double qty = 0;
		for (int i = 0; i < item.size(); i++) {
			if (rowNum != 10) {
				Common.copyRow(wb, sheet.getRow(10), sheet.createRow(rowNum), true);
			}
			sheet.getRow(10 + i).getCell(1).setCellValue(String.valueOf(i + 1));
			sheet.getRow(10 + i).getCell(2).setCellValue(String.valueOf(item.get(i).get("specs_model")));
			sheet.getRow(10 + i).getCell(3).setCellValue(String.valueOf(item.get(i).get("package_type_name")));
			sheet.getRow(10 + i).getCell(4).setCellValue(String.valueOf(item.get(i).get("reqnum")));
			reqnum += Double.parseDouble(String.valueOf(item.get(i).get("reqnum")));
			sheet.getRow(10 + i).getCell(5).setCellValue(String.valueOf(item.get(i).get("qty")));
			qty += Double.parseDouble(String.valueOf(item.get(i).get("qty")));
			sheet.getRow(10 + i).getCell(6).setCellValue(String.valueOf(item.get(i).get("production_batch")));
			sheet.getRow(10 + i).getCell(7).setCellValue(String.valueOf(item.get(i).get("erp_batch")));
			sheet.getRow(10 + i).getCell(8).setCellValue(String.valueOf(item.get(i).get("num")));
			sheet.getRow(10 + i).getCell(9).setCellValue(String.valueOf(item.get(i).get("position")));
			sheet.getRow(10 + i).getCell(10).setCellValue(String.valueOf(item.get(i).get("itemremark")));
			rowNum++;
		}
		Common.copyRow(wb, sheet.getRow(11), sheet.createRow(rowNum), true);
		sheet.getRow(rowNum).getCell(1).setCellValue("合计");
		sheet.getRow(rowNum).getCell(4).setCellValue(reqnum);
		sheet.getRow(rowNum).getCell(5).setCellValue(qty);

		Common.copyRow(wb, sheet.getRow(13), sheet.createRow(rowNum + 2), true);
		sheet.getRow(rowNum + 2).getCell(2).setCellValue(String.valueOf("理货员：" + header.get("tally_clerk")));// 理货员
		sheet.getRow(rowNum + 2).getCell(4).setCellValue(String.valueOf("叉车工：" + header.get("forklift_worker")));// 叉车工
		sheet.getRow(rowNum + 2).getCell(8).setCellValue(String.valueOf("搬运工：" + header.get("remover")));// 搬运工

		Common.copyRow(wb, sheet.getRow(14), sheet.createRow(rowNum + 3), true);
		Common.copyRow(wb, sheet.getRow(14), sheet.createRow(rowNum + 4), true);
		sheet.getRow(rowNum + 4).getCell(1).setCellValue("以下为出库下架单的附本：");
		/*
		 * 2复联
		 * 
		 */
		int lastRowNum = item.size() + 15;
		// sheet.setRowBreak(lastRowNum);

		for (int i = 0; i < lastRowNum - 4; i++) {
			Common.copyRow(wb, sheet.getRow(i + 3), sheet.createRow(lastRowNum + i), true);
			// Common.copyRow(wb, sheet.getRow(i), sheet.createRow(lastRowNum * 2 + i),
			// true);
		}

		sheet.setRowBreak(item.size() * 2 + 25);
		// 设置打印区域
		wb.setPrintArea(0, 0, 10, 0, sheet.getLastRowNum());

		// 修改模板内容导出新模板
		FileOutputStream out = new FileOutputStream(OutXlsxFile);
		wb.write(out);
		out.close();

		// 转换pdf
		OpenOfficeConnection connection = new SocketOpenOfficeConnection(8100);
		connection.connect();
		DocumentConverter converter = new OpenOfficeDocumentConverter(connection);
		File inputFile = new File(OutXlsxFile);
		File outputFile = new File(OutPdfFile);
		converter.convert(inputFile, outputFile);
		connection.disconnect();

		if (null != String.valueOf(header.get("print_name"))
				&& !(String.valueOf(header.get("print_name")).equals(""))) {
			PrintFileUtil.printFileAction(OutXlsxFile, String.valueOf(header.get("print_name")));
		}

		// 截取文件名
		/*
		 * String[] as = OutPdfFile.split(File.separator); String fileName =
		 * as[as.length-1];
		 * 
		 * return fileName;
		 */
		return OutPdfFile.substring(OutPdfFile.lastIndexOf(File.separator) + 1);

	}

	@Override
	public HSSFWorkbook makeExcel(HSSFWorkbook hssInputExcelFile, List<Map> dataList, String type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPath() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getQRPath() {
		// TODO Auto-generated method stub
		return null;
	}

}
