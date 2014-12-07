package com.foo.bar.si.file.demo.service.activators;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.file.FileHeaders;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import au.com.bytecode.opencsv.CSVReader;

@Component
public class CsvFileReader {

	Logger logger = LoggerFactory.getLogger(CsvFileReader.class);

	@ServiceActivator
	public void printCsv(final Message<byte[]> message)
			throws IOException {

		String fileName = (String) message.getHeaders().get(
				FileHeaders.FILENAME);

		ByteArrayInputStream bais = new ByteArrayInputStream(
				message.getPayload());

		logger.info("Reading Csv Byte from file {} :", fileName);

		CSVReader reader = new CSVReader(new InputStreamReader(bais));

		List<String[]> lignes = reader.readAll();
		reader.close();

		for (String[] s : lignes) {
			for (String str : s)
				System.out.println(str);
		}

	}

}
