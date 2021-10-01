package com.util;

public enum Operation {
  lessThan("<"),
  moreThan(">"),
  equal("=");

  private String code;

  Operation(String str) {
    this.code = str;
  }

  public String getCode() {
    return this.code;
  }
}
