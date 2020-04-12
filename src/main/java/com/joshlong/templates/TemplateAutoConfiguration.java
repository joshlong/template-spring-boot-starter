package com.joshlong.templates;

import com.samskivert.mustache.Mustache;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import java.nio.charset.Charset;

@Configuration
@EnableConfigurationProperties (TemplateProperties.class)
public class TemplateAutoConfiguration {

	private final Charset charset;

	public TemplateAutoConfiguration(TemplateProperties templateProperties) {
		var charset = templateProperties.getCharset() ;
		this.charset = !StringUtils.hasText(charset) ? Charset.defaultCharset() : Charset.forName(charset);
	}

	@Bean
	MarkdownService markdownService() {
		return new MarkdownService(Parser.builder().build(), HtmlRenderer.builder().build());
	}

	@Bean
	MustacheService mustacheService(Mustache.Compiler compiler) {
		return new MustacheService(compiler, this.charset);
	}

}
