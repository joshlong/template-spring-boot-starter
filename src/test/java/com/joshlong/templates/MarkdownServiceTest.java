package com.joshlong.templates;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;

@Log4j2
@SpringBootTest
public class MarkdownServiceTest {

	@Autowired
	private MarkdownService markdownService;

	@Value("classpath:/templates/sample.md")
	private Resource resource;

	@Test
	public void markdown() throws Exception {
		String markdownHtml = this.markdownService.convertMarkdownTemplateToHtml(this.resource);
		log.debug(markdownHtml);
		Assertions.assertTrue(markdownHtml.contains("<li>Now is the time</li>"));
		Assertions.assertTrue(markdownHtml.contains("<h2>A Subheader</h2>"));
		Assertions.assertTrue(markdownHtml.contains("<a href=\"http://slashdot.org\">nonsense</a>"));
		Assertions.assertTrue(markdownHtml.contains("<h1>A Simple Markdown Document</h1>"));
	}

}