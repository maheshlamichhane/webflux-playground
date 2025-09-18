package com.vinsguru.webflux_playground.sec08.dto;

import java.util.UUID;

public record UploadResponse(UUID confirmatinId,Long productsCount) {
}
