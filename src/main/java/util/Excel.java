package util;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class Excel {

    public static HashMap<Object,Object> devolverMapaExcel(String path) throws IOException {
        HashMap<Object,Object> mapaDatos = new HashMap<>();
        FileInputStream excelFile = new FileInputStream(new File(path));
        Workbook workbook = new XSSFWorkbook(excelFile);
        Sheet datatypeSheet = workbook.getSheetAt(0);
        Iterator<Row> iteratorFila = datatypeSheet.iterator();
        //siempre pasaremos dos columnas no más <key, value> cada fila del excel
        while (iteratorFila.hasNext()) {
            Row currentRow = iteratorFila.next();
            String key   = devolverValorCelda(currentRow.getCell(0),"");
            String value = "";
            if(key.compareTo("zipCode") == 0 || key.compareTo("birthDay") ==0 || key.compareTo("birthYear")==0){
                value = devolverValorCelda(currentRow.getCell(1),"INTEGER");
            }else{
                value =  devolverValorCelda(currentRow.getCell(1),"");
            }

            mapaDatos.put(key,value);
        }

        return mapaDatos;
    }

    public static String devolverValorCelda(Cell celda, String key){
        String valor = "";
        if (celda == null){
            return valor;
        }
        if(celda.getCellType().compareTo(CellType.STRING)==0){
            valor =   celda.getStringCellValue();
        }else{
            if(celda.getCellType().compareTo(CellType.NUMERIC)==0 && key.compareTo("INTEGER")==0){
                valor = ""+(int)celda.getNumericCellValue();
            }
        }
        return valor;
    }

    public  static void crearExcel(String path,List<String> cadenas) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Salida");
        for(int i = 0; i<cadenas.size();i++){
            Row row = sheet.createRow(i);
            Cell cell = row.createCell(i);
            cell.setCellValue(cadenas.get(i));
        }

        FileOutputStream outputStream = new FileOutputStream(path);
        workbook.write(outputStream);
        workbook.close();
    }
}
