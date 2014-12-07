/*
 * Copyright 2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.foo.bar.si.file.demo.starter;

import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import com.foo.bar.si.file.demo.context.SpringIntegrationFileDemoContext;


/**
 * Starts the Spring Context and will initialize the Spring Integration routes.
 *
 * @author Tarak AKIK
 * @since 1.0
 *
 */
public final class FileDemoStarter {

	private static final Logger LOGGER = LoggerFactory.getLogger(FileDemoStarter.class);

	private FileDemoStarter() { }

	/**
	 * Load the Spring Integration Application Context
	 *
	 * @param args - command line arguments
	 */
	public static void main(final String... args) {
		

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("\n========================================================="
					  + "\n                                                         "
					  + "\n    Welcome to Spring File Demo Integration!             "
					  + "\n                                                         "
					  + "\n    For more information please visit:                   "
					  + "\n    http://www.tarakakik.wordpress/spring-integration    "
					  + "\n                                                         "
					  + "\n=========================================================" );
		}

		final AbstractApplicationContext context =
				new  AnnotationConfigApplicationContext(SpringIntegrationFileDemoContext.class);

		context.registerShutdownHook();

		SpringIntegrationUtils.displayDirectories(context);

		final Scanner scanner = new Scanner(System.in);

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("\n========================================================="
					  + "\n                                                         "
					  + "\n    Please press 'q + Enter' to quit the application.    "
					  + "\n                                                         "
					  + "\n=========================================================" );
		}

		while (!scanner.hasNext("q")) {
			//Do nothing unless user presses 'q' to quit.
		}

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("Exiting application...bye.");
		}

		System.exit(0);

	}
}
