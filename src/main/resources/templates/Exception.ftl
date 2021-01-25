package ${packagePath}.exception;

import ${packagePath}.exception.enums.${modelClass}ExceptionEnum;

public class ${modelClass}Exception extends RuntimeException {

    private Integer code;

    public ${modelClass}Exception(${modelClass}ExceptionEnum ${modelName}ExceptionEnum) {
        super(${modelName}ExceptionEnum.getErrMsg());
        this.code = ${modelName}ExceptionEnum.getErrCode();
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
