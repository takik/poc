package com.foo.bar.si.file.demo.transformer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.file.FileHeaders;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import au.com.bytecode.opencsv.CSVWriter;


@Component
public class XlsxByteArray2CsvTransformer {

	
	@Transformer
	public Message<byte[]> toCsv(final Message<byte[]> message) throws Exception {

		String fileName = (String) message.getHeaders().get(
				FileHeaders.FILENAME);
		
		InputStream input=new ByteArrayInputStream(message.getPayload());
		XSSFWorkbook xssfWork = new XSSFWorkbook(input);
		XSSFSheet sheet = xssfWork.getSheetAt(0);
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
		
		CSVWriter writer = new CSVWriter(new OutputStreamWriter(baos), ';',CSVWriter.NO_QUOTE_CHARACTER);
		
		 String[] ligne=null;

		for (Row row : sheet) {
			ligne=new  String[row.getLastCellNum()];
			for (int cn = 0; cn < row.getLastCellNum(); cn++) {
				// If the cell is missing from the file, generate a blank one
				Cell cell = row.getCell(cn, Row.CREATE_NULL_AS_BLANK);
				ligne[cn]=cell.toString();
			}
			writer.writeNext(ligne);
		}
		
		writer.flush();
		writer.close();
		
		
		final Message<byte[]> csvMessage = MessageBuilder
				.withPayload(baos.toByteArray())
				.setHeader(FileHeaders.FILENAME, fileName + ".csv")
				.copyHeadersIfAbsent(message.getHeaders()).build();
		return csvMessage;
	}
}