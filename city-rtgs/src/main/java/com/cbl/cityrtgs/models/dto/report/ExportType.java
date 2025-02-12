package com.cbl.cityrtgs.models.dto.report;

import io.swagger.annotations.ApiModel;

@ApiModel
public enum ExportType {
  PDF("pdf", "application/pdf"),
  EXCEL("xlsx", "application/octet-stream");

  private final String extension;
  private final String contentType;

  ExportType(String extension, String contentType) {
    this.extension = extension;
    this.contentType = contentType;
  }

  public String getExtension() {
    return "." + extension;
  }

  public String getContentType() {
    return contentType;
  }
}
