/**
 * 业务公用实现。
 * 
 * 项目名：	wxreport-web
 * 文件名：	ExcelHelper.java
 * 模块说明：
 * 修改历史：
 * 2016-6-12 - xiepingping - 创建。
 */
package com.hd123.hema.store.web.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author xiepingping
 * 
 */
public class ExcelHelper {

  /** Excel 2003 */
  private final static String XLS = "xls";
  /** Excel 2007 */
  private final static String XLSX = "xlsx";
  /** 分隔符 */
  public final static String SEPARATOR = "∑";

  /**
   * 由Excel文件的Sheet导出至List
   * 
   * @param file
   * @return
   */
  public static List<String> exportListFromExcel(MultipartFile file) throws IOException {
    return exportListFromExcel(file.getInputStream(),
        FilenameUtils.getExtension(file.getOriginalFilename()), 0);
  }

  /**
   * 由Excel文件的Sheet导出至List
   * 
   * @param file
   * @param sheetNum
   * @return
   */
  public static List<String> exportListFromExcel(MultipartFile file, int sheetNum)
      throws IOException {
    if (file == null)
      throw new IllegalArgumentException("文件不存在");
    String name = file.getOriginalFilename();
    long size = file.getSize();
    if ((name == null || name.equals("")) && size == 0)
      throw new IllegalArgumentException("文件不合法");

    return exportListFromExcel(file.getInputStream(),
        FilenameUtils.getExtension(file.getOriginalFilename()), sheetNum);
  }

  /**
   * 由Excel文件的Sheet导出至List
   * 
   * @param file
   * @param sheetNum
   *          第几个Sheet
   * @return
   */
  public static List<String> exportListFromExcel(File file, int sheetNum) throws IOException {
    if (file == null)
      throw new IllegalArgumentException("文件不存在");
    String name = file.getName();
    long size = file.length();
    if ((name == null || name.equals("")) && size == 0)
      throw new IllegalArgumentException("文件不合法");

    return exportListFromExcel(new FileInputStream(file),
        FilenameUtils.getExtension(file.getName()), sheetNum);
  }

  /**
   * 由Excel流的Sheet导出至List
   * 
   * @param is
   * @param extensionName
   * @param sheetNum
   * @return
   * @throws IOException
   */
  public static List<String> exportListFromExcel(InputStream is, String extensionName, int sheetNum)
      throws IOException {

    Workbook workbook = null;

    if (extensionName.toLowerCase().equals(XLS)) {
      workbook = new HSSFWorkbook(is);
    } else if (extensionName.toLowerCase().equals(XLSX)) {
      workbook = new XSSFWorkbook(is);
    } else {
      throw new IllegalArgumentException("不支持的Excel文件");
    }

    return exportListFromExcel(workbook, sheetNum);
  }

  /**
   * 由指定的Sheet导出至List
   * 
   * @param workbook
   * @param sheetNum
   * @return
   * @throws IOException
   */
  private static List<String> exportListFromExcel(Workbook workbook, int sheetNum) {

    Sheet sheet = workbook.getSheetAt(sheetNum);
    if (sheet.getLastRowNum() == 0 && sheet.getRow(0) == null)
      return new ArrayList<String>();

    // 解析公式结果
    FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();

    List<String> list = new ArrayList<String>();

    int minRowIx = sheet.getFirstRowNum() + 1;
    int maxRowIx = sheet.getLastRowNum();
    for (int rowIx = minRowIx; rowIx <= maxRowIx; rowIx++) {
      Row row = sheet.getRow(rowIx);
      StringBuilder sb = new StringBuilder();

      short minColIx = row.getFirstCellNum();
      short maxColIx = row.getLastCellNum();
      for (short colIx = minColIx; colIx <= maxColIx; colIx++) {
        Cell cell = row.getCell(new Integer(colIx));
        CellValue cellValue = evaluator.evaluate(cell);
        if (cellValue == null) {
          continue;
        }
        // 经过公式解析，最后只存在Boolean、Numeric和String三种数据类型，此外就是Error了
        // 其余数据类型，根据官方文档，完全可以忽略http://poi.apache.org/spreadsheet/eval.html
        switch (cellValue.getCellType()) {
        case Cell.CELL_TYPE_BOOLEAN:
          sb.append(SEPARATOR + cellValue.getBooleanValue());
          break;
        case Cell.CELL_TYPE_NUMERIC:
          // 这里的日期类型会被转换为数字类型，需要判别后区分处理
          if (DateUtil.isCellDateFormatted(cell)) {
            sb.append(SEPARATOR + cell.getDateCellValue());
          } else {
            sb.append(SEPARATOR + NumberToTextConverter.toText(cell.getNumericCellValue()));
          }
          break;
        case Cell.CELL_TYPE_STRING:
          sb.append(SEPARATOR + cellValue.getStringValue());
          break;
        case Cell.CELL_TYPE_FORMULA:
          break;
        case Cell.CELL_TYPE_BLANK:
          break;
        case Cell.CELL_TYPE_ERROR:
          break;
        default:
          break;
        }
      }
      list.add(sb.toString());
    }
    return list;
  }

  /**
   * 按实体字段分割字符串
   * 
   * @param strs 实体字符串
   * @return
   */
  public static String[] substring(String strs) {
    if (StringUtils.isEmpty(strs))
      throw new IllegalArgumentException("传入参数不能为空");

    return strs.substring(1).split(ExcelHelper.SEPARATOR);
  }

  public static void main(String[] args) {
    String path = "D:\\Java\\wssh\\wxreport\\trunk\\wxreport-web\\src\\main\\java\\com\\hd123\\wms\\wxreport\\web\\test\\1.xlsx";
    List<String> list = null;
    try {
      list = ExcelHelper.exportListFromExcel(new File(path), 0);
      for (String content : list)
        System.out.println(content);
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
  }
}