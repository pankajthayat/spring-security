package com.practice.demoapp.modal.response;

public class OperationStatusModal {

    private String operationResult;
    private String oprationName;

    public String getOperationResult() {
        return operationResult;
    }

    public void setOperationResult(String operationResult) {
        this.operationResult = operationResult;
    }

    public String getOprationName() {
        return oprationName;
    }

    public void setOprationName(String oprationName) {
        this.oprationName = oprationName;
    }

    public OperationStatusModal(String operationResult, String oprationName) {
        this.operationResult = operationResult;
        this.oprationName = oprationName;
    }

    public OperationStatusModal() {
    }
}
