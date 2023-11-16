package br.com.dv.account.dto.accounting;

import br.com.dv.account.enums.StatusMessage;

public record PaymentUploadResponse(StatusMessage status) {
}
