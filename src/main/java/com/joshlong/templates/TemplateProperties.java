package com.joshlong.templates;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("templates")
public record TemplateProperties(String charset) {
}