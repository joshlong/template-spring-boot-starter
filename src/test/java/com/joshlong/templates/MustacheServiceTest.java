package com.joshlong.templates;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Log4j2
@SpringBootTest
public class MustacheServiceTest {

	@Autowired
	private MustacheService service;

	@Value("classpath:templates/sample.mustache")
	private Resource sample;

	record Link(String href, String description) {
	}

	@Test
	public void convertToHtml() {

		var formatter = DateTimeFormatter.ISO_LOCAL_DATE.withLocale(Locale.US)
				.withZone(ZoneId.of(ZoneId.SHORT_IDS.get("PST")));

		var now = Instant.ofEpochSecond(1562812157);
		var context = Map.of("date", formatter.format(now), //
				"links", List.of(new Link("http://cnn.com", "A link to CNN"),
						new Link("http://microsoft.com", "a link to Microsoft")));

		var html = this.service.convertMustacheTemplateToHtml(this.sample, context);
		log.info("html: " + html);
		Assertions.assertTrue(html.contains("href=\"http://cnn.com\""));
		Assertions.assertTrue(html.contains("href=\"http://microsoft.com\""));
		Assertions.assertTrue(html.contains("2019-07-10"));
	}

}
