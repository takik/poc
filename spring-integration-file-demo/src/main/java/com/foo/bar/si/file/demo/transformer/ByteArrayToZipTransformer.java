package com.foo.bar.si.file.demo.transformer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.Deflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.file.FileHeaders;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

/**
 * Custom Zip transformer
 *
 * @author Tarak AKIK
 * @version 1.0
 *
 */

@Component
public class ByteArrayToZipTransformer {

	/**
	 * Customize the Zip compression level, defaults to Deflater.BEST_SPEED
	 * 
	 * @see {@link Deflater}
	 */
	private final int compressionLevel;

	// ~~~~~Constructors~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	public ByteArrayToZipTransformer() {
		this.compressionLevel = Deflater.BEST_SPEED;
	}

	public ByteArrayToZipTransformer(int compressionLevel) {
		this.compressionLevel = compressionLevel;
	}

	// ~~~~Methods~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	/**
	 * Compressing the input data.
	 */
	@Transformer
	public Message<byte[]> zip(final Message<byte[]> message)
			throws Exception {
		final String fileName = (String) message.getHeaders().get(
				FileHeaders.FILENAME);
		final InputStream is = new ByteArrayInputStream(message.getPayload());
		final ByteArrayOutputStream out = new ByteArrayOutputStream();
		final ZipOutputStream zipOutput = new ZipOutputStream(out);
		zipOutput.setLevel(compressionLevel);
		try {
			zipOutput.putNextEntry(new ZipEntry(fileName));
		} catch (IOException e) {
			throw new IllegalStateException(
					"Error while adding a new Zip File Entry (File name: '"
							+ fileName + "').", e);
		}
		IOUtils.copy(is, zipOutput);
		zipOutput.close();
		final Message<byte[]> zipMessage = MessageBuilder
				.withPayload(out.toByteArray())
				.setHeader(FileHeaders.FILENAME, fileName + ".zip")
				.copyHeadersIfAbsent(message.getHeaders()).build();
		return zipMessage;
	}
}
